package com.revenga.rits.back.service.graphql.helper;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.BasicElement;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MapRequests implements Serializable {

	private static final long serialVersionUID = 8898637687668183143L;

	public enum Mode {
		GET_ALL, GET_BY_TYPE, GET
	}

	private Map<Pair<Class<?>, String>, List<BasicElement>> map;

	private Mode mode;

	public MapRequests(Mode mode) {

		map = new HashedMap<>();
		this.mode = mode;
	}

	public boolean isGetByType() {

		return Mode.GET_BY_TYPE.equals(mode);
	}

	public boolean isGetAll() {

		return Mode.GET_ALL.equals(mode);
	}

	public List<BasicElement> get(Class<?> clazz, String method, Long typeId) {

		List<BasicElement> basicElement = null;

		Pair<Class<?>, String> pair = new MutablePair<>(clazz, method);

		if (map.containsKey(pair)) {

			basicElement = map.get(pair);

		} else {

			Object obj = null;

			try {

				obj = clazz.getDeclaredConstructor(Boolean.class).newInstance(false);

				if (obj != null) {

					Method getMethod = null;
					getMethod = typeId != null ? clazz.getMethod(method, Long.class, Boolean.class) : clazz.getMethod(method, Boolean.class);

					if (getMethod != null) {

						List<?> items = (List<?>) (typeId != null ? getMethod.invoke(obj, typeId, false)
								: getMethod.invoke(obj, false));

						if (!CollectionUtils.isEmpty(items)) {
							
							basicElement = items.stream().map(BasicElement.class::cast).collect(Collectors.toList());
						}
						map.put(pair, basicElement);
					}
				}

			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
		return basicElement;
	}
}
