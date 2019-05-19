package com.usthe.sureness.sample.support;

import com.usthe.sureness.provider.DefaultAccount;
import com.usthe.sureness.provider.SurenessAccount;
import com.usthe.sureness.provider.SurenessAccountProvider;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

/**
 * 从文件中加载账户信息 -- 提供者
 * @author tomsun28
 * @date 00:08 2019-05-13
 */
@Component
public class FileTextSurenessAccountProvider implements SurenessAccountProvider {
    @Override
    public SurenessAccount loadAccount(String appId) {
        // 暂时模拟数据测试
        return DefaultAccount.builder()
                .setAppId("tom")
                .setPassword("1234")
                .setSalt("123")
                .setOwnRoles(Collections.singletonList("role1"))
                .setDisabledAccount(false)
                .setExcessiveAttempts(false)
                .build();
    }
}
