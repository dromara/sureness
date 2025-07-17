package org.dromara.sureness.sample.tom.service.impl;


/**
 * data conflict exception
 * @author tomsun28
 * @date 22:55 2020-04-27
 */
public class DataConflictException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9018162942771262068L;

	public DataConflictException(String msg) {
        super(msg);
    }
}
