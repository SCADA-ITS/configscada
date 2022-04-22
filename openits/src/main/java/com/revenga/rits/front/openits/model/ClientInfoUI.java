package com.revenga.rits.front.openits.model;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class ClientInfoUI implements Serializable {

	private static final long serialVersionUID = 1783450175806606694L;
	
	String version;
	long upTime;
}

