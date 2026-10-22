package com.revenga.rits.back.front.core.model.helper.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.front.core.model.helper.BasicElementHelper;
import com.revenga.rits.back.front.core.model.helper.CommandElementTypeHelper;
import com.revenga.rits.back.front.core.model.helper.CommandElementTypeParamHelper;
import com.revenga.rits.back.front.core.model.helper.ElementHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementTypeVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;

@Deprecated
public class SignallingCommandHelper {

	private SignallingCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static CommandElementVO get(final SignallingCommand signallingCommand) {

		CommandElementVO commandElementVO = null;

		if (signallingCommand != null) {

			commandElementVO = new CommandElementVO();

			assign(signallingCommand, commandElementVO);
		}

		return commandElementVO;
	}

	public static SignallingCommand get(final CommandElementVO commandElementVO) {

		SignallingCommand signallingCommand = null;

		if (commandElementVO != null) {

			signallingCommand = new SignallingCommand();

			if (commandElementVO.getCommandElementTypeId() != null) {

				Key key = new Key(commandElementVO.getCommandElementTypeId());

				if (key.getClazz() == CommandElementType.class) {

					signallingCommand.setSignallingCommandId(Long.valueOf(key.getId(1)));
					signallingCommand.setElementTypeId(Long.valueOf(key.getId(2)));
				}
			}

			if (commandElementVO.getElementId() != null) {

				Key key = new Key(commandElementVO.getElementId());

				if (key.getClazz() == Element.class) {

					signallingCommand.setElementTypeId(Long.valueOf(key.getId(1)));
					signallingCommand.setElementId(Long.valueOf(key.getId(2)));
				}
			}

			if (commandElementVO.getCommandElementValues() != null) {

				Key keyCommandElementTypeParam = null;

				List<SignallingParam> signallingParams = new ArrayList<>();

				for (CommandElementValueVO commandElementValue : commandElementVO.getCommandElementValues()) {

					if (commandElementValue.getCommandElementTypeParamId() != null) {
						
						keyCommandElementTypeParam = new Key(commandElementValue.getCommandElementTypeParamId());

						if (keyCommandElementTypeParam.getClazz() == CommandElementTypeParam.class) {

							SignallingParam signallingParam = new SignallingParam();

							signallingParam.setId(Integer.parseInt(keyCommandElementTypeParam.getId(3)));
							signallingParam.setValue(commandElementValue.getValue());
							
							if (ObjectUtils.allNotNull(commandElementValue.getCommandElementTypeParam())) {

								Key keyDataType = null;
								
								if (commandElementValue.getCommandElementTypeParam().getDataTypeId() != null) {

									keyDataType = new Key(commandElementValue.getCommandElementTypeParam().getDataTypeId());
									
								} else if (commandElementValue.getCommandElementTypeParam().getDataType() != null && 
										   commandElementValue.getCommandElementTypeParam().getDataType().getId() != null) {

									keyDataType = new Key(commandElementValue.getCommandElementTypeParam().getDataType().getId());
								}
								
								if (keyDataType != null && keyDataType.getClazz() == DataType.class) {
									
									signallingParam.setType(Integer.parseInt(keyDataType.getId(1)));
								}
							}

							signallingParams.add(signallingParam);
						}
					}
				}
				signallingCommand.setSignallingParams(signallingParams);

			}
		}
		return signallingCommand;
	}
	

	public static void assign(final SignallingCommand signallingCommand, CommandElementVO commandElementVO) {

		if (ObjectUtils.allNotNull(signallingCommand, commandElementVO)) {

			if (signallingCommand.getElementId() != null && signallingCommand.getElementTypeId() != null) {
				
				Element element = new Element();
				element.setElementTypeId(signallingCommand.getElementTypeId());
				element.setId(signallingCommand.getElementId());
				
				commandElementVO.setElementId(BasicElementHelper.getIdFromElement(element));
				commandElementVO.setElement(ElementHelper.get(element));
			
			}
			
			if (signallingCommand.getSignallingParams() != null) {

				List<CommandElementValueVO> commandElementValuesVO = new ArrayList<>();

				for (SignallingParam signallingParam : signallingCommand.getSignallingParams()) {

					CommandElementValueVO commandElementValueVO = new CommandElementValueVO();

					CommandElementTypeParam commandElementTypeParam = new CommandElementTypeParam();
					commandElementTypeParam.setCommandElementTypeId(signallingCommand.getSignallingCommandId());
					commandElementTypeParam.setElementTypeId(signallingCommand.getElementTypeId());
					commandElementTypeParam.setId((long) signallingParam.getId());
					commandElementTypeParam.setCommandElementTypeId(signallingCommand.getId());
					commandElementValueVO
							.setCommandElementTypeParamId(BasicElementHelper.getIdFromElement(commandElementTypeParam));

					commandElementValueVO.setValue(signallingParam.getValue());
					commandElementValueVO
							.setCommandElementTypeParam(CommandElementTypeParamHelper.get(commandElementTypeParam));
					
					commandElementValuesVO.add(commandElementValueVO);
					
				}
				
				commandElementVO.setCommandElementValues(commandElementValuesVO);
				
			}
			
			if (signallingCommand.getId() !=null && signallingCommand.getElementTypeId() != null) {
				
				CommandElementType commandElementType = new CommandElementType();
				commandElementType.setId(signallingCommand.getId());
				commandElementType.setElementTypeId(signallingCommand.getElementTypeId());
				
				CommandElementTypeVO commandElementTypeVO = CommandElementTypeHelper.get(commandElementType);
				
				commandElementVO.setCommandElementType(commandElementTypeVO);
				
			}

		}
	}
}
