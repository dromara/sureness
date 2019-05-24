package com.usthe.sureness.sample.support;

import com.usthe.sureness.provider.DefaultAccount;
import com.usthe.sureness.provider.SurenessAccount;
import com.usthe.sureness.provider.SurenessAccountProvider;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * 从文件中加载账户信息 -- 提供者
 * @author tomsun28
 * @date 00:08 2019-05-13
 */
@Component
public class FileTextSurenessAccountProvider implements SurenessAccountProvider {

    private YamlFileResource fileResource;

    public FileTextSurenessAccountProvider(YamlFileResource fileResource) {
        this.fileResource = fileResource;
    }


    @Override
    public SurenessAccount loadAccount(String appId) {
        if (appId == null) {
            throw new NullPointerException("appId can not null");
        }
        Optional<Map<String, String>> mapOptional = fileResource.getUser().stream()
                .filter(map -> appId.equals(map.get("appId")))
                .findFirst();
        DefaultAccount.Builder builder = new DefaultAccount.Builder(appId);
        mapOptional.ifPresent(accountMap ->
                builder.setAppId(appId).setPassword(accountMap.get("credential"))
                        .setSalt(accountMap.get("salt"))
        );

//        if (mapOptional.isPresent()) {
//            Map<String, String> accountMap = mapOptional.get();
//            DefaultAccount.Builder builder = new DefaultAccount.Builder();
//            builder.setAppId(appId);
//            if (accountMap.get("credential") != null) {
//                builder.setPassword(accountMap.get("credential"));
//            }
//            if (accountMap.get("salt") != null) {
//                builder.setSalt(accountMap.get("salt"));
//            }
//            fileResource.getUserRole().stream()
//                    .filter(map -> map.containsKey(appId))
//                    .findFirst();
//            builder.setDisabledAccount(false)
//                    .setExcessiveAttempts(false)
//        } else {
//            return null;
//        }

        return DefaultAccount.builder(appId)
                .setAppId("admin")
                .setPassword("0192023A7BBD73250516F069DF18B500")
                .setSalt("123")
                .setOwnRoles(Collections.singletonList("role1"))
                .setDisabledAccount(false)
                .setExcessiveAttempts(false)
                .build();
    }
}
