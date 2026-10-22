package com.revenga.rits.back.cache.core.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.cache.core.helper.CacheModelHelper;
import com.revenga.rits.back.cache.core.manager.RedisConnection;
import com.revenga.rits.back.cache.core.model.Cmd;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.Jedis;

@Log4j2
public class CommandCache {

	private static final String REDIS_PROPERTIES = "redis.properties";
	private RedisConnection redisConnection;
	
	@Getter
	private boolean commandCacheBin = false;
	
	public CommandCache(RedisConnection redisConnection) {
		
		super();
		this.redisConnection = redisConnection;
		
		this.commandCacheBin = Boolean
				.parseBoolean(ConfigProperty.getInstance().getProperty(REDIS_PROPERTIES, "redis.commandcache_bin")); 

	}

	public Cmd publish(Command command) {
		
		Cmd cmd = null;
		String message = null;
		byte[] messageBin = null;
		
		try (Jedis jedis = redisConnection.getConnectionPool().getResource()) {

			command.setCreatedAt(Instant.now().toEpochMilli());
			
			cmd = new Cmd(command);
				
			if (commandCacheBin) {
				
				messageBin = CacheModelHelper.getBin(command);
				
				jedis.publish(cmd.getClazz().getSimpleName().getBytes(), messageBin);
			}
			else {
			
				message = CacheModelHelper.getJson(command);
				
				jedis.publish(cmd.getClazz().getSimpleName(), message);
			}
			

        } catch (Exception e) {
            
        	log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			
			cmd = null;
        }
		
		return cmd;
	}
	
	public Cmd publish(String channel, Command command) {
		
		Cmd cmd = null;
		String message = null;
		byte[] messageBin = null;
		
		try (Jedis jedis = redisConnection.getConnectionPool().getResource()) {

			command.setCreatedAt(Instant.now().toEpochMilli());
			
			cmd = new Cmd(command);
				
			if (commandCacheBin) {
				
				messageBin = CacheModelHelper.getBin(command);
				
				jedis.publish(channel.getBytes(), messageBin);
			}
			else {
			
				message = CacheModelHelper.getJson(command);
				
				jedis.publish(channel, message);
			}
			

        } catch (Exception e) {
            
        	log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
			
			cmd = null;
        }
		
		return cmd;
	}
	
	public Cmd[] publish(Command[] commands) {
		
		List<Cmd> cmds = new ArrayList<>();
		
		if (!ArrayUtils.isEmpty(commands)) {
			
			for (Command command : commands) {
				
				cmds.add(publish(command));
			}
		}
		
		return cmds.stream().toArray(Cmd[]::new);
	}
	
	public Command get(Class<? extends Command> clazz, String message) {
		
		Command command = null;
		
		try (Jedis jedis = redisConnection.getConnectionPool().getResource()) {
			
			command = (Command) CacheModelHelper.getObject(clazz, message);

        } catch (Exception e) {
            
        	log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
        }
		
		return command;
	}
	
	public Command get(byte[] message) {
		
		return SerializationUtils.deserialize(message);
	}
}
