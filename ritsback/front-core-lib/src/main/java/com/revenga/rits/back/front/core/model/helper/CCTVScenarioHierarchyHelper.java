package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.CCTVScenarioHierarchy;
import com.revenga.rits.back.front.core.model.vo.CCTVScenarioHierarchyVO;


public class CCTVScenarioHierarchyHelper {

	private CCTVScenarioHierarchyHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static void assign(final CCTVScenarioHierarchy scenarioHierarchy, CCTVScenarioHierarchyVO scenarioHierarchyVO) {

		if (ObjectUtils.allNotNull(scenarioHierarchy, scenarioHierarchyVO)) {

			BasicElementHelper.fillBasicProperties(scenarioHierarchyVO, scenarioHierarchy);
		}
	}
}
