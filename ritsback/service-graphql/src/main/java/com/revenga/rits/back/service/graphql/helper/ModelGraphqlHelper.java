package com.revenga.rits.back.service.graphql.helper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.cache.core.service.impl.CommandCache;
import com.revenga.rits.back.cache.core.service.impl.DataCache;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.front.core.model.helper.BasicElementHelper;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO;

import lombok.extern.log4j.Log4j2;

@Log4j2

@Deprecated
public class ModelGraphqlHelper {

	public static BasicElementVO get(final BasicElement pattern, Class<?> clazz,
			Map<String, BasicElementVO> localObjcollection) {

		BasicElementVO basicElementVO = null;

		if (pattern != null) {

			if (localObjcollection != null) {
				basicElementVO = localObjcollection.get(BasicElementHelper.getIdFromElement(pattern));
			}

			if (basicElementVO == null) {

				DataCache dataCache = new DataCache(RedisConnection.getInstance());

				if (dataCache != null) {

					BasicElement[] basicElementArray = dataCache.gets(new Key(pattern));

					if (!ArrayUtils.isEmpty(basicElementArray) && pattern.getClass().isInstance(basicElementArray[0])) {

						try {
							Object object;

							if (localObjcollection != null) {

								object = clazz.getDeclaredConstructor(pattern.getClass(), Map.class)
										.newInstance(basicElementArray[0], localObjcollection);

								basicElementVO = (BasicElementVO) object;

								localObjcollection.put(BasicElementHelper.getIdFromElement(basicElementArray[0]),
										basicElementVO);
							} else {

								object = clazz.getDeclaredConstructor(pattern.getClass())
										.newInstance(basicElementArray[0]);

								basicElementVO = (BasicElementVO) object;
							}

						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException | NoSuchMethodException | SecurityException e) {

							log.info(e.toString());
						}

					}
				}
			}
		} else {
			log.info("pattern null pointer");
		}

		return basicElementVO;
	}

	public static List<BasicElementVO> gets(final BasicElement pattern, Class<?> clazz,
			Map<String, BasicElementVO> localObjcollection) {

		List<BasicElementVO> BasicElementsVO = new ArrayList<>();

		if (pattern == null) {

			log.info("Patter null");

		} else {

			DataCache dataCache = new DataCache(RedisConnection.getInstance());

			if (dataCache != null) {

				BasicElement[] basicElementArray = dataCache.gets(new Key(pattern));

				if (!ArrayUtils.isEmpty(basicElementArray)) {

					for (BasicElement basicElement : basicElementArray) {

						BasicElementVO basicElementVO = null;

						if (localObjcollection != null) {

							basicElementVO = localObjcollection.get(BasicElementHelper.getIdFromElement(basicElement));
						}

						if (basicElementVO == null) {
							try {
								Object object;

								if (localObjcollection != null) {

									object = clazz.getDeclaredConstructor(pattern.getClass(), Map.class)
											.newInstance(basicElement, localObjcollection);

									basicElementVO = (BasicElementVO) object;

									localObjcollection.put(BasicElementHelper.getIdFromElement(basicElement),
											basicElementVO);
								} else {

									object = clazz.getDeclaredConstructor(pattern.getClass()).newInstance(basicElement);

									basicElementVO = (BasicElementVO) object;
								}
							} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
									| InvocationTargetException | NoSuchMethodException | SecurityException e) {

								log.info(e.toString());
							}

						}

						if (basicElementVO != null) {

							BasicElementsVO.add(basicElementVO);
						}
					}
				}
			}
		}

		return BasicElementsVO;
	}

	public static BasicElement[] getByPattern(final BasicElement pattern) {

		if (pattern == null) {

			log.info("Patter null");

		} else {

			DataCache dataCache = new DataCache(RedisConnection.getInstance());

			if (dataCache != null) {

				return dataCache.gets(new Key(pattern));
			}
		}

		return null;
	}

	public static BasicElement[] getByKey(final String key) {

		BasicElement[] basicsElements = null;
		
		if (key == null) {

			log.info("Id null");

		} else {

			try {
				DataCache dataCache = new DataCache(RedisConnection.getInstance());

				if (dataCache != null) {

					basicsElements = dataCache.gets(new Key(key));
				}
			} catch (Exception e) {
				
				log.error("DataCache error, getByKey");
				basicsElements = null;
			}
		}

		return basicsElements;
	}

	public static BasicElement[] getByIndex(Class<?> clazz, String fieldName, String fieldValue)
	{
		if (clazz == null || fieldName == null || fieldValue == null) {

			log.error("Null");

		} else {

			DataCache dataCache = new DataCache(RedisConnection.getInstance());

			if (dataCache != null) {

				return dataCache.gets(clazz, fieldName, fieldValue);
			}
		}

		return null;
	}

	public static boolean put(final BasicElement basicElement) {

		if (basicElement == null) {

			log.info("basicElement null");

		} else {

			DataCache dataCache = new DataCache(RedisConnection.getInstance());

			if (dataCache != null && dataCache.put(basicElement) != null) {

				return true;
			}
		}

		return false;
	}

	public static boolean puts(final List<BasicElement> basicElements) {

		if (CollectionUtils.isEmpty(basicElements)) {

			log.info("Empty list");

			return true;

		} else {

			DataCache dataCache = new DataCache(RedisConnection.getInstance());

			if (dataCache != null && dataCache.puts(basicElements.stream().toArray(BasicElement[]::new)) != null) {

				return true;
			}
		}

		return false;
	}

	public static boolean del(final BasicElement patter) {

		if (patter == null) {

			log.info("patter null");

			return true;

		} else {

			DataCache dataCache = new DataCache(RedisConnection.getInstance());

			if (dataCache != null && dataCache.del(new Key(patter)) != null) {

				return true;
			}
		}

		return false;
	}

	public static boolean sendCommand(final Command command) {

		if (command == null) {

			log.info("command null");

		} else {

			CommandCache cmdCache = new CommandCache(RedisConnection.getInstance());

			if (cmdCache.publish(command) != null) {

				return true;
			}
		}

		return false;
	}

	public static boolean sendCommands(final List<Command> commands) {

		if (CollectionUtils.isEmpty(commands)) {

			log.info("Empty list");

			return true;

		} else {

			CommandCache cmdCache = new CommandCache(RedisConnection.getInstance());

			if (cmdCache.publish(commands.stream().toArray(Command[]::new)) != null) {

				return true;
			}
		}

		return false;
	}

}
