import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementValuesToVentilationCommand;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

class SendValuesToVentilation {

	org.apache.logging.log4j.Logger log;


	final long ELEMENT_TYPE_PARAM_1 = 1L; // co_concentration - no_concentration - opac_concentration - fan_state
	
	final List<Long> elementTypeParamIdList = Collections.unmodifiableList(Arrays.asList(ELEMENT_TYPE_PARAM_1));

	SendValuesToVentilation(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	boolean process(Element element, List<Pair<ElementValue, ElementValue>> elementValues) {

		try {

			if (!CollectionUtils.isEmpty(elementValues)) {

				SetElementValuesToVentilationCommand cmd = new SetElementValuesToVentilationCommand();

				for (Pair<ElementValue, ElementValue> pair : elementValues) {

					if(pair.getRight() != null && Long.valueOf(ParamType.MEASURE).equals(pair.getRight().getParamTypeId()) && 
						elementTypeParamIdList.contains(pair.getRight().getElementTypeParamId())) {

						cmd.addElementValue(pair.getRight());
					}
				}
				EntitiesManager.getInstance().sendCommand(cmd);
			}

			return true;
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return false;
	}
}
