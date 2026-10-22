package com.revenga.rits.back.service.graphql.type;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.PmDisabledQueuePriority;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.front.core.model.helper.PmQueuePriorityHelper;
import com.revenga.rits.back.front.core.model.vo.PmQueuePriorityVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class PmQueuePriorityGraphql extends PmQueuePriorityVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4407504405154188276L;

	private PmQueuePriority pmQueuePriority;
	private List<PmDisabledQueuePriority> pmDisabledQueuePriorities;

	public PmQueuePriorityGraphql(final PmQueuePriority pmQueuePriority,
			final List<PmDisabledQueuePriority> pmDisabledQueuePriorities) {

		PmQueuePriorityHelper.assign(pmQueuePriority, this);

		this.pmQueuePriority = pmQueuePriority;
		this.pmDisabledQueuePriorities = pmDisabledQueuePriorities;
	}

	@Override
	public Boolean getEnable() {

		if (super.getEnable() == null) {

			if (pmQueuePriority != null && CollectionUtils.isNotEmpty(this.pmDisabledQueuePriorities)) {

				PmDisabledQueuePriority pmDisabledQueuePriority = this.pmDisabledQueuePriorities.stream()
						.filter(be -> be.getPriorityId().equals(pmQueuePriority.getId())).findFirst().orElse(null);

				super.setEnable(pmDisabledQueuePriority == null);

			} else {

				super.setEnable(true);
			}
		}

		return super.getEnable();
	}
}
