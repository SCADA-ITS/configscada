package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.front.core.model.helper.CommandElementValueHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class CommandElementValueGraphql extends CommandElementValueVO {

	/**
	 * 
	 */

	private static final long serialVersionUID = 3908796455858736265L;
	private Long commandElementTypeId;
	private Long elementTypeId;
	private Long commandElementTypeParamId;

	public CommandElementValueGraphql(final CommandElementValue commandElementValue) {

		CommandElementValueHelper.assign(commandElementValue, this);
		this.commandElementTypeId = commandElementValue.getCommandElementTypeId();
		this.elementTypeId = commandElementValue.getElementTypeId();
		this.commandElementTypeParamId = commandElementValue.getCommandElementTypeParamId();
	}

	public CommandElementValueGraphql(final PmCommandElementValue commandElementValue) {

		CommandElementValueHelper.assign(commandElementValue, this);
		this.commandElementTypeId = commandElementValue.getCommandElementTypeId();
		this.elementTypeId = commandElementValue.getElementTypeId();
		this.commandElementTypeParamId = commandElementValue.getCommandElementTypeParamId();
	}
	
	@Override
	public CommandElementTypeParamVO getCommandElementTypeParam() {

		if (super.getCommandElementTypeParam() == null) {

			CommandElementTypeParam commandElementTypeParam = EntitiesManager.getInstance()
					.getCommandElementTypeParamRepository()
					.get(this.commandElementTypeId, this.elementTypeId, this.commandElementTypeParamId, false);

			if (commandElementTypeParam != null) {

				super.setCommandElementTypeParam(new CommandElementTypeParamGraphql(commandElementTypeParam));
			}

		}
		return super.getCommandElementTypeParam();
	}

}
