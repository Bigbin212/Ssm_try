package com.lbcom.dadelion.common.base.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 
 * @author  liubin
 * @date 2015年12月17日 下午1:05:58 
 * @version 1.0 
 * @parameter
 * @since  
 * @return
 */
@SuppressWarnings("serial")
public abstract class MapperGeneratedCriteria implements Serializable{
    protected List<MapperCriterion> criteria;

    protected MapperGeneratedCriteria() {
        super();
        criteria = new ArrayList<MapperCriterion>();
    }

    public boolean isValid() {
        return criteria.size() > 0;
    }

    public List<MapperCriterion> getAllCriteria() {
        return criteria;
    }

    public List<MapperCriterion> getCriteria() {
        return criteria;
    }

    public void addCriterion(String condition) {
        if (condition == null) {
            throw new RuntimeException("Value for condition cannot be null");
        }
        criteria.add(new MapperCriterion(condition));
    }

    public void addCriterion(String condition, Object value, String property) {
        if (value == null) {
            throw new RuntimeException("Value for " + property + " cannot be null");
        }
        criteria.add(new MapperCriterion(condition, value));
    }

    public void addCriterion(String condition, Object value1, Object value2, String property) {
        if (value1 == null || value2 == null) {
            throw new RuntimeException("Between values for " + property + " cannot be null");
        }
        criteria.add(new MapperCriterion(condition, value1, value2));
    }

}
