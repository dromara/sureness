package org.dromara.sureness.processor.exception;

/**
 * Authentication exception: Unknown account exception
 * @author tomsun28
 * @date 19:22 2019-03-11
 */
public class UnknownAccountException extends SurenessAuthenticationException {

	private static final long serialVersionUID = -7424387213268192767L;

	public UnknownAccountException(String message) {
        super(message);
    }
}
