package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.PlanHierarchy;
import com.revenga.rits.back.data.core.model.PlanNode;
import com.revenga.rits.back.front.core.model.helper.PlanNodeHelper;
import com.revenga.rits.back.front.core.model.vo.PlanNodeVO;
import com.revenga.rits.back.service.graphql.type.PlanNodeGraphql;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class PlanNodeService implements QueryInterface<PlanNodeVO>, MutationInterface<PlanNodeVO> {

	@Override
	public List<PlanNodeVO> getAll() {

		List<PlanNodeVO> planNodesVO = new ArrayList<>();

		List<PlanNode> planNodes = EntitiesManager.getInstance().getPlanNodeRepository().getAll(false);

		if (!CollectionUtils.isEmpty(planNodes)) {

			for (PlanNode planNode : planNodes) {

				planNodesVO.add(new PlanNodeGraphql(planNode));
			}
		}

		return planNodesVO;
	}

	@Override
	public Optional<PlanNodeVO> get(String nodeId) {

		Optional<PlanNodeVO> planNodeVO = null;
		
		try {
			Key key = new Key(nodeId);
			
			if (key.getClazz() == PlanNode.class) {

				PlanNode planNode = EntitiesManager.getInstance().getPlanNodeRepository().get(Long.valueOf(key.getId(1)), false);

				if (planNode != null) {

					planNodeVO = Optional.of(new PlanNodeGraphql(planNode));
				}
			}			
			
		} catch (Exception e) {

			log.error("The id does not correspond to the required object: nodeId");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return planNodeVO;
	}

	@Override
	public List<PlanNodeVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Optional<PlanNodeVO> create(PlanNodeVO planNodeVO) {
		Optional<PlanNodeVO> resp = null;

		if (planNodeVO != null) {

			PlanNode planNode = PlanNodeHelper.get(planNodeVO);

			PlanNode planNodeCreated = EntitiesManager.getInstance().getPlanNodeRepository().create(planNode);

			if (planNodeCreated != null) {

				if(planNodeVO.getParentId() != null && planNodeCreated.getId() != null)
				{
					Key key = new Key(planNodeVO.getParentId()); 
					
					if (key.getClazz() == PlanNode.class) {
						
						PlanHierarchy planHierarchy = new PlanHierarchy();
						planHierarchy.setNodeParentId(Long.valueOf(key.getId(1)));
						planHierarchy.setNodeChildId(planNodeCreated.getId());
						
						EntitiesManager.getInstance().getPlanHierarchyRepository().create(planHierarchy);
					}
				}
				
				resp = Optional.of(new PlanNodeGraphql(planNodeCreated));
			}
		}
		return resp;

	}

	@Override
	public Optional<PlanNodeVO> clone(String item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PlanNodeVO> update(PlanNodeVO item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PlanNodeVO> delete(String planNodeId) {
		
		Optional<PlanNodeVO> resp = null;

		if (planNodeId != null) {

			PlanNodeVO planNodeVO = new PlanNodeVO();
			planNodeVO.setId(planNodeId);

			PlanNode planNode = PlanNodeHelper.get(planNodeVO);

			PlanNode planNodeDeleted = EntitiesManager.getInstance().getPlanNodeRepository().delete(planNode);

			if (planNodeDeleted != null) {

				resp = Optional.of(new PlanNodeGraphql(planNodeDeleted));
			}
		}

		return resp;
	}
}
