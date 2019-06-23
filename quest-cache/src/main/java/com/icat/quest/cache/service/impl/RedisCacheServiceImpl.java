package com.icat.quest.cache.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.icat.quest.cache.service.RedisCacheService;
import com.icat.quest.cache.utils.RedisCacheConstants;
import com.icat.quest.cache.utils.RedisCacheTemplate;
import com.icat.quest.cache.vo.ApplicationClassesVo;
import com.icat.quest.cache.vo.ApplicationExceptionClassesVo;

@Service
public class RedisCacheServiceImpl implements RedisCacheService {

	@Autowired
	private RedisCacheTemplate<String, Object> redisTemplate;

	private static final Logger logger = LoggerFactory.getLogger(RedisCacheServiceImpl.class);

	@Override
	public Object getValue(final String key) {
		
		if (key == null) {
			logger.warn("key is null");
			return null;
		}

		if (!redisTemplate.hasKey(key)) {
		//	logger.warn("Key:" + key + " does not exist");
			return null;
		}

		if (redisTemplate.type(key).code().equalsIgnoreCase("hash")) {
			
			return redisTemplate.opsForHash().entries(key);
			
		} else if (redisTemplate.type(key).code().equalsIgnoreCase("list")) {
			
			return redisTemplate.opsForList().range(key, 0, redisTemplate.opsForList().size(key));
		
		}else if (redisTemplate.type(key).code().equalsIgnoreCase("set")) {
			
			return redisTemplate.opsForSet().members(key);
		
		}else
			return redisTemplate.opsForValue().get(key);
	}

	
	@Override
	public void remove(String key) {
		redisTemplate.delete(key);
	}
	
	
	@Override
	public void setValueStringFormat(final String key, final Object value) {
		if (key == null) {
			logger.warn("key is null");
			return;
		}

		if (value == null) {
			logger.warn("value is null");
			return;
		}
		redisTemplate.opsForValue().set(key, value);

	}

