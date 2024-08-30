package org.dromara.sureness.processor.exception;

/**
 * Authentication exception: Too many attempts after regular authentication failure
 * @author tomsun28
 * @date 19:24 2019-03-11
 */
public class ExcessiveAttemptsException extends SurenessAuthenticationException {

	private static final long serialVersionUID = -6803340753339433865L;

	public ExcessiveAttemptsException(String message) {
        super(message);
    }
}
