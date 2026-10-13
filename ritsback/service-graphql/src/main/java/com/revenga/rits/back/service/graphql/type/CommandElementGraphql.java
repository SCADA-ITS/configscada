package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.front.core.model.helper.CommandElementHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementTypeVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class CommandElementGraphql extends CommandElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3908796455858736265L;
	private CommandElement commandElement;

	public CommandElementGraphql(final CommandElement commandElement) {

		CommandElementHelper.assign(commandElement, this);
		this.commandElement = commandElement;
	}

	@Override
	public CommandElementTypeVO getCommandElementType() {

		if (super.getCommandElementType() == null) {

			if (commandElement != null) {

				CommandElementType commandElementType = EntitiesManager.getInstance().getCommandElementTypeRepository()
						.get(commandElement.getCommandElementTypeId(), commandElement.getElementTypeId(), false);

				if (commandElementType != null) {

					super.setCommandElementType(new CommandElementTypeGraphql(commandElementType));
				}

			} else {

				log.error("commandElement has null pointer");
			}

		}

		return super.getCommandElementType();
	}

	@Override
	public ElementVO getElement() {

		if (super.getElement() == null) {

			if (commandElement != null) {

				Element element = EntitiesManager.getInstance().getElementRepository()
						.get(commandElement.getElementTypeId(), commandElement.getElementId(), false);
				
				if (element != null) {

					super.setElement(new ElementGraphql(element));
				}

			} else {

				log.error("commandElement has null pointer");
			}

		}
		return super.getElement();
	}

	@Override
	public List<CommandElementValueVO> getCommandElementValues() {

		if (super.getCommandElementValues() == null) {

			if (commandElement != null) {

				List<CommandElementValue> commandElementValues = EntitiesManager.getInstance()
						.getCommandElementValueRepository()
						.getsByCommandElement(commandElement.getCommandElementTypeId(),
								commandElement.getElementTypeId(), commandElement.getId(), false);

				if (!CollectionUtils.isEmpty(commandElementValues)) {

					List<CommandElementValueVO> commandElementValuesVO = new ArrayList<>();

					for (CommandElementValue commandElementValue : commandElementValues) {

						commandElementValuesVO.add(new CommandElementValueGraphql(commandElementValue));
					}
					super.setCommandElementValues(commandElementValuesVO);
				}
			} else {

				log.error("commandElement has null pointer");
			}
		}

		return super.getCommandElementValues();
	}
}
