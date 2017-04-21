package com.lbcom.dadelion.redis;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lbcom.dadelion.redis.factory.RedisFactory;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

/**    
 *     
 * 类名称：DadelionRedis      
 *     
 */
public class DadelionRedis implements  JedisCommands{

	//日志对象
	private static Logger logger = LoggerFactory.getLogger(DadelionRedis.class);

	//单机redis对象
	private static  JedisPool jedisPool = null;
	//集群redis对象
	private static  JedisCluster jedisCluster = null;
	
	private Jedis jedis = null;
	
	/**
	 * 使用系统变量
	 * getInstance(这里用一句话描述这个方法的作用)        
	 * @Exception 异常对象
	 */
	public static DadelionRedis  getInstance(){
		
		//如果两个对象都为空，则需要进行初始化
		if(null == jedisPool && null==jedisCluster){
			Object redisObject = RedisFactory.getRedisClusterInstance();
			if(null == redisObject){
				throw  new RuntimeException("redis 启动异常!!!");
			}
			
			if(redisObject instanceof JedisPool){
				jedisPool = (JedisPool) redisObject;
			}else if (redisObject instanceof JedisCluster) {
				jedisCluster = (JedisCluster) redisObject;
			}
		}
		return new DadelionRedis();
	}
	
	
	/**
	 * 传入ip地址参数
	 * getInstance(这里用一句话描述这个方法的作用)    
	 * @Exception 异常对象
	 */
	public static DadelionRedis  getInstance(String jedisClusterConfig){
		
		//如果两个对象都为空，则需要进行初始化
		if(null == jedisPool && null==jedisCluster){
			Object redisObject = RedisFactory.getRedisClusterInstance(jedisClusterConfig);
			if(null == redisObject){
				throw  new RuntimeException("redis 启动异常!!!");
			}
			
			if(redisObject instanceof JedisPool){
				jedisPool = (JedisPool) redisObject;
			}else if (redisObject instanceof JedisCluster) {
				jedisCluster = (JedisCluster) redisObject;
			}
		}
		return new DadelionRedis();
	}
	
