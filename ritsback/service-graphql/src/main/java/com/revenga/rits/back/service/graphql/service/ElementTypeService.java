package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.front.core.model.vo.ElementTypeVO;
import com.revenga.rits.back.service.graphql.type.ElementTypeGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ElementTypeService implements QueryInterface<ElementTypeVO> {

	public List<ElementTypeVO> getAll() {

		List<ElementTypeVO> elementTypesVO = new ArrayList<>();

		for (ElementType elementType : EntitiesManager.getInstance().getElementTypeRepository().getAll(false)) {

			elementTypesVO.add(new ElementTypeGraphql(elementType));
		}

		return elementTypesVO;
	}

	public Optional<ElementTypeVO> get(final String elementTypeKey) {

		Optional<ElementTypeVO> elementTypeVO = null;
		try {
			Key key = new Key(elementTypeKey);

			if (key.getClazz() == ElementType.class) {

				ElementType elementType = EntitiesManager.getInstance().getElementTypeRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (elementType != null) {

					elementTypeVO = Optional.of(new ElementTypeGraphql(elementType));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ElementType");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return elementTypeVO;
	}

	@Override
	public List<ElementTypeVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

}