	@Override
	public void setValueForNewKey(final String key, final Object value) {

		if (key == null) {
			logger.warn("key is null");
			return;
		}

		if (value == null) {
			logger.warn("value is null");
			return;
		}

		if (redisTemplate.hasKey(key)) {
			redisTemplate.delete(key);
			logger.warn("Key:" + key + " does not exist");
		}

		addValueToKey(key, value);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addValueToKey(final String key, final Object value) {

		if (key == null) {
			logger.warn("key is null");
			return;
		}

		if (value == null) {
			logger.warn("value is null");
			return;
		}

		if (!redisTemplate.hasKey(key)) {
			logger.warn("Key:" + key + " does not exist");
			
		}

		if (value instanceof HashMap) {
			HashOperations<String, Object, Object> hashOperation = redisTemplate.opsForHash();

			HashMap<? extends Object, ? extends Object> value1 = (HashMap<? extends Object, ? extends Object>) value;

			hashOperation.putAll(key, value1);

		}

		else if (value instanceof ConcurrentHashMap) {
			HashOperations<String, Object, Object> hashOperation = redisTemplate.opsForHash();

			ConcurrentHashMap<? extends Object, ? extends Object> value1 = (ConcurrentHashMap<? extends Object, ? extends Object>) value;

			hashOperation.putAll(key, value1);

		} else if (value instanceof LinkedHashMap) {
			HashOperations<String, Object, Object> hashOperation = redisTemplate.opsForHash();

			HashMap<? extends Object, ? extends Object> value1 = (HashMap<? extends Object, ? extends Object>) value;

			hashOperation.putAll(key, value1);
			
		} else if (value instanceof ArrayList) {
			
			ListOperations<String, Object> listOperation = redisTemplate.opsForList();

			ArrayList arrayList = (ArrayList) value;

			listOperation.leftPushAll(key, arrayList);
		
		} else if (value instanceof LinkedList) {
			
			ListOperations<String, Object> listOperation = redisTemplate.opsForList();

			LinkedList linkedList = (LinkedList) value;

			listOperation.leftPushAll(key, linkedList);
			
		} else if (value instanceof HashSet) {
			
			SetOperations<String, Object> setOperation = redisTemplate.opsForSet();

			HashSet hashSet = (HashSet) value;

			setOperation.add(key, hashSet.toArray());
			
		} else if (value instanceof TreeSet) {
			
			SetOperations<String, Object> setOperation = redisTemplate.opsForSet();

			TreeSet treeSet = (TreeSet) value;

			setOperation.add(key, treeSet.toArray());
		}
		
		else {
			redisTemplate.opsForValue().set(key, value);
		}
	}

	@Override
	public void setNewValueWithExpire(final String key, final Object value, TimeUnit timeUnit, Long timeUnitValue) {

		if (key == null) {
			logger.warn("key is null");
			return;
		}

		if (value == null) {
			logger.warn("value is null");
			return;
		}

		if (timeUnit == null || timeUnitValue == null) {
			logger.warn("timeUnitValue or timeUnit is null");
			
		}

		setValueForNewKey(key, value);
		redisTemplate.expire(key, timeUnitValue, TimeUnit.SECONDS);
	}

	@Override
	public Object getSingleMapValueByKey(final String key, final Object hashKey) {
		if (key == null) {
			logger.warn("key is null");
			return null;
		}

		if (hashKey == null) {
			logger.warn("hashKeys is null");
			return null;
		}

		if (!redisTemplate.hasKey(key)) {
			logger.warn("Key:" + key + " does not exist");
			return null;
		}

		if (redisTemplate.type(key).code().equalsIgnoreCase("hash")) {
			return redisTemplate.opsForHash().get(key, hashKey);
		} else {
			logger.warn("HashValue not available at given key:" + key);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getMultipleMapValueByKeys(final String key, final Object hashKeys) {
		if (key == null) {
			logger.warn("key is null");
			return null;
		}

		if (hashKeys == null) {
			logger.warn("hashKeys is null");
			return null;
		}

		if (!redisTemplate.hasKey(key)) {
			logger.warn("Key:" + key + " does not exist");
			return null;
		}

		if (redisTemplate.type(key).code().equalsIgnoreCase("hash")) {
			if (hashKeys instanceof ArrayList) {
				return redisTemplate.opsForHash().multiGet(key, (Collection<Object>) hashKeys);
			}
		} else {
			logger.warn("HashValue not available at given key:" + key);
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object delMapValueByHashKeys(final String key, final Object hashKeys) {
		if (key == null) {
			logger.warn("key is null");
			return null;
		}

		if (hashKeys == null) {
			logger.warn("hashKeys is null");
			return null;
		}

		if (!redisTemplate.hasKey(key)) {
			logger.warn("Key:" + key + " does not exist");
			return null;
		}

		if (redisTemplate.type(key).code().equalsIgnoreCase("hash")) {
			if (hashKeys instanceof ArrayList) {
				return redisTemplate.opsForHash().delete(key, ((ArrayList) hashKeys).toArray());
			}
		} else {
			logger.warn("HashValue not available at given key:" + key);
		}

		return null;
	}

	@Override
   public Object deleteEntryFromMapByHashkey(final String redisKey, final Object hashKey) {
		
		if (redisKey == null) {
			logger.warn("redisKey is null");
			return null;
		}

		if (hashKey == null) {
			logger.warn("hashKey is null");
			return null;
		}

		if (!redisTemplate.hasKey(redisKey)) {
			logger.warn("redisKey :" + redisKey + " does not exist");
			return null;
		}

		if (redisTemplate.type(redisKey).code().equalsIgnoreCase("hash")) {
			
			return redisTemplate.opsForHash().delete(redisKey, hashKey);
		
		} else {
			logger.warn("HashValue not available at given redisKey:" + redisKey);
		}

		return null;
	 }
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object deleteEntriesFromMapByHashkeys(final String redisKey, final List hashKeys) {
		
		if (redisKey == null) {
			logger.warn("redisKey is null");
			return null;
		}

		if (hashKeys == null) {
			logger.warn("hashKeys is null");
			return null;
		}

		if (!redisTemplate.hasKey(redisKey)) {
			logger.warn("redisKey:" + redisKey + " does not exist");
			return null;
		}

		if (redisTemplate.type(redisKey).code().equalsIgnoreCase("hash")) {
			
			return redisTemplate.opsForHash().delete(redisKey, hashKeys.toArray());
		
		} else {
			logger.warn("HashValue not available at given redisKey :" + redisKey);
		}

		return null;
	}
	
	
	@Override
	 public Object deleteElementFromList(final String redisKey, final Object element) {
			
			if (redisKey == null) {
				logger.warn("redisKey is null");
				return null;
			}

			if (element == null) {
				logger.warn("listValues is null");
				return null;
			}

			if (!redisTemplate.hasKey(redisKey)) {
				logger.warn("redisKey:" + redisKey + " does not exist");
				return null;
			}

			if (redisTemplate.type(redisKey).code().equalsIgnoreCase("list")) {
				
				redisTemplate.opsForList().remove(redisKey, 1, element);

				return element;

			} else {
				logger.warn("listValues not available at given redisKey:" + redisKey);
			}

			return null;
		}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List deleteElementsFromList(final String redisKey, final List elements) {
		
		if (redisKey == null) {
			logger.warn("redisKey is null");
			return null;
		}

		if (elements == null) {
			logger.warn("listValues is null");
			return null;
		}

		if (!redisTemplate.hasKey(redisKey)) {
			logger.warn("redisKey:" + redisKey + " does not exist");
			return null;
		}

		if (redisTemplate.type(redisKey).code().equalsIgnoreCase("list")) {
			
			elements.stream().forEach(value -> {
					redisTemplate.opsForList().remove(redisKey, 1, value);
				});

			return elements;

		} else {
			logger.warn("listValues not available at given redisKey:" + redisKey);
		}

		return null;
	}

	
	@Override
	public Object deleteElementFromSet(final String redisKey, final Object element) {
		if (redisKey == null) {
			logger.warn("redisKey is null");
			return null;

		}

		if (element == null) {
			logger.warn("setValues is null");
			return null;
		}

		if (!redisTemplate.hasKey(redisKey)) {
			logger.warn("redisKey:" + redisKey + " does not exist");
			return null;
		}

		if (redisTemplate.type(redisKey).code().equalsIgnoreCase("set")) {
			
			 redisTemplate.opsForSet().remove(redisKey, element);
			
			return element;

		} else {
			logger.warn("HashValue not available at given redisKey:" + redisKey);
		}

		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Set deleteElementsFromSet(final String redisKey, final Set elements) {
		if (redisKey == null) {
			logger.warn("redisKey is null");
			return null;

		}

		if (elements == null) {
			logger.warn("setValues is null");
			return null;
		}

		if (!redisTemplate.hasKey(redisKey)) {
			logger.warn("redisKey:" + redisKey + " does not exist");
			return null;
		}

		if (redisTemplate.type(redisKey).code().equalsIgnoreCase("set")) {
			
			redisTemplate.opsForSet().remove(redisKey, elements.toArray());

			return elements;

		} else {
			logger.warn("HashValue not available at given redisKey:" + redisKey);
		}

		return null;
	}
	
	

	@Override
	public void publish(final String message) {

		redisTemplate.execute(new RedisCallback<Long>() {
			@SuppressWarnings("unchecked")
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.publish(
						((RedisSerializer<String>) redisTemplate.getKeySerializer()).serialize("queue"),
						((RedisSerializer<Object>) redisTemplate.getValueSerializer()).serialize(message));
			}
		});
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ApplicationExceptionClassesVo getCachedRecordForApplicationExceptionClasses(String exceptionHandlerName) {

		Object applicationExceptionClassesObj = redisTemplate.opsForValue().get(RedisCacheConstants.CACHE_KEY_APPLICATION_EXCEPTION_CLASSES_LIST);
		List<ApplicationExceptionClassesVo> applicationExceptionClassesVos = new ArrayList<ApplicationExceptionClassesVo>();
		if (applicationExceptionClassesObj != null)
			applicationExceptionClassesVos.addAll((List<ApplicationExceptionClassesVo>) applicationExceptionClassesObj);

		ApplicationExceptionClassesVo applicationExceptionClassesVo = null;
		for (ApplicationExceptionClassesVo applicationExceptionClassesVoLocal : applicationExceptionClassesVos)
			if (applicationExceptionClassesVoLocal.getExceptionClassHandler().equalsIgnoreCase(exceptionHandlerName)) {

				applicationExceptionClassesVo = applicationExceptionClassesVoLocal;
				break;
			}
		return applicationExceptionClassesVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationClassesVo getCachedRecordForApplicationClasses(String className) {

		Object applicationClassesObj =  redisTemplate.opsForValue().get(RedisCacheConstants.CACHE_KEY_APPLICATION_CLASSES_LIST);
		List<ApplicationClassesVo> applicationClassesVos = new ArrayList<ApplicationClassesVo>();
		if (applicationClassesObj != null)
			applicationClassesVos.addAll((List<ApplicationClassesVo>) applicationClassesObj);

		ApplicationClassesVo applicationClassesVo = null;
		for (ApplicationClassesVo applicationClassesVoLocal : applicationClassesVos)
			if (applicationClassesVoLocal.getClassName().equalsIgnoreCase(className)) {

				applicationClassesVo = applicationClassesVoLocal;
				break;
			}
		return applicationClassesVo;
	}



	
	

}
