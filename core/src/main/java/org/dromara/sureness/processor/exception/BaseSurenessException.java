package org.dromara.sureness.processor.exception;

/**
 * sureness basic exception, other exceptions inherit this exception
 * @author tomsun28
 * @date 22:40 2019-01-23
 */
public class BaseSurenessException extends RuntimeException {

	private static final long serialVersionUID = -4061441676174632518L;

	public BaseSurenessException(String message) {
        super(message);
    }

}
