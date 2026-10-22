package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.front.core.model.vo.PmQueuePriorityVO;

public class PmQueuePriorityHelper {

	private PmQueuePriorityHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static PmQueuePriorityVO get(final PmQueuePriority pmQueuePriority) {

		PmQueuePriorityVO pmQueuePriorityVO = null;

		if (pmQueuePriority != null) {

			pmQueuePriorityVO = new PmQueuePriorityVO();

			assign(pmQueuePriority, pmQueuePriorityVO);
		}

		return pmQueuePriorityVO;
	}

	public static void assign(PmQueuePriority pmQueuePriority, PmQueuePriorityVO pmQueuePriorityVO) {

		if (pmQueuePriority != null && pmQueuePriorityVO != null) {

			BasicElementHelper.fillBasicProperties(pmQueuePriorityVO, pmQueuePriority);

			pmQueuePriorityVO.setPriority(pmQueuePriority.getPriority());
			pmQueuePriorityVO.setDefaultPriority(pmQueuePriority.getDefaultPriority());
			pmQueuePriorityVO.setCheckConflict(pmQueuePriority.getCheckConflict());
		}
	}
}
