package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.CCTVScenario;
import com.revenga.rits.back.front.core.model.vo.CCTVScenarioVO;


public class CCTVScenarioHelper {

	private CCTVScenarioHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static void assign(final CCTVScenario cctvScenario, CCTVScenarioVO cctvScenarioVO) {

		if (ObjectUtils.allNotNull(cctvScenario, cctvScenarioVO)) {

			BasicElementHelper.fillBasicProperties(cctvScenarioVO, cctvScenario);
			
			cctvScenarioVO.setNRows(cctvScenario.getNRows());
			cctvScenarioVO.setNColumns(cctvScenario.getNColumns());
		}
	}
}
