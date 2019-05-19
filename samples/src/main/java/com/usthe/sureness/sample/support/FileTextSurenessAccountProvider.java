package com.usthe.sureness.sample.support;

import com.usthe.sureness.provider.SurenessAccount;
import com.usthe.sureness.provider.SurenessAccountProvider;
import org.springframework.stereotype.Component;

/**
 * 从文件中加载账户信息 -- 提供者
 * @author tomsun28
 * @date 00:08 2019-05-13
 */
@Component
public class FileTextSurenessAccountProvider implements SurenessAccountProvider {
    @Override
    public SurenessAccount loadAccount(String appId) {
        return null;
    }
}
