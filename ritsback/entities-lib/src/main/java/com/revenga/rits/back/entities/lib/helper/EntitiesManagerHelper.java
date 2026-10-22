package com.revenga.rits.back.entities.lib.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.entities.lib.annotation.EntityRepositoryParam;
import com.revenga.rits.back.entities.lib.repository.entity.EntityOnChange;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class EntitiesManagerHelper {
	
	private static final Long CACHE_RETENTION = 1000000000000L;

	private EntitiesManagerHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());

	}
	
	public static void initialize(Object manager) {
		
		initialize(manager, null, null);
	}
	
	public static void initialize(Object manager, List<EntityRepository> repositoryList) {
		
		initialize(manager, repositoryList, null);
	}
	
	public static void initialize(Object manager, Map<Class<?>, EntityRepository> repositories) {
		
		initialize(manager, null, repositories);
	}
		
	public static void initialize(Object manager, List<EntityRepository> repositoryList,
			Map<Class<?>, EntityRepository> repositories) {
		
		if (repositoryList == null) {
			
			repositoryList = new ArrayList<>(); 
		}
		
		if (repositories == null) {
			
			repositories = new HashMap<>();
		}
		
		instance(manager, repositoryList, repositories);
		cacheRepository(repositoryList);
	}
	
	public static void startSubscriptions(List<EntityRepository> repositoryList) {
		
		if (!CollectionUtils.isEmpty(repositoryList)) {
			
			repositoryList.forEach(e -> {
				
				if (e.isSubscribeChanges()) {
					
					e.startSubscription();
				}
			});	
		}
	}
	
	private static void instance(Object manager, List<EntityRepository> repositoryList, Map<Class<?>, EntityRepository> repositories) {

		List<Field> fields = ClassesUtil.getFields(manager.getClass());

		for (Field field : fields) {
			
			if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {

				continue;
			}
			
			try {
				
				if (field.getType().getSuperclass() == EntityRepository.class) {
					
					field.setAccessible(true);
					
					EntityRepositoryParam entityRepositoryParam = field.getAnnotation(EntityRepositoryParam.class);
					EntityRepositoryOptions options = null;
					
					if (field.get(manager) == null) {
						
						if (entityRepositoryParam != null) {
					
							EntityOnChange entityOnChangeInstance = null;
							
							if (entityRepositoryParam.entityOnChange() != null
								&& !entityRepositoryParam.entityOnChange().equals(EntityOnChange.class)) {
							
								entityOnChangeInstance = entityRepositoryParam.entityOnChange().getDeclaredConstructor().newInstance();
							}
							
							options = new EntityRepositoryOptions(entityRepositoryParam.buffered(), 
									entityRepositoryParam.buffered() ? CACHE_RETENTION : null,
									repositoryList, entityRepositoryParam.publishChanges(),
									entityRepositoryParam.subscribeChanges(),
									entityOnChangeInstance);
						}
						else {
							
							options = new EntityRepositoryOptions(true, CACHE_RETENTION,
									repositoryList, false, false, null);
						}
						
						instanceRepository(manager, field, options, repositoryList, repositories);
					}
				}
				
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException | InstantiationException
					| InvocationTargetException | NoSuchMethodException e) {
				
				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}
	}

	private static EntityRepository instanceRepository(Object manager, Field field, EntityRepositoryOptions options,
			List<EntityRepository> repositoryList, Map<Class<?>, EntityRepository> repositories) {
		
		Object repository = null;
		
		try {
			
			Constructor<?> constructorEntityRepositoryOptions = Arrays
					.asList(field.getType().getDeclaredConstructors()).stream().filter(e -> Arrays
							.equals(e.getParameterTypes(), (new Class<?>[] { EntityRepositoryOptions.class })))
					.findFirst().orElse(null);
			
			if (constructorEntityRepositoryOptions != null) {
			
				repository = constructorEntityRepositoryOptions.newInstance(options);
			}
			else {
				
				repository = field.getType().getDeclaredConstructor(Boolean.class, Long.class)
						.newInstance(true, CACHE_RETENTION);	
			}
			
			if (repository != null) {
				
				EntityRepository entityRepository = (EntityRepository) repository;
			
				field.set(manager, entityRepository);
				
				if (repositoryList != null) {
					
					repositoryList.add(entityRepository);
				}
				
				if (repositories != null) {
				
					repositories.put(entityRepository.getClazz(), entityRepository);
				}
			}
			
		} catch (IllegalArgumentException | IllegalAccessException | InstantiationException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return (repository instanceof EntityRepository) ? (EntityRepository) repository : null;
	}
	
	private static void cacheRepository(List<EntityRepository> repositories) {
		
		List<Integer> itemsNum = Collections.synchronizedList(new ArrayList<>());
		Instant instantInit = Instant.now();
		
		Comparator<EntityRepository> compareByClassName = (EntityRepository o1, EntityRepository o2) -> (o1.getClass().getSimpleName()).compareTo(o2.getClass().getSimpleName());

		Collections.sort(repositories, compareByClassName);
		
		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		List<Future<?>> futures = new ArrayList<>();

		for (EntityRepository repository : repositories) {
			
			Runnable task = () -> {

				try {
					
					if (repository.isCacheable()) {
						
						Instant instantItem = Instant.now();
						
						Method getAll = repository.getClass().getMethod("getAll", Boolean.class);
						
						if (getAll != null) {
							
							List<?> items = (List<?>) getAll.invoke(repository, false);
							
							if (!CollectionUtils.isEmpty(items)) {

								Integer size = items.size();
								itemsNum.add(size);
								
								log.info("Caching " + size + " elements of type " + repository.getClazz().getSimpleName() + " in "
										+ Duration.between(instantItem, Instant.now()).toMillis() + " ms.");
							}
						}
					}
				} 
				catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			};
			
			Future<?> f = executor.submit(task);

			futures.add(f);
			
		}
		
		// A) Await all runnables to be done (blocking)
		for (Future<?> future : futures) {

			try {

				future.get(); // get will block until the future is done

			} catch (InterruptedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();

			} catch (ExecutionException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		log.info("All items cached: " + itemsNum.stream().mapToInt(i -> i).sum() + " in "
				+ Duration.between(instantInit, Instant.now()).toMillis() + " ms.");
	}

	@Deprecated
	public static void cacheRepository(Object manager) {

		List<Field> fields = ClassesUtil.getFields(manager.getClass());
		List<Integer> itemsNum = Collections.synchronizedList(new ArrayList<>());

		Instant instantInit = Instant.now();

		Comparator<Field> compareByClassName = (Field o1, Field o2) -> (o1.getName()).compareTo(o2.getName());

		Collections.sort(fields, compareByClassName);

		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		List<Future<?>> futures = new ArrayList<>();

		for (Field field : fields) {

			if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {

				continue;
			}

			Runnable task = () -> {

				try {

					field.setAccessible(true);
					
					if (field.getType().getSuperclass() == EntityRepository.class) {
						
						Object obj = field.get(manager);

						if (obj != null) {

							Class<?> clazz = obj.getClass();

							if (obj instanceof EntityRepository) {

								Instant instantItem = Instant.now();

								Method getAll = clazz.getMethod("getAll", Boolean.class);
								Method buffered = clazz.getMethod("isCacheable");

								boolean isCacheable = (boolean) buffered.invoke(obj);

								if (isCacheable) {

									List<?> items = (List<?>) getAll.invoke(obj, false);

									if (!CollectionUtils.isEmpty(items)) {

										Integer size = items.size();
										itemsNum.add(size);
										
										log.info("Caching " + size + " elements of type " + clazz.getSimpleName() + " in "
												+ Duration.between(instantItem, Instant.now()).toMillis() + " ms.");
									}
								}
							}

						} else {

							log.warn("Repository " + field + " not created");
						}
					}
					

				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {

					log.error(e.getMessage());
					log.debug(ExceptionUtils.getStackTrace(e));
				}
			};

			Future<?> f = executor.submit(task);

			futures.add(f);
		}

		// A) Await all runnables to be done (blocking)
		for (Future<?> future : futures) {

			try {

				future.get(); // get will block until the future is done

			} catch (InterruptedException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
				Thread.currentThread().interrupt();

			} catch (ExecutionException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		log.info("All items cached: " + itemsNum.stream().mapToInt(i -> i).sum() + " in "
				+ Duration.between(instantInit, Instant.now()).toMillis() + " ms.");

	}
}
