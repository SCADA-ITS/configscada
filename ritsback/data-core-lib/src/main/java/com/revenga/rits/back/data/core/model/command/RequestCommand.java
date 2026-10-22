package com.revenga.rits.back.data.core.model.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.data.core.model.Element;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RequestCommand extends Command {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 531627585750483529L;
	
	public static final int REQ_STATE = 1;
	public static final int REQ_MEASURES = 2; 
	public static final int REQ_ALARMS = 3;
	
	private List<RequestCommandElement> requestCommandElements;
	
	private Integer requestTypeId;
	
	@Data
	public static class RequestCommandElement implements Serializable {
	
		/**
		 * 
		 */
		private static final long serialVersionUID = -6421157303431584788L;
		
		Integer requestTypeId;
		Long elementTypeId;
		Long elementId;
	}
	
	public void add(Element element, int requestType) {
		
		if (element != null) {
		
			if (requestCommandElements == null) {
				
				requestCommandElements = new ArrayList<>();
			}
			
			RequestCommandElement requestCommandElement = new RequestCommandElement();
			
			requestCommandElement.setElementTypeId(element.getElementTypeId());
			requestCommandElement.setElementId(element.getId());
			requestCommandElement.setRequestTypeId(requestType);
			
			requestCommandElements.add(requestCommandElement);
		}
	}
	
	public void add(RequestCommandElement requestCommandElement) {
		
		if (requestCommandElement != null) {
		
			if (requestCommandElements == null) {
				
				requestCommandElements = new ArrayList<>();
			}
			
			requestCommandElements.add(requestCommandElement);
		}
	}

	public RequestCommand() {

		super(Command.REQUEST_COMMAND);
	}
}
