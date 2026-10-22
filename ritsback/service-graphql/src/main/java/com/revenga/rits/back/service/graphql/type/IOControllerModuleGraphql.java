package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.front.core.model.helper.IOControllerModuleHelper;
import com.revenga.rits.back.front.core.model.vo.IOControllerModuleVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class IOControllerModuleGraphql extends IOControllerModuleVO {

	private static final long serialVersionUID = 3449180371038715820L;

	private IOControllerModule ioControllerModule;

	public IOControllerModuleGraphql(final IOControllerModule ioControllerModule) {

		IOControllerModuleHelper.assign(ioControllerModule, this);
	}

}
