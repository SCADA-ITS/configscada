package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.front.core.model.vo.DataTypeVO;

public class DataTypeHelper {
	
	private DataTypeHelper() {

		super();
	}

	public static DataTypeVO get(DataType dataType) {

		DataTypeVO dataTypeVO = null;

		if (dataType != null) {

			dataTypeVO = new DataTypeVO();

			assign(dataType, dataTypeVO);
		}

		return dataTypeVO;
	}

	public static DataType get(DataTypeVO dataTypeVO) {

		DataType dataType = null;

		if (dataTypeVO != null) {

			dataType = new DataType();

			if (dataTypeVO.getId() != null) {

				Key key = new Key(dataTypeVO.getId());

				if (key.getClazz() == DataType.class) {

					dataType.setId(Long.valueOf(key.getId(1)));
				}
			}

			dataType.setAlias(dataTypeVO.getAlias());
			dataType.setDescription((dataTypeVO.getDescription()));

		}

		return dataType;
	}

	public static void assign(final DataType dataType, DataTypeVO dataTypeVO) {

		if (dataType != null && dataTypeVO != null) {

			BasicElementHelper.fillBasicProperties(dataTypeVO, dataType);

		}
	}

}
