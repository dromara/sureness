package com.usthe.sureness.provider;

import com.usthe.sureness.matcher.PathTreeProvider;
import com.usthe.sureness.matcher.SurenessLoadDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
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

    private static final String CREDENTIAL = "credential";

    private static final String SALT = "salt";

    private static final String ROLE = "role";

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
    @SuppressWarnings("unchecked")
    public SurenessAccount loadAccount(String appId) {
        if (appId == null) {
            return null;
        }
        try {
            DocumentResourceEntity entity = DocumentResourceAccess.loadConfig();
            Optional<Map<String, Object>> mapOptional = entity.getAccount().stream()
                    .filter(map -> appId.equals(map.get("appId")))
                    .findFirst();
            DefaultAccount.Builder builder = new DefaultAccount.Builder(appId);
            if (mapOptional.isPresent()) {
                Map<String, Object> accountMap = mapOptional.get();
                if (accountMap.get(CREDENTIAL) != null) {
                    builder.setPassword(String.valueOf(accountMap.get(CREDENTIAL)));
                }
                if (accountMap.get(SALT) != null) {
                    builder.setPassword(String.valueOf(accountMap.get(SALT)));
                }
                if (accountMap.get(ROLE) != null) {
                    builder.setOwnRoles((List<String>)accountMap.get(ROLE));
                }
                builder.setDisabledAccount(Boolean.FALSE)
                        .setExcessiveAttempts(Boolean.FALSE);
                return builder.build();
            }
        } catch (IOException e) {
            logger.error("load config data from yaml file error: ", e);
        }
        return null;
    }
}
