package com.revenga.rits.front.openits.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ResourcesUtil {

	private ResourcesUtil() {

		throw new IllegalStateException(this.getClass().getSimpleName());

	}
	
	private static String getResourcesPath() {
		
		String resourcesPath = null;
		
		try {
			
			URL res = ResourcesUtil.class.getClassLoader().getResource("application.properties");
			
			File file = Paths.get(res.toURI()).toFile();
			
			resourcesPath = file.getParent();
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resourcesPath;
	}

	private static String getPath() {

		return getResourcesPath() + "/";
	}
	
	public static InputStream getResource(String name) {
		
		try {
			
			return new FileInputStream(getPath() + name);
			
		} catch (FileNotFoundException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		return null;
	}
	
	public static File getFile(String name) {
	
		return new File(getPath() + name);
	}
	
	public static BasicFileAttributes getFileAttributes(String name) {
		
		Path path = Paths.get(getFile(name).getAbsolutePath());
		
		try {
			
			return Files.readAttributes(path, BasicFileAttributes.class);
			
		} catch (IOException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return null;
	}
	
	public static String getPath(String name) {
		
		return getPath() + name;
	}
	
	public static List<InputStream> getResources(String relativePath) {
		
		List<InputStream> listInputStream = new ArrayList<>();
		
		try (Stream<Path> walk = Files.walk(Paths.get(getPath() + relativePath))) {

			List<String> result = walk.filter(Files::isRegularFile)
					.map(x -> x.toString()).collect(Collectors.toList());
			
			Collections.sort(result);
			
			result.forEach(e -> {
				
				try {
			
					listInputStream.add(new FileInputStream(e));
					
				} catch (FileNotFoundException e1) {
					
					log.error(e1.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e1));
				}
			});

		} catch (IOException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

		}
		
		return listInputStream;
	}
	
	public static List<String> getFiles(String relativePath) {
		
		List<String> result = Collections.emptyList();

		try (Stream<Path> walk = Files.walk(Paths.get(getPath() + relativePath))) {

			result = walk.filter(Files::isRegularFile).map(x -> x.toFile().getName()).collect(Collectors.toList());

		} catch (IOException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return result;
	}
}
