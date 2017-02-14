package com.lbcom.dadelion.common.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.lbcom.dadelion.common.base.mapper.MapperExample;

/**
 * @description
 * @author liubin
 * @date 2015年12月17日 上午11:16:18
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@SuppressWarnings("rawtypes")
public interface Dao<T> {

    public  int insert( T t );

    public  int insertPatch( List<T> t );

    public T selectByKey( Object key );

    public List<T> selectByExample(MapperExample mapperExample, RowBounds rowBounds);

	public List<T> selectByMap(Map  params, RowBounds rowBounds);

    public int countByExample(MapperExample mapperExample);

	public int countByMap(Map params);

    public int updateByKey( T t );

	public int updateByMap( Map map );

    public int updatePatchByKey( List<T> t );

    public int updatePatchByMap( List<T> t );

    public int deleteByKey(Object key);

    public int deleteByExample(MapperExample mapperExample);

	public int deleteByMap(Map map);

}
