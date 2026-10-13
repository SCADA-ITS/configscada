package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsExtIncident;
import com.revenga.rits.back.data.core.model.ImsExtIncidentValue;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.SetExIncidentPublishCommand;
import com.revenga.rits.back.front.core.model.helper.ImsExtIncidentHelper;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentVO;
import com.revenga.rits.back.service.graphql.type.ImsExtIncidentGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ExtIncidentService implements QueryInterface<ImsExtIncidentVO> {

	@Override
	public List<ImsExtIncidentVO> getAll() {

		List<ImsExtIncidentVO> imsExtIncidents = new ArrayList<>();

		for (ImsExtIncident imsExtIncident : EntitiesManager.getInstance().getImsExtIncidentRepository()
				.getAll(false)) {

			imsExtIncidents.add(new ImsExtIncidentGraphql(imsExtIncident));
		}

		return imsExtIncidents;
	}

	@Override
	public Optional<ImsExtIncidentVO> get(String extIncidentId) {

		Optional<ImsExtIncidentVO> imsExtIncidentVO = null;
		try {
			Key key = new Key(extIncidentId);

			if (key.getClazz() == ImsExtIncident.class) {

				ImsExtIncident imsExtIncident = EntitiesManager.getInstance().getImsExtIncidentRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (imsExtIncident != null) {

					imsExtIncidentVO = Optional.of(new ImsExtIncidentGraphql(imsExtIncident));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ImsExtIncident");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return imsExtIncidentVO;
	}

	@Override
	public List<ImsExtIncidentVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ImsExtIncidentVO> sendCreateExIncident(List<ImsExtIncidentVO> extIncidents) {

		List<ImsExtIncidentVO> imsExtIncidentsVO = null;

		SetExIncidentPublishCommand setExIncidentPublishCommand = null;

		if (!CollectionUtils.isEmpty(extIncidents)) {

			for (ImsExtIncidentVO imsExtIncidentVO : extIncidents) {

				ImsExtIncident imsExtIncident = ImsExtIncidentHelper.get(imsExtIncidentVO);

				if (imsExtIncident != null) {

					imsExtIncident = EntitiesManager.getInstance().getImsExtIncidentRepository().create(imsExtIncident);

					List<ImsExtIncidentValue> imsExtIncidentValues = EntitiesManager.getInstance()
							.getImsExtIncidentValueRepository()
							.getsByImsExtIncident(imsExtIncident.getExtIncidentTypeId(), imsExtIncident.getId(), false);

					if (!CollectionUtils.isEmpty(imsExtIncidentValues)) {

						imsExtIncident.setExtIncidentValues(imsExtIncidentValues);
					}

					if (imsExtIncident != null) {

						if (imsExtIncidentsVO == null) {

							imsExtIncidentsVO = new ArrayList<>();
						}

						imsExtIncidentsVO.add(new ImsExtIncidentGraphql(imsExtIncident));

						if (setExIncidentPublishCommand == null) {

							setExIncidentPublishCommand = new SetExIncidentPublishCommand();
						}

						if (setExIncidentPublishCommand.getAction() == null
								|| setExIncidentPublishCommand.getAction().intValue() == Command.ACTION_CREATE) {

							setExIncidentPublishCommand.setAction(Command.ACTION_CREATE);
							setExIncidentPublishCommand.addImsExtIncident(imsExtIncident);

						}
					}

				}
			}

			if (setExIncidentPublishCommand != null) {

				EntitiesManager.getInstance().sendCommand(setExIncidentPublishCommand);

			}
		}

		return imsExtIncidentsVO;

	}

	public List<ImsExtIncidentVO> sendUpdateExIncident(List<ImsExtIncidentVO> extIncidents) {

		List<ImsExtIncidentVO> imsExtIncidentsVO = null;

		SetExIncidentPublishCommand setExIncidentPublishCommand = null;

		if (!CollectionUtils.isEmpty(extIncidents)) {

			for (ImsExtIncidentVO imsExtIncidentVO : extIncidents) {

				ImsExtIncident imsExtIncident = ImsExtIncidentHelper.get(imsExtIncidentVO);

				if (imsExtIncident != null) {

					if (ObjectUtils.allNotNull(imsExtIncident.getId())) {

						ImsExtIncident imsExtIncidentUpdate = EntitiesManager.getInstance()
								.getImsExtIncidentRepository().update(imsExtIncident);

						if (imsExtIncidentUpdate != null) {

							if (imsExtIncidentsVO == null) {

								imsExtIncidentsVO = new ArrayList<>();
							}

							imsExtIncidentsVO.add(new ImsExtIncidentGraphql(imsExtIncidentUpdate));

							if (setExIncidentPublishCommand == null) {

								setExIncidentPublishCommand = new SetExIncidentPublishCommand();
							}

							if (setExIncidentPublishCommand.getAction() == null
									|| setExIncidentPublishCommand.getAction().intValue() == Command.ACTION_UPDATE) {

								setExIncidentPublishCommand.setAction(Command.ACTION_UPDATE);
								imsExtIncident.setExtIncidentTypeId(imsExtIncidentUpdate.getExtIncidentTypeId());
								setExIncidentPublishCommand.addImsExtIncident(imsExtIncident);

							}
						}

					}
				}
			}

			if (setExIncidentPublishCommand != null) {

				EntitiesManager.getInstance().sendCommand(setExIncidentPublishCommand);

			}
		}

		return imsExtIncidentsVO;
	}

	public List<ImsExtIncidentVO> sendDeleteExIncident(List<String> extIncidentIds) {

		List<ImsExtIncidentVO> imsExtIncidentsVO = null;

		SetExIncidentPublishCommand setExIncidentPublishCommand = null;

		if (!CollectionUtils.isEmpty(extIncidentIds)) {

			for (String exIncidentId : extIncidentIds) {

				ImsExtIncidentVO imsExtIncidentVO = new ImsExtIncidentVO();
				imsExtIncidentVO.setId(exIncidentId);

				ImsExtIncident imsExtIncident = ImsExtIncidentHelper.get(imsExtIncidentVO);

				if (imsExtIncident != null) {

					if (ObjectUtils.allNotNull(imsExtIncident.getId())) {

						ImsExtIncident imsExtIncidentDelete = EntitiesManager.getInstance()
								.getImsExtIncidentRepository().delete(imsExtIncident);

						if (imsExtIncidentDelete != null) {

							if (imsExtIncidentsVO == null) {

								imsExtIncidentsVO = new ArrayList<>();
							}

							imsExtIncidentsVO.add(new ImsExtIncidentGraphql(imsExtIncidentDelete));

							if (setExIncidentPublishCommand == null) {

								setExIncidentPublishCommand = new SetExIncidentPublishCommand();
							}

							if (setExIncidentPublishCommand.getAction() == null
									|| setExIncidentPublishCommand.getAction().intValue() == Command.ACTION_DELETE) {

								setExIncidentPublishCommand.setAction(Command.ACTION_DELETE);
								imsExtIncident.setExtIncidentTypeId(imsExtIncidentDelete.getExtIncidentTypeId());
								setExIncidentPublishCommand.addImsExtIncident(imsExtIncident);

							}
						}

					}
				}
			}

			if (setExIncidentPublishCommand != null) {

				EntitiesManager.getInstance().sendCommand(setExIncidentPublishCommand);

			}
		}

		return imsExtIncidentsVO;
	}

}
