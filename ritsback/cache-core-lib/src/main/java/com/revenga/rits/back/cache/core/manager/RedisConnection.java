package com.revenga.rits.back.cache.core.manager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.cache.core.service.impl.ProcessMonitor;
import com.revenga.rits.back.data.core.annotation.DbConfig;
import com.revenga.rits.back.data.core.util.ClassesUtil;
import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Log4j2
public class RedisConnection 
{
	static RedisConnection instance;
	
	private static final String REDIS_PROPERTIES = "redis.properties";
	
//	JedisPool jedisPool = null;
    JedisPoolConfig poolConfig = null;
    
    
    Map<Pair<Long, Integer>, JedisPool> mapPools = Collections
			.synchronizedMap(new HashMap<>());
    
    Map<Class<?>, Integer> mapClassDB = Collections
			.synchronizedMap(new HashMap<>());
    
	public static final String HOST = ConfigProperty.getInstance().getProperty(REDIS_PROPERTIES, "redis.host");
	public static final Integer PORT = Integer
			.valueOf(ConfigProperty.getInstance().getProperty(REDIS_PROPERTIES, "redis.port"));
	public static final Integer TIMEOUT = Integer
			.valueOf(ConfigProperty.getInstance().getProperty(REDIS_PROPERTIES, "redis.timeout"));
	public static final String PASSWORD = ConfigProperty.getInstance().getProperty(REDIS_PROPERTIES, "redis.password");
	public static final Integer DATABASE = Integer
			.valueOf(ConfigProperty.getInstance().getProperty(REDIS_PROPERTIES, "redis.database"));
	public static final Integer RECONNECTION_ATTEMPTS = Integer
			.valueOf(ConfigProperty.getInstance().getProperty(REDIS_PROPERTIES, "redis.reconnection_attempts"));
	public static final Integer RECONNECTION_TIMEOUT_MS = Integer
			.valueOf(ConfigProperty.getInstance().getProperty(REDIS_PROPERTIES, "redis.reconnection_timeout_ms"));
	
	private static final String PACKAGE_DATA_CORE_MODEL = "com.revenga.rits.back.data.core.model";
    
    public enum Message {
        
    	HSET, HDEL, SET, DEL, EXPIRE, EXPIRED;

        private final String value;

        Message() {
        	
        	value = this.name().toLowerCase();
        }

        public String getValue() {
          return value;
        }
      }
    
    @Getter
    private ProcessMonitor processMonitor;
    
	public static RedisConnection getInstance()
	{
		if (instance == null) {
			
			instance = new RedisConnection();
		}
		
		return instance;
	}
	
	private RedisConnection() {
		
		associateClassesDB();
		
    	poolConfig = new JedisPoolConfig();
    	getConnectionPool();
        
    	processMonitor = new ProcessMonitor(this);
    	
        (new Thread(processMonitor, "Thread-ProcMon")).start(); 
	}
	
	private void associateClassesDB() {
		
		try {
			
			Class<?>[] clazzes = ClassesUtil.getClassesForPackage(PACKAGE_DATA_CORE_MODEL);
						
			if (!ArrayUtils.isEmpty(clazzes)) {
				
				for (Class<?> clazz : clazzes) {
					
					DbConfig dbConfig = clazz.getAnnotation(DbConfig.class);
					
					if (dbConfig != null && NumberUtils.isCreatable(dbConfig.db())) {
					
						mapClassDB.put(clazz, Integer.parseInt(dbConfig.db()));
					}
					else {
					
						mapClassDB.put(clazz, DATABASE);
					}
				}
			}
			
		} catch (ClassNotFoundException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	public JedisPool getConnectionPool() {
		
		return getConnectionPool(null);
	}

	/**
	 * 
	 * Get redis connection pool object
	 * 
	 * @return Redis connection pool object
	 */
	public JedisPool getConnectionPool(Class<?> clazz) {
		
		JedisPool jedPool = null;
		Long threadId = Long.valueOf(Thread.currentThread().getId());
		Integer db = DATABASE;
		Pair<Long, Integer> refMapPool = null;
		
		if (clazz != null && mapClassDB.containsKey(clazz)) {
			
			db = mapClassDB.get(clazz);
		}
		
		refMapPool = new ImmutablePair<>(threadId, db);
		
		if (!MapUtils.isEmpty(mapPools)) {
			
			jedPool = mapPools.get(refMapPool);
		}

		if (jedPool == null) {
			
			jedPool = newConnectionPool(db);
			mapPools.put(refMapPool, jedPool);
		}
		
		return jedPool;
	}
		
	/**
	 * 
	 * Create new redis connection pool
	 * 
	 * @return new Redis connection pool
	 */
	public JedisPool newConnectionPool(Integer db) {
		
		
		return new JedisPool(poolConfig, RedisConnection.HOST, RedisConnection.PORT,
				RedisConnection.TIMEOUT, RedisConnection.PASSWORD, db);
	}
	
	/**
	 * 
	 * Close redis connection pool session
	 * 
	 * @throws Exception
	 */
//	public void disconnect() throws Exception {
//
//		JedisPool jedPool = null;
//		Long threadId = Long.valueOf(Thread.currentThread().getId());
//		
//		if (!MapUtils.isEmpty(mapPools)) {
//		
//			jedPool = mapPools.remove(threadId);
//			
//			if (jedPool != null) {
//
//				jedPool.close();
//			}
//		}
//	}
}
