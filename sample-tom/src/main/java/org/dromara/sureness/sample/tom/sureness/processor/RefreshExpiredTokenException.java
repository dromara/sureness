package org.dromara.sureness.sample.tom.sureness.processor;

import org.dromara.sureness.processor.exception.SurenessAuthenticationException;

/**
 * refresh token message
 * @author tomsun28
 * @date 2020-12-03 23:29
 */
public class RefreshExpiredTokenException extends SurenessAuthenticationException {
    public RefreshExpiredTokenException(String message) {
        super(message);
    }
}
