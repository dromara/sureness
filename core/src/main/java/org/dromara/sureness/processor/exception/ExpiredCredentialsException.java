package org.dromara.sureness.processor.exception;

/**
 * Authentication exception: Expired certificate
 * @author tomsun28
 * @date 19:22 2019-03-11
 */
public class ExpiredCredentialsException extends SurenessAuthenticationException {

	private static final long serialVersionUID = -6757582855462476662L;

	public ExpiredCredentialsException(String message) {
        super(message);
    }
}
