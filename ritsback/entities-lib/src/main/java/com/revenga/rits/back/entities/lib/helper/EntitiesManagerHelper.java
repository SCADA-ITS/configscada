package com.revenga.rits.back.entities.lib.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class EntitiesManagerHelper {

	private EntitiesManagerHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());

	}

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
