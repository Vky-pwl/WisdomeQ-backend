package com.icat.quest.generic.dao.framework.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.icat.quest.generic.dao.framework.CustomGenericType;

public class CustomGenericTypeImpl<T> implements CustomGenericType<T>{
	
	private Class<T> type;
	
	 public CustomGenericTypeImpl(Class<T> type) {
		// TODO Auto-generated constructor stub
		 this.type = type;
	}

	@Override
	public T converter(Object[] objArray, List<String> excludedFieldNames){
		if(objArray == null) {
			return null;
		}
		T object =null;
		try {
		object = type.newInstance();
		int i = 0;
		for (Field field : type.getDeclaredFields()) {
				if ("serialVersionUID".equalsIgnoreCase(field.getName()) || (excludedFieldNames != null && excludedFieldNames.contains(field.getName()))) {
					continue;
				}
				field.setAccessible(true);
				if(objArray[i] != null && !field.getType().equals(objArray[i].getClass())) {
					setValue(field, object, objArray[i]);	
				} else {
				field.set(object, objArray[i]);
				}
				i++;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return object;
	}

	private void setValue(Field field, Object obj,Object value ) {
		try {
		if(field.getType().equals(Integer.class)) {
			field.set(obj, Integer.parseInt(value.toString()));
		} else if(field.getType().equals(Long.class)) {
			field.set(obj, Long.parseLong(value.toString()));
		} else if(field.getType().equals(Float.class)) {
			field.set(obj, Float.parseFloat(value.toString()));
		} else if(field.getType().equals(Boolean.class)) {
			if(value.toString().equals("1")) {
			field.set(obj, true);
			} else {
				field.set(obj, false);	
			}
		} else if(field.getType().equals(BigInteger.class)) {
			field.set(obj, new BigInteger(value.toString()));
		} else if(field.getType().equals(BigDecimal.class)) {
			field.set(obj, new BigDecimal(value.toString()));
		} else if(field.getType().equals(String.class)) {
			field.set(obj, value.toString());
		} 
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
