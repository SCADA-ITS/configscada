package com.revenga.rits.back.ep.pub.stomp.service;

import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.cache.core.service.impl.DataCache;
import com.revenga.rits.back.cache.core.service.impl.SubscriptorCache;
import com.revenga.rits.back.cache.core.subscriptor.SubscriptorEventHandler;
import com.revenga.rits.back.cache.core.subscriptor.SubscriptorManager;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.Plan;
import com.revenga.rits.back.data.core.model.PlanOrder;
import com.revenga.rits.back.data.core.model.PlanSchedules;

@Service
public class ElementCacheManager {

	DataCache dataCache = new DataCache(RedisConnection.getInstance());
	SubscriptorCache subscriptorCache = new SubscriptorCache();

	public Element getElement(Long elementTypeId, Long elementId) {

		Element element = null;

		if (dataCache != null) {

			Element elementPattern = new Element();

			elementPattern.setElementTypeId(elementTypeId);
			elementPattern.setId(elementId);

			Key keyPatternElement = new Key(elementPattern);

			if (!keyPatternElement.hasFilterPatterns()) {

				BasicElement basicElement = dataCache.gets(keyPatternElement)[0];

				if (basicElement != null) {

					if (basicElement instanceof Element) {

						element = (Element) basicElement;
					}
				}
			}
		}

		return element;
	}

	public ElementTypeParam getElementTypeParam(Long elementTypeId, Long paramTypeId, Long id) {

		ElementTypeParam elementTypeParam = null;

		if (dataCache != null) {

			ElementTypeParam elementTypeParamPattern = new ElementTypeParam();

			elementTypeParamPattern.setElementTypeId(elementTypeId);
			elementTypeParamPattern.setParamTypeId(paramTypeId);
			elementTypeParamPattern.setId(id);

			Key keyPatternElementTypeParam = new Key(elementTypeParamPattern);

			if (!keyPatternElementTypeParam.hasFilterPatterns()) {

				BasicElement basicElement = dataCache.gets(keyPatternElementTypeParam)[0];

				if (basicElement != null) {

					if (basicElement instanceof ElementTypeParam) {

						elementTypeParam = (ElementTypeParam) basicElement;
					}
				}
			}
		}

		return elementTypeParam;
	}

	public ElementTypeParam getElementTypeParam(ElementValue elementValue) {

		ElementTypeParam elementTypeParam = null;

		if (dataCache != null) {

			ElementTypeParam elementTypeParamPattern = new ElementTypeParam();

			elementTypeParamPattern.setElementTypeId(elementValue.getElementTypeId());
			elementTypeParamPattern.setParamTypeId(elementValue.getParamTypeId());
			elementTypeParamPattern.setId(elementValue.getElementTypeParamId());

			Key keyPatternElementTypeParam = new Key(elementTypeParamPattern);

			if (!keyPatternElementTypeParam.hasFilterPatterns()) {

				BasicElement basicElement = dataCache.gets(keyPatternElementTypeParam)[0];

				if (basicElement != null) {

					if (basicElement instanceof ElementTypeParam) {

						elementTypeParam = (ElementTypeParam) basicElement;
					}
				}
			}
		}

		return elementTypeParam;
	}

	public Plan getPlan(Long id) {

		Plan plan = null;

		if (dataCache != null) {

			Plan planPattern = new Plan();

			planPattern.setId(id);

			Key keyPatternPlan = new Key(planPattern);

			if (!keyPatternPlan.hasFilterPatterns()) {

				BasicElement basicElement = dataCache.gets(keyPatternPlan)[0];

				if (basicElement != null) {

					if (basicElement instanceof Plan) {

						plan = (Plan) basicElement;
					}
				}
			}
		}

		return plan;
	}

	public PlanOrder getPlanOrder(Long planId, Long id) {

		PlanOrder planOrder = null;

		if (dataCache != null) {

			PlanOrder planOrderPattern = new PlanOrder();

			planOrderPattern.setPlanId(planId);
			planOrderPattern.setId(id);

			Key keyPatternPlanOrder = new Key(planOrderPattern);

			if (!keyPatternPlanOrder.hasFilterPatterns()) {

				BasicElement basicElement = dataCache.gets(keyPatternPlanOrder)[0];

				if (basicElement != null) {

					if (basicElement instanceof PlanOrder) {

						planOrder = (PlanOrder) basicElement;
					}
				}
			}
		}

		return planOrder;
	}

	public PlanSchedules getPlanSchedules(Long id) {

		PlanSchedules planSchedules = null;

		if (dataCache != null) {

			PlanSchedules planSchedulesPattern = new PlanSchedules();

			planSchedulesPattern.setId(id);

			Key keyPatternPlanOrder = new Key(planSchedulesPattern);

			if (!keyPatternPlanOrder.hasFilterPatterns()) {

				BasicElement basicElement = dataCache.gets(keyPatternPlanOrder)[0];

				if (basicElement != null) {

					if (basicElement instanceof PlanSchedules) {

						planSchedules = (PlanSchedules) basicElement;
					}
				}
			}
		}

		return planSchedules;
	}

	public BasicElement getFromChannel(String channel) {

		return dataCache.getFromChannel(channel);
	}

	public BasicElement getDeleted(String channel) {

		return dataCache.getDeleted(channel);
	}
	
	public BasicElement getDeleted(byte[] channel) {

		return dataCache.getDeleted(channel);
	}

	public SubscriptorManager subscribe(SubscriptorEventHandler subscriptorEventHandler) {

		return subscriptorCache.subscribe(subscriptorEventHandler);
	}
}
