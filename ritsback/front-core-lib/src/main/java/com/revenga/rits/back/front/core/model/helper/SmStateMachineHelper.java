package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.SmStateMachine;
import com.revenga.rits.back.front.core.model.vo.SmStateMachineVO;


public class SmStateMachineHelper {

	private SmStateMachineHelper() {
		super();
	}

	public static SmStateMachineVO get(final SmStateMachine smStateMachine) {

		SmStateMachineVO smStateMachineVO = null;

		if (smStateMachine != null) {

			smStateMachineVO = new SmStateMachineVO();

			assign(smStateMachine, smStateMachineVO);
		}

		return smStateMachineVO;
	}

	public static SmStateMachine get(final SmStateMachineVO smStateMachineVO) {

		SmStateMachine smStateMachine = null;

		if (smStateMachineVO != null) {
			
			smStateMachine = new SmStateMachine();

			if (smStateMachineVO.getId() != null) {

				Key key = new Key(smStateMachineVO.getId());

				if (key.getClazz() == SmStateMachine.class) {

					smStateMachine.setId(Long.valueOf(key.getId(1)));
				}
			}

			
			smStateMachine.setAlias(smStateMachineVO.getAlias());
			smStateMachine.setDescription((smStateMachineVO.getDescription()));
			smStateMachine.setLabelAlias(smStateMachineVO.getLabelAlias());
			smStateMachine.setLabelDescription(smStateMachineVO.getLabelDescription());
			
		}
		
		return smStateMachine;
	}

	public static void assign(final SmStateMachine smStateMachine, SmStateMachineVO smStateMachineVO) {

		if (smStateMachine != null && smStateMachineVO != null) {

			BasicElementHelper.fillBasicProperties(smStateMachineVO, smStateMachine);
		
		}

		ModelVOHelper.translate(smStateMachineVO);
	}
}