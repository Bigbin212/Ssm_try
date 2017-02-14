package com.lbcom.dadelion.common.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import com.lbcom.dadelion.common.base.mapper.MapperExample;
import com.lbcom.dadelion.common.base.result.PageResultEntity;
import com.lbcom.dadelion.common.base.result.ResultEntity;
import com.lbcom.dadelion.common.base.result.ResultEntityBuilder;
import com.lbcom.dadelion.common.base.result.ResultStatus;

/**
 * @description 
 * @author  liubin
 * @date 2015年12月17日 下午1:15:18 
 * @version 1.0 
 * @parameter
 * @since  
 * @return
 */
@SuppressWarnings("hiding")
public abstract class BaseResource <T>{

	@Autowired
    private BaseService<T> baseService;

    public PageResultEntity<T> selectByExample(MapperExample example, int skip, int pageSize){

        RowBounds rowBounds = new RowBounds(skip, pageSize);

        List<T> ts = baseService.selectByExample(example, rowBounds);
        int count = baseService.countByExample(example);

        PageResultEntity<T> pageResultEntity = new PageResultEntity<T>();
        pageResultEntity.setData( ts );
        pageResultEntity.setCount( count );

        return pageResultEntity;
    }

    public PageResultEntity<T> selectByMap(Map<String, Object> params, int skip, int pageSize){

        PageResultEntity<T> pageResultEntity = getPageResultEntity(params, skip, pageSize);
        return pageResultEntity;
    }

    protected PageResultEntity<T> getPageResultEntity(Map<String, Object> params, int skip, int pageSize) {
        RowBounds rowBounds = new RowBounds(skip, pageSize);

        List<T> ts = baseService.selectByMap(params, rowBounds);
        int count = baseService.countByMap(params);

        PageResultEntity<T> pageResultEntity = new PageResultEntity<T>();
        pageResultEntity.setData( ts );
        pageResultEntity.setCount( count );
        return pageResultEntity;
    }

    @SuppressWarnings("unchecked")
	public PageResultEntity<T> select(WebRequest request,int skip, int pageSize  ) {
        @SuppressWarnings("rawtypes")
		HashMap params = new HashMap();
        Iterator<String> it = request.getParameterNames();
        while (it.hasNext())
        {
            String key = it.next();
            if(!key.equals("take")&&!key.equals("page")&&!key.equals("pageSize")&&!key.equals("skip"))
                params.put(key,request.getParameter(key));
        }

        PageResultEntity<T> pageResultEntity = getPageResultEntity(params, skip, pageSize);
        return pageResultEntity;
    }


    public ResponseEntity<ResultEntity> countByExample(MapperExample example){

        int count = baseService.countByExample(example);
        return new ResponseEntity<ResultEntity>(ResultEntityBuilder.build().result( String.valueOf( count ) ), HttpStatus.OK);
    }

    public T selectByKey( String id  ){
        T t =baseService.selectByKey( id );
        return t;
    }

    public ResponseEntity<ResultEntity> update( T t ){
        baseService.updateByKey( t );
        ResultEntity resultEntity = ResultEntityBuilder.build().status(ResultStatus.SUCCESS).msg( "更新操作成功." );
        ResponseEntity<ResultEntity> responseEntity = new ResponseEntity<ResultEntity>( resultEntity, HttpStatus.OK);
        return responseEntity;
    }

    public ResponseEntity<ResultEntity> insert( T t ){
    	System.out.println(baseService);
        baseService.insert(t);

        ResultEntity resultEntity = ResultEntityBuilder.build().status(ResultStatus.SUCCESS).msg( "新增操作成功." );
        ResponseEntity<ResultEntity> responseEntity = new ResponseEntity<ResultEntity>(resultEntity, HttpStatus.OK);
        return responseEntity;
    }

    public ResponseEntity<ResultEntity> insertPatch( List<T> t ){
        baseService.insertPatch(t);

        ResultEntity resultEntity = ResultEntityBuilder.build().status(ResultStatus.SUCCESS).msg( "批量新增操作成功." );
        ResponseEntity<ResultEntity> responseEntity = new ResponseEntity<ResultEntity>(resultEntity, HttpStatus.OK);
        return responseEntity;
    }

    public ResponseEntity<ResultEntity> delete( String id ){

        baseService.deleteByKey(id);

        ResultEntity resultEntity = ResultEntityBuilder.build().status(ResultStatus.SUCCESS).msg( "删除操作成功." );
        ResponseEntity<ResultEntity> responseEntity = new ResponseEntity<ResultEntity>( resultEntity, HttpStatus.OK);
        return responseEntity;
    }
}
