package com.revenga.rits.back.manager.lib.app;

import java.io.File;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.revenga.rits.back.data.core.util.ArgumentProcessor;
import com.revenga.rits.back.manager.lib.service.ManagerService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AppInfra {

	private static final String MAVEN_PACKAGE = "com.revenga.rits.back";
	private static final String MAVEN_PROP_VERSION = "version";
	private String mavenArtifact;
			
	private static final Pattern debugPattern = Pattern.compile("-Xdebug|jdwp");
	
	private String version;
	private Class<?> clazz;
	
	@Autowired
	ManagerService managerService;
	
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
	
	public String getVersion() {
		
		if (StringUtils.isEmpty(this.version)) {
		
			this.version = getVer();
		}
		
		return this.version;
	}

	public void start() {
		
		start(null, null, null);
	}

	public void start(String mavenArtifact) {
		
		start(null, null, null);
	}
	
	public void start(String mavenArtifact, Callable<Integer> onBefore) {
				
		start(null, onBefore, null);
	}
	
	public void start(String mavenArtifact, Callable<Integer> onBefore, Callable<Integer> onAfter) {
		
		try {
			
			this.clazz = getCallerClass();
			this.mavenArtifact = getMavenArtifact();
			
			showSplash();
			
			initShutdownHook();
			
			String xmlAdapter = ArgumentProcessor.getInstance().getArgumentValue(ArgumentProcessor.ARG_XML_ADAPTER);
			
			if (!StringUtils.isEmpty(xmlAdapter)) {
				
				if (onBefore != null) {
				
					Instant instantInit = Instant.now();
					log.info("onBefore tasks...");
					onBefore.call();
					log.info("onBefore tasks done in " + Duration.between(instantInit, Instant.now()).toMillis()
							+ " ms.");
				}
			
				managerService.load(xmlAdapter);
				
				if (onAfter != null) {
					
					Instant instantInit = Instant.now();
					log.info("onAfter tasks...");
					onAfter.call();
					log.info("onAfter done in " + Duration.between(instantInit, Instant.now()).toMillis() + " ms.");
				}
			}
			else {
			
				log.error("XML Adapter file is empty. Please insert argument: --xml.adapter=adapter.xml");
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void initShutdownHook() {

		Runtime.getRuntime().addShutdownHook(new Thread() {
			
			@Override
			public void run() {
				
				try {
					
					Thread.sleep(200);
					
					if (log.isWarnEnabled()) {
					
						log.warn("Shutting down application ...");
					}
					else {
						
						System.out.println(mavenArtifact + " -> Shutting down application ...");
					}
					
					managerService.stop();
					
					if (log.isWarnEnabled()) {
					
						log.warn("The application has been closed");
						log.warn("Bye bye!");
					}
					else {
						
						System.out.println(mavenArtifact + " -> The application has been closed");
						System.out.println(mavenArtifact + " -> Bye bye!");
					}
					
				
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
		log.info("\tName            : " + mavenArtifact);
		log.info("\tVersion         : " + getVer() + " (" + (isDebugging() ? "debug" : "release") +  ")");
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
		
		// Try to get version number from maven properties in jar's META-INF
		try (InputStream is = this.clazz
				.getResourceAsStream("/META-INF/maven/" + MAVEN_PACKAGE + "/" + this.mavenArtifact + "/pom.properties")) {
			if (is != null) {
				Properties p = new Properties();
				p.load(is);
				String ver = p.getProperty(MAVEN_PROP_VERSION, "").trim();
				if (!ver.isEmpty()) {
					return ver;
				}
			}
		} catch (Exception e) {
			// Ignore
		}
		
		// Try to get version number from pom.xml (available in Eclipse)
		try {
			String className = this.clazz.getName();
			String classfileName = File.separator + className.replace('.', '/') + ".class";
			URL classfileResource = this.clazz.getResource(classfileName);
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
					String ver = (String) XPathFactory.newInstance().newXPath().compile("/project/version")
							.evaluate(doc, XPathConstants.STRING);
					if (ver != null) {
						ver = ver.trim();
						if (!ver.isEmpty()) {
							return ver;
						}
					}
				}
			}
		} catch (Exception e) {
			// Ignore
		}

		// Fallback to using Java API to get version from MANIFEST.MF
		String ver = null;
		Package pkg = this.clazz.getPackage();
		if (pkg != null) {
			ver = pkg.getImplementationVersion();
			if (ver == null) {
				ver = pkg.getSpecificationVersion();
			}
		}
		ver = ver == null ? "" : ver.trim();
		return ver.isEmpty() ? "unknown" : ver;
	}
	
	private String getMavenArtifact() {
		
		String[] classPaths = System.getProperty("java.class.path").split(":");
		
		if (classPaths.length == 1 && classPaths[0].endsWith(".jar")) {
			
			this.mavenArtifact = classPaths[0];
			int index = this.mavenArtifact.lastIndexOf(File.separator);
			
			if (index > -1) {
				
				this.mavenArtifact = this.mavenArtifact.substring(index + 1);
			}
			
			this.mavenArtifact = this.mavenArtifact.replace(".jar", "");
		}
		else {
			
			String userDir = System.getProperty("user.dir");
    		Path path = Paths.get(userDir);
    		this.mavenArtifact = path.getFileName().toString();
		}
		
        return this.mavenArtifact;
	}
	
	private Class<?> getCallerClass() {
		
		Class<?> callerClass = null;
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		
		for (int i = 1; i < stElements.length; i++) {
		
			StackTraceElement ste = stElements[i];
			
			if (!ste.getClassName().equals(this.getClass().getName())
					&& ste.getClassName().indexOf("java.lang.Thread") != 0) {
			
				try {
					
					callerClass = Class.forName(ste.getClassName());
					
				} catch (ClassNotFoundException e) {
					
					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}
		
		return callerClass;
	}
}
