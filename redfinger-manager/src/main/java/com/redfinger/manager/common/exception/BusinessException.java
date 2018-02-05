package com.redfinger.manager.common.exception;

/**
 * 数据异常，不允许的操作
 * @author figo
 * @date 2015年2月14日 下午4:14:02
 */
public class BusinessException extends RuntimeException  {

	private static final long serialVersionUID = 7448968526963110744L;
	
    public BusinessException(String message) {
    	super(message);  
    }  

}
