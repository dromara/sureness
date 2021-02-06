package com.usthe.sureness.provider.ducument;

import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.matcher.SurenessLoadDataException;
import com.usthe.sureness.util.SurenessCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * provide resource data loading from yaml(default sureness.yml) document
 * @author tomsun28
 * @date 22:40 2019-05-24
 */
public class DocumentPathTreeProvider implements PathTreeProvider {

    /** logger **/
    private static final Logger logger = LoggerFactory.getLogger(DocumentPathTreeProvider.class);

    /** sureness config memory cache **/
    private DocumentResourceEntity entity;

    @Override
    public Set<String> providePathData() {
        try {
            if (entity == null) {
                entity = DocumentResourceAccess.loadConfig();
            }
            List<String> resource = entity.getResourceRole();
            Set<String> resourceSet;
            if (resource != null) {
                resourceSet = new HashSet<>(resource);
            } else {
                resourceSet = new HashSet<>();
            }
            return SurenessCommonUtil.attachContextPath(getContextPath(), resourceSet);
        } catch (IOException e) {
            logger.error("load config data from yaml file error: ", e);
            throw new SurenessLoadDataException(e.getMessage());
        }
    }

    @Override
    public Set<String> provideExcludedResource() {
        try {
            if (entity == null) {
                entity = DocumentResourceAccess.loadConfig();
            }
            List<String> resource = entity.getExcludedResource();
            Set<String> resourceSet;
            if (resource != null) {
                resourceSet = new HashSet<>(resource);
            } else {
                resourceSet = new HashSet<>();
            }
            return SurenessCommonUtil.attachContextPath(getContextPath(), resourceSet);
        } catch (IOException e) {
            logger.error("load config data from yaml file error: ", e);
            throw new SurenessLoadDataException(e.getMessage());
        }
    }
}
