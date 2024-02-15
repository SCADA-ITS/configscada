package com.revenga.rits.front.openits.util;

import static javax.naming.directory.SearchControls.SUBTREE_SCOPE;

import java.io.Serializable;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.front.openits.app.AppInfra;
import com.revenga.rits.lib.encrypt.util.AES;

import lombok.Data;

public class LDAPManager {

	private static final String LDAP_URL = AppInfra.getProperty("ldap.url");
	private static final String LDAP_ACCOUNT = AppInfra.getProperty("ldap.account");
	private static final String LDAP_PASSWORD = AppInfra.getProperty("ldap.password");
	private static final String LDAP_BASE_DN = AppInfra.getProperty("ldap.baseDN");
	private static final String LDAP_ATTRIB_LOGIN = AppInfra.getProperty("ldap.attrib.login");
	private static final String LDAP_ATTRIB_FIRSTNAME = AppInfra.getProperty("ldap.attrib.firstname");
	private static final String LDAP_ATTRIB_LASTNAME = AppInfra.getProperty("ldap.attrib.lastname");
	private static final String LDAP_ATTRIB_EMAIL = AppInfra.getProperty("ldap.attrib.email");
	private static final String LDAP_USER_DEFAULT_ROLE_ID = AppInfra.getProperty("ldap.user.default_role_id");
	private static final String LDAP_USER_DEFAULT_LOCALE_ID = AppInfra.getProperty("ldap.user.default_locale_id");

	@Data
	public static class LDAPUser implements Serializable {

		private static final long serialVersionUID = -6063868720080666540L;
		
		private String dn;
		private String login;
		private String firstName;
		private String lastName;
		private String email;
		private String roleId;
		private String localeId;
		
		public LDAPUser(String dn, String login, String firstName, String lastName, String email, String roleId,
				String localeId) {
			
			super();
			
			init(dn, login, firstName, lastName, email);
			
			this.roleId = roleId;
			this.localeId = localeId;
		}
		
		public LDAPUser(String dn, String login, String firstName, String lastName, String email) {
			
			super();
			
			init(dn, login, firstName, lastName, email);
			
			this.roleId = LDAP_USER_DEFAULT_ROLE_ID;
			this.localeId = LDAP_USER_DEFAULT_LOCALE_ID;
		}
		
		private void init(String dn, String login, String firstName, String lastName, String email) {
			
			this.dn = dn;
			this.login = login;
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
		}
	}

	
	private LDAPManager() {}

	public static LDAPUser login(String login, String password) {

		LDAPUser ldapUser = null;

		try {

			LdapContext ctxAdmin = auth(LDAP_ACCOUNT, AES.decrypt(LDAP_PASSWORD));

			if (ctxAdmin != null) {

				SearchControls controls = new SearchControls();
				controls.setSearchScope(SUBTREE_SCOPE);
				controls.setReturningAttributes(new String[] { LDAP_ATTRIB_LOGIN, LDAP_ATTRIB_FIRSTNAME,
						LDAP_ATTRIB_LASTNAME, LDAP_ATTRIB_EMAIL });

				NamingEnumeration<SearchResult> answer;

				answer = ctxAdmin.search(LDAP_BASE_DN, "(objectClass=*)", controls);

				while (answer.hasMore() && ldapUser == null) {

					SearchResult result = answer.next();

					Attributes attr = result.getAttributes();

					if (attr.get(LDAP_ATTRIB_LOGIN) != null) {

						if (StringUtils.equalsIgnoreCase(login, (String) attr.get(LDAP_ATTRIB_LOGIN).get())) {

							String dnUser = result.getNameInNamespace();

							if (!StringUtils.isEmpty(dnUser)) {

								if (auth(dnUser, password) != null) {

									String firstName = (attr.get(LDAP_ATTRIB_FIRSTNAME) != null
											? (String) attr.get(LDAP_ATTRIB_FIRSTNAME).get()
											: null);
									String lastName = (attr.get(LDAP_ATTRIB_LASTNAME) != null
											? (String) attr.get(LDAP_ATTRIB_LASTNAME).get()
											: null);
									String email = (attr.get(LDAP_ATTRIB_EMAIL) != null
											? (String) attr.get(LDAP_ATTRIB_EMAIL).get()
											: null);

									ldapUser = new LDAPUser(dnUser, login, firstName, lastName, email);
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {

			// Nothing to do
		}

		return ldapUser;
	}

	private static LdapContext auth(String dn, String password) {

		LdapContext ctxAdmin = null;

		try {

			Hashtable<String, String> props = new Hashtable<>();

			props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			props.put(Context.PROVIDER_URL, LDAP_URL);
			props.put(Context.SECURITY_AUTHENTICATION, "simple");
			props.put(Context.SECURITY_PRINCIPAL, dn);

			if (!StringUtils.isEmpty(password)) {

				props.put(Context.SECURITY_CREDENTIALS, password);
			}

			ctxAdmin = new InitialLdapContext(props, null);

		} catch (Exception e) {

			// Nothing to do
		}

		return ctxAdmin;
	}
}
