package com.lbcom.dadelion.redis.factory;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lbcom.dadelion.util.ConfigManager;
import com.lbcom.dadelion.util.ConstUtil;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @CopyRight ©1995-2016: 苏州科达科技股份有限公司 
 * @Project： 
 * @Module：
 * @Description 
 * @Author  liubin
 * @Date 2017年4月21日 下午4:24:46 
 * @Version 1.0 
 */
public class RedisFactory {

	private static Object redisObject;
	private static Object lock = new Object();
	//日志对象
	private static Logger logger = LoggerFactory.getLogger(RedisFactory.class);
	
	public RedisFactory() {
		
	}
	
	/**
	 * 获取redis集群实例对象
	 * getRedisClusterInstance(这里用一句话描述这个方法的作用)
	 * @param        
	 * @return JedisCluster    
	 * @Exception 异常对象
	 */
	public static Object getRedisClusterInstance(){
		if(null == redisObject){
			synchronized (lock) {//
				if(null == redisObject){
					try {
						String jedisClusterConfig  = ConfigManager.commonCfg.getString("redisCluster");
						
						String[] hosts =  jedisClusterConfig.split(ConstUtil.KEYWORD_SPLIT_COMMA);
						if(hosts.length==1){
							redisObject = new JedisPool(new JedisPoolConfig(), hosts[0].split(":")[0], Integer.valueOf(hosts[0].split(":")[1]),15000);
						}else{
							Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
							HostAndPort hostAndPort = null;
							for(int i=0;i<hosts.length;i++){
								String host = hosts[i];
								hostAndPort = new HostAndPort(host.split(":")[0],Integer.valueOf(host.split(":")[1]));
								hostAndPorts.add(hostAndPort);
							}
							redisObject = new JedisCluster(hostAndPorts, 15000, 100,new JedisPoolConfig());
						}
					} catch (Exception e) {
						logger.error("初始化redis异常", e);
					}
				}
			}
		}
		return redisObject;
	}
	
	/**
	 * 获取redis集群实例对象
	 * getRedisClusterInstance(这里用一句话描述这个方法的作用) 
	 * @param        
	 * @return JedisCluster    
	 * @Exception 异常对象
	 */
	public static Object getRedisClusterInstance(String jedisClusterConfig){
		if(null == redisObject){
			synchronized (lock) {//
				if(null == redisObject){
					try {
						//String jedisClusterConfig  = CacheUtil.getInstance().getXtpzValue("redisCluster");
						
						String[] hosts =  jedisClusterConfig.split(ConstUtil.KEYWORD_SPLIT_COMMA);
						if(hosts.length==1){
							redisObject = new JedisPool(new JedisPoolConfig(), hosts[0].split(":")[0], Integer.valueOf(hosts[0].split(":")[1]),15000);
						}else{
							Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
							HostAndPort hostAndPort = null;
							for(int i=0;i<hosts.length;i++){
								String host = hosts[i];
								hostAndPort = new HostAndPort(host.split(":")[0],Integer.valueOf(host.split(":")[1]));
								hostAndPorts.add(hostAndPort);
							}
							redisObject = new JedisCluster(hostAndPorts, 15000, 100,new JedisPoolConfig());
						}
					} catch (Exception e) {
						logger.error("初始化redis异常", e);
					}
				}
			}
		}
		return redisObject;
	}

}
