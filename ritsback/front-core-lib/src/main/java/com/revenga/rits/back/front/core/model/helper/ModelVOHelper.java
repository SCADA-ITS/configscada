package com.revenga.rits.back.front.core.model.helper;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Id;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.cache.core.service.impl.DataCache;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.front.core.annotation.I18nField;
import com.revenga.rits.back.front.core.annotation.TranslatedField;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ModelVOHelper {
	
	public static final boolean I18N_ENABLED = Boolean
			.parseBoolean(ConfigProperty.getInstance().getProperty("application.properties", "i18n.enabled"));
	
	@Data
	public static class Translation {
		
		Map<String, String> labels = new HashMap<>();
		Long timestamp;
	}
	
	private static final long TRANSLATION_CACHE_RETENTION_TIME = 60000L;
	
	private static Map<String, Translation> translationsChache = Collections.synchronizedMap(new HashMap<>());
	
	private ModelVOHelper() {
	
		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static BasicElementVO translate(BasicElementVO basicElementVO) {

		try {
			
			if (basicElementVO != null) {
				
				if (I18N_ENABLED) {
					
					return i18n(basicElementVO);
				}

				Class<?> clazz = basicElementVO.getClass();

				List<Field> fields = new ArrayList<>();

				fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

				Class<?> superClazz = clazz.getSuperclass();

				if (superClazz != null) {

					List<Field> fieldsSuper = Arrays.asList(superClazz.getDeclaredFields());
					fields.addAll(fieldsSuper);
				}

				for (Field field : fields) {
					
					field.setAccessible(true);

					if (field.isAnnotationPresent(TranslatedField.class)) {

						String lblTranslate = field.getDeclaringClass().getSimpleName() + "." + field.getName();

						TranslatedField translatedField = field.getAnnotation(TranslatedField.class);

						for (String referenceStr : translatedField.references()) {

							Field fieldReferenceId = ClassesUtil.getField(field.getDeclaringClass(), referenceStr);

							if (fieldReferenceId != null) {

								fieldReferenceId.setAccessible(true);

								Object objReferenceId = fieldReferenceId.get(basicElementVO);

								Long referenceId = 0L;

								if (objReferenceId instanceof Long) {

									referenceId = (Long) objReferenceId;

								} else if (objReferenceId instanceof Integer) {

									referenceId = Long.valueOf((Integer) objReferenceId);
								}

								lblTranslate = lblTranslate + "." + referenceId;
							}
						}

						field.set(basicElementVO, lblTranslate);

					}
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return basicElementVO;
	}
	
	public static BasicElementVO i18n(BasicElementVO basicElementVO) {

		try {
			
			if (basicElementVO == null) {
			
				throw new NullPointerException("basicElementVO is null");
			}

			for (Field field : ClassesUtil.getFields(basicElementVO.getClass())) {

				if (field.isAnnotationPresent(I18nField.class)) {
					
					I18nField i18nField = field.getAnnotation(I18nField.class);
					
					field.setAccessible(true);
					
					Class<?> model = ClassesUtil.getModelClassBySimpleClassName(i18nField.model());
					String labelFieldName = i18nField.labelFieldName();
					String[] relations = i18nField.relations();
					
					if (model != null && !StringUtils.isEmpty(labelFieldName) && !ArrayUtils.isEmpty(relations)) {
						
						Map<String, String> ids = new HashMap<>();
						
						for (String relation : relations) {
							
							String[] pair = relation.split("=");
							
							if (!ArrayUtils.isEmpty(pair) && pair.length == 2) {
								
								String pkName = pair[0].trim();
								String pkValue = pair[1].trim();
								
								Pattern pattern = Pattern.compile("\'([^\']*)\'");
								Matcher matcher = pattern.matcher(pkValue);
								
								if (matcher.find()) {
								  
									pkValue = matcher.group(1);
									
								}
								else {
									
									Field fieldReferenceId = null;
									
									if (!pkValue.contains(".")) {
									
										fieldReferenceId = ClassesUtil.getField(field.getDeclaringClass(), pkValue);
										
										fieldReferenceId.setAccessible(true);
										
										if (fieldReferenceId != null) {
											
											pkValue = String.valueOf(fieldReferenceId.get(basicElementVO));
										}
									}
									else {
										
										fieldReferenceId = ClassesUtil.getField(field.getDeclaringClass(), pkValue.substring(0, pkValue.indexOf('.')));
										
										fieldReferenceId.setAccessible(true);
										
										if (fieldReferenceId != null && fieldReferenceId.isAnnotationPresent(Id.class)) {
											
											String idxId = pkValue.substring(pkValue.indexOf('.') + 1); 
											
											if (idxId != null && NumberUtils.isCreatable(idxId)) {
												
//												Object targetInstance = model.getDeclaredConstructor().newInstance();
												
												//ClassesUtil.getField(model, name)
											
												Key keyVO = new Key(String.valueOf(fieldReferenceId.get(basicElementVO)));
												
												if (keyVO != null) {
													
													pkValue = keyVO.getId(Integer.parseInt(idxId));
												}
											}
										}
									}
								}
								
								ids.put(pkName, pkValue);
							}
						}
						
						if (!MapUtils.isEmpty(ids)) {
							
							Key key = new Key(model, ids);
							
							Translation translation = translationsChache.get(key.getSimpleValue());
							
							String lblTranslate = null; 
									
							if (translation != null
									&& Duration.between(Instant.ofEpochMilli(translation.getTimestamp()), Instant.now())
											.toMillis() < TRANSLATION_CACHE_RETENTION_TIME) {

								lblTranslate = translation.getLabels().get(labelFieldName);
								
								field.set(basicElementVO, lblTranslate);
							}
							else {

								DataCache dataCache = new DataCache(RedisConnection.getInstance());
								
								if (dataCache != null) {
								
									BasicElement be = dataCache.get(key);
									
									if (be != null) {
										
										Field fieldLabel = ClassesUtil.getField(be.getClass(), labelFieldName);
										
										if (fieldLabel != null) {
											
											fieldLabel.setAccessible(true);
											
											if (fieldLabel.get(be) != null) {
											
												lblTranslate = String.valueOf(fieldLabel.get(be));
												
												if (translation == null) {
												
													translation = new Translation();
													
													translationsChache.put(key.getSimpleValue(), translation);
												}
													
												translation.getLabels().put(labelFieldName, lblTranslate);
												translation.setTimestamp(Instant.now().toEpochMilli());
												
												field.set(basicElementVO, lblTranslate);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
		} catch (IllegalArgumentException | ClassNotFoundException | IllegalAccessException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return basicElementVO;
	}	
}
