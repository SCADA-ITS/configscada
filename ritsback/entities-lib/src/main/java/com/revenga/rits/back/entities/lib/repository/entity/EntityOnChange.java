package com.revenga.rits.back.entities.lib.repository.entity;

import java.util.Arrays;
import java.util.concurrent.Callable;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.command.Command;

import lombok.Getter;
import lombok.Setter;

public abstract class EntityOnChange implements Callable<Integer> {

	@Getter
	@Setter
	protected BasicElement basicElement;
	@Getter
	@Setter
	protected Integer action;
	protected Integer[] commandActionsAllowed = new Integer[] {Command.ACTION_CREATE, Command.ACTION_UPDATE, Command.ACTION_DELETE};
	protected Class<?> clazz;
	
	protected EntityOnChange(Class<?> clazz) {
		
		super();
		
		this.clazz = clazz;
	}
	
	@Override
	public Integer call() throws Exception {
		
		if (this.basicElement != null && this.basicElement.getClass().equals(clazz) && Arrays.stream(this.commandActionsAllowed).anyMatch(action::equals)) {
			
			if (action == Command.ACTION_CREATE) {
				
				create();
			}
			else if (action == Command.ACTION_UPDATE) {
				
				update();
			}
			else if (action == Command.ACTION_DELETE) {
				
				delete();
			}
		}
		
		return 0;
	}
	
	protected abstract void create();
	protected abstract void update();
	protected abstract void delete();
}
