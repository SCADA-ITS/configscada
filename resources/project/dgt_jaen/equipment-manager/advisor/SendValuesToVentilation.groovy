import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.equipment.manager.helper.GroovyHelper;

import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementValueState;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

class SendValuesToVentilation {

	org.apache.logging.log4j.Logger log;


	final long ELEMENT_TYPE_PARAM_1 = 1L; // co_concentration - no_concentration - opac_concentration - fan_state
	
	final List<Long> elementTypeParamIdList = Collections.unmodifiableList(Arrays.asList(ELEMENT_TYPE_PARAM_1));

	static final Long FAN = 9
	static final Long CO = 19
	static final Long OPAC = 21

	SendValuesToVentilation(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {

		try {

			if (!CollectionUtils.isEmpty(elementValues)) {

				List<ElementValue> values = new ArrayList<>();

				for (Pair<ElementValue, ElementValue> pair : elementValues) {

					if(pair.getRight() != null && Long.valueOf(ParamType.MEASURE).equals(pair.getRight().getParamTypeId()) && 
						(pair.getRight().getElementValueStateId() == null ||
							pair.getRight().getElementValueStateId().equals(ElementValueState.STATE_ONLINE) ||
							pair.getRight().getElementValueStateId().equals(ElementValueState.STATE_ALERTED)) &&
						elementTypeParamIdList.contains(pair.getRight().getElementTypeParamId())) {

						values.add(pair.getRight());
					}
				}
				EntitiesManager.getInstance().sendElementValuesToVentilation(values);

				if(element.elementTypeId == FAN){
					values = EntitiesManager.instance.getElementValues(FAN, element.id)
				}else if(element.elementTypeId == CO){
					values = EntitiesManager.instance.getElementValues(CO, element.id)
				}else if(element.elementTypeId == OPAC){
					values = EntitiesManager.instance.getElementValues(OPAC, element.id)
				}else{
					log.debug("El tipo de equipo no esta contemplado")
				}

				element.setElementValues(values);
				
				GroovyHelper.sendElementToHistoriclManager(element);
			}

			return true;
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return false;
	}
}
