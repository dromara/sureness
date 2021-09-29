package com.usthe.sureness.processor.exception;

/**
 * Authentication exception: Can not support this request
 * SubjectCreator list unable to create the corresponding subject according to the request
 * @author tomsun28
 * @date 19:23 2019-03-11
 */
public class UnsupportedSubjectException extends SurenessAuthenticationException {

    public UnsupportedSubjectException(String message) {
        super(message);
    }

    /**
     * single instance for UnsupportedSubjectException
     * @return instance exception
     */
    public static RuntimeException getDefaultInstance() {
        return Instance.INSTANCE;
    }

    private static class Instance {
        private static final RuntimeException INSTANCE = new UnsupportedSubjectException("this request can not " +
                "create subject by creators,please config no subject creator by default");
    }
}
