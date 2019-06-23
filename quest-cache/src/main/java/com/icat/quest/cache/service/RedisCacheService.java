package com.icat.quest.cache.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.icat.quest.cache.vo.ApplicationClassesVo;
import com.icat.quest.cache.vo.ApplicationExceptionClassesVo;

public interface RedisCacheService {

	/**
	 * Get value By Key
	 * @param key
	 * @return
	 */
	Object getValue(String key);

	//void publish(String message);

	/**
	 *  set value with expireDate
	 * Set Time with Expire
	 * choose proper TimeUnit format and give its value
	 * @param key
	 * @param value
	 * @param timeUnit
	 * @param sec
	 */
	void setNewValueWithExpire(String key, Object value,TimeUnit timeUnit,Long timeUnitValue);

	
	/**
	 * set value without expireDate
	 * @param key
	 * @param value
	 */
	void setValueForNewKey(String key, Object value);

	void publish(String message);

	/**
	 * Add value to existing key
	 * @param key
	 * @param value
	 */
	void addValueToKey(String key, Object value);

	/**
	 * used to store whole object(map,list...) at once.it is high storage
	 * @param key
	 * @param value
	 */			
	void setValueStringFormat(String key, Object value);

	/**
	 * Geting value At hashKey
	 * @param key
	 * @param hashKey
	 * @return
	 */
	Object getMultipleMapValueByKeys(String key, Object hashKeys);

	/**
	 * delete value at key
	 * @param key
	 * @param hashKeys
	 * @return
	 */
	Object delMapValueByHashKeys(String key, Object hashKeys);

	Object getSingleMapValueByKey(String key, Object hashKeys);

	

	Object deleteEntryFromMapByHashkey(final String redisKey, final Object hashKey);
	
	Object deleteEntriesFromMapByHashkeys(final String redisKey, @SuppressWarnings("rawtypes") final List hashKeys);
	
	
	
	Object deleteElementFromList(final String redisKey, final Object element);
	

	@SuppressWarnings("rawtypes")
	List deleteElementsFromList(final String redisKey,  final List elements);
	

	Object deleteElementFromSet(final String redisKey, final Object element);
	
	

	@SuppressWarnings("rawtypes")
	Set deleteElementsFromSet(final String redisKey,  final Set elements);

	ApplicationExceptionClassesVo getCachedRecordForApplicationExceptionClasses(String exceptionHandlerName);

	ApplicationClassesVo getCachedRecordForApplicationClasses(String className);

	void remove(String key);
	
	
}
