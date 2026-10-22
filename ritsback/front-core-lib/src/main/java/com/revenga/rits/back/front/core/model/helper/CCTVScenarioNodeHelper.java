package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.CCTVScenarioNode;
import com.revenga.rits.back.front.core.model.vo.CCTVScenarioNodeVO;


public class CCTVScenarioNodeHelper {

	private CCTVScenarioNodeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static void assign(final CCTVScenarioNode cctvScenarioNode, CCTVScenarioNodeVO cctvScenarioNodeVO) {

		if (ObjectUtils.allNotNull(cctvScenarioNode, cctvScenarioNodeVO)) {

			BasicElementHelper.fillBasicProperties(cctvScenarioNodeVO, cctvScenarioNode);
		}
	}
}
