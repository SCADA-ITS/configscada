package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.front.core.model.helper.CommandElementTypeHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementTypeVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class CommandElementTypeGraphql extends CommandElementTypeVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3908796455858736265L;
	private CommandElementType commandElementType;

	public CommandElementTypeGraphql(final CommandElementType commandElementType) {

		CommandElementTypeHelper.assign(commandElementType, this);
		this.commandElementType = commandElementType;
	}

	@Override
	public List<CommandElementTypeParamVO> getCommandElementTypeParams() {

		if (super.getCommandElementTypeParams() == null && commandElementType != null) {

			List<CommandElementTypeParam> params = EntitiesManager.getInstance().getCommandElementTypeParamRepository()
					.getsByCommandElementType(commandElementType.getId(), commandElementType.getElementTypeId(), false);
				
			if (!CollectionUtils.isEmpty(params)) {
				
				commandElementTypeParams = new ArrayList<>();

				for (CommandElementTypeParam param : params) {

					commandElementTypeParams.add(new CommandElementTypeParamGraphql(param));
				}
			}
		}

		return commandElementTypeParams;
	}
}
