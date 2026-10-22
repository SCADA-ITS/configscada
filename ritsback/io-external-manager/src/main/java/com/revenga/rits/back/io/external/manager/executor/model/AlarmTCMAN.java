package com.revenga.rits.back.io.external.manager.executor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AlarmTCMAN  {

	@JsonProperty("numero_cliente")
	private String clientAlias;
	
	@JsonProperty("codigoSICNS")
	private String sourceAlias;
	
	@JsonProperty("descripcion")
	private String alarmAlias;
	
	@JsonProperty("observaciones")
	private String alarmDescription;
	
	@JsonProperty("fechacreacion")
	private String createdAt;
	
	@JsonProperty("usuario_generador")
	private String userName;
	
	@JsonProperty("id_GIM")
	private Integer gimId;
	
	@JsonProperty("numero_GIM")
	private Integer gimNumber;
}
