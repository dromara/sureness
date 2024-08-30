package org.dromara.sureness.processor.exception;

/**
 * Authentication exception
 * Basic exceptions, exceptions related to custom authentication need to inherit
 * @author tomsun28
 * @date 12:59 2019-03-11
 */
public class SurenessAuthenticationException extends BaseSurenessException {

	private static final long serialVersionUID = -2636197665321265226L;

	public SurenessAuthenticationException(String message) {
        super(message);
    }
}
