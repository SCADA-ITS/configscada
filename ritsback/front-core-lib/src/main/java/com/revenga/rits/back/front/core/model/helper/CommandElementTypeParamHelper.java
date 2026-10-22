package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.front.core.model.vo.CommandElementTypeParamVO;

public class CommandElementTypeParamHelper {

	private CommandElementTypeParamHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static CommandElementTypeParamVO get(final CommandElementTypeParam commandElementTypeParam) {

		CommandElementTypeParamVO commandElementTypeParamVO = null;

		if (commandElementTypeParam != null) {

			commandElementTypeParamVO = new CommandElementTypeParamVO();

			assign(commandElementTypeParam, commandElementTypeParamVO);
		}

		return commandElementTypeParamVO;
	}

	public static CommandElementTypeParam get(final CommandElementTypeParamVO commandElementTypeParamVO) {

		CommandElementTypeParam commandElementTypeParam = null;

		if (commandElementTypeParamVO != null) {

			commandElementTypeParam = new CommandElementTypeParam();
			if (commandElementTypeParamVO.getId() != null) {

				Key key = new Key(commandElementTypeParamVO.getId());

				if (key.getClazz() == CommandElementTypeParam.class) {

					commandElementTypeParam.setId(Long.valueOf(key.getId(1)));
				}
			}

			commandElementTypeParam.setAlias(commandElementTypeParamVO.getAlias());
			commandElementTypeParam.setDescription((commandElementTypeParamVO.getDescription()));

			if (commandElementTypeParamVO.getDataTypeId() != null) {

				Key key = new Key(commandElementTypeParamVO.getDataTypeId());

				if (key.getClazz() == DataType.class) {

					commandElementTypeParam.setDataTypeId(Long.valueOf(key.getId(1)));
				}
			}

			if (commandElementTypeParamVO.getElementTypeParamId() != null) {

				Key key = new Key(commandElementTypeParamVO.getElementTypeParamId());

				if (key.getClazz() == ElementTypeParam.class) {

					commandElementTypeParam.setElementTypeParamId(Long.valueOf(key.getId(3)));
				}
			}
		}

		return commandElementTypeParam;
	}

	public static void assign(final CommandElementTypeParam commandElementTypeParam,
			CommandElementTypeParamVO commandElementTypeParamVO) {

		if (ObjectUtils.allNotNull(commandElementTypeParam, commandElementTypeParamVO)) {

			BasicElementHelper.fillBasicProperties(commandElementTypeParamVO, commandElementTypeParam);

			if (commandElementTypeParam.getDataTypeId() != null) {

				DataType dataType = new DataType();
				dataType.setId(commandElementTypeParam.getDataTypeId());
				commandElementTypeParamVO.setDataTypeId(BasicElementHelper.getIdFromElement(dataType));
			}
			
			if (commandElementTypeParam.getElementTypeParamId() != null) {
				
				ElementTypeParam elementTypeParam = new ElementTypeParam();
				
				elementTypeParam.setId(commandElementTypeParam.getElementTypeParamId());
				elementTypeParam.setElementTypeId(commandElementTypeParam.getElementTypeId());
				elementTypeParam.setParamTypeId(commandElementTypeParam.getParamTypeId());
				
				commandElementTypeParamVO.setElementTypeParamId(BasicElementHelper.getIdFromElement(elementTypeParam));
			}
			
			commandElementTypeParamVO.setThreshold1(commandElementTypeParam.getThreshold1());
			commandElementTypeParamVO.setThreshold2(commandElementTypeParam.getThreshold2());
		}
	}
}
