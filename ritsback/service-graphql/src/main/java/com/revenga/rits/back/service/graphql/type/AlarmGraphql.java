package com.revenga.rits.back.service.graphql.type;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.front.core.model.helper.AlarmHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigVO;
import com.revenga.rits.back.front.core.model.vo.AlarmVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.UserVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class AlarmGraphql extends AlarmVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4407504405154188276L;

	private Alarm alarm;

	public AlarmGraphql(final Alarm alarm) {

		AlarmHelper.assign(alarm, this);

		this.alarm = alarm;
	}

	@Override
	public AlarmConfigVO getAlarmConfig() {

		if (super.getAlarmConfig() == null && ObjectUtils.allNotNull(alarm)) {

			AlarmConfig alarmConfig = EntitiesManager.getInstance().getAlarmConfigRepository()
					.get(alarm.getAlarmConfigId(), false);

			if (alarmConfig != null) {

				super.setAlarmConfig(new AlarmConfigGraphql(alarmConfig));
			}
		}

		return super.getAlarmConfig();
	}

	@Override
	public ElementVO getSourceElement() {

		if (super.getSourceElement() == null && ObjectUtils.allNotNull(alarm)) {

			Element element = EntitiesManager.getInstance().getElementRepository().get(alarm.getSourceElementTypeId(),
					alarm.getSourceElementId(), false);

			if (element != null) {

				super.setSourceElement(new ElementGraphql(element));
			}
		}

		return super.getSourceElement();
	}

	@Override
	public ElementVO getTargetElement() {

		if (super.getTargetElement() == null && ObjectUtils.allNotNull(alarm)) {

			Element element = EntitiesManager.getInstance().getElementRepository().get(alarm.getTargetElementTypeId(),
					alarm.getTargetElementId(), false);

			if (element != null) {

				super.setTargetElement(new ElementGraphql(element));
			}
		}

		return super.getTargetElement();
	}
	
	/**
	 * 
	 */
	@Override
	public UserVO getCreationUser() {

		if (super.getCreationUser() == null && alarm != null && alarm.getCreationUserId() != null) {

			User user = EntitiesManager.getInstance().getUserRepository().get(alarm.getCreationUserId(), false);

			if (user != null) {

				super.setCreationUser(new UserGraphql(user));
			}
		}

		return super.getCreationUser();
	}
	
	/**
	 * 
	 */
	@Override
	public UserVO getRecognizedUser() {

		if (super.getRecognizedUser() == null && alarm != null && alarm.getRecognizedUserId() != null) {

			User user = EntitiesManager.getInstance().getUserRepository().get(alarm.getRecognizedUserId(), false);

			if (user != null) {

				super.setRecognizedUser(new UserGraphql(user));
			}
		}

		return super.getRecognizedUser();
	}	
	
	/**
	 * 
	 */
	@Override
	public UserVO getDeactivationUser() {

		if (super.getDeactivationUser() == null && alarm != null && alarm.getDeactivationUserId() != null) {

			User user = EntitiesManager.getInstance().getUserRepository().get(alarm.getDeactivationUserId(), false);

			if (user != null) {

				super.setDeactivationUser(new UserGraphql(user));
			}
		}

		return super.getDeactivationUser();
	}		
}
