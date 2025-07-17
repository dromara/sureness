package org.dromara.sureness.sample.tom.sureness.processor;

import org.dromara.sureness.processor.exception.SurenessAuthenticationException;

/**
 * refresh token message
 * @author tomsun28
 * @date 2020-12-03 23:29
 */
public class RefreshExpiredTokenException extends SurenessAuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1918264927084522965L;

	public RefreshExpiredTokenException(String message) {
        super(message);
    }
}
