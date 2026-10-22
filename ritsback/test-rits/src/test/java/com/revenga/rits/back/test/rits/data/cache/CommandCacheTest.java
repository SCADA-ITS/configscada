package com.revenga.rits.back.test.rits.data.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Instant;
import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.model.Cmd;
import com.revenga.rits.back.cache.core.service.impl.CommandCache;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.SetMeasuresCommand;

import redis.clients.jedis.Jedis;

public class CommandCacheTest {

	public static Long countEvents = 0L;

	@Test
	public void connect() {
		
		Jedis jedis = RedisConnection.getInstance().getConnectionPool().getResource();
		assertNotNull(jedis);
	}

	@Test
	public void putsAndDel() {
			
		CommandCache cmdCache = new CommandCache(RedisConnection.getInstance());
		
		if (cmdCache != null) {
			
			cmdCache.delAll();
			
			SetMeasuresCommand setMeasuresCommand1 = new SetMeasuresCommand();
			setMeasuresCommand1.setCreatedAt(Instant.now().toEpochMilli());
			
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
			
			setMeasuresCommand1.setElementValues(new ArrayList<ElementValue>());
			setMeasuresCommand1.getElementValues().add(elementValue1);
			setMeasuresCommand1.getElementValues().add(elementValue2);
					
			SetMeasuresCommand setMeasuresCommand2 = new SetMeasuresCommand();
			setMeasuresCommand2.setCreatedAt(Instant.now().toEpochMilli());
					
			Cmd[] cmds = cmdCache.puts(ArrayUtils.toArray(setMeasuresCommand1, setMeasuresCommand2));
			
			assertEquals(2, cmds.length);
			
			Cmd cmdPattern = new Cmd(new SetMeasuresCommand());
			
			cmds = cmdCache.del(cmdPattern);
			
			assertEquals(2, cmds.length);
		}
	}
	
	@Test
	public void getElements() {
			
		CommandCache cmdCache = new CommandCache(RedisConnection.getInstance());
		
		if (cmdCache != null) {
			
			cmdCache.delAll();
			
			SetMeasuresCommand setMeasuresCommand1 = new SetMeasuresCommand();
			setMeasuresCommand1.setCreatedAt(Instant.now().toEpochMilli());
			
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
			
			setMeasuresCommand1.setElementValues(new ArrayList<ElementValue>());
			setMeasuresCommand1.getElementValues().add(elementValue1);
			setMeasuresCommand1.getElementValues().add(elementValue2);
							
			Cmd[] cmds = cmdCache.puts(ArrayUtils.toArray(setMeasuresCommand1));
			
			assertEquals(1, cmds.length);
						
			SetMeasuresCommand setMeasuresCommand1_get = (SetMeasuresCommand) cmdCache.gets(new Cmd(setMeasuresCommand1))[0];
			
			assertEquals(setMeasuresCommand1, setMeasuresCommand1_get);
		}
	}
}
