package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.ElementGroup;
import com.revenga.rits.back.data.core.model.ElementHierarchyAllowed;
import com.revenga.rits.back.data.core.model.ElementSubtype;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.PmElementTypeQueue;
import com.revenga.rits.back.data.core.model.PmQueue;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.front.core.model.helper.ElementTypeHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementTypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementGroupVO;
import com.revenga.rits.back.front.core.model.vo.ElementSubtypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeVO;
import com.revenga.rits.back.front.core.model.vo.PmQueuePriorityVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@EqualsAndHashCode(callSuper = false)
@Log4j2
public class ElementTypeGraphql extends ElementTypeVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5763674841645070963L;

	private ElementType elementType;

	public ElementTypeGraphql(final ElementType elementType) {

		ElementTypeHelper.assign(elementType, this);

		this.elementType = elementType;
	}

	@Override
	public ElementTypeStateVO getInitialState() {

		if (super.getInitialState() == null && elementType != null
				&& elementType.getInitialElementTypeStateId() != null) {

			ElementTypeState elementTypeState = EntitiesManager.getInstance().getElementTypeStateRepository()
					.get(elementType.getId(), elementType.getInitialElementTypeStateId(), false);

			if (elementTypeState != null) {

				super.setInitialState(new ElementTypeStateGraphql(elementTypeState));
			}
		}

		return super.getInitialState();
	}

	@Override
	public ElementGroupVO getElementGroup() {

		if (super.getElementGroup() == null) {

			if (elementType != null) {

				ElementGroup elementGroup = EntitiesManager.getInstance().getElementGroupRepository()
						.get(elementType.getElementGroupId(), false);

				if (elementGroup != null)
					super.setElementGroup(new ElementGroupGraphql(elementGroup));
			}

		} else {

			log.error("elementType has null pointer");
		}

		return super.getElementGroup();
	}

	@Override
	public List<ElementTypeParamVO> getElementTypeParams() {

		if (super.getElementTypeParams() == null && elementType != null) {

			List<ElementTypeParam> elementTypeParams = EntitiesManager.getInstance().getElementTypeParamRepository()
					.gets(elementType.getId(), false);

			if (!CollectionUtils.isEmpty(elementTypeParams)) {

				List<ElementTypeParamVO> elementTypeParamsVO = new ArrayList<>();

				for (ElementTypeParam elementTypeParam : elementTypeParams) {

					elementTypeParamsVO.add(new ElementTypeParamGraphql(elementTypeParam));
				}

				super.setElementTypeParams(elementTypeParamsVO);
			}
		}

		return super.getElementTypeParams();

	}

	@Override
	public List<CommandElementTypeVO> getCommandElementTypes() {

		if (super.getCommandElementTypes() == null && elementType != null) {

			List<CommandElementType> commandElementTypes = EntitiesManager.getInstance()
					.getCommandElementTypeRepository().getsByElementType(elementType.getId(), false);

			if (!CollectionUtils.isEmpty(commandElementTypes)) {

				List<CommandElementTypeVO> commandElementTypesVO = new ArrayList<>();

				for (CommandElementType commandElementType : commandElementTypes) {

					commandElementTypesVO.add(new CommandElementTypeGraphql(commandElementType));
				}

				super.setCommandElementTypes(commandElementTypesVO);
			}
		}

		return super.getCommandElementTypes();
	}

	@Override
	public List<ElementTypeVO> getParents() {

		List<ElementTypeVO> elementTypeParentVO = new ArrayList<>();

		if (super.getParents() == null) {

			if (elementType != null) {

				List<ElementHierarchyAllowed> elementHierarchyAlloweds = EntitiesManager.getInstance()
						.getElementHierarchiesAllowedRepository().getsByChildElementType(elementType.getId(), false);

				if (!CollectionUtils.isEmpty(elementHierarchyAlloweds)) {

					for (ElementHierarchyAllowed elementHierarchyAllowedChild : elementHierarchyAlloweds) {

						ElementType elementTypeParent = EntitiesManager.getInstance().getElementTypeRepository()
								.get(elementHierarchyAllowedChild.getParentElementTypeId(), false);

						if (elementTypeParent != null) {

							elementTypeParentVO.add(new ElementTypeGraphql(elementTypeParent));
						}
					}

					super.setParents(elementTypeParentVO);
				}

			} else {

				log.error("elementType has null pointer");
			}
		}

		return super.getParents();
	}

	@Override
	public List<ElementTypeStateVO> getElementTypeStates() {

		if (super.getElementTypeStates() == null && elementType != null) {

			List<ElementTypeState> elementTypeStates = EntitiesManager.getInstance().getElementTypeStateRepository()
					.getsByElementType(elementType.getId(), false);

			if (!CollectionUtils.isEmpty(elementTypeStates)) {

				List<ElementTypeStateVO> elementTypeStatesVO = new ArrayList<>();

				for (ElementTypeState elementTypeState : elementTypeStates) {

					elementTypeStatesVO.add(new ElementTypeStateGraphql(elementTypeState));
				}

				super.setElementTypeStates(elementTypeStatesVO);
			}
		}

		return super.getElementTypeStates();
	}

	@Override
	public List<ElementSubtypeVO> getElementSubtypes() {

		if (super.getElementSubtypes() == null && elementType != null) {

			List<ElementSubtype> elementSubtypes = EntitiesManager.getInstance().getElementSubtypeRepository()
					.getsByElementType(elementType.getId(), false);

			if (!CollectionUtils.isEmpty(elementSubtypes)) {

				List<ElementSubtypeVO> elementSubtypesVO = new ArrayList<>();

				for (ElementSubtype elementSubtype : elementSubtypes) {

					elementSubtypesVO.add(new ElementSubtypeGraphql(elementSubtype));
				}

				super.setElementSubtypes(elementSubtypesVO);
			}
		}

		return super.getElementSubtypes();
	}

	@Override
	public List<PmQueuePriorityVO> getPriorities() {

		if (super.getPriorities() == null && elementType != null) {

			List<PmElementTypeQueue> pmElementTypeQueue = EntitiesManager.getInstance()
					.getPmElementTypeQueueRepository().getsByElementType(elementType.getId(), false);

			if (CollectionUtils.isNotEmpty(pmElementTypeQueue)) {

				PmQueue pmQueue = EntitiesManager.getInstance().getPmQueueRepository()
						.get(pmElementTypeQueue.get(0).getQueueId(), false);

				if (pmQueue != null) {

					List<PmQueuePriority> pmQueuePriorities = EntitiesManager.getInstance()
							.getPmQueuePriorityRepository().getsByQueue(pmQueue.getId(), false);

					if (!CollectionUtils.isEmpty(pmQueuePriorities)) {

						List<PmQueuePriorityVO> pmQueuePrioritiesVO = new ArrayList<>();

						for (PmQueuePriority pmQueuePriority : pmQueuePriorities) {

							pmQueuePrioritiesVO.add(new PmQueuePriorityGraphql(pmQueuePriority, null));
						}

						super.setPriorities(pmQueuePrioritiesVO);
					}
				}
			}
		}

		return super.getPriorities();
	}
}
