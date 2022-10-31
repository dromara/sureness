package sureness.service.impl;

import com.usthe.sureness.provider.DefaultAccount;
import com.usthe.sureness.provider.SurenessAccount;
import com.usthe.sureness.util.Md5Util;
import com.usthe.sureness.util.SurenessCommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sureness.dao.AuthUserDao;
import sureness.dao.AuthUserRoleBindDao;
import sureness.pojo.dto.Account;
import sureness.pojo.entity.AuthUserDO;
import sureness.pojo.entity.AuthUserRoleBindDO;
import sureness.service.AccountService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author tomsun28
 * @date 10:58 2019-08-04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AuthUserDao authUserDao;

    private AuthUserRoleBindDao userRoleBindDao;

    @Override
    public boolean authenticateAccount(Account account) {
        Optional<AuthUserDO> authUserOptional = authUserDao.findAuthUserByUsername(account.getUsername());
        if (!authUserOptional.isPresent()) {
            return false;
        }
        AuthUserDO authUser = authUserOptional.get();
        String password = account.getPassword();
        if (password == null) {
            return false;
        }
        if (Objects.nonNull(authUser.getSalt())) {
            // md5 with salt
            password = Md5Util.md5(password + authUser.getSalt());

        }
        return authUser.getPassword().equals(password);
    }

    @Override
    public List<String> loadAccountRoles(String username) {
        return authUserDao.findAccountOwnRoles(username);
    }

    @Override
    public boolean registerAccount(Account account) {
        if (isAccountExist(account)) {
            return false;
        }
        String salt = SurenessCommonUtil.getRandomString(6);
        String password = Md5Util.md5(account.getPassword() + salt);
        AuthUserDO authUser = AuthUserDO.builder().username(account.getUsername())
                .password(password).salt(salt).status(1).build();
        authUserDao.save(authUser);
        return true;
    }

    @Override
    public boolean isAccountExist(Account account) {
        Optional<AuthUserDO> authUserOptional = authUserDao.findAuthUserByUsername(account.getUsername());
        return authUserOptional.isPresent();
    }

    @Override
    public SurenessAccount loadAccount(String username) {
        Optional<AuthUserDO> authUserOptional = authUserDao.findAuthUserByUsername(username);
        if (authUserOptional.isPresent()) {
            AuthUserDO authUser = authUserOptional.get();
            DefaultAccount.Builder accountBuilder = DefaultAccount.builder(username)
                    .setPassword(authUser.getPassword())
                    .setSalt(authUser.getSalt())
                    .setDisabledAccount(1 != authUser.getStatus())
                    .setExcessiveAttempts(2 == authUser.getStatus());
            List<String> roles = loadAccountRoles(username);
            if (roles != null) {
                accountBuilder.setOwnRoles(roles);
            }
            return accountBuilder.build();
        } else {
            return null;
        }
    }

    @Override
    public boolean authorityUserRole(String appId, Long roleId) {
        Optional<AuthUserDO> optional = authUserDao.findAuthUserByUsername(appId);
        if (!optional.isPresent()) {
            return false;
        }
        Long userId = optional.get().getId();
        AuthUserRoleBindDO userRoleBindDO = AuthUserRoleBindDO.builder().userId(userId).roleId(roleId).build();

        userRoleBindDao.save(userRoleBindDO);
        return true;
    }

    @Override
    public boolean deleteAuthorityUserRole(String appId, Long roleId) {
        Optional<AuthUserDO> optional = authUserDao.findAuthUserByUsername(appId);
        if (!optional.isPresent()) {
            return false;
        }
        Long userId = optional.get().getId();
        userRoleBindDao.deleteRoleResourceBind(roleId, userId);
        return true;
    }

}
