package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.ElementSubtype;
import com.revenga.rits.back.data.core.model.ElementSubtypeActiveCommand;
import com.revenga.rits.back.data.core.model.ElementSubtypeActiveParam;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.front.core.model.helper.ElementSubtypeHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementTypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementSubtypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeParamVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementSubtypeGraphql extends ElementSubtypeVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6535332147744263084L;

	private ElementSubtype elementSubtype;

	public ElementSubtypeGraphql(final ElementSubtype elementSubtype) {

		ElementSubtypeHelper.assign(elementSubtype, this);

		this.elementSubtype = elementSubtype;
	}

	/**
	 * 
	 */
	@Override
	public List<ElementTypeParamVO> getElementSubtypeActiveParams() {

		List<ElementTypeParamVO> elementTypeParamsVO = new ArrayList<ElementTypeParamVO>();

		if (super.getElementSubtypeActiveParams() == null && elementSubtype != null) {

			List<ElementSubtypeActiveParam> elementSubtypeActiveParams = EntitiesManager.getInstance()
					.getElementSubtypeActiveParamRepository()
					.gets(elementSubtype.getElementTypeId(), elementSubtype.getId(), false);

			if (!CollectionUtils.isEmpty(elementSubtypeActiveParams)) {

				for (ElementSubtypeActiveParam elementSubtypeActiveParam : elementSubtypeActiveParams) {

					ElementTypeParam elementTypeParam = EntitiesManager.getInstance().getElementTypeParamRepository()
							.get(elementSubtypeActiveParam.getElementTypeId(),
									elementSubtypeActiveParam.getParamTypeId(), elementSubtypeActiveParam.getId(),
									false);

					if (elementTypeParam != null) {

						elementTypeParamsVO.add(new ElementTypeParamGraphql(elementTypeParam));
					}
				}

				super.setElementSubtypeActiveParams(elementTypeParamsVO);
			}
		}

		return super.getElementSubtypeActiveParams();
	}

	/**
	 * 
	 */
	@Override
	public List<CommandElementTypeVO> getCommandElementSubtypes() {

		if (super.getCommandElementSubtypes() == null) {

			List<CommandElementTypeVO> commandElementTypesVO = null;

			if (elementSubtype == null) {

				log.error("elementSubtype has null pointer");
			} else if (elementSubtype.getId() != null && elementSubtype.getElementTypeId() != null) {

				List<ElementSubtypeActiveCommand> elementSubtypeActiveCommands = EntitiesManager.getInstance()
						.getElementSubtypeActiveCommandRepository()
						.getsByElementSubtype(elementSubtype.getElementTypeId(), elementSubtype.getId(), false);

				if (!CollectionUtils.isEmpty(elementSubtypeActiveCommands)) {

					commandElementTypesVO = new ArrayList<>();

					for (ElementSubtypeActiveCommand oElement : elementSubtypeActiveCommands) {

						CommandElementType commandElementType = EntitiesManager.getInstance()
								.getCommandElementTypeRepository()
								.get(oElement.getCommandElementTypeId(), oElement.getElementTypeId(), false);

						if (commandElementType != null) {

							commandElementTypesVO.add(new CommandElementTypeGraphql(commandElementType));
						}
					}
				}
			}

			super.setCommandElementSubtypes(commandElementTypesVO);
		}

		return super.getCommandElementSubtypes();
	}

}
