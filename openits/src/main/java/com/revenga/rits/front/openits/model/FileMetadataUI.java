package com.revenga.rits.front.openits.model;

import java.io.Serializable;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
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
	private String author;
}
