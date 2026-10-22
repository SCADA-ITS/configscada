package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.front.core.model.vo.CommandElementValueVO;
import com.revenga.rits.back.front.core.model.vo.PmCommandElementVO;

public class PmCommandElementHelper {

	private PmCommandElementHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static PmCommandElementVO get(PmCommandElement pmCommandElement) {

		PmCommandElementVO pmCommandElementVO = null;

		if (pmCommandElement != null) {

			pmCommandElementVO = new PmCommandElementVO();

			assign(pmCommandElement, pmCommandElementVO);
		}

		return pmCommandElementVO;
	}

	public static void assign(final PmCommandElement pmCommandElement, PmCommandElementVO pmCommandElementVO) {

		if (ObjectUtils.allNotNull(pmCommandElement, pmCommandElementVO)) {

			BasicElementHelper.fillBasicProperties(pmCommandElementVO, pmCommandElement);

			pmCommandElementVO.setStartTime(pmCommandElement.getStartTime());
			pmCommandElementVO.setEndTime(pmCommandElement.getEndTime());
			pmCommandElementVO.setEnable(pmCommandElement.getEnable() != null && pmCommandElement.getEnable());
			pmCommandElementVO.setSource(pmCommandElement.getSource());
			pmCommandElementVO.setConflict(pmCommandElement.getConflict());
			
		}

	}

	public static PmCommandElement get(final PmCommandElementVO pmCommandElementVO) {

		PmCommandElement pmCommandElement = null;

		if (pmCommandElementVO != null) {

			pmCommandElement = new PmCommandElement();

			if (pmCommandElementVO.getCommandElementTypeId() != null) {

				Key key = new Key(pmCommandElementVO.getCommandElementTypeId());

				if (key.getClazz() == CommandElementType.class) {

					pmCommandElement.setCommandElementTypeId(Long.valueOf(key.getId(1)));
					pmCommandElement.setElementTypeId(Long.valueOf(key.getId(2)));
				}
			}

			if (pmCommandElementVO.getElementId() != null) {

				Key key = new Key(pmCommandElementVO.getElementId());

				if (key.getClazz() == Element.class) {

					pmCommandElement.setElementTypeId(Long.valueOf(key.getId(1)));
					pmCommandElement.setElementId(Long.valueOf(key.getId(2)));
				}
			}

			if (pmCommandElementVO.getPriorityId() != null) {

				Key key = new Key(pmCommandElementVO.getPriorityId());

				if (key.getClazz() == PmQueuePriority.class) {

					pmCommandElement.setQueueId(Long.valueOf(key.getId(1)));
					pmCommandElement.setPriorityId(Long.valueOf(key.getId(2)));
				}
			}

			pmCommandElement.setStartTime(pmCommandElementVO.getStartTime());
			pmCommandElement.setEndTime(pmCommandElementVO.getEndTime());
			pmCommandElement.setSource(pmCommandElementVO.getSource());
			pmCommandElement.setEnable(pmCommandElementVO.getEnable() != null && pmCommandElementVO.getEnable());
			pmCommandElement.setConflict(pmCommandElementVO.getConflict());
			
			if (pmCommandElementVO.getCommandElementValues() != null) {

				List<PmCommandElementValue> pmcommandElementValues = new ArrayList<>();

				for (CommandElementValueVO commandElementValueVO : pmCommandElementVO.getCommandElementValues()) {

					CommandElementValue commandElementValue = CommandElementValueHelper.get(commandElementValueVO);

					PmCommandElementValue pmCommandElementValue = new PmCommandElementValue();
					pmCommandElementValue.setQueueId(pmCommandElement.getQueueId());
					pmCommandElementValue.setCommandElementTypeId(commandElementValue.getCommandElementTypeId());
					pmCommandElementValue.setPmCommandElementId(commandElementValue.getCommandElementId());
					pmCommandElementValue.setElementTypeId(commandElementValue.getElementTypeId());
					pmCommandElementValue.setElementId(pmCommandElement.getElementId());
					pmCommandElementValue
							.setCommandElementTypeParamId(commandElementValue.getCommandElementTypeParamId());
					pmCommandElementValue.setValue(commandElementValue.getValue());
					pmcommandElementValues.add(pmCommandElementValue);
				}

				pmCommandElement.setPmCommandElementValues(pmcommandElementValues);
			}

		}

		return pmCommandElement;
	}
}
