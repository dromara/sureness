package com.usthe.sureness.provider.ducument;

import com.usthe.sureness.provider.DefaultAccount;
import com.usthe.sureness.provider.SurenessAccount;
import com.usthe.sureness.provider.SurenessAccountProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * provide account data loading from yaml(default sureness.yml) document
 * @author tomsun28
 * @date 2021/1/19 11:07
 */
public class DocumentAccountProvider implements SurenessAccountProvider {

    private static final Logger logger = LoggerFactory.getLogger(DocumentAccountProvider.class);

    private static final String CREDENTIAL = "credential";

    private static final String SALT = "salt";

    private static final String ROLE = "role";

    /** sureness config memory cache **/
    private DocumentResourceEntity entity;

    @Override
    @SuppressWarnings("unchecked")
    public SurenessAccount loadAccount(String appId) {
        if (appId == null) {
            return null;
        }
        try {
            if (entity == null) {
                entity = DocumentResourceAccess.loadConfig();
            }
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
                    builder.setSalt(String.valueOf(accountMap.get(SALT)));
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
