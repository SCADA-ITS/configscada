package com.revenga.rits.front.openits.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class FileMetadataUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6972145682177721906L;
	
	public static final String FILE_NAME = "metadata.info";
	
	private String fileName;
	private String alias;
	private String description;
}
