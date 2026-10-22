package com.revenga.rits.back.service.graphql.mutation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.revenga.rits.back.front.core.model.vo.CommandElementErrorVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementPreconditionVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.ElementStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.PmCommandElementVO;
import com.revenga.rits.back.service.graphql.service.CommandService;
import com.revenga.rits.back.service.graphql.service.ElementService;

@Component
public class ElementMutation implements GraphQLMutationResolver {

	@Autowired
	private ElementService elementService;

	@Autowired
	private CommandService commandService;

	public Optional<ElementVO> createElement(final ElementVO element) {

		return elementService.create(element);
	}

	public Optional<ElementVO> cloneElement(final String elementId) {

		return elementService.clone(elementId);
	}

	public Optional<ElementVO> updateElement(final ElementVO element) {

		return elementService.update(element);
	}

	public Optional<ElementVO> deleteElement(final String elementId) {

		return elementService.delete(elementId);
	}
	
	public Boolean sendChangeElementState(List<ElementStateVO> elementStates) {
		
		return elementService.sendChangeElementState(elementStates);
	}
	

	//
	// Command
	//
	public List<CommandElementPreconditionVO> sendCommandElement(final List<CommandElementVO> commands) {
		
		return commandService.sendCommandElement(commands);
	}

	public List<CommandElementErrorVO> sendCommandElementSync(final List<CommandElementVO> commands) {
		
		return commandService.sendCommandElementSync(commands);
	}

	public List<CommandElementErrorVO> addCommandElement(final List<PmCommandElementVO> commands) {
		
		return commandService.addCommandElement(commands);
	}

	public List<CommandElementErrorVO> deleteCommandElement(final List<String> commandElementsId) {
		
		return commandService.deleteCommandElement(commandElementsId);
	}

	public List<CommandElementErrorVO> deleteCommandElementBySource(final String source) {
		
		return commandService.deleteCommandElementBySource(source);
	}
	
	public List<CommandElementErrorVO> disablePriority(final String elementId, final String priorityId) {
		
		return commandService.disablePriority(elementId, priorityId);
	}
	
	public List<CommandElementErrorVO> enablePriority(final String elementId, final String priorityId) {
		
		return commandService.enablePriority(elementId, priorityId);
	}
}