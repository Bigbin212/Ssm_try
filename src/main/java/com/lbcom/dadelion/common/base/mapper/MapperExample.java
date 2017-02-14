package com.lbcom.dadelion.common.base.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 
 * @author  liubin
 * @date 2015年12月17日 下午1:04:02 
 * @version 1.0 
 * @parameter
 * @since  
 * @return
 */
@SuppressWarnings("serial")
public class MapperExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<MapperCriteria> oredCriteria;

    public MapperExample() {
        oredCriteria = new ArrayList<MapperCriteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<MapperCriteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(MapperCriteria criteria) {
        oredCriteria.add(criteria);
    }

    public MapperCriteria or() {
        MapperCriteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public MapperCriteria createCriteria() {
        MapperCriteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected MapperCriteria createCriteriaInternal() {
        MapperCriteria criteria = new MapperCriteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }
}
