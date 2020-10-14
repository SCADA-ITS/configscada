package com.revenga.rits.front.openits.app;

import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AppInfra {

	private static final String MAVEN_ARTIFACT = "openits";
	private static final String MAVEN_PACKAGE = "com.revenga.rits.front";
	
		
	private static final Pattern debugPattern = Pattern.compile("-Xdebug|jdwp");
	
	@Getter
	private final String version = getVer();
	
		
	public static boolean isDebugging() {
	    for (String arg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
	        if (debugPattern.matcher(arg).find()) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public AppInfra() {
		super();
				
		
	}
	
	//@PostConstruct
	public void start() throws InterruptedException {
		
		showSplash();
		
		initShutdownHook();
	}
	
	private void initShutdownHook() {

		Runtime.getRuntime().addShutdownHook(new Thread() {
			
			@Override
			public void run() {
				
				try {
					
					Thread.sleep(200);
					log.warn("Shutting down application ...");

					log.warn("The application has been closed");
					log.warn("Bye bye!");
				
				} catch (InterruptedException e) {
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
					Thread.currentThread().interrupt();
					
				} catch (Exception e) {
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		});
		
		log.info("Initialized shutdown handler success");
	}

	private void showSplash() {

		log.info("--------------------------------------------------------------------------------");
		log.info("");
		log.info("\tName            : " + MAVEN_ARTIFACT);
		log.info("\tVersion         : " + getVer());
		log.info("\tjdk.debug       : " + System.getProperty("jdk.debug"));  
		log.info("\tjava.vm.name    : " + System.getProperty("java.vm.name")); 
		log.info("\tjava.vm.vendor  : " + System.getProperty("java.vm.vendor"));
		log.info("\tjava.vm.version : " + System.getProperty("java.vm.version"));
		log.info("\tjava.vm.info    : " + System.getProperty("java.vm.info"));
		log.info("\tUser dir        : " + System.getProperty("user.dir"));
		int nClasspathEntry = 0;
		for (String classpath : System.getProperty("java.class.path").split(";")) {

			if (nClasspathEntry++ == 0) {

				log.info("\tClasspath       : " + classpath);
			} else {

				log.debug("\t            " + classpath);
			}

		}
		log.info("");
		log.info("--------------------------------------------------------------------------------");
	}

	private String getVer() {
		// Try to get version number from pom.xml (available in Eclipse)
		try {
			String className = getClass().getName();
			String classfileName = "/" + className.replace('.', '/') + ".class";
			URL classfileResource = getClass().getResource(classfileName);
			if (classfileResource != null) {
				Path absolutePackagePath = Paths.get(classfileResource.toURI()).getParent();
				int packagePathSegments = className.length() - className.replace(".", "").length();
				// Remove package segments from path, plus two more levels
				// for "target/classes", which is the standard location for
				// classes in Eclipse.
				Path path = absolutePackagePath;
				for (int i = 0, segmentsToRemove = packagePathSegments + 2; i < segmentsToRemove; i++) {
					path = path.getParent();
				}
				Path pom = path.resolve("pom.xml");
				try (InputStream is = Files.newInputStream(pom)) {
					Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
					doc.getDocumentElement().normalize();
					String version = (String) XPathFactory.newInstance().newXPath().compile("/project/version")
							.evaluate(doc, XPathConstants.STRING);
					if (version != null) {
						version = version.trim();
						if (!version.isEmpty()) {
							return version;
						}
					}
				}
			}
		} catch (Exception e) {
			// Ignore
		}

		// Try to get version number from maven properties in jar's META-INF
		try (InputStream is = getClass()
				.getResourceAsStream("/META-INF/maven/" + MAVEN_PACKAGE + "/" + MAVEN_ARTIFACT + "/pom.properties")) {
			if (is != null) {
				Properties p = new Properties();
				p.load(is);
				String version = p.getProperty("version", "").trim();
				if (!version.isEmpty()) {
					return version;
				}
			}
		} catch (Exception e) {
			// Ignore
		}

		// Fallback to using Java API to get version from MANIFEST.MF
		String version = null;
		Package pkg = getClass().getPackage();
		if (pkg != null) {
			version = pkg.getImplementationVersion();
			if (version == null) {
				version = pkg.getSpecificationVersion();
			}
		}
		version = version == null ? "" : version.trim();
		return version.isEmpty() ? "unknown" : version;
	}
}
