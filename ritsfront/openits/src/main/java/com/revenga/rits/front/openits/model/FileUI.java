package com.revenga.rits.front.openits.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class FileUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4790231710841649853L;
	
	private String fileName;
	private String filePath;
	private String fileExtension;
	private Long size;
	private Long lastModified;
	
	private FileMetadataUI metadata;
}
