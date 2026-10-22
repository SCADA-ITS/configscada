package com.revenga.rits.back.entities.lib.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.I18nLabel;
import com.revenga.rits.back.data.core.model.I18nModule;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository.PersistOperation;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class I18nLabelRepository extends EntityRepository
		implements EntityCacheable<I18nLabel>, EntityPersistent<I18nLabel> {

	// region Constructors
	public I18nLabelRepository() {

		super(I18nLabel.class);
	}

	public I18nLabelRepository(Boolean cacheable) {

		super(I18nLabel.class, cacheable);
	}

	public I18nLabelRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(I18nLabel.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public I18nLabel get(String localeCode, Long moduleId, String label, Boolean fillObject) {

		I18nLabel i18nLabel = null;

		if (!moduleId.equals(I18nModule.I18N_MODULE_DEFAULT)) {

			i18nLabel = (I18nLabel) getFromCache(new String[] { localeCode, String.valueOf(moduleId), label },
					fillObject);
		}

		// If not found in Module moduleId, search in I18nModule.I18N_MODULE_DEFAULT
		if (i18nLabel == null) {

			i18nLabel = (I18nLabel) getFromCache(
					new String[] { localeCode, String.valueOf(I18nModule.I18N_MODULE_DEFAULT), label }, fillObject);
		}

		// TODO: If not found translation anywhere, you can create this label for this
		// moduleId

		return i18nLabel;
	}

	public List<I18nLabel> getsByLocale(String localeCode, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { localeCode, null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (I18nLabel) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<I18nLabel> getsByLocaleModule(String localeCode, Long moduleId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { localeCode, String.valueOf(moduleId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (I18nLabel) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<I18nLabel> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (I18nLabel) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public I18nLabel put(I18nLabel entity) {
		return (I18nLabel) putFromCache(entity);
	}

	@Override
	public List<I18nLabel> put(List<I18nLabel> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (I18nLabel) be).collect(Collectors.toList());
	}

	@Override
	public I18nLabel del(I18nLabel entity) {

		return (I18nLabel) delFromCache(entity);
	}

	@Override
	public List<I18nLabel> del(List<I18nLabel> entities) {
		List<BasicElement> basicElements = entities.stream().map(be -> (BasicElement) be).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (I18nLabel) be).collect(Collectors.toList());
	}

	@Override
	public I18nLabel create(I18nLabel i18nLabel) {

		try {

			return create(i18nLabel, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public I18nLabel create(I18nLabel i18nLabel, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		I18nLabel i18nLabelResult = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert i18nLabel in BBDD
			I18nLabel i18nLabelInserted = null;

			i18nLabelInserted = (I18nLabel) getObjectDao().insert(conn, i18nLabel);

			if (i18nLabelInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, i18nLabel);
			}

			persistOperations.add(new MutablePair<>(PersistOperation.PUT, i18nLabelInserted));

			if (commit) {
				conn.commit();

				persistOperations(persistOperations);
				
				i18nLabelResult = get(i18nLabelInserted.getLocaleCode(), i18nLabelInserted.getModuleId(),
						i18nLabelInserted.getLabel(), false);
			}

			
		} catch (EntityDBException | SQLException | IOException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));

			if (closeConnection) {

				try {
					conn.rollback();

				} catch (SQLException e1) {

					log.error(e1.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e1));
				}
			}
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, i18nLabel);

		} finally {

			try {

				if (closeConnection) {

					conn.close();
				}

			} catch (SQLException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return i18nLabelResult;
	}

	@Override
	public I18nLabel clone(I18nLabel entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I18nLabel clone(I18nLabel entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I18nLabel update(I18nLabel i18nLabel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I18nLabel update(I18nLabel i18nLabel, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I18nLabel delete(I18nLabel entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I18nLabel delete(I18nLabel entity, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}
}
