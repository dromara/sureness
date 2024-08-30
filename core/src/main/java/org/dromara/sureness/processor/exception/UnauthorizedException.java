package org.dromara.sureness.processor.exception;

/**
 * Authorization exception: No permission to access the resource
 * @author tomsun28
 * @date 19:25 2019-03-11
 */
public class UnauthorizedException extends SurenessAuthorizationException {

	private static final long serialVersionUID = -7713907539505292238L;

	public UnauthorizedException(String message) {
        super(message);
    }
}
