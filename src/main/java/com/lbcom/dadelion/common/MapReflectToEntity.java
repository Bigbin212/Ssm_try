package com.lbcom.dadelion.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @CopyRight ©1995-2016: 苏州科达科技股份有限公司 
 * @Project： 
 * @Module：
 * @Description 
 * @Author  liubin
 * @Date 2017年8月8日 下午2:29:08 
 * @Version 1.0 
 */
public class MapReflectToEntity<T>{
	
	private Logger log = LoggerFactory.getLogger(MapReflectToEntity.class);
	
	private Class<T> cls;
	
	public MapReflectToEntity(Class<T> t){
		this.cls = t;
	}
	
	/**
	 * 将map转化为Entity
	 * @param map (map)
	 * @param cls (Entity的class)
	 * @return Entity
	 * @throws InstantiationException 无法实例化类型异常
	 * @throws IllegalAccessException 无法创建实例化对象（私有构造）
	 */
	public T mapReflectEntity(Map<String, Object> map)
			throws InstantiationException, IllegalAccessException {
		Field[] fields = cls.getDeclaredFields();
		Method[] methods = cls.getMethods();
		T t = cls.newInstance();
		for (Field field : fields) {
			Set<String> keys = map.keySet();
			for (String key : keys) {
				// 如果hashMap的key与field的名字相同
				if (field.getName().toUpperCase().equals(key.toUpperCase())) {
					for (Method method : methods) {
						if (isSetter(method, field)) {
							String value = map.get(key).toString();
							try {
								Object rtnObject = setValueByType(value,field.getType());
								method.invoke(t, rtnObject);
							} catch (IllegalArgumentException e) {
								log.info("反射方法"+method.getName()+"参数传入异常", e);
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								log.info("反射方法"+method.getName()+"赋值异常", e);
								e.printStackTrace();
							}
						}
					}
					break;
				}
			}
		}
		return t;
	}
	
	/**
	 * 将list<map>按照实际存放的实体类  转化为 list<Entity>
	 * @param listMaps 传入的map集合
	 * @param cls map里存放的实体类型
	 * @return 实体类集合
	 */
	public List<T> listMapReflectListEntitys(List<Map<String, Object>> listMaps){
		List<T> lists = new ArrayList<T>();
		for(Map<String,Object> map:listMaps){
			try {
				lists.add(mapReflectEntity(map));
			} catch (InstantiationException e) {
				log.info("map转化entity异常",e);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				log.info("map转化entity异常",e);
				e.printStackTrace();
			}
		}
		return lists;
	}
	
	/**
	 * 将list<map>按照实际存放的实体类  转化为 list<Entity>
	 * @param listMaps 传入的map集合
	 * @param cls map里存放的实体类型
	 * @return 实体类集合
	 */
	public List<T> listMapReflectListEntitysEX(List<Map<String, Object>> listMaps){
		List<T> lists = new ArrayList<T>();
		for(Map<String,Object> map:listMaps){
			try {
				lists.add(mapReflectEntityEX(map));
			} catch (InstantiationException e) {
				log.info("map转化entity异常",e);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				log.info("map转化entity异常",e);
				e.printStackTrace();
			}
		}
		return lists;
	}
	
	/**
	 * 将map转化为Entity
	 * @param map (map)
	 * @param cls (Entity的class)
	 * @return Entity
	 * @throws InstantiationException 无法实例化类型异常
	 * @throws IllegalAccessException 无法创建实例化对象（私有构造）
	 */
	public T mapReflectEntityEX(Map<String, Object> map)
			throws InstantiationException, IllegalAccessException {
		List<Field> fieldList = new ArrayList<Field>();
		List<Method> methodList = new ArrayList<Method>();
		Field[] fields = cls.getDeclaredFields();
		Collections.addAll(fieldList, fields);
		Method[] methods = cls.getMethods();
		Collections.addAll(methodList, methods);
		Class<?> clazz = cls.getSuperclass() ;
		if(null !=clazz && clazz != Object.class){
			Field[] superFields = clazz.getDeclaredFields();
			 Collections.addAll(fieldList, superFields);
			 Method[]  superMethods = clazz.getMethods();
			 Collections.addAll(methodList, superMethods);
		}
		
		T t = cls.newInstance();
		for (Field field : fieldList) {
			Set<String> keys = map.keySet();
			for (String key : keys) {
				// 如果hashMap的key与field的名字相同
				if (field.getName().toUpperCase().equals(key.toUpperCase())) {
					for (Method method : methodList) {
						if (isSetter(method, field)) {
							String value = map.get(key).toString();
							try {
								Object rtnObject = setValueByType(value,field.getType());
								method.invoke(t, rtnObject);
							} catch (IllegalArgumentException e) {
								log.info("反射方法"+method.getName()+"参数传入异常", e);
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								log.info("反射方法"+method.getName()+"赋值异常", e);
								e.printStackTrace();
							}
						}
					}
					break;
				}
			}
		}
		return t;
	}
	public Object setValueByType(String elder ,Class<?> cls){
		Object newer = null;
		if(cls == String.class){										//String类型
			newer = elder;
		}else if(cls == int.class || cls == Integer.class){				// int / Integer
			newer = Integer.valueOf(elder);
		}else if(cls == Short.class){
			newer = Short.valueOf(elder);
		}else if(cls == BigDecimal.class){								//BIGDecimal
			newer = BigDecimal.valueOf(Long.valueOf(elder));
		}else if(cls == Long.class){									//long
			newer = Long.valueOf(elder);
		}else if(cls == Double.class){									//double
			newer = Double.valueOf(elder);
		}else if(cls == Date.class){									//date
			try {
				newer = DateUtil.getDateByFormateAndStringDate("yyyy-MM-dd HH:mm:ss", elder);
			} catch (ParseException e) {
				log.info("日期转换异常！", e);
				e.printStackTrace();
			}
		}else{															//默认字符串
			newer = elder;
		}
		return newer;
	}
	
	public boolean isSetter(Method method,Field field){
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("SET").append(field.getName());
		return sBuffer.toString().toUpperCase().equals(method.getName().toUpperCase());
	}
}
