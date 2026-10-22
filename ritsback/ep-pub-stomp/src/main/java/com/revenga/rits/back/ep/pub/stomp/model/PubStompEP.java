package com.revenga.rits.back.ep.pub.stomp.model;

import java.io.Serializable;

import com.revenga.rits.back.adapter.lib.model.PubStompAdapterV1;

import lombok.Data;

@Data
public class PubStompEP implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3245843792497519829L;
		
	private String xmlAdapter;
	private PubStompAdapterV1 pubStompAdapter;
}
