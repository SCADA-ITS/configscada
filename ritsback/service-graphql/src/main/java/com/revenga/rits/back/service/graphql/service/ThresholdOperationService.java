package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ThresholdOperation;
import com.revenga.rits.back.front.core.model.vo.ThresholdOperationVO;
import com.revenga.rits.back.service.graphql.type.ThresholdOperationGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ThresholdOperationService implements QueryInterface<ThresholdOperationVO> {

	@Override
	public List<ThresholdOperationVO> getAll() {

		List<ThresholdOperationVO> thresholdOperations = new ArrayList<>();

		for (ThresholdOperation alarmThresholdOperation : EntitiesManager.getInstance()
				.getThresholdOperationRepository().getAll(false)) {

			thresholdOperations.add(new ThresholdOperationGraphql(alarmThresholdOperation));
		}

		return thresholdOperations;
	}

	@Override
	public Optional<ThresholdOperationVO> get(String thresholdOperationId) {

		Optional<ThresholdOperationVO> thresholdOperationVO = null;
		try {
			Key key = new Key(thresholdOperationId);

			if (key.getClazz() == ThresholdOperation.class) {

				ThresholdOperation thresholdOperation = EntitiesManager.getInstance()
						.getThresholdOperationRepository().get(Long.valueOf(key.getId(1)), false);

				if (thresholdOperation != null) {

					thresholdOperationVO = Optional
							.of(new ThresholdOperationGraphql(thresholdOperation));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ThresholdOperation");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return thresholdOperationVO;
	}

	@Override
	public List<ThresholdOperationVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

}

