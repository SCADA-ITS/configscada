package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.front.core.model.helper.LogTypeHelper;
import com.revenga.rits.back.front.core.model.vo.LogSubtypeVO;
import com.revenga.rits.back.front.core.model.vo.LogTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.LogTypeVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogTypeGraphql extends LogTypeVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2024413709955798391L;
	private LogType logType;

	public LogTypeGraphql(final LogType logType) {

		LogTypeHelper.assign(logType, this);

		this.logType = logType;
	}

	@Override
	public List<LogTypeParamVO> getLogTypeParams() {

		if (super.getLogTypeParams() == null) {

			if (logType != null) {

				List<LogTypeParam> logTypeParams = EntitiesManager.getInstance().getLogTypeParamRepository()
						.getsByLogType(logType.getId(), false);

				if (!CollectionUtils.isEmpty(logTypeParams)) {

					List<LogTypeParamVO> logTypeParamsVO = new ArrayList<>();

					for (LogTypeParam logTypeParam : logTypeParams) {

						logTypeParamsVO.add(new LogTypeParamGraphql(logTypeParam));
					}
					super.setLogTypeParams(logTypeParamsVO);
				}

			} else {

				log.error("logType has null pointer");
			}
		}

		return super.getLogTypeParams();
	}

	@Override
	public List<LogSubtypeVO> getLogSubtypes() {

		if (super.getLogSubtypes() == null) {

			if (logType != null) {

				List<LogSubtype> logSubtypes = EntitiesManager.getInstance().getLogSubtypeRepository()
						.getByLogType(logType.getId(), false);

				if (!CollectionUtils.isEmpty(logSubtypes)) {

					List<LogSubtypeVO> logSubtypesVO = new ArrayList<>();

					for (LogSubtype logSubtype : logSubtypes) {

						logSubtypesVO.add(new LogSubtypeGraphql(logSubtype));
					}
					super.setLogSubtypes(logSubtypesVO);
				}

			} else {

				log.error("logType has null pointer");
			}
		}

		return super.getLogSubtypes();
	}
}
