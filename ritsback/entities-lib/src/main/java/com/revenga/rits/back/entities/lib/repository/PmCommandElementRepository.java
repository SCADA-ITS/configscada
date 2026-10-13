package com.revenga.rits.back.entities.lib.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.dao.impl.ObjectDaoImpl;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.PmCommandElement;
import com.revenga.rits.back.data.core.model.PmCommandElementValue;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PmCommandElementRepository extends EntityRepository implements EntityCacheable<PmCommandElement> {

	private static final String TRUNCATE_PM_COMMAND_ELEMENTS_QUERY = "TRUNCATE TABLE  rt.pm_command_element_values, rt.pm_command_elements;";

	private ObjectDaoImpl objectDaoPmElementValue = new ObjectDaoImpl(PmCommandElementValue.class);
	private ObjectDaoImpl objectDaoPmElement = new ObjectDaoImpl(PmCommandElement.class);
	
	// region Constructors
	public PmCommandElementRepository() {

		super(PmCommandElement.class);
	}

	public PmCommandElementRepository(Boolean cacheable) {

		super(PmCommandElement.class, cacheable);
	}

	public PmCommandElementRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(PmCommandElement.class, cacheable, cacheRetentionTime);
	}

	public PmCommandElementRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(PmCommandElement.class, cacheable, repositories);
	}

	public PmCommandElementRepository(Boolean cacheable, Long cacheRetentionTime, List<EntityRepository> repositories) {

		super(PmCommandElement.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public PmCommandElement get(Long queueId, Long commandElementTypeId, Long id, Long elementTypeId, Long elementId,
			Boolean fillObject) {

		return (PmCommandElement) getFromCache(
				new String[] { String.valueOf(queueId), String.valueOf(commandElementTypeId), String.valueOf(id),
						String.valueOf(elementTypeId), String.valueOf(elementId) },
				fillObject);
	}

	public List<PmCommandElement> gets(Long queueId, Long elementTypeId, Long elementId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(queueId), null, null,
				String.valueOf(elementTypeId), String.valueOf(elementId) }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmCommandElement.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<PmCommandElement> gets(Long queueId, Long elementTypeId, Long elementId, Long priorityId,
			Boolean fillObject) {

		if (priorityId != null) {
			List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(queueId), null, null,
					String.valueOf(elementTypeId), String.valueOf(elementId) }, fillObject);

			if (!CollectionUtils.isEmpty(basicElements)) {

				return basicElements.stream().map(PmCommandElement.class::cast)
						.filter(be -> priorityId.equals(be.getPriorityId())).collect(Collectors.toList());
			}
		}

		return Collections.emptyList();
	}


	public List<PmCommandElement> getsByElementType(Long elementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { null, null, null,
				String.valueOf(elementTypeId), null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmCommandElement.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	@Override
	public List<PmCommandElement> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(PmCommandElement.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<PmCommandElement> getBySource(String source, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			if (source != null) {

				return basicElements.stream().map(PmCommandElement.class::cast)
						.filter(be -> source.equals(be.getSource())).collect(Collectors.toList());
			} else {
				return basicElements.stream().map(PmCommandElement.class::cast).collect(Collectors.toList());
			}
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public PmCommandElement put(PmCommandElement entity) {

		return (PmCommandElement) putFromCache(entity);
	}

	@Override
	public List<PmCommandElement> put(List<PmCommandElement> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(PmCommandElement.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public PmCommandElement del(PmCommandElement entity) {

		return (PmCommandElement) delFromCache(entity);

	}

	@Override
	public List<PmCommandElement> del(List<PmCommandElement> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(PmCommandElement.class::cast).collect(Collectors.toList());
	}
	// endregion dels

	public PmCommandElement enable(PmCommandElement pmCommandElement) {

		PmCommandElement result = null;
		PmCommandElement aux = null;

		try {

			if (pmCommandElement == null) {

				throw new NullPointerException("PmCommandElement is null");
			}

			if (pmCommandElement.getQueueId() != null && pmCommandElement.getCommandElementTypeId() != null
					&& pmCommandElement.getId() != null && pmCommandElement.getElementTypeId() != null
					&& pmCommandElement.getElementId() != null) {

				aux = get(pmCommandElement.getQueueId(), pmCommandElement.getCommandElementTypeId(),
						pmCommandElement.getId(), pmCommandElement.getElementTypeId(), pmCommandElement.getElementId(),
						false);
			}

			if (aux == null) {

				throw new NullPointerException("PmCommandElement not found");

			}

			aux.setEnable(true);
			result = put(aux);

		} catch (NullPointerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

		}

		return result;
	}

	public PmCommandElement disable(PmCommandElement pmCommandElement) {

		PmCommandElement result = null;
		PmCommandElement aux = null;

		try {

			if (pmCommandElement == null) {

				throw new NullPointerException("PmCommandElement is null");
			}

			if (pmCommandElement.getQueueId() != null && pmCommandElement.getCommandElementTypeId() != null
					&& pmCommandElement.getId() != null && pmCommandElement.getElementTypeId() != null
					&& pmCommandElement.getElementId() != null) {

				aux = get(pmCommandElement.getQueueId(), pmCommandElement.getCommandElementTypeId(),
						pmCommandElement.getId(), pmCommandElement.getElementTypeId(), pmCommandElement.getElementId(),
						false);
			}

			if (aux == null) {

				throw new NullPointerException("PmCommandElement not found");

			}

			aux.setEnable(false);
			result = put(aux);

		} catch (NullPointerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

		}

		return result;
	}

	public void dbSync() {

		Connection connection = null;
		Statement stmt = null;

		try {
			connection = DataSourceConnection.getInstance().getConnection();
			connection.setAutoCommit(false);

			stmt = connection.createStatement();
			stmt.executeUpdate(TRUNCATE_PM_COMMAND_ELEMENTS_QUERY);

			List<PmCommandElement> pmCommandElements = getAll(false);

			if (CollectionUtils.isNotEmpty(pmCommandElements)) {

				Connection conn = connection;

				pmCommandElements.forEach(pmCommandElement -> objectDaoPmElement.insert(conn, pmCommandElement));

				PmCommandElementValueRepository pmCommandElementValueRepository = (PmCommandElementValueRepository) getRepository(
						PmCommandElementValue.class);

				if (pmCommandElementValueRepository != null) {

					List<PmCommandElementValue> pmCommandElementValues = pmCommandElementValueRepository.getAll(false);

					if (CollectionUtils.isNotEmpty(pmCommandElementValues)) {

						pmCommandElementValues.forEach(
								pmCommandElementValue -> objectDaoPmElementValue.insert(conn, pmCommandElementValue));
					}
				}
			}

			connection.commit();

		} catch (SQLException | IOException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

			if (connection != null) {
				try {

					connection.rollback();

				} catch (SQLException e2) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e2));
				}
			}

		} finally {

			try {

				if (stmt != null) {
					stmt.close();
				}

				if (connection != null) {

					connection.close();
				}

			} catch (SQLException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

	}
}
