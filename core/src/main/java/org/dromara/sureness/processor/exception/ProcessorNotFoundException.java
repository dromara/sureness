package org.dromara.sureness.processor.exception;

/**
 * Authentication exception: there is no processor support this subject
 * @author tomsun28
 * @date 12:50 2019-03-12
 */
public class ProcessorNotFoundException extends BaseSurenessException {

	private static final long serialVersionUID = 306924477676292358L;

	public ProcessorNotFoundException(String message) {
        super(message);
    }
}
