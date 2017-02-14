package com.lbcom.dadelion.common.base.result;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @description 
 * @author  liubin
 * @date 2015年12月17日 下午1:23:55 
 * @version 1.0 
 * @parameter
 * @since  
 * @return
 */
@SuppressWarnings("serial")
public class ResultEntity implements Serializable {

    private String status;

    private String result;

    private String msg;

    private String detailMsg;

    private String errorCode;

    @SuppressWarnings("rawtypes")
	private Map ext;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @SuppressWarnings("rawtypes")
	public Map getExt() {
        return ext;
    }

    @SuppressWarnings("rawtypes")
	public void setExt(Map ext) {
        this.ext = ext;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ResultEntity status( String status ){
        this.setStatus( status );
        return this;
    }

    public ResultEntity msg( String msg ){
        this.setMsg(msg);
        return this;
    }

    public ResultEntity result( String result ){
        this.setResult( result );
        return this;
    }

    public ResultEntity detailMsg( String detailMsg ){
        this.setDetailMsg(detailMsg);
        return this;
    }

    public ResultEntity errorCode( String errorCode ){
        this.setErrorCode(errorCode);
        return this;
    }

    @SuppressWarnings("unchecked")
	public ResultEntity attr( String key, Object value ){
        if( this.ext == null ){
            ext = Maps.newHashMap();
        }
        ext.put( key, value );
        return this;
    }
}

