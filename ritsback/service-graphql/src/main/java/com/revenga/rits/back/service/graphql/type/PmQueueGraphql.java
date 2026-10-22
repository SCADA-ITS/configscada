package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.PmDisabledQueuePriority;
import com.revenga.rits.back.data.core.model.PmQueue;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.entities.lib.repository.PmCommandElementRepository;
import com.revenga.rits.back.entities.lib.repository.PmCommandElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.PmDisabledQueuePriorityRepository;
import com.revenga.rits.back.front.core.model.helper.PmQueueHelper;
import com.revenga.rits.back.front.core.model.vo.PmCommandElementVO;
import com.revenga.rits.back.front.core.model.vo.PmQueuePriorityVO;
import com.revenga.rits.back.front.core.model.vo.PmQueueVO;
import com.revenga.rits.back.service.graphql.helper.MapRequests;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EqualsAndHashCode(callSuper = false)
public class PmQueueGraphql extends PmQueueVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4407504405154188276L;

	private PmQueue pmQueue;
	private Element element;
	private MapRequests mapRequests;
	private List<PmDisabledQueuePriority> pmDisabledQueuePriorities;

	public PmQueueGraphql(final PmQueue pmQueue, final Element element) {

		PmQueueHelper.assign(pmQueue, this);

		this.pmQueue = pmQueue;
		this.element = element;
		this.pmDisabledQueuePriorities = null;
	}

	public PmQueueGraphql(final PmQueue pmQueue, final Element element, MapRequests mapRequests) {

		PmQueueHelper.assign(pmQueue, this);

		this.pmQueue = pmQueue;
		this.element = element;
		this.pmDisabledQueuePriorities = null;
		this.mapRequests = mapRequests;
	}

	private List<PmDisabledQueuePriority> getPmDisabledQueuePriorities() {

		if (element != null && pmDisabledQueuePriorities == null) {

			if (mapRequests == null) {

				pmDisabledQueuePriorities = EntitiesManager.getInstance().getPmDisabledQueuePriorityRepository()
						.gets(pmQueue.getId(), element.getElementTypeId(), element.getId(), false);
			} else {

				List<BasicElement> basicElement = null;

				if (mapRequests.isGetAll()) {

					basicElement = mapRequests.get(PmDisabledQueuePriorityRepository.class, "getAll", null);

				} else {

					basicElement = mapRequests.get(PmDisabledQueuePriorityRepository.class, "getsByElementType",
							element.getElementTypeId());
				}

				if (!CollectionUtils.isEmpty(basicElement)) {

					pmDisabledQueuePriorities = basicElement.stream().filter(
							be -> element.getElementTypeId().equals(((PmDisabledQueuePriority) be).getElementTypeId())
									&& element.getId().equals(((PmDisabledQueuePriority) be).getElementId())
									&& pmQueue.getId().equals(((PmDisabledQueuePriority) be).getQueueId()))
							.map(PmDisabledQueuePriority.class::cast).collect(Collectors.toList());
				}

			}
		}

		return pmDisabledQueuePriorities;
	}

	@Override
	public List<PmQueuePriorityVO> getPriorities() {

		if (super.getPriorities() == null) {

			if (pmQueue != null) {

				List<PmQueuePriority> pmQueuePriorities = EntitiesManager.getInstance().getPmQueuePriorityRepository()
						.getsByQueue(pmQueue.getId(), false);

				if (!CollectionUtils.isEmpty(pmQueuePriorities)) {

					List<PmQueuePriorityVO> pmQueuePrioritiesVO = new ArrayList<>();

					for (PmQueuePriority pmQueuePriority : pmQueuePriorities) {

						pmQueuePrioritiesVO
								.add(new PmQueuePriorityGraphql(pmQueuePriority, getPmDisabledQueuePriorities()));
					}

					super.setPriorities(pmQueuePrioritiesVO);
				}

			} else {

				log.error("pmQueue has null pointer");
			}
		}

		return super.getPriorities();
	}

	@Override
	public List<PmCommandElementVO> getCommandElements() {

		if (super.getCommandElements() == null && pmQueue != null && element != null) {

			List<PmCommandElement> pmCommandElements = null;

			if (mapRequests == null) {

				pmCommandElements = EntitiesManager.getInstance().getPmCommandElementRepository().gets(pmQueue.getId(),
						element.getElementTypeId(), element.getId(), false);
			} else {

				List<BasicElement> basicElement = null;

				if (mapRequests.isGetAll()) {

					basicElement = mapRequests.get(PmCommandElementRepository.class, "getAll", null);

				} else {

					basicElement = mapRequests.get(PmCommandElementRepository.class, "getsByElementType",
							element.getElementTypeId());
				}

				if (!CollectionUtils.isEmpty(basicElement)) {

					pmCommandElements = basicElement.stream()
							.filter(be -> element.getElementTypeId().equals(((PmCommandElement) be).getElementTypeId())
									&& element.getId().equals(((PmCommandElement) be).getElementId())
									&& pmQueue.getId().equals(((PmCommandElement) be).getQueueId()))
							.map(PmCommandElement.class::cast).collect(Collectors.toList());
				}
			}

			if (!CollectionUtils.isEmpty(pmCommandElements)) {

				List<PmCommandElementValue> pmCommandElementValues = null;

				if (mapRequests == null) {

					pmCommandElementValues = EntitiesManager.getInstance().getPmCommandElementValueRepository()
							.gets(pmQueue.getId(), element.getElementTypeId(), element.getId(), false);
				} else {

					List<BasicElement> basicElement = null;

					if (mapRequests.isGetAll()) {

						basicElement = mapRequests.get(PmCommandElementValueRepository.class, "getAll", null);

					} else {

						basicElement = mapRequests.get(PmCommandElementValueRepository.class, "getsByElementType",
								element.getElementTypeId());
					}

					if (!CollectionUtils.isEmpty(basicElement)) {

						pmCommandElementValues = basicElement.stream().filter(
								be -> element.getElementTypeId().equals(((PmCommandElementValue) be).getElementTypeId())
										&& element.getId().equals(((PmCommandElementValue) be).getElementId())
										&& pmQueue.getId().equals(((PmCommandElementValue) be).getQueueId()))
								.map(PmCommandElementValue.class::cast).collect(Collectors.toList());
					}
				}

				List<PmCommandElementVO> pmCommandElementsVO = new ArrayList<>();

				for (PmCommandElement pmCommandElement : pmCommandElements) {

					pmCommandElementsVO.add(new PmCommandElementGraphql(pmCommandElement, pmCommandElementValues,
							getPmDisabledQueuePriorities()));
				}

				super.setCommandElements(pmCommandElementsVO);
			}
		}

		return super.getCommandElements();
	}
}
