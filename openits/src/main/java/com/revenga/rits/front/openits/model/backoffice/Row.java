package com.revenga.rits.front.openits.model.backoffice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Row implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 3515110338135944787L;
	
	@Data
	public static class RowData implements Cloneable, Serializable {
		
		private static final long serialVersionUID = 1689896293999857137L;
		
		@JsonIgnore
		private Column column;
		
		private Integer columnId;
		private String value;
		private String label;
		
		public RowData(Column column, String value) {
			
			super();
			this.column = column;
			this.value = value;
			this.columnId = null;
			
			if (column != null) {
				
				this.columnId = column.getId();
			}
		}

		@Override
		public String toString() {
			return column.name + " = " + this.value;
		}
		
		@Override
		public Object clone() throws CloneNotSupportedException {
			
			return super.clone();
		}
	}
	
	private List<RowData> data = new ArrayList<>();
	
	private List<Attachment> images = new ArrayList<>();
	private List<Attachment> attachments = new ArrayList<>();
	
	@Override
	public Object clone() throws CloneNotSupportedException {

		Object objCloned = super.clone(); 
		
		if (!CollectionUtils.isEmpty(this.data)) {
			
			((Row) objCloned).setData(new ArrayList<>());
			
			for (RowData rowData : this.data) {
			
				((Row) objCloned).getData().add((RowData) rowData.clone());
			}
		}
		
		if (!CollectionUtils.isEmpty(this.images)) {
			
			((Row) objCloned).setImages(new ArrayList<>());
			
			for (Attachment image : this.images) {
			
				((Row) objCloned).getAttachments().add((Attachment) image.clone());
			}
		}
		
		if (!CollectionUtils.isEmpty(this.attachments)) {
			
			((Row) objCloned).setAttachments(new ArrayList<>());
			
			for (Attachment attachment : this.attachments) {
			
				((Row) objCloned).getAttachments().add((Attachment) attachment.clone());
			}
		}
		
		return objCloned;
	}
}
