package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.front.core.model.helper.DataTypeHelper;
import com.revenga.rits.back.front.core.model.vo.DataTypeVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class DataTypeGraphql extends DataTypeVO {

	private static final long serialVersionUID = -5081806387727870958L;

	private DataType dataType;

	public DataTypeGraphql(final DataType dataType) {

		DataTypeHelper.assign(dataType, this);

	}
}
