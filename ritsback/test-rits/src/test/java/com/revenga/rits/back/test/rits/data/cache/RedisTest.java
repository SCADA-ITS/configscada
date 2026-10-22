package com.revenga.rits.back.test.rits.data.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import com.revenga.rits.back.cache.core.helper.CacheModelHelper;
import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.cache.core.service.ElementCacheService;
import com.revenga.rits.back.cache.core.service.impl.ElementCacheServiceImpl;
import com.revenga.rits.back.cache.core.subscriptor.SubscriptorManager;
import com.revenga.rits.back.cache.core.subscriptor.SubscriptorEventHandler;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;

import redis.clients.jedis.Jedis;

public class RedisTest {

	public static Long countEvents = 0L;
	@Test
	public void connect() {
		
		Jedis jedis = RedisConnection.getInstance().getConnectionPool().getResource();
		assertNotNull(jedis);
	}
	
	@Test
	public void putsAndDel() {
			
		ElementCacheService elementCacheService = new ElementCacheServiceImpl(RedisConnection.getInstance());
		
		if (elementCacheService != null) {
			
			elementCacheService.delAll();
			
			Element element1 = new Element();
			
			element1.setElementTypeId(1L);
			element1.setId(1L);
			
			Element element2 = new Element();
			
			element2.setElementTypeId(2L);
			element2.setId(2L);
			Key[] keys = elementCacheService.puts(ArrayUtils.toArray(element1,element2));
			
			assertEquals(2, keys.length);
			
			Key keyPattern = new Key(Element.class, null);
			
			keys = elementCacheService.del(keyPattern);
			
			assertEquals(2, keys.length);
		}
	}
	
	@Test
	public void getElements() {
			
		ElementCacheService elementCacheService = new ElementCacheServiceImpl(RedisConnection.getInstance());
		
		if (elementCacheService != null) {
			
			elementCacheService.delAll();
			
			Element element1 = new Element();
			
			element1.setElementTypeId(1L);
			element1.setId(1L);
			
			Element element2 = new Element();
			
			element2.setElementTypeId(2L);
			element2.setId(2L);
			Key[] keys = elementCacheService.puts(ArrayUtils.toArray(element1,element2));
			
			assertEquals(2, keys.length);
						
			Element element1_get = (Element) elementCacheService.gets(new Key(element1))[0];
			
			assertEquals(element1, element1_get);
			
			Element element2_get = (Element) elementCacheService.gets(new Key(element2))[0];
			
			assertEquals(element2, element2_get);
		}
	}
	
	@Test
	public void fillObject() {
		
		ElementCacheService elementCacheService = new ElementCacheServiceImpl(RedisConnection.getInstance());
		
		if (elementCacheService != null) {
			
			elementCacheService.delAll();
			
			ElementType elementType1 = new ElementType();
			{
				elementType1.setId(1L);
			}
			
			Element element1 = new Element();
			{
				element1.setElementTypeId(1L);
				element1.setId(1L);
			}
			
			ElementTypeParam elementTypeParam1 = new ElementTypeParam();
			{
				elementTypeParam1.setParamTypeId(1l);
				elementTypeParam1.setElementTypeId(1L);
				elementTypeParam1.setId(1L);
			}
			
			ElementTypeParam elementTypeParam2 = new ElementTypeParam();
			{
				elementTypeParam2.setParamTypeId(1l);
				elementTypeParam2.setElementTypeId(1L);
				elementTypeParam2.setId(2L);
				
			}

			ElementValue elementValue1 = new ElementValue();
			{
				elementValue1.setElementTypeId(1L);
				elementValue1.setElementId(1L);
				elementValue1.setElementTypeParamId(1L);
				elementValue1.setParamTypeId(1L);
				elementValue1.setValue("Valor 1 de conf");
			}
			
			ElementValue elementValue2 = new ElementValue();
			{
				elementValue2.setElementTypeId(1L);
				elementValue2.setElementId(1L);
				elementValue2.setElementTypeParamId(2L);
				elementValue2.setParamTypeId(1L);
				elementValue2.setValue("Valor 2 de medida");
			}
			
			Key[] keys = elementCacheService.puts(ArrayUtils.toArray(elementType1, element1, elementTypeParam1, elementTypeParam2, elementValue1, elementValue2));
			
			assertEquals(6, keys.length);
			
			element1 = (Element) CacheModelHelper.fillObject(element1);
			
			System.out.println(element1);
		}
	}
	
	@Test
	public void findByPattern() {
			
		ElementCacheService elementCacheService = new ElementCacheServiceImpl(RedisConnection.getInstance());
		
		if (elementCacheService != null) {
			
			elementCacheService.delAll();
			
			Element element1 = new Element();
			
			element1.setElementTypeId(1L);
			element1.setId(1L);
			
			Element element2 = new Element();
			
			element2.setElementTypeId(2L);
			element2.setId(2L);
			Key[] keys = elementCacheService.puts(ArrayUtils.toArray(element1,element2));
			
			assertEquals(2, keys.length);
						
			Element elementPattern = new Element();
			
			elementPattern.setId(null);
			
			Key keyPattern = new Key(elementPattern);
			
			keys = elementCacheService.find(keyPattern);
			
			assertEquals(2, keys.length);			
		}
	}
	
	@Test
	public void subscription() {
		
		ElementCacheService elementCacheService = new ElementCacheServiceImpl(RedisConnection.getInstance());
		
		
		if (elementCacheService != null) {
			
			elementCacheService.delAll();
			
			Element element1 = new Element();
			
			element1.setElementTypeId(1L);
			element1.setId(1L);
			
			Element element2 = new Element();
			
			element2.setElementTypeId(2L);
			element2.setId(2L);
			Key[] keys = elementCacheService.puts(ArrayUtils.toArray(element1,element2));
			
			assertEquals(2, keys.length);
						
			Element elementPattern = new Element();
			
			elementPattern.setId(null);
			
			Key keyPattern = new Key(elementPattern);
			
			SubscriptorEventHandler subscriptorEventHandler = new SubscriptorEventHandler(keyPattern) {

				@Override
				public void onPMessage(String pattern, String channel, String message) {

					super.onPMessage(pattern, channel, message);
					
					System.out.println("onPMessage(" + pattern + ", " + channel + ", " + message +")");
				}

				@Override
				public void onPSubscribe(String pattern, int subscribedChannels) {

					super.onPSubscribe(pattern, subscribedChannels);
					
					System.out.println("onPSubscribe(" + pattern + ", " + subscribedChannels +")");
				}
				
				@Override
				public void onPUnsubscribe(String pattern, int subscribedChannels) {
				  
					super.onPUnsubscribe(pattern, subscribedChannels);
					
					System.out.println("onPUnsubscribe(" + pattern + ", " + subscribedChannels +")");
				}
			};
			
			SubscriptorManager subscriptor = elementCacheService.subscribe(subscriptorEventHandler);
			
			try {
				
				// Simulamos para que llegen eventos
				Thread.sleep(1000);
				
				element1.setDescription("Description element1");
				elementCacheService.put(element1);
				
				element2.setDescription("Description element2");
				elementCacheService.put(element2);
				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			subscriptor.stop();
			
			assertEquals(2, (Object) subscriptorEventHandler.getCounterMessage());
		}
	}
}
