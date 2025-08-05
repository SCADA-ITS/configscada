import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

class SendStateToVentilation {

	org.apache.logging.log4j.Logger log;

	SendStateToVentilation(org.apache.logging.log4j.Logger log) {

		this.log = log;
	}

	boolean process(Element element, ElementState elementState) {

		try {

			if (elementState != null) {

				EntitiesManager.getInstance().sendElementStateToVentilation(elementState);
			}

			return true;
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return false;
	}
}
