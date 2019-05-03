package com.usthe.sureness.mgt;


import com.usthe.sureness.processor.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tomsun28
 * @date 15:22 2019-03-03
 */
public class SurenessProcessorFactoryBean implements FactoryBean {

    private static final Logger logger = LoggerFactory.getLogger(SurenessProcessorFactoryBean.class);
    private Map<String, Processor> processorList = new LinkedHashMap<>();

    @Override
    public Object getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}