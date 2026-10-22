package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.SmCondition;
import com.revenga.rits.back.data.core.model.SmStateMachine;
import com.revenga.rits.back.data.core.model.SmThreshold;
import com.revenga.rits.back.data.core.model.SmTimer;
import com.revenga.rits.back.data.core.model.SmTransitionCondition;
import com.revenga.rits.back.front.core.model.helper.SmStateMachineHelper;
import com.revenga.rits.back.front.core.model.helper.SmThresholdHelper;
import com.revenga.rits.back.front.core.model.vo.SmStateMachineVO;
import com.revenga.rits.back.front.core.model.vo.SmThresholdVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SmStateMachineService implements QueryInterface<SmStateMachineVO>, MutationInterface<SmStateMachineVO> {



	public List<SmThresholdVO> updateSmThresholds(List<SmThresholdVO> smThresholds) {

		List<SmThresholdVO> smThresholdsUpdated = null;

		if (smThresholds != null && !CollectionUtils.isEmpty(smThresholds)) {

			smThresholdsUpdated = new ArrayList<>();

			for (SmThresholdVO smThreshold : smThresholds) {

				SmThreshold smThresholdUpdated = EntitiesManager.getInstance().getSmThresholdRepository()
						.update(SmThresholdHelper.get(smThreshold));

				if (smThresholdUpdated != null) {

					smThresholdsUpdated.add(SmThresholdHelper.get(smThresholdUpdated));
				}
			}
		}

		return smThresholdsUpdated;
	}


	public List<SmThresholdVO> getsSmThresholds(String stateMachineId) {

		Set<Long> smThresholdIds = getSmThresholdsByStateMachine(stateMachineId);

		List<SmThreshold> smThresholds = EntitiesManager.getInstance().getSmThresholdRepository().getAll(false);

		List<SmThresholdVO> smThresholdsVO = new ArrayList<>();
		
		if (smThresholds != null && !CollectionUtils.isEmpty(smThresholds) && smThresholdIds != null) {

			for (SmThreshold smThreshold : smThresholds) {

				if (CollectionUtils.isEmpty(smThresholdIds) || smThresholdIds.contains(smThreshold.getId())) {

					smThresholdsVO.add(SmThresholdHelper.get(smThreshold));
				}
			}
		}
		
		return smThresholdsVO;
	}
	
	
	public Set<Long> getSmThresholdsByStateMachine(String stateMachineId) {
		
		SmStateMachine stateMachine = null;
		
		Set<Long> smThresholdIds = new HashSet<>();

		if (stateMachineId != null) {

			try {
				Key key = new Key(stateMachineId);

				if (key.getClazz() == SmStateMachine.class) {

					stateMachine = EntitiesManager.getInstance().getSmStateMachineRepository()
							.get(Long.valueOf(key.getId(1)), false);
				}

			} catch (Exception e) {

				log.error("The id does not correspond to the required object: SmStateMachine");
				log.debug(ExceptionUtils.getStackTrace(e));
			}

			if (stateMachine != null) {

				List<SmTransitionCondition> smTransitionConditions = EntitiesManager.getInstance()
						.getSmTransitionConditionRepository().getsByStateMachine(stateMachine.getId(), false);

				if (smTransitionConditions != null && !CollectionUtils.isEmpty(smTransitionConditions)) {

					SmCondition smCondition = null;

					for (SmTransitionCondition transitionCondition : smTransitionConditions) {

						if (transitionCondition != null && transitionCondition.getId() != null) {

							smCondition = EntitiesManager.getInstance().getSmConditionRepository()
									.get(transitionCondition.getId(), false);

							if (smCondition != null) {

								if (smCondition.getThresholdVal1Id() != null) {

									smThresholdIds.add(smCondition.getThresholdVal1Id());
								}

								if (smCondition.getThresholdVal2Id() != null) {

									smThresholdIds.add(smCondition.getThresholdVal2Id());
								}

								if (smCondition.getTimerId() != null) {

									SmTimer smTimer = EntitiesManager.getInstance().getSmTimerRepository()
											.get(smCondition.getTimerId(), false);

									if (smTimer.getThresholdId() != null) {

										smThresholdIds.add(smTimer.getThresholdId());
									}
								}
							}
						}
					}
				}
			}
		}
		
		return smThresholdIds;
	}
	
	public List<SmStateMachineVO> getSmStateMachines(List<String> smStateMachineIds ){
		
		List<SmStateMachineVO> smStateMachinesVO = new ArrayList<>();
		
		List<SmThreshold> smThresholds = EntitiesManager.getInstance().getSmThresholdRepository().getAll(false);
		
		if (smStateMachineIds != null && !CollectionUtils.isEmpty(smStateMachineIds)) {
			
			for (String smStateMachineId : smStateMachineIds) {
				
				SmStateMachine smStateMahine = null;
				
				try {
					Key key = new Key(smStateMachineId);

					if (key.getClazz() == SmStateMachine.class) {

						smStateMahine = EntitiesManager.getInstance().getSmStateMachineRepository()
								.get(Long.valueOf(key.getId(1)), false);
					}

				} catch (Exception e) {

					log.error("The id does not correspond to the required object: SmStateMachine");
					log.debug(ExceptionUtils.getStackTrace(e));
				}
				
				if (smStateMahine != null) {
					
					SmStateMachineVO smStateMachineVO = SmStateMachineHelper.get(smStateMahine); 
					
					if (smStateMachineVO != null) {
						
						smStateMachinesVO.add(smStateMachineVO);
					}
				}
			}
		} else {
			
			List<SmStateMachine> smStateMachines = EntitiesManager.getInstance().getSmStateMachineRepository().getAll(false);
			
			for (SmStateMachine smStateMachine: smStateMachines) {
				
				SmStateMachineVO smStateMachineVO = SmStateMachineHelper.get(smStateMachine);
				
				if (smStateMachineVO != null) {
					
					smStateMachinesVO.add(SmStateMachineHelper.get(smStateMachine));
				}	
			}
		}
		
		for (SmStateMachineVO smStateMachineVO : smStateMachinesVO) {
			
			Set<Long> smThresholdIds = getSmThresholdsByStateMachine(smStateMachineVO.getId());
			
			List<SmThresholdVO> smThresholdsVO = new ArrayList<>();
			
			if (smThresholds != null && !CollectionUtils.isEmpty(smThresholds) && smThresholdIds != null && !CollectionUtils.isEmpty(smThresholdIds)) {

				for (SmThreshold smThreshold : smThresholds) {

					if (smThresholdIds.contains(smThreshold.getId())) {

						smThresholdsVO.add(SmThresholdHelper.get(smThreshold));
					}
				}
			}
			
			smStateMachineVO.setSmThresholds(smThresholdsVO);
		}
		
		return smStateMachinesVO;
	}

	@Override
	public Optional<SmStateMachineVO> create(SmStateMachineVO item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SmStateMachineVO> update(SmStateMachineVO item) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Optional<SmStateMachineVO> clone(String item) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Optional<SmStateMachineVO> delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<SmStateMachineVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Optional<SmStateMachineVO> get(String idItem) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<SmStateMachineVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}
}
