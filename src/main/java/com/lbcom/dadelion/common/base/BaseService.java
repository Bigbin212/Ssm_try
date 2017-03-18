package com.lbcom.dadelion.common.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lbcom.dadelion.common.base.mapper.MapperExample;


/**
 * @description 
 * @author  liubin
 * @date 2015年12月17日 上午11:13:44 
 * @version 1.0 
 * @parameter
 * @since  
 * @return
 */
public abstract class BaseService<T> {

    private Logger myLogger = Logger.getLogger(BaseService.class);

    public static final int OPT_ERROR = -1;

    @Autowired
    Dao<T> dao;

    public int insert( T t ){

        myLogger.debug( t );
        try{
            return dao.insert( t );
        }catch ( Exception e ){
            e.printStackTrace();
        }
        return OPT_ERROR;
    }

    public int insertPatch( List<T> t ){

        myLogger.debug( t );
        try{
            return dao.insertPatch(t);
        }catch ( Exception e ){
            e.printStackTrace();
        }

        return OPT_ERROR;
    }

    public int updateByKey( T t ){

        myLogger.debug( t );
        try{
            return dao.updateByKey(t);
        }catch ( Exception e ){
            e.printStackTrace();
        }
        return OPT_ERROR;
    }

    public int updatePatchByKey( List<T> t ){

        myLogger.debug( t );
        try{
            return dao.updatePatchByKey(t);
        }catch ( Exception e ){
            e.printStackTrace();
        }

        return OPT_ERROR;
    }

    @SuppressWarnings("rawtypes")
	public int updateByMap( Map map ){

        myLogger.debug( map );
        try{
            return dao.updateByMap(map);
        }catch ( Exception e ){
            e.printStackTrace();
        }
        return OPT_ERROR;
    }

    public List<T> selectByExample(MapperExample mapperExample, RowBounds rowBounds){

        List<T> resultList = new ArrayList<T>();
        myLogger.debug( mapperExample.createCriteria().getAllCriteria() );
        myLogger.debug( rowBounds );
        try{
            resultList = dao.selectByExample(mapperExample, rowBounds);
        }catch ( Exception e ){
            e.printStackTrace();
        }
        return resultList;
    }

    public  List<T> selectByMap(Map<String, Object> params, RowBounds rowBounds)
    {

        List<T> resultList = new ArrayList<T>();
        myLogger.debug( params );
        myLogger.debug( rowBounds );
        try{
            resultList = dao.selectByMap(params, rowBounds);
        }catch ( Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public T selectByKey( Object key ){

        myLogger.debug( key );
        T t = null;
        try{
            t = dao.selectByKey(key);
        }catch ( Exception e ){
            e.printStackTrace();
        }
        return t;
    }

    public int countByExample(MapperExample mapperExample){

        myLogger.debug( mapperExample.createCriteria().getAllCriteria() );
        int count = 0;
        try{

            count = dao.countByExample(mapperExample);

        }catch ( Exception e ){
            e.printStackTrace();
        }

        return count;
    }

    public int countByMap(Map<String, Object> params)
    {
        myLogger.debug( params );
        int count = 0;
        try{

            count = dao.countByMap(params);

        }catch ( Exception e ) {
            e.printStackTrace();
        }

        return count;
    }

    public int deleteByKey(Object key){

        myLogger.debug( key );
        try{
            return dao.deleteByKey(key);
        }catch ( Exception e ){
            e.printStackTrace();
        }

        return OPT_ERROR;
    }

    public int deleteByExample(MapperExample mapperExample){

        myLogger.debug( mapperExample.createCriteria().getAllCriteria() );
        try{
            return dao.deleteByExample(mapperExample);
        }catch ( Exception e ){
            e.printStackTrace();
        }
        return OPT_ERROR;
    }

    @SuppressWarnings("rawtypes")
	public int deleteByMap( Map map ){

        try{
            return dao.deleteByMap(map);
        }catch ( Exception e ){
            e.printStackTrace();
        }

        return OPT_ERROR;
    }


}