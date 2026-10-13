package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ElementTree;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.front.core.model.vo.ElementTreeVO;
import com.revenga.rits.back.service.graphql.type.ElementTreeGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ElementTreeService implements QueryInterface<ElementTreeVO> {

	public Optional<ElementTreeVO> get(final String elementTreeKey) {

		Optional<ElementTreeVO> elementTreeVO = Optional.empty();
		
		try {
			
			Key key = new Key(elementTreeKey);

			if (key.getClazz() == ElementTree.class) {

				ElementTree elementTree = EntitiesManager.getInstance().getElementTreeRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (elementTree != null) {

					elementTreeVO = Optional.of(new ElementTreeGraphql(elementTree));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ElementTree");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return elementTreeVO;
	}

	@Override
	public List<ElementTreeVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<ElementTreeVO> getsByUser(String userKey) {
		
		List<ElementTreeVO> elementTreesVO = new ArrayList<>();
		
		try {
			
			Key key = new Key(userKey);

			if (key.getClazz() == User.class) {
				
				User user = EntitiesManager.getInstance().getUserRepository().get(Long.valueOf(key.getId(1)), false);
				
				if (user != null) {
					
					Long userId = Long.valueOf(key.getId(1));
					Long roleId = user.getRoleId();
					
					if (userId != null && roleId != null) {
						
						for (ElementTree elementTree : EntitiesManager.getInstance().getElementTreeRepository().getAll(false)) {
							
							if ((elementTree.getUserId() == null && elementTree.getRoleId() == null)
									|| userId.equals(elementTree.getUserId()) || roleId.equals(elementTree.getRoleId())) {
								
								elementTreesVO.add(new ElementTreeGraphql(elementTree));
							}
						}
					}
				}
			}
			
		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ElementTree");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return elementTreesVO;
	}

	@Override
	public List<ElementTreeVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
