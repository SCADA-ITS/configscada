package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.I18nLabel;
import com.revenga.rits.back.data.core.model.I18nLocale;
import com.revenga.rits.back.entities.lib.repository.I18nLabelRepository;
import com.revenga.rits.back.front.core.model.vo.I18nLabelVO;
import com.revenga.rits.back.service.graphql.type.I18nLabelGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class I18nLabelService implements QueryInterface<I18nLabelVO> {

	@Override
	public List<I18nLabelVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<I18nLabelVO> get(final String labelKey) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<I18nLabelVO> getTranslations(final String localeId, final Long moduleId) {

		List<I18nLabelVO> i18nLabelsVO = null;
		List<I18nLabel> i18nLabels = null;

		try {

			Key key = new Key(localeId);

			if (key.getClazz() == I18nLocale.class) {

				I18nLabelRepository i18nLabelRepository = EntitiesManager.getInstance().getI18nLabelRepository();

				i18nLabels = (moduleId != null) ? i18nLabelRepository.getsByLocaleModule(key.getId(1), moduleId, false)
						: i18nLabelRepository.getsByLocale(key.getId(1), false);

				if (!CollectionUtils.isEmpty(i18nLabels)) {

					i18nLabelsVO = new ArrayList<>();

					for (I18nLabel i18nLabel : i18nLabels) {

						i18nLabelsVO.add(new I18nLabelGraphql(i18nLabel));
					}
				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: I18nLabel");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return i18nLabelsVO;
	}

	public Optional<I18nLabelVO> getTranslation(final String localeId, final Long moduleId, final String label,
			final List<String> fields) {

		Optional<I18nLabelVO> i18nLabelVO = null;

		try {

			Key key = new Key(localeId);

			if (key.getClazz() == I18nLocale.class) {

				I18nLabel i18nLabel = EntitiesManager.getInstance().getI18nLabelRepository().get(key.getId(1), moduleId,
						label, false);

				if (i18nLabel != null) {

					if (fields != null) {

						Object[] arguments = fields.toArray();

						if (i18nLabel.getTranslation().contains("%s")) {

							i18nLabel = (I18nLabel) i18nLabel.clone();
							
							i18nLabel.setTranslation(String.format(i18nLabel.getTranslation(), arguments));
						}
					}

					i18nLabelVO = Optional.of(new I18nLabelGraphql(i18nLabel));

				} else {

					if (CollectionUtils.isEmpty(fields) || fields == null) {

						i18nLabel = new I18nLabel();
						i18nLabel.setLabel(label);
						i18nLabel.setModuleId(moduleId);
						i18nLabel.setLocaleCode(key.getId(1));
						i18nLabel.setTranslation("¿¿" + label + "??");
						i18nLabel = EntitiesManager.getInstance().getI18nLabelRepository().create(i18nLabel);
						i18nLabelVO = Optional.of(new I18nLabelGraphql(i18nLabel));
					}
				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: I18nLabel");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return i18nLabelVO;
	}

	@Override
	public List<I18nLabelVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}
}