	/* 
	 * 获取Jedis对象
	 */
	public Object getJedisObject() {
		if(null != jedisPool){		
			return jedisPool.getResource();
		}else if(null !=jedisCluster){
			return jedisCluster;
		}else{
			return null;
		}
	}
	
	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#set(java.lang.String, java.lang.String)    
	 */
	@Override
	public String set(String key, String value) {
		String ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.set(key, value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.set(key, value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#set(java.lang.String, java.lang.String, java.lang.String, java.lang.String, long)    
	 */
	@Override
	public String set(String key, String value, String nxxx, String expx,
			long time) {
		String ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.set(key, value,nxxx,expx,time);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.set(key, value,nxxx,expx,time);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#set(java.lang.String, java.lang.String, java.lang.String)    
	 */
	@SuppressWarnings("deprecation")
	@Override
	public String set(String key, String value, String nxxx) {
		String ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.set(key, value,nxxx);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.set(key, value,nxxx);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#get(java.lang.String)    
	 */
	@Override
	public String get(String key) {
		
		String ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.get(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.get(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#exists(java.lang.String)    
	 */
	@Override
	public Boolean exists(String key) {
		boolean ret = false;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.exists(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.exists(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#persist(java.lang.String)    
	 */
	@Override
	public Long persist(String key) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.persist(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.persist(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#type(java.lang.String)    
	 */
	@Override
	public String type(String key) {
		String ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.type(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.type(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#expire(java.lang.String, int)    
	 */
	@Override
	public Long expire(String key, int seconds) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.expire(key,seconds);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.expire(key,seconds);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#pexpire(java.lang.String, long)    
	 */
	@Override
	public Long pexpire(String key, long milliseconds) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.pexpire(key,milliseconds);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.pexpire(key,milliseconds);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#expireAt(java.lang.String, long)    
	 */
	@Override
	public Long expireAt(String key, long unixTime) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.expireAt(key,unixTime);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.expireAt(key,unixTime);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#pexpireAt(java.lang.String, long)    
	 */
	@Override
	public Long pexpireAt(String key, long millisecondsTimestamp) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.expireAt(key,millisecondsTimestamp);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.expireAt(key,millisecondsTimestamp);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#ttl(java.lang.String)    
	 */
	@Override
	public Long ttl(String key) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.ttl(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.ttl(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#pttl(java.lang.String)    
	 */
	@Override
	public Long pttl(String key) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.pttl(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.pttl(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#setbit(java.lang.String, long, boolean)    
	 */
	@Override
	public Boolean setbit(String key, long offset, boolean value) {
		boolean ret = false;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.setbit(key,offset,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.setbit(key,offset,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#setbit(java.lang.String, long, java.lang.String)    
	 */
	@Override
	public Boolean setbit(String key, long offset, String value) {
		boolean ret = false;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.setbit(key,offset,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.setbit(key,offset,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#getbit(java.lang.String, long)    
	 */
	@Override
	public Boolean getbit(String key, long offset) {
		boolean ret = false;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.getbit(key,offset);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.getbit(key,offset);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#setrange(java.lang.String, long, java.lang.String)    
	 */
	@Override
	public Long setrange(String key, long offset, String value) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.setrange(key,offset,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.setrange(key,offset,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#getrange(java.lang.String, long, long)    
	 */
	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		String ret =null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.getrange(key,startOffset,endOffset);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.getrange(key,startOffset,endOffset);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#getSet(java.lang.String, java.lang.String)    
	 */
	@Override
	public String getSet(String key, String value) {
		String ret =null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.getSet(key,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.getSet(key,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#setnx(java.lang.String, java.lang.String)    
	 */
	@Override
	public Long setnx(String key, String value) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.setnx(key,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.setnx(key,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#setex(java.lang.String, int, java.lang.String)    
	 */
	@Override
	public String setex(String key, int seconds, String value) {
		String ret =null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.setex(key,seconds,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.setex(key,seconds,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#psetex(java.lang.String, long, java.lang.String)    
	 */
	@Override
	public String psetex(String key, long milliseconds, String value) {
		String ret =null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.psetex(key,milliseconds,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.psetex(key,milliseconds,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#decrBy(java.lang.String, long)    
	 */
	@Override
	public Long decrBy(String key, long integer) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.decrBy(key,integer);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.decrBy(key,integer);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#decr(java.lang.String)    
	 */
	@Override
	public Long decr(String key) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.decr(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.decr(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#incrBy(java.lang.String, long)    
	 */
	@Override
	public Long incrBy(String key, long integer) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.incrBy(key,integer);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.incrBy(key,integer);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#incrByFloat(java.lang.String, double)    
	 */
	@Override
	public Double incrByFloat(String key, double value) {
		Double ret = 0.0D;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.incrByFloat(key,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.incrByFloat(key,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#incr(java.lang.String)    
	 */
	@Override
	public Long incr(String key) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.incr(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.incr(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#append(java.lang.String, java.lang.String)    
	 */
	@Override
	public Long append(String key, String value) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.append(key,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.append(key,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#substr(java.lang.String, int, int)    
	 */
	@Override
	public String substr(String key, int start, int end) {
		String ret =null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.substr(key,start,end);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.substr(key,start,end);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#hset(java.lang.String, java.lang.String, java.lang.String)    
	 */
	@Override
	public Long hset(String key, String field, String value) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.hset(key,field,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.hset(key,field,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#hget(java.lang.String, java.lang.String)    
	 */
	@Override
	public String hget(String key, String field) {
		String ret =null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.hget(key,field);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.hget(key,field);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#hsetnx(java.lang.String, java.lang.String, java.lang.String)    
	 */
	@Override
	public Long hsetnx(String key, String field, String value) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.hsetnx(key,field,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.hsetnx(key,field,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#hmset(java.lang.String, java.util.Map)    
	 */
	@Override
	public String hmset(String key, Map<String, String> hash) {
		String ret =null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.hmset(key,hash);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.hmset(key,hash);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#hmget(java.lang.String, java.lang.String[])    
	 */
	@Override
	public List<String> hmget(String key, String... fields) {
		List<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.hmget(key,fields);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.hmget(key,fields);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#hincrBy(java.lang.String, java.lang.String, long)    
	 */
	@Override
	public Long hincrBy(String key, String field, long value) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.hincrBy(key,field,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.hincrBy(key,field,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#hincrByFloat(java.lang.String, java.lang.String, double)    
	 */
	@Override
	public Double hincrByFloat(String key, String field, double value) {
		Double ret = 0.0D;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.hincrByFloat(key,field,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.hincrByFloat(key,field,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#hexists(java.lang.String, java.lang.String)    
	 */
	@Override
	public Boolean hexists(String key, String field) {
		boolean ret = false;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.hexists(key,field);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.hexists(key,field);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#hdel(java.lang.String, java.lang.String[])    
	 */
	@Override
	public Long hdel(String key, String... field) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.hdel(key,field);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.hdel(key,field);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#hlen(java.lang.String)    
	 */
	@Override
	public Long hlen(String key) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.hlen(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.hlen(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#hkeys(java.lang.String)    
	 */
	@Override
	public Set<String> hkeys(String key) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.hkeys(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.hkeys(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#hvals(java.lang.String)    
	 */
	@Override
	public List<String> hvals(String key) {
		List<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.hvals(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.hvals(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#hgetAll(java.lang.String)    
	 */
	@Override
	public Map<String, String> hgetAll(String key) {
		Map<String, String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.hgetAll(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.hgetAll(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#rpush(java.lang.String, java.lang.String[])    
	 */
	@Override
	public Long rpush(String key, String... string) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.rpush(key,string);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.rpush(key,string);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#lpush(java.lang.String, java.lang.String[])    
	 */
	@Override
	public Long lpush(String key, String... string) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.lpush(key,string);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.lpush(key,string);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#llen(java.lang.String)    
	 */
	@Override
	public Long llen(String key) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.llen(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.llen(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#lrange(java.lang.String, long, long)    
	 */
	@Override
	public List<String> lrange(String key, long start, long end) {
		List<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.lrange(key,start,end);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.lrange(key,start,end);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#ltrim(java.lang.String, long, long)    
	 */
	@Override
	public String ltrim(String key, long start, long end) {
		String ret =null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.ltrim(key,start,end);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.ltrim(key,start,end);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#lindex(java.lang.String, long)    
	 */
	@Override
	public String lindex(String key, long index) {
		String ret =null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.lindex(key,index);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.lindex(key,index);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#lset(java.lang.String, long, java.lang.String)    
	 */
	@Override
	public String lset(String key, long index, String value) {
		String ret =null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.lset(key,index,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.lset(key,index,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#lrem(java.lang.String, long, java.lang.String)    
	 */
	@Override
	public Long lrem(String key, long count, String value) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.lrem(key,count,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.lrem(key,count,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#lpop(java.lang.String)    
	 */
	@Override
	public String lpop(String key) {
		String ret =null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.lpop(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.lpop(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#rpop(java.lang.String)    
	 */
	@Override
	public String rpop(String key) {
		String ret =null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.rpop(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.rpop(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#sadd(java.lang.String, java.lang.String[])    
	 */
	@Override
	public Long sadd(String key, String... member) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.sadd(key,member);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.sadd(key,member);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#smembers(java.lang.String)    
	 */
	@Override
	public Set<String> smembers(String key) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.smembers(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.smembers(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#srem(java.lang.String, java.lang.String[])    
	 */
	@Override
	public Long srem(String key, String... member) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.srem(key,member);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.srem(key,member);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#spop(java.lang.String)    
	 */
	@Override
	public String spop(String key) {
		String ret =null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.spop(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.spop(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#spop(java.lang.String, long)    
	 */
	@Override
	public Set<String> spop(String key, long count) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.spop(key,count);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.spop(key,count);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#scard(java.lang.String)    
	 */
	@Override
	public Long scard(String key) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.scard(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.scard(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#sismember(java.lang.String, java.lang.String)    
	 */
	@Override
	public Boolean sismember(String key, String member) {
		Boolean ret = false;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.sismember(key,member);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.sismember(key,member);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#srandmember(java.lang.String)    
	 */
	@Override
	public String srandmember(String key) {
		String ret =null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.srandmember(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.srandmember(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#srandmember(java.lang.String, int)    
	 */
	@Override
	public List<String> srandmember(String key, int count) {
		List<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.srandmember(key,count);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.srandmember(key,count);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#strlen(java.lang.String)    
	 */
	@Override
	public Long strlen(String key) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.strlen(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.strlen(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zadd(java.lang.String, double, java.lang.String)    
	 */
	@Override
	public Long zadd(String key, double score, String member) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zadd(key,score,member);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zadd(key,score,member);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zadd(java.lang.String, double, java.lang.String, redis.clients.jedis.params.sortedset.ZAddParams)    
	 */
	@Override
	public Long zadd(String key, double score, String member, ZAddParams params) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zadd(key,score,member,params);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zadd(key,score,member,params);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zadd(java.lang.String, java.util.Map)    
	 */
	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zadd(key,scoreMembers);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zadd(key,scoreMembers);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zadd(java.lang.String, java.util.Map, redis.clients.jedis.params.sortedset.ZAddParams)    
	 */
	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers,
			ZAddParams params) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zadd(key,scoreMembers,params);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zadd(key,scoreMembers,params);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrange(java.lang.String, long, long)    
	 */
	@Override
	public Set<String> zrange(String key, long start, long end) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrange(key,start,end);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrange(key,start,end);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrem(java.lang.String, java.lang.String[])    
	 */
	@Override
	public Long zrem(String key, String... member) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrem(key,member);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrem(key,member);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zincrby(java.lang.String, double, java.lang.String)    
	 */
	@Override
	public Double zincrby(String key, double score, String member) {
		Double ret = 0.0D;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zincrby(key,score,member);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zincrby(key,score,member);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zincrby(java.lang.String, double, java.lang.String, redis.clients.jedis.params.sortedset.ZIncrByParams)    
	 */
	@Override
	public Double zincrby(String key, double score, String member,
			ZIncrByParams params) {
		Double ret = 0.0D;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zincrby(key,score,member,params);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zincrby(key,score,member,params);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrank(java.lang.String, java.lang.String)    
	 */
	@Override
	public Long zrank(String key, String member) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrank(key,member);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrank(key,member);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrevrank(java.lang.String, java.lang.String)    
	 */
	@Override
	public Long zrevrank(String key, String member) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrevrank(key,member);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrevrank(key,member);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrevrange(java.lang.String, long, long)    
	 */
	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrevrange(key,start,end);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrevrange(key,start,end);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrangeWithScores(java.lang.String, long, long)    
	 */
	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		Set<Tuple> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrangeWithScores(key,start,end);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrangeWithScores(key,start,end);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrevrangeWithScores(java.lang.String, long, long)    
	 */
	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		Set<Tuple> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrevrangeWithScores(key,start,end);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrevrangeWithScores(key,start,end);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zcard(java.lang.String)    
	 */
	@Override
	public Long zcard(String key) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zcard(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zcard(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zscore(java.lang.String, java.lang.String)    
	 */
	@Override
	public Double zscore(String key, String member) {
		Double ret = 0.0D;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zscore(key,member);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zscore(key,member);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#sort(java.lang.String)    
	 */
	@Override
	public List<String> sort(String key) {
		List<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.sort(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.sort(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#sort(java.lang.String, redis.clients.jedis.SortingParams)    
	 */
	@Override
	public List<String> sort(String key, SortingParams sortingParameters) {
		List<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.sort(key,sortingParameters);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.sort(key,sortingParameters);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zcount(java.lang.String, double, double)    
	 */
	@Override
	public Long zcount(String key, double min, double max) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zcount(key,min,max);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zcount(key,min,max);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zcount(java.lang.String, java.lang.String, java.lang.String)    
	 */
	@Override
	public Long zcount(String key, String min, String max) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zcount(key,min,max);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zcount(key,min,max);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrangeByScore(java.lang.String, double, double)    
	 */
	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrangeByScore(key,min,max);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrangeByScore(key,min,max);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrangeByScore(java.lang.String, java.lang.String, java.lang.String)    
	 */
	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrangeByScore(key,min,max);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrangeByScore(key,min,max);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByScore(java.lang.String, double, double)    
	 */
	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrevrangeByScore(key,max,min);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrevrangeByScore(key,max,min);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrangeByScore(java.lang.String, double, double, int, int)    
	 */
	@Override
	public Set<String> zrangeByScore(String key, double min, double max,
			int offset, int count) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrangeByScore(key,min,max,offset,count);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrangeByScore(key,min,max,offset,count);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByScore(java.lang.String, java.lang.String, java.lang.String)    
	 */
	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrevrangeByScore(key,max,min);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrevrangeByScore(key,max,min);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrangeByScore(java.lang.String, java.lang.String, java.lang.String, int, int)    
	 */
	@Override
	public Set<String> zrangeByScore(String key, String min, String max,
			int offset, int count) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrangeByScore(key,min,max,offset,count);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrangeByScore(key,min,max,offset,count);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByScore(java.lang.String, double, double, int, int)    
	 */
	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min,
			int offset, int count) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrevrangeByScore(key,max,min,offset,count);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrevrangeByScore(key,max,min,offset,count);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrangeByScoreWithScores(java.lang.String, double, double)    
	 */
	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		Set<Tuple> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrangeByScoreWithScores(key,min,max);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrangeByScoreWithScores(key,min,max);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByScoreWithScores(java.lang.String, double, double)    
	 */
	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min) {
		Set<Tuple> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrevrangeByScoreWithScores(key,max,min);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrevrangeByScoreWithScores(key,max,min);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrangeByScoreWithScores(java.lang.String, double, double, int, int)    
	 */
	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min,
			double max, int offset, int count) {
		Set<Tuple> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrangeByScoreWithScores(key,min,max,offset,count);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrangeByScoreWithScores(key,min,max,offset,count);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByScore(java.lang.String, java.lang.String, java.lang.String, int, int)    
	 */
	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min,
			int offset, int count) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrevrangeByScore(key,max,min,offset,count);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrevrangeByScore(key,max,min,offset,count);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrangeByScoreWithScores(java.lang.String, java.lang.String, java.lang.String)    
	 */
	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		Set<Tuple> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrangeByScoreWithScores(key,min,max);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrangeByScoreWithScores(key,min,max);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByScoreWithScores(java.lang.String, java.lang.String, java.lang.String)    
	 */
	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max,
			String min) {
		Set<Tuple> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrevrangeByScoreWithScores(key,max,min);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrevrangeByScoreWithScores(key,max,min);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrangeByScoreWithScores(java.lang.String, java.lang.String, java.lang.String, int, int)    
	 */
	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min,
			String max, int offset, int count) {
		Set<Tuple> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrevrangeByScoreWithScores(key,min,max,offset,count);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrevrangeByScoreWithScores(key,min,max,offset,count);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByScoreWithScores(java.lang.String, double, double, int, int)    
	 */
	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min, int offset, int count) {
		Set<Tuple> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrevrangeByScoreWithScores(key,max,min,offset,count);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrevrangeByScoreWithScores(key,max,min,offset,count);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByScoreWithScores(java.lang.String, java.lang.String, java.lang.String, int, int)    
	 */
	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max,
			String min, int offset, int count) {
		Set<Tuple> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrevrangeByScoreWithScores(key,max,min,offset,count);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrevrangeByScoreWithScores(key,max,min,offset,count);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zremrangeByRank(java.lang.String, long, long)    
	 */
	@Override
	public Long zremrangeByRank(String key, long start, long end) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zremrangeByRank(key,start,end);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zremrangeByRank(key,start,end);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zremrangeByScore(java.lang.String, double, double)    
	 */
	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zremrangeByScore(key,start,end);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zremrangeByScore(key,start,end);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zremrangeByScore(java.lang.String, java.lang.String, java.lang.String)    
	 */
	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zremrangeByScore(key,start,end);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zremrangeByScore(key,start,end);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zlexcount(java.lang.String, java.lang.String, java.lang.String)    
	 */
	@Override
	public Long zlexcount(String key, String min, String max) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zlexcount(key,min,max);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zlexcount(key,min,max);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrangeByLex(java.lang.String, java.lang.String, java.lang.String)    
	 */
	@Override
	public Set<String> zrangeByLex(String key, String min, String max) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrangeByLex(key,min,max);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrangeByLex(key,min,max);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrangeByLex(java.lang.String, java.lang.String, java.lang.String, int, int)    
	 */
	@Override
	public Set<String> zrangeByLex(String key, String min, String max,
			int offset, int count) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrangeByLex(key,min,max,offset,count);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrangeByLex(key,min,max,offset,count);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByLex(java.lang.String, java.lang.String, java.lang.String)    
	 */
	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrevrangeByLex(key,max,min);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrevrangeByLex(key,max,min);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zrevrangeByLex(java.lang.String, java.lang.String, java.lang.String, int, int)    
	 */
	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min,
			int offset, int count) {
		Set<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zrevrangeByLex(key,max,min,offset,count);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zrevrangeByLex(key,max,min,offset,count);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zremrangeByLex(java.lang.String, java.lang.String, java.lang.String)    
	 */
	@Override
	public Long zremrangeByLex(String key, String min, String max) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zremrangeByLex(key,min,max);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zremrangeByLex(key,min,max);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#linsert(java.lang.String, redis.clients.jedis.BinaryClient.LIST_POSITION, java.lang.String, java.lang.String)    
	 */
	@Override
	public Long linsert(String key, LIST_POSITION where, String pivot,
			String value) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.linsert(key,where,pivot,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.linsert(key,where,pivot,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#lpushx(java.lang.String, java.lang.String[])    
	 */
	@Override
	public Long lpushx(String key, String... string) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.lpushx(key,string);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.lpushx(key,string);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#rpushx(java.lang.String, java.lang.String[])    
	 */
	@Override
	public Long rpushx(String key, String... string) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.rpushx(key,string);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.rpushx(key,string);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#blpop(java.lang.String)    
	 */
	@SuppressWarnings("deprecation")
	@Override
	public List<String> blpop(String arg) {
		List<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.blpop(arg);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.blpop(arg);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#blpop(int, java.lang.String)    
	 */
	@Override
	public List<String> blpop(int timeout, String key) {
		List<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.blpop(timeout,key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.blpop(timeout,key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#brpop(java.lang.String)    
	 */
	@SuppressWarnings("deprecation")
	@Override
	public List<String> brpop(String arg) {
		List<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.brpop(arg);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.brpop(arg);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#brpop(int, java.lang.String)    
	 */
	@Override
	public List<String> brpop(int timeout, String key) {
		List<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.brpop(timeout,key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.brpop(timeout,key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#del(java.lang.String)    
	 */
	@Override
	public Long del(String key) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.del(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.del(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#echo(java.lang.String)    
	 */
	@Override
	public String echo(String string) {
		String ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.echo(string);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.echo(string);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#move(java.lang.String, int)    
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Long move(String key, int dbIndex) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.move(key,dbIndex);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.move(key,dbIndex);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#bitcount(java.lang.String)    
	 */
	@Override
	public Long bitcount(String key) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.bitcount(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.bitcount(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#bitcount(java.lang.String, long, long)    
	 */
	@Override
	public Long bitcount(String key, long start, long end) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.bitcount(key,start,end);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.bitcount(key,start,end);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#bitpos(java.lang.String, boolean)    
	 */
	@Override
	public Long bitpos(String key, boolean value) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.bitpos(key,value);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.bitpos(key,value);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#bitpos(java.lang.String, boolean, redis.clients.jedis.BitPosParams)    
	 */
	@Override
	public Long bitpos(String key, boolean value, BitPosParams params) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.bitpos(key,value,params);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.bitpos(key,value,params);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#hscan(java.lang.String, int)    
	 */
	@SuppressWarnings("deprecation")
	@Override
	public ScanResult<Entry<String, String>> hscan(String key, int cursor) {
		ScanResult<Entry<String, String>> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.hscan(key,cursor);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.hscan(key,cursor);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#sscan(java.lang.String, int)    
	 */
	@SuppressWarnings("deprecation")
	@Override
	public ScanResult<String> sscan(String key, int cursor) {
		ScanResult<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.sscan(key,cursor);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.sscan(key,cursor);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zscan(java.lang.String, int)    
	 */
	@SuppressWarnings("deprecation")
	@Override
	public ScanResult<Tuple> zscan(String key, int cursor) {
		ScanResult<Tuple> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zscan(key,cursor);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zscan(key,cursor);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#hscan(java.lang.String, java.lang.String)    
	 */
	@Override
	public ScanResult<Entry<String, String>> hscan(String key, String cursor) {
		ScanResult<Entry<String, String>> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.hscan(key,cursor);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.hscan(key,cursor);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#hscan(java.lang.String, java.lang.String, redis.clients.jedis.ScanParams)    
	 */
	@Override
	public ScanResult<Entry<String, String>> hscan(String key, String cursor,
			ScanParams params) {
		ScanResult<Entry<String, String>> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.hscan(key,cursor,params);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.hscan(key,cursor,params);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#sscan(java.lang.String, java.lang.String)    
	 */
	@Override
	public ScanResult<String> sscan(String key, String cursor) {
		ScanResult<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.sscan(key,cursor);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.sscan(key,cursor);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#sscan(java.lang.String, java.lang.String, redis.clients.jedis.ScanParams)    
	 */
	@Override
	public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
		ScanResult<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.sscan(key,cursor,params);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.sscan(key,cursor,params);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zscan(java.lang.String, java.lang.String)    
	 */
	@Override
	public ScanResult<Tuple> zscan(String key, String cursor) {
		ScanResult<Tuple> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zscan(key,cursor);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zscan(key,cursor);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#zscan(java.lang.String, java.lang.String, redis.clients.jedis.ScanParams)    
	 */
	@Override
	public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
		ScanResult<Tuple> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.zscan(key,cursor,params);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.zscan(key,cursor,params);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#pfadd(java.lang.String, java.lang.String[])    
	 */
	@Override
	public Long pfadd(String key, String... elements) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.pfadd(key,elements);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.pfadd(key,elements);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#pfcount(java.lang.String)    
	 */
	@Override
	public long pfcount(String key) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.pfcount(key);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.pfcount(key);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#geoadd(java.lang.String, double, double, java.lang.String)    
	 */
	@Override
	public Long geoadd(String key, double longitude, double latitude,
			String member) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.geoadd(key,longitude,latitude,member);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.geoadd(key,longitude,latitude,member);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#geoadd(java.lang.String, java.util.Map)    
	 */
	@Override
	public Long geoadd(String key,
			Map<String, GeoCoordinate> memberCoordinateMap) {
		Long ret = 0L;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.geoadd(key,memberCoordinateMap);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.geoadd(key,memberCoordinateMap);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#geodist(java.lang.String, java.lang.String, java.lang.String)    
	 */
	@Override
	public Double geodist(String key, String member1, String member2) {
		Double ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.geodist(key,member1,member2);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.geodist(key,member1,member2);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#geodist(java.lang.String, java.lang.String, java.lang.String, redis.clients.jedis.GeoUnit)    
	 */
	@Override
	public Double geodist(String key, String member1, String member2,
			GeoUnit unit) {
		Double ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.geodist(key,member1,member2,unit);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.geodist(key,member1,member2,unit);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#geohash(java.lang.String, java.lang.String[])    
	 */
	@Override
	public List<String> geohash(String key, String... members) {
		List<String> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.geohash(key,members);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.geohash(key,members);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#geopos(java.lang.String, java.lang.String[])    
	 */
	@Override
	public List<GeoCoordinate> geopos(String key, String... members) {
		List<GeoCoordinate> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.geopos(key,members);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.geopos(key,members);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#georadius(java.lang.String, double, double, double, redis.clients.jedis.GeoUnit)    
	 */
	@Override
	public List<GeoRadiusResponse> georadius(String key, double longitude,
			double latitude, double radius, GeoUnit unit) {
		List<GeoRadiusResponse> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.georadius(key,longitude,latitude,radius,unit);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.georadius(key,longitude,latitude,radius,unit);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#georadius(java.lang.String, double, double, double, redis.clients.jedis.GeoUnit, redis.clients.jedis.params.geo.GeoRadiusParam)    
	 */
	@Override
	public List<GeoRadiusResponse> georadius(String key, double longitude,
			double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
		List<GeoRadiusResponse> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.georadius(key,longitude,latitude,radius,unit,param);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.georadius(key,longitude,latitude,radius,unit,param);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#georadiusByMember(java.lang.String, java.lang.String, double, redis.clients.jedis.GeoUnit)    
	 */
	@Override
	public List<GeoRadiusResponse> georadiusByMember(String key, String member,
			double radius, GeoUnit unit) {
		List<GeoRadiusResponse> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.georadiusByMember(key,member,radius,unit);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.georadiusByMember(key,member,radius,unit);
		}
		return ret;
	}

	/* (non-Javadoc)    
	 * @see redis.clients.jedis.JedisCommands#georadiusByMember(java.lang.String, java.lang.String, double, redis.clients.jedis.GeoUnit, redis.clients.jedis.params.geo.GeoRadiusParam)    
	 */
	@Override
	public List<GeoRadiusResponse> georadiusByMember(String key, String member,
			double radius, GeoUnit unit, GeoRadiusParam param) {
		List<GeoRadiusResponse> ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.georadiusByMember(key,member,radius,unit,param);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.georadiusByMember(key,member,radius,unit,param);
		}
		return ret;
	}
	
	
	
	
	/**
	 * 实现集群的keys操作
	 * keys(这里用一句话描述这个方法的作用)
	 * @param        
	 * @return Set<String>    
	 * @Exception 异常对象
	 */
	public Set<String> keys(String pattern){
		Set<String> keys = new TreeSet<>();  
		
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			keys = jedis.keys(pattern);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
			for(String k : clusterNodes.keySet()){  
				JedisPool jp = clusterNodes.get(k);  
				Jedis connection = jp.getResource();  
				try {
					keys.addAll(connection.keys(pattern));  
				} catch (Exception e) {
					logger.error("读取keys异常", e);
				}finally{
					connection.close();
				}
			}
		}
		
		return keys;  
	}
	
	/**
	 * 发布
	 * publish(这里用一句话描述这个方法的作用) 
	 * @param        
	 * @return Long    
	 * @Exception 异常对象
	 */
	public Long publish(String channel, String message)
	{
		
		Long ret = null;
		if(null != jedisPool){		
			jedis = jedisPool.getResource();
			ret = jedis.publish(channel, message);
			if(null !=jedis){
				jedis.close();
			}
		}else if(null !=jedisCluster){
			ret = jedisCluster.publish(channel, message);
		}
		
		return ret;
	}
}

