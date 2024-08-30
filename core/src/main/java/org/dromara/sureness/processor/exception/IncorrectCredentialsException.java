package org.dromara.sureness.processor.exception;

/**
 * Authentication exception: Bad credentials
 * @author tomsun28
 * @date 19:21 2019-03-11
 */
public class IncorrectCredentialsException extends SurenessAuthenticationException {

	private static final long serialVersionUID = -283801595997077954L;

	public IncorrectCredentialsException(String message) {
        super(message);
    }
}
