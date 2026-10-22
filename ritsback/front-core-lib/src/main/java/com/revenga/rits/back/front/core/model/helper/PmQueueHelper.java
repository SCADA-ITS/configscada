package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.data.core.model.PmQueue;
import com.revenga.rits.back.front.core.model.vo.PmQueueVO;

public class PmQueueHelper {

	private PmQueueHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static PmQueueVO get(final PmQueue pmQueue) {

		PmQueueVO pmQueueVO = null;

		if (pmQueue != null) {

			pmQueueVO = new PmQueueVO();

			assign(pmQueue, pmQueueVO);
		}

		return pmQueueVO;
	}

	public static void assign(final PmQueue pmQueue, PmQueueVO pmQueueVO) {

		if (pmQueue != null && pmQueueVO != null) {

			BasicElementHelper.fillBasicProperties(pmQueueVO, pmQueue);

		}
	}
}
