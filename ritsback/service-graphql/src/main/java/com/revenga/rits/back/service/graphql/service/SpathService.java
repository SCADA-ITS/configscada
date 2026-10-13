package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Spath;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.front.core.model.vo.SpathVO;
import com.revenga.rits.back.service.graphql.type.SpathGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SpathService implements QueryInterface<SpathVO> {

	public List<SpathVO> getAll(String stretchId) {

		List<SpathVO> spaths = new ArrayList<>();
		Long stretch = null;

		if (stretchId != null) {
			Key key = new Key(stretchId);

			if (key.getClazz() == Stretch.class) {

				stretch = Long.valueOf(key.getId(1));
			}
		}

		for (Spath spath : EntitiesManager.getInstance().getSpathRepository().getAll(false)) {

			spaths.add(new SpathGraphql(spath, stretch));
		}

		return spaths;
	}

	public List<SpathVO> gets(List<String> spathIds, final String stretchId) {

		List<SpathVO> spathsVO = new ArrayList<>();

		if (CollectionUtils.isEmpty(spathIds)) {

			spathsVO = getAll(stretchId);

		} else {

			List<Long> ids = new ArrayList<>();
			Long stretch = null;

			if (stretchId != null) {
				Key key = new Key(stretchId);

				if (key.getClazz() == Stretch.class) {

					stretch = Long.valueOf(key.getId(1));
				}
			}

			for (String spathId : spathIds) {

				try {

					Key key = new Key(spathId);

					if (key.getClazz() == Spath.class) {

						Long id = Long.valueOf(key.getId(1));

						if (!ids.contains(id)) {

							ids.add(id);
							Spath spath = EntitiesManager.getInstance().getSpathRepository().get(id, false);

							if (spath != null) {

								spathsVO.add(new SpathGraphql(spath, stretch));
							}
						}

					}

				} catch (Exception e) {

					log.error("The id does not correspond to the required object: Spath");
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			}
		}

		return spathsVO;
	}

	@Override
	public Optional<SpathVO> get(String idItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SpathVO> gets(List<String> idItems) {
		return gets(idItems, null);
	}

	@Override
	public List<SpathVO> getAll() {
		return getAll(null);
	}
}