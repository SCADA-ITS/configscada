package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ManagementArea;
import com.revenga.rits.back.data.core.model.ManagementAreaUser;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.front.core.model.vo.ManagementAreaVO;
import com.revenga.rits.back.service.graphql.type.ManagementAreaGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ManagementAreaService implements QueryInterface<ManagementAreaVO> {

	@Override
	public List<ManagementAreaVO> getAll() {

		List<ManagementAreaVO> managementAreasVO = new ArrayList<>();

		List<ManagementArea> managementAreas = EntitiesManager.getInstance().getManagementAreaRepository()
				.getAll(false);

		if (!CollectionUtils.isEmpty(managementAreas)) {
			for (ManagementArea managementArea : managementAreas) {

				managementAreasVO.add(new ManagementAreaGraphql(managementArea));
			}
		}
		return managementAreasVO;
	}

	@Override
	public Optional<ManagementAreaVO> get(String managementAreaId) {

		Optional<ManagementAreaVO> managementAreaVO = Optional.empty();

		try {
			Key key = new Key(managementAreaId);

			if (key.getClazz() == ManagementArea.class) {

				ManagementArea managementArea = EntitiesManager.getInstance().getManagementAreaRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (managementArea != null) {

					managementAreaVO = Optional.of(new ManagementAreaGraphql(managementArea));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ManagementArea");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return managementAreaVO;
	}

	@Override
	public List<ManagementAreaVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ManagementAreaVO> gets(String userId) {

		List<ManagementAreaVO> managementAreasVO = new ArrayList<>();

		try {
			Key key = new Key(userId);

			if (key.getClazz() == User.class) {

				List<ManagementAreaUser> managementAreaUsers = EntitiesManager.getInstance()
						.getManagementAreaUserRepository().gets(Long.valueOf(key.getId(1)), false);

				if (!CollectionUtils.isEmpty(managementAreaUsers)) {

					for (ManagementAreaUser managementAreaUser : managementAreaUsers) {

						if (managementAreaUser.getId() != null) {

							ManagementArea managementArea = EntitiesManager.getInstance().getManagementAreaRepository()
									.get(managementAreaUser.getId(), false);

							if (managementArea != null) {

								managementAreasVO.add(new ManagementAreaGraphql(managementArea));
							}
						}
					}
				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: User");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return managementAreasVO;
	}

	public List<ManagementAreaVO> getByUser(String userId) {

		if (userId == null) {

			return getAll();
			
		} else {
			
			return gets(userId);
		}
		
	}
}
