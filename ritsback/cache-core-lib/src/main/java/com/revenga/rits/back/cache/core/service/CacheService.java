package com.revenga.rits.back.cache.core.service;

/**
 * 
 * Interface to manage operations with types in cache
 * 
 * @author alvaro
 *
 */
public interface CacheService <T, U> {

	/**
	 * 
	 * Put basic element into cache service
	 * 
	 * @param basicElement
	 * @return Key of the basic element
	 */
	public U put(T object);
//	/**
//	 * 
//	 * Put basic element into cache for a expire time in seconds
//	 * 
//	 * @param basicElement
//	 * @param expire time in seconds
//	 * @return Key of the basic element
//	 */
//	public U put(T object, int expire);
//	/**
//	 * 
//	 * Put basic element into cache and expire at time long expireAt
//	 * 
//	 * @param basicElement
//	 * @param expireAt long time to expire key
//	 * @return Key of the basic element
//	 */
//	public U put(T object, long expireAt);
	/**
	 * 
	 * Put list of basic elements into cache service
	 * 
	 * @param basicElements
	 * @return Array of keys for basic elements
	 */
	public U[] puts(T [] objects);
//	/**
//	 * 
//	 * Put list of basic elements into cache service for a expire time in seconds
//	 * 
//	 * @param basicElements
//	 * @param expire time in seconds
//	 * @return Array of keys for basic elements
//	 */
//	public U[] puts(T [] objects, int expire);
//	/**
//	 * 
//	 * Put list of basic elements into cache and expire at time long expireAt
//	 * 
//	 * @param basicElements
//	 * @param expireAt long time to expire key
//	 * @return Array of keys for basic elements
//	 */
//	public U[] puts(T [] objects, long expireAt);
//	/**
//	 * 
//	 * Set a timeout on the specified key pattern. 
//	 * After the timeout the key will be automatically deleted by the server. 
//	 * A key with an associated timeout is said to be volatile in Redis terminology.
//	 * 
//	 * @param key of basic element
//	 * @param expire time in seconds
//	 * @return Array of keys
//	 */
//	public U[] expire(U model, int expire);
//	/**
//	 * 
//	 * Set a target timeout on the specified key pattern. 
//	 * After the timeout the key will be automatically deleted by the server. 
//	 * A key with an associated timeout is said to be volatile in Redis terminology.
//	 * 
//	 * @param key of basic element
//	 * @param expireAt target time to expire keys. UnixTime format
//	 * @return Array of keys
//	 */
//	public U[] expire(U model, long expireAt);
//	/**
//	 * 
//	 * Set a timeout on the specified keys pattern. 
//	 * After the timeout the key will be automatically deleted by the server. 
//	 * A key with an associated timeout is said to be volatile in Redis terminology.
//	 * 
//	 * @param keys array of basic elements
//	 * @param expire time in seconds
//	 * @return Array of keys
//	 */
//	public U[] expires(U[] models, int expire);
//	/**
//	 * 
//	 * Set a target timeout on the specified keys pattern. 
//	 * After the timeout the key will be automatically deleted by the server. 
//	 * A key with an associated timeout is said to be volatile in Redis terminology.
//	 * 
//	 * @param keys array of basic elements
//	 * @param expireAt target time to expire keys. UnixTime format
//	 * @return Array of keys
//	 */
//	public U[] expires(U[] models, long expireAt);
//	/**
//	 * 
//	 * Undo a expire at turning the expire key into a normal key from key pattern
//	 * 
//	 * @param key pattern to undo a expire
//	 * @return Array of keys
//	 */
//	public U[] persist(U model);
//	/**
//	 * 
//	 * Undo a expire at turning the expire key into a normal key for array of keys
//	 * 
//	 * @param Array of keys to undo expire
//	 * @return Array of keys
//	 */
//	public U[] persists(U[] models);
	
	public T get(U model);
	/**
	 * 
	 * Gets basic elements instance from a pattern key
	 * 
	 * @param pattern
	 * @return BasicElement array instance
	 * 
	 */
	public T [] gets(U model);
	/**
	 * 
	 * Gets array of basic elements that contains fields with given values.
	 * <p>This method first search by keys IDX:* in cache
	 * <p>Otherwise, a full scan is performed on all format keys with pattern DAT:[clazz]:*
	 * 
	 * @param clazz Type of BasicElement
	 * @param fieldName Name of field
	 * @param fieldValue String value of field
	 * 
	 * @return Array of basic elements
	 */
	public T[] gets(Class<?> clazz, String fieldName, String fieldValue);
	/**
	 * 
	 * Get basic element instance from key
	 * 
	 * @deprecated this function can be removed in future versions
	 * <p>replaced by: {@code public BasicElement[] gets(Key key)}.
     * 
	 * @param key
	 * @return BasicElement instance
	 * 
	 */
	/**
	 * 
	 * Del Keys matches with key
	 * 
	 * @param key
	 * @return Array of keys
	 */
	public U[] del(U model);
	/**
	 * 
	 * Del all keys in current database cache
	 *
	 */
	public void delAll();
	/**
	 * 
	 * Find Keys matches with key
	 * 
	 * @param key
	 * @return Array of keys
	 */
	public U[] find(U model);
		
	public T getFromChannel(String channel);
	
	public T getDeleted(String channel);
}
