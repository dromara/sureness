package com.usthe.sureness.provider;

import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.matcher.SurenessLoadDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author tomsun28
 * @date 22:40 2019-05-24
 */
public class DocumentResourceDefaultProvider implements PathTreeProvider, SurenessAccountProvider {

    /** 日志操作 **/
    private static final Logger logger = LoggerFactory.getLogger(DocumentResourceDefaultProvider.class);

    @Override
    public Set<String> providePathData() throws SurenessLoadDataException {
        try {
            DocumentResourceEntity entity = DocumentResourceAccess.loadConfig();
            return new HashSet<>(entity.getResourceRole());
        } catch (IOException e) {
            logger.error("load config data from yaml file error: ", e);
            throw new SurenessLoadDataException(e.getMessage());
        }
    }

    @Override
    public SurenessAccount loadAccount(String appId) {
        if (appId == null) {
            return null;
        }
        try {
            DocumentResourceEntity entity = DocumentResourceAccess.loadConfig();
            Optional<Map<String, String>> mapOptional = entity.getAccount().stream()
                    .filter(map -> appId.equals(map.get("appId")))
                    .findFirst();
            DefaultAccount.Builder builder = new DefaultAccount.Builder(appId);
            if (mapOptional.isPresent()) {
                Map<String, String> accountMap = mapOptional.get();
                builder.setPassword(accountMap.get("credential"))
                        .setSalt(accountMap.get("salt"));
                return builder.build();
            }
        } catch (IOException e) {
            logger.error("load config data from yaml file error: ", e);
        }
        return null;
    }
}
