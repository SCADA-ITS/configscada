package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.CommandElementError;
import com.revenga.rits.back.data.core.model.CommandElementPrecondition;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.front.core.model.helper.CommandElementErrorHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementErrorVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementPreconditionVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class CommandElementErrorGraphql extends CommandElementErrorVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3908796455858736265L;
	private CommandElementError commandElementError;

	public CommandElementErrorGraphql(final CommandElementError commandElementError) {

		CommandElementErrorHelper.assign(commandElementError, this);
		this.commandElementError = commandElementError;
	}

	@Override
	public ElementVO getElement() {

		if (super.getElement() == null && commandElementError != null && commandElementError.getElementTypeId() != null
				&& commandElementError.getElementId() != null) {

			Element element = EntitiesManager.getInstance().getElementRepository()
					.get(commandElementError.getElementTypeId(), commandElementError.getElementId(), false);

			if (element != null) {

				super.setElement(new ElementGraphql(element));
			}

		}
		return super.getElement();
	}

	@Override
	public List<CommandElementPreconditionVO> getCommandElementPreconditions() {

		if (super.getCommandElementPreconditions() == null && commandElementError != null
				&& CollectionUtils.isNotEmpty(commandElementError.getCommandElementPreconditions())) {

			List<CommandElementPreconditionVO> commandElementPreconditions = new ArrayList<>();

			for (CommandElementPrecondition commandElementPrecondition : commandElementError
					.getCommandElementPreconditions()) {

				commandElementPreconditions.add(new CommandElementPreconditionGraphql(commandElementPrecondition));

			}
			super.setCommandElementPreconditions(commandElementPreconditions);
		}

		return super.getCommandElementPreconditions();
	}
}
