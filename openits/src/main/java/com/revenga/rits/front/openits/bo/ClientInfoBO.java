package com.revenga.rits.front.openits.bo;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.app.AppInfra;
import com.revenga.rits.front.openits.model.ClientInfoUI;
import com.revenga.rits.front.openits.model.ClientInfoUI.GitInfo;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ClientInfoBO {
	
	private static final String STATIC_FOLDER = "static";
	private static final String[] RESOURCE_EXTENSIONS = new String[]{".jpg", ".jpeg", ".mp3", ".avi", ".png", ".svg"};
	
	@Autowired
	AppInfra appInfra;

	public ClientInfoUI getInfo() {
		
		ClientInfoUI clientInfo = new ClientInfoUI();
		
		clientInfo.setVersion(appInfra.getVersion());
		
		String gitInfoStr = appInfra.getGitInfoStr();
		int lineOffset = 0;
		
		if (!StringUtils.isEmpty(gitInfoStr)) {
			
			try (Scanner scanner = new Scanner(gitInfoStr)) {
				
				String commit = null;
				String author = null;
				String date = null;
				String message = null;
				StringBuilder sbCommitMessage = new StringBuilder();
				
				while (scanner.hasNextLine()) {
					
					lineOffset++;
					
					String line = scanner.nextLine().trim();
					
					if (!StringUtils.isEmpty(line)) {
						
						if (lineOffset == GitInfo.LINE_COMMIT) {
							
							commit = line;
						}
						else if (lineOffset == GitInfo.LINE_AUTHOR) {
							
							author = line;
						}
						else if (lineOffset == GitInfo.LINE_DATE) {
							
							date = line;
						}
						else {

							sbCommitMessage.append(line);
						}
					}
				}
				
				message = sbCommitMessage.toString();
				
				clientInfo.createGitInfo(commit, author, date, message);
			}
		}
		
		clientInfo.setUpTime(ManagementFactory.getRuntimeMXBean().getUptime());
		
		return clientInfo;
	}
	
	public List<String> getResources(String dir) {
		
		List<String> fileList = new ArrayList<>();
		
		try {
			
			URL url;
			
			if (!StringUtils.isEmpty(dir)) {
			
				url = this.getClass().getClassLoader().getResource(STATIC_FOLDER + File.separator + URLDecoder.decode(dir, StandardCharsets.UTF_8));
			}
			else {
				
				url = this.getClass().getClassLoader().getResource(STATIC_FOLDER);
			}
			
			if (url != null) {
				
				File fileRoot = new File(url.toURI());
				
				if (fileRoot.isDirectory()) {
					
					try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(fileRoot.getAbsolutePath()))) {
						
						for (Path path : stream) {
							
							if (!Files.isDirectory(path) && Arrays.asList(RESOURCE_EXTENSIONS).stream()
									.filter(extension -> path.toString().toLowerCase().endsWith(extension)).findFirst().orElse(null) != null) {

								fileList.add(path.getFileName().toString());
							}
				        }
					}
				}
			}
			
		} catch (IOException | URISyntaxException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return fileList;
	}
}
