package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.PlanHierarchy;
import com.revenga.rits.back.front.core.model.vo.PlanHierarchyVO;


public class PlanHierarchyHelper {

	private PlanHierarchyHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static void assign(final PlanHierarchy planHierarchy, PlanHierarchyVO planHierarchyVO) {

		if (ObjectUtils.allNotNull(planHierarchy, planHierarchyVO)) {

			BasicElementHelper.fillBasicProperties(planHierarchyVO, planHierarchy);
		}
	}
}
