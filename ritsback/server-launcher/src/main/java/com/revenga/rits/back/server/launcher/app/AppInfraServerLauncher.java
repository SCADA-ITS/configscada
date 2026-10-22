package com.revenga.rits.back.server.launcher.app;

import java.io.File;
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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.revenga.rits.back.data.core.util.ArgumentProcessor;
import com.revenga.rits.back.manager.lib.service.ManagerService;
import com.revenga.rits.back.server.launcher.service.ServerLauncherManager;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AppInfraServerLauncher {

	public static final String ARG_XML_SERVER_LAUNCHER_ADAPTER = "xml.server_launcher_adapter";
	private static final String MAVEN_PACKAGE = "com.revenga.rits.back";

	private String mavenArtifact = "Unknown";

	private static final Pattern debugPattern = Pattern.compile("-Xdebug|jdwp");

	@Autowired
	ManagerService managerService;
	
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

	public AppInfraServerLauncher() {
		super();
	}

	// @PostConstruct
	public void start(String mavenArtifact) {

		this.mavenArtifact = mavenArtifact;

		showSplash();

		initShutdownHook();

		String xmlServerLauncherAdapter = ArgumentProcessor.getInstance()
				.getArgumentValue(ARG_XML_SERVER_LAUNCHER_ADAPTER);

		if (!StringUtils.isEmpty(xmlServerLauncherAdapter)) {

			ServerLauncherManager.getInstance().load(xmlServerLauncherAdapter);

		} else {

			log.error(
					"XML Adapter file is empty. Please insert argument: --xml.server_launcher_adapter=server_launcher_adapter.xml");
		}

		String xmlAdapter = ArgumentProcessor.getInstance().getArgumentValue(ArgumentProcessor.ARG_XML_ADAPTER);

		if (!StringUtils.isEmpty(xmlAdapter)) {

			managerService.load(xmlAdapter);

		} else {

			log.error("XML Adapter file is empty. Please insert argument: --xml.adapter=adapter.xml");
		}

	}

	private void initShutdownHook() {

		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {

				try {

					Thread.sleep(200);
					log.warn("Shutting down application ...");

					ServerLauncherManager.getInstance().stop();

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
		log.info("\tName            : " + mavenArtifact);
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
			String classfileName = File.separator + className.replace('.', '/') + ".class";
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

		// Try to get version number from maven properties in jar's META-INF
		try (InputStream is = getClass().getResourceAsStream(
				"/META-INF/maven/" + MAVEN_PACKAGE + "/" + this.mavenArtifact + "/pom.properties")) {
			if (is != null) {
				Properties p = new Properties();
				p.load(is);
				String ver = p.getProperty("version", "").trim();
				if (!ver.isEmpty()) {
					return ver;
				}
			}
		} catch (Exception e) {
			// Ignore
		}

		// Fallback to using Java API to get version from MANIFEST.MF
		String ver = null;
		Package pkg = getClass().getPackage();
		if (pkg != null) {
			ver = pkg.getImplementationVersion();
			if (ver == null) {
				ver = pkg.getSpecificationVersion();
			}
		}
		ver = ver == null ? "" : ver.trim();
		return ver.isEmpty() ? "unknown" : ver;
	}
}
