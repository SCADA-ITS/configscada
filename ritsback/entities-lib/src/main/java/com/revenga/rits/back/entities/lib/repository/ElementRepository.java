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
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.cache.core.helper.CacheModelHelper;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementIOControllerModule;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.data.core.model.ManagementAreaElement;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.exception.EntityDBException;
import com.revenga.rits.back.entities.lib.exception.EntityPersistenceException;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityPersistent;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementRepository extends EntityRepository implements EntityCacheable<Element>, EntityPersistent<Element> {

	public static final String ALIAS_IN_CLONE = ConfigProperty.getInstance().getProperty("entities-lib.properties",
			"service.element.clone.alias");

	public static final String DESCRIPTION_IN_CLONE = ConfigProperty.getInstance().getProperty("entities-lib.properties",
			"service.element.clone.description");

	// region Constructors
	public ElementRepository() {

		super(Element.class);
	}

	public ElementRepository(Boolean cacheable) {

		super(Element.class, cacheable);
	}
	
	public ElementRepository(EntityRepositoryOptions options) {

		super(Element.class, options);
	}

	public ElementRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(Element.class, cacheable, cacheRetentionTime);
	}

	public ElementRepository(Boolean cacheable, List<EntityRepository> repositories) {

		super(Element.class, cacheable, repositories);
	}

	public ElementRepository(Boolean cacheable, Long cacheRetentionTime, List<EntityRepository> repositories) {

		super(Element.class, cacheable, cacheRetentionTime, repositories);
	}

	// endregion Constructors

	// region getters
	public Element get(Long elementTypeId, Long id, Boolean fillObject) {

		return (Element) getFromCache(new String[] { String.valueOf(elementTypeId), String.valueOf(id) }, fillObject);
	}

	public List<Element> gets(Long elementTypeId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(elementTypeId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (Element) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<Element> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (Element) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public Element put(Element entity) {

		return (Element) putFromCache(entity);
	}

	@Override
	public List<Element> put(List<Element> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(be -> (Element) be).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public Element del(Element entity) {

		return (Element) delFromCache(entity);

	}

	@Override
	public List<Element> del(List<Element> entities) {

		List<BasicElement> basicElements = entities.stream().map(element -> (BasicElement) element)
				.collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (Element) be).collect(Collectors.toList());
	}
	// endregion dels

	@Override
	public Element create(Element element) {

		try {
			return create(element, null, null);
		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public Element create(Element element, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		Element elementResult = null;
		List<ElementTypeParam> elementTypeParams = null;

		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert element in BBDD
			Element elementInserted = null;

			elementInserted = (Element) getObjectDao().insert(conn, element);

			if (elementInserted == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, element);
			}

			if (!CollectionUtils.isEmpty(getRepositories())) {

				ElementTypeParamRepository entityRepositoryElementTypeParam = (ElementTypeParamRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ElementTypeParam.class)).findFirst()
						.orElse(null);

				if (entityRepositoryElementTypeParam != null) {

					elementTypeParams = entityRepositoryElementTypeParam.gets(elementInserted.getElementTypeId(),
							false);

					// elementValue
					if (!CollectionUtils.isEmpty(elementTypeParams)) {

						ElementValueRepository entityRepositoryElementValue = (ElementValueRepository) getRepositories()
								.stream().filter(repository -> repository.getClazz().equals(ElementValue.class))
								.findFirst().orElse(null);

						if (entityRepositoryElementValue != null) {

							for (ElementTypeParam elementTypeParam : elementTypeParams) {

								ElementValue value = new ElementValue();

								value.setElementId(elementInserted.getId());
								value.setElementTypeId(elementInserted.getElementTypeId());
								value.setElementTypeParamId(elementTypeParam.getId());
								value.setParamTypeId(elementTypeParam.getParamTypeId());

								if (elementTypeParam.getDefaultValue() != null) {

									value.setValue(elementTypeParam.getDefaultValue());
								}

								if (!CollectionUtils.isEmpty(element.getElementValues())) {

									for (ElementValue elementValue : element.getElementValues()) {

										if (elementValue.getElementTypeParamId() != null
												&& elementValue.getElementTypeId() != null
												&& (Long.valueOf((ParamType.CONFIG)))
														.equals(elementValue.getParamTypeId())
												&& elementValue.getElementTypeParamId().equals(elementTypeParam.getId())
												&& elementValue.getElementTypeId()
														.equals(elementTypeParam.getElementTypeId())
												&& elementValue.getParamTypeId()
														.equals(elementTypeParam.getParamTypeId())) {

											value.setValue(elementValue.getValue());
										}
									}
								}

								entityRepositoryElementValue.create(value, conn, persistOperations);
							}
						}
					}
				}
			}

			// Insert all parents
			if (!CollectionUtils.isEmpty(element.getParentElementHierarchies())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				ElementHierarchiesRepository entityRepository = (ElementHierarchiesRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ElementHierarchy.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					for (ElementHierarchy elementHierarchy : element.getParentElementHierarchies()) {

						elementHierarchy.setChildElementId(elementInserted.getId());
						elementHierarchy.setChildElementTypeId(elementInserted.getElementTypeId());
						entityRepository.create(elementHierarchy, conn, persistOperations);
					}
				}
			}
			
			// Insert all childs
			if (!CollectionUtils.isEmpty(element.getChildElementHierarchies())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				ElementHierarchiesRepository entityRepository = (ElementHierarchiesRepository) getRepository(ElementHierarchy.class);

				if (entityRepository != null) {

					for (ElementHierarchy elementHierarchy : element.getChildElementHierarchies()) {

						elementHierarchy.setParentElementId(elementInserted.getId());
						elementHierarchy.setParentElementTypeId(elementInserted.getElementTypeId());
						entityRepository.create(elementHierarchy, conn, persistOperations);
					}
				}
			}

			// Assign IOController Module
			if (!CollectionUtils.isEmpty(element.getIoControllerModules())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				ElementIOControllerModuleRepository entityRepository = (ElementIOControllerModuleRepository) getRepository(ElementIOControllerModule.class);						

				if (entityRepository != null) {

					for (IOControllerModule ioControllerModule : element.getIoControllerModules()) {

						ElementIOControllerModule elementIOControllerModule = new ElementIOControllerModule();
						elementIOControllerModule.setElementId(elementInserted.getId());
						elementIOControllerModule.setElementTypeId(elementInserted.getElementTypeId());
						elementIOControllerModule.setModuleTypeId(ioControllerModule.getModuleTypeId());
						elementIOControllerModule.setModuleId(ioControllerModule.getId());
						
						entityRepository.create(elementIOControllerModule, conn, persistOperations);
					}
				}
			}
			
			// Assign Areas
			if (!CollectionUtils.isEmpty(element.getManagementAreaElement())
					&& !CollectionUtils.isEmpty(getRepositories())) {

				ManagementAreaElementRepository entityRepository = (ManagementAreaElementRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ManagementAreaElement.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					for (ManagementAreaElement managementAreaElement : element.getManagementAreaElement()) {
						
						managementAreaElement.setElementTypeId(elementInserted.getElementTypeId());
						managementAreaElement.setElementId(elementInserted.getId());

						entityRepository.create(managementAreaElement, conn, persistOperations);
					}
				}
			}

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
				
				putFromCache((BasicElement) CacheModelHelper.cloneBasicElementForPut(elementInserted));
				
				elementResult = get(elementInserted.getElementTypeId(), elementInserted.getId(), false);
			}

			if (elementResult == null) {

				throw new NullPointerException("Element not found");
			}

		} catch (EntityDBException | SQLException | NullPointerException | IOException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, element);

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

		return elementResult;
	}

	@Override
	public Element clone(Element element) {

		try {

			return clone(element, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public Element clone(Element element, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		Element elementResult = null;
		List<ElementValue> elementValues = null;
		Element elementCloned = null;
		Element elementInserted = null;
		try {

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Insert element in BBDD
			elementInserted = (Element) get(element.getElementTypeId(), element.getId(), false).clone();
			elementInserted.setAlias(elementInserted.getAlias() + ALIAS_IN_CLONE);
			elementInserted.setDescription(elementInserted.getDescription() + DESCRIPTION_IN_CLONE);

			elementCloned = (Element) getObjectDao().insert(conn, elementInserted);

			if (elementCloned == null) {

				throw new EntityDBException(EntityDBException.Operation.INSERT, elementInserted);
			}

			if (!CollectionUtils.isEmpty(getRepositories())) {

				ElementValueRepository entityRepository = (ElementValueRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(ElementValue.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					elementValues = entityRepository.gets(element.getElementTypeId(), element.getId(), false);

					// elementValue
					if (!CollectionUtils.isEmpty(elementValues)) {

						for (ElementValue elementValue : elementValues) {

							ElementValue value = new ElementValue();

							value.setElementId(elementCloned.getId());
							value.setElementTypeId(elementValue.getElementTypeId());
							value.setElementTypeParamId(elementValue.getElementTypeParamId());
							value.setParamTypeId(elementValue.getParamTypeId());
							value.setValue(elementValue.getValue());

							entityRepository.create(value, conn, persistOperations);
						}
					}
				}
			}

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
				
				putFromCache((BasicElement) CacheModelHelper.cloneBasicElementForPut(elementCloned));
				
				elementResult = get(elementCloned.getElementTypeId(), elementCloned.getId(), false);
			}


			if (elementResult == null) {

				throw new NullPointerException("Element not found");
			}

		} catch (EntityDBException | SQLException | CloneNotSupportedException | NullPointerException | IOException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.CREATE, elementInserted);

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

		return elementResult;
	}

	@Override
	public Element update(Element element) {

		try {

			return update(element, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public Element update(Element element, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		Element elementResult = null;
		Element elementAux = null;
		List<ElementHierarchy> elementHierarchies = null;
		List<ElementIOControllerModule> elementIOControllerModules = null;
		List<ManagementAreaElement> managementAreaElements = null;

		try {

			if (element == null || element.getId() == null) {

				throw new NullPointerException("element is null");
			}

			elementAux = get(element.getElementTypeId(), element.getId(), false);

			if (elementAux == null) {

				throw new NullPointerException("element not found");
			}

			if (element.getAlias() != null)
				elementAux.setAlias(element.getAlias());
			if (element.getDescription() != null)
				elementAux.setDescription(element.getDescription());
			if (element.getLocationId() != null)
				elementAux.setLocationId(element.getLocationId());
			if (element.getKeepValuesOnDisconnected() != null)
				elementAux.setKeepValuesOnDisconnected(element.getKeepValuesOnDisconnected());

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// elementValue
			if (!CollectionUtils.isEmpty(element.getElementValues()) && !CollectionUtils.isEmpty(getRepositories())) {

				ElementValueRepository entityRepository = (ElementValueRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(ElementValue.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					for (ElementValue elementValue : element.getElementValues()) {

						entityRepository.update(elementValue, conn, persistOperations);
					}
				}

			}

			// Insert all parents
			if (element.getParentElementHierarchies() != null && !CollectionUtils.isEmpty(getRepositories())) {

				// Delete all elementHierarchiesChild
				ElementHierarchiesRepository entityRepository = (ElementHierarchiesRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ElementHierarchy.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					elementHierarchies = entityRepository.getsByChild(element.getElementTypeId(), element.getId(),
							false);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {

						for (ElementHierarchy elementHierarchyDeleted : elementHierarchies) {

							entityRepository.delete(elementHierarchyDeleted, conn, persistOperations);
						}
					}

					if (!CollectionUtils.isEmpty(element.getParentElementHierarchies())) {

						for (ElementHierarchy elementParentHierarchy : element.getParentElementHierarchies()) {

							entityRepository.create(elementParentHierarchy, conn, persistOperations);
						}

					}
				}
			}
			
			// Insert all childs
			if (element.getChildElementHierarchies() != null && !CollectionUtils.isEmpty(getRepositories())) {

				// Delete all elementHierarchiesChild
				ElementHierarchiesRepository entityRepository = (ElementHierarchiesRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ElementHierarchy.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					elementHierarchies = entityRepository.getsByParent(element.getElementTypeId(), element.getId(),
							false);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {

						for (ElementHierarchy elementHierarchyDeleted : elementHierarchies) {

							entityRepository.delete(elementHierarchyDeleted, conn, persistOperations);
						}
					}

					if (!CollectionUtils.isEmpty(element.getChildElementHierarchies())) {

						for (ElementHierarchy elementChildHierarchy : element.getChildElementHierarchies()) {

							entityRepository.create(elementChildHierarchy, conn, persistOperations);
						}

					}
				}
			}
			
			// Assign IOController Module
			if (element.getIoControllerModules() != null
					&& !CollectionUtils.isEmpty(getRepositories())) {

				ElementIOControllerModuleRepository entityRepository = (ElementIOControllerModuleRepository) getRepository(ElementIOControllerModule.class);						

				if (entityRepository != null) {

					// Delete all ElementIOControllerModuleRepository
					
					elementIOControllerModules = entityRepository
							.gets(element.getElementTypeId(), element.getId(), false);

					if (!CollectionUtils.isEmpty(elementIOControllerModules)) {

						for (ElementIOControllerModule elementIOControllerModule : elementIOControllerModules) {

							entityRepository.delete(elementIOControllerModule, conn, persistOperations);
						}
					}

					if (!CollectionUtils.isEmpty(element.getIoControllerModules())) {

						for (IOControllerModule ioControllerModule : element.getIoControllerModules()) {

							ElementIOControllerModule elementIOControllerModule = new ElementIOControllerModule();
							elementIOControllerModule.setElementId(element.getId());
							elementIOControllerModule.setElementTypeId(element.getElementTypeId());
							elementIOControllerModule.setModuleTypeId(ioControllerModule.getModuleTypeId());
							elementIOControllerModule.setModuleId(ioControllerModule.getId());
							
							entityRepository.create(elementIOControllerModule, conn, persistOperations);
						}
					}
				}
			}
			
			// Insert all managementAreaElements
			if (element.getManagementAreaElement() != null && !CollectionUtils.isEmpty(getRepositories())) {

				// Delete all managementAreaElements
				ManagementAreaElementRepository entityRepository = (ManagementAreaElementRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ManagementAreaElement.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					managementAreaElements = entityRepository.gets(element.getElementTypeId(), element.getId(), false);

					if (!CollectionUtils.isEmpty(managementAreaElements)) {

						for (ManagementAreaElement managementAreaElement : managementAreaElements) {

							entityRepository.delete(managementAreaElement, conn, persistOperations);
						}
					}

					if (!CollectionUtils.isEmpty(element.getManagementAreaElement())) {

						for (ManagementAreaElement managementAreaElement : element.getManagementAreaElement()) {

							entityRepository.create(managementAreaElement, conn, persistOperations);
						}
					}
				}
			}

			// Update element in BBDD
			Element elementUpdated = null;

			elementUpdated = (Element) getObjectDao().update(conn, elementAux);
			if (elementUpdated == null) {

				throw new EntityDBException(EntityDBException.Operation.UPDATE, elementAux);
			}

			if (commit) {

				conn.commit();

				persistOperations(persistOperations);
				
				putFromCache((BasicElement) CacheModelHelper.cloneBasicElementForPut(elementUpdated));
			}

			elementResult = elementUpdated;

		} catch (EntityDBException | SQLException | NullPointerException | IOException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.UPDATE, element);

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

		return elementResult;

	}

	@Override
	public Element delete(Element element) {

		try {

			return delete(element, null, null);

		} catch (EntityPersistenceException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	@Override
	public Element delete(Element element, Connection connection,
			List<Pair<PersistOperation, BasicElement>> persistOperations) throws EntityPersistenceException {

		Connection conn = null;
		boolean closeConnection = false;
		boolean commit = false;
		conn = connection;
		Element elementResult = null;
		Element elementAux = null;
		List<ElementValue> elementValues = null;
		List<ElementHierarchy> elementHierarchies = null;
		List<ElementIOControllerModule> elementIOControllerModules = null;
		List<ManagementAreaElement> managementAreaElements = null;

		try {

			if (element == null || element.getId() == null) {

				throw new NullPointerException("Element is null");
			}

			elementAux = get(element.getElementTypeId(), element.getId(), false);

			if (elementAux == null) {

				throw new NullPointerException("Element not found");
			}

			if (conn == null) {

				conn = DataSourceConnection.getInstance().getConnection();
				conn.setAutoCommit(false);
				closeConnection = true;
				commit = true;
				persistOperations = new ArrayList<>();
			}

			// Delete all elementValues
			if (!CollectionUtils.isEmpty(getRepositories())) {

				ElementValueRepository entityRepository = (ElementValueRepository) getRepositories().stream()
						.filter(repository -> repository.getClazz().equals(ElementValue.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					elementValues = entityRepository.gets(elementAux.getElementTypeId(), elementAux.getId(), false);

					if (!CollectionUtils.isEmpty(elementValues)) {

						for (ElementValue elementValue : elementValues) {

							entityRepository.delete(elementValue, conn, persistOperations);
						}
					}
				}
			}

			// Delete all elementHierarchiesChild
			if (!CollectionUtils.isEmpty(getRepositories())) {
				ElementHierarchiesRepository entityRepository = (ElementHierarchiesRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ElementHierarchy.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					elementHierarchies = entityRepository.getsByChild(elementAux.getElementTypeId(), elementAux.getId(),
							false);

					if (!CollectionUtils.isEmpty(elementHierarchies)) {

						for (ElementHierarchy elementHierarchy : elementHierarchies) {

							entityRepository.delete(elementHierarchy, conn, persistOperations);
						}
					}
				}
			}

			// Delete all elementHierarchiesParent
			if (!CollectionUtils.isEmpty(getRepositories())) {

				ElementHierarchiesRepository entityRepository = (ElementHierarchiesRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ElementHierarchy.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					elementHierarchies = entityRepository.getsByParent(elementAux.getElementTypeId(),
							elementAux.getId(), false);
					if (!CollectionUtils.isEmpty(elementHierarchies)) {

						for (ElementHierarchy elementHierarchy : elementHierarchies) {

							entityRepository.delete(elementHierarchy, conn, persistOperations);
						}
					}
				}
			}
			
			// Delete all elementIOControllerModules
			if (!CollectionUtils.isEmpty(getRepositories())) {

				ElementIOControllerModuleRepository entityRepository = (ElementIOControllerModuleRepository) getRepository(ElementIOControllerModule.class);						

				if (entityRepository != null) {

					elementIOControllerModules = entityRepository.gets(elementAux.getElementTypeId(), elementAux.getId(),
							false);
					if (!CollectionUtils.isEmpty(elementIOControllerModules)) {

						for (ElementIOControllerModule elementIOControllerModule : elementIOControllerModules) {

							entityRepository.delete(elementIOControllerModule, conn, persistOperations);
						}
					}
				}
			}
			
			// Delete all managementAreaElements
			if (!CollectionUtils.isEmpty(getRepositories())) {

				ManagementAreaElementRepository entityRepository = (ManagementAreaElementRepository) getRepositories()
						.stream().filter(repository -> repository.getClazz().equals(ManagementAreaElement.class)).findFirst()
						.orElse(null);

				if (entityRepository != null) {

					managementAreaElements = entityRepository.gets(elementAux.getElementTypeId(), elementAux.getId(),
							false);
					
					if (!CollectionUtils.isEmpty(managementAreaElements)) {

						for (ManagementAreaElement managementAreaElement : managementAreaElements) {

							entityRepository.delete(managementAreaElement, conn, persistOperations);
						}
					}
				}
			}

			// Delete element in BBDD
			Element elementDeleted = null;

			elementDeleted = (Element) getObjectDao().delete(conn, elementAux);

			if (elementDeleted == null) {

				throw new EntityDBException(EntityDBException.Operation.DELETE, elementAux);
			}

			if (commit) {

				conn.commit();

				// Update cache
				persistOperations(persistOperations);
				
				delFromCache((BasicElement) CacheModelHelper.cloneBasicElementForPut(elementDeleted));
			}

			elementResult = elementDeleted;

		} catch (EntityDBException | SQLException | NullPointerException | IOException e) {

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
			throw new EntityPersistenceException(EntityPersistenceException.Operation.DELETE, element);

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

		return elementResult;
	}

}
