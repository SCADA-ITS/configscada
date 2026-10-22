package com.revenga.rits.back.data.core.model.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChangeEntityRepositoryCommand extends Command {


	private static final long serialVersionUID = 5654279995244703002L;
	
	private List<Pair<BasicElement, Integer>> basicElementActions;

	public ChangeEntityRepositoryCommand() {

		super(Command.CHANGE_ENTITY_REPOSITORY);
	}

	public void add(BasicElement basicElement, Integer action) {

		if (basicElement != null && action != null) {

			if (basicElementActions == null) {

				basicElementActions = new ArrayList<>();
			}

			basicElementActions.add(new ImmutablePair<>(basicElement, action));
		}
	}
	
	public void add(ChangeEntityRepositoryCommand changeEntityRepositoryCommand) {

		if (changeEntityRepositoryCommand != null) {
			
			List<Pair<BasicElement, Integer>> basicElementActionsAdded = changeEntityRepositoryCommand.getBasicElementActions();
			
			if (!CollectionUtils.isEmpty(basicElementActionsAdded)) {
				
				if (basicElementActions == null) {

					basicElementActions = new ArrayList<>();
				}
				
				basicElementActions.addAll(basicElementActionsAdded);
			}
		}
	}
}
