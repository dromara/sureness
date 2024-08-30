package org.dromara.sureness.mgt;


import org.dromara.sureness.processor.exception.ExtSurenessException;

/**
 * sureness not init exception
 * @author tomsun28
 * @date 18:00 2019-03-10
 */
public class SurenessNoInitException extends ExtSurenessException {

	private static final long serialVersionUID = -1978973168384683300L;

	public SurenessNoInitException(String message) {
        super(message);
    }
}
