package com.usthe.sureness.mgt;

/**
 * the description of this class
 * @author tomsun28
 * @date 15:15 2019-03-03
 */
public interface FactoryBean<T> {


    /**
     * description  获取对应实例的bean
     *
     * @return T
     * @throws Exception how
     */
    T getObject() throws Exception;

    /**
     * description 获取对应实例的type
     *
     * @return java.lang.Class<?>
     */
    Class<?> getObjectType();

    /**
     * description 是否为单实例
     *
     * @return boolean
     */
    default boolean isSingleton() {
        return true;
    }
}
