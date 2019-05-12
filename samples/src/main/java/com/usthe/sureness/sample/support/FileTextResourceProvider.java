package com.usthe.sureness.sample.support;

import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.matcher.SurenessLoadDataException;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author tomsun28
 * @date 23:53 2019-05-12
 */
@Component
public class FileTextResourceProvider implements PathTreeProvider {
    @Override
    public Set<String> providePathData() throws SurenessLoadDataException {
        return null;
    }
}
