package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.PmDisabledQueuePriority;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.front.core.model.helper.PmCommandElementHelper;
import com.revenga.rits.back.front.core.model.vo.CommandElementTypeVO;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;
import com.revenga.rits.back.front.core.model.vo.PmCommandElementVO;
import com.revenga.rits.back.front.core.model.vo.PmQueuePriorityVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class PmCommandElementGraphql extends PmCommandElementVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3908796455858736265L;
	private PmCommandElement pmCommandElement;
	private List<PmCommandElementValue> pmCommandElementValues;
	private List<PmDisabledQueuePriority> pmDisabledQueuePriorities;

	public PmCommandElementGraphql(final PmCommandElement pmCommandElement,
			List<PmCommandElementValue> pmCommandElementValues,
			List<PmDisabledQueuePriority> pmDisabledQueuePriorities) {

		PmCommandElementHelper.assign(pmCommandElement, this);
		this.pmCommandElement = pmCommandElement;
		this.pmCommandElementValues = pmCommandElementValues;
		this.pmDisabledQueuePriorities = pmDisabledQueuePriorities;
	}

	@Override
	public CommandElementTypeVO getCommandElementType() {

		if (super.getCommandElementType() == null) {

			if (pmCommandElement != null) {

				CommandElementType commandElementType = EntitiesManager.getInstance().getCommandElementTypeRepository()
						.get(pmCommandElement.getCommandElementTypeId(), pmCommandElement.getElementTypeId(), false);

				if (commandElementType != null) {

					super.setCommandElementType(new CommandElementTypeGraphql(commandElementType));
				}

			} else {

				log.error("pmCommandElement has null pointer");
			}

		}

		return super.getCommandElementType();
	}

	@Override
	public List<CommandElementValueVO> getCommandElementValues() {

		if (super.getCommandElementValues() == null && pmCommandElement != null
				&& CollectionUtils.isNotEmpty(pmCommandElementValues)) {

			List<PmCommandElementValue> values = pmCommandElementValues.stream()
					.filter(be -> be.getPmCommandElementId().equals(pmCommandElement.getId()))
					.collect(Collectors.toList());

			if (!CollectionUtils.isEmpty(values)) {

				List<CommandElementValueVO> commandElementValuesVO = new ArrayList<>();

				for (PmCommandElementValue pmCommandElementValue : values) {

					commandElementValuesVO.add(new CommandElementValueGraphql(pmCommandElementValue));
				}
				super.setCommandElementValues(commandElementValuesVO);
			}
		}

		return super.getCommandElementValues();
	}

	@Override
	public PmQueuePriorityVO getPriority() {

		if (super.getPriority() == null && pmCommandElement != null) {

			PmQueuePriority pmQueuePriority = EntitiesManager.getInstance().getPmQueuePriorityRepository()
					.get(pmCommandElement.getQueueId(), pmCommandElement.getPriorityId(), false);

			if (pmQueuePriority != null) {

				super.setPriority(new PmQueuePriorityGraphql(pmQueuePriority, pmDisabledQueuePriorities));
			}

		}

		return super.getPriority();
	}
}
