package com.usthe.sureness.subject.support;

import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.SubjectAuToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.naming.AuthenticationException;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author tomsun28
 * @date 22:03 2019-01-22
 */
public class DefaultSubject implements Subject {

    private static transient final Logger LOGGER = LoggerFactory.getLogger(DefaultSubject.class);

    private boolean authenticated = false;
    private String principal;
    private String[] roles;

    /**
     *  当有一次调用onceLogin之后,特指方法才生效
     */
    private boolean onceLogin = false;

    private boolean isOnceLogin() {
        return onceLogin;
    }


    @Override
    public void login(SubjectAuToken var1) throws AuthenticationException {
        onceLogin = !onceLogin || onceLogin;

    }

    @Override
    public void logout() {
        authenticated = false;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }


    @Override
    public boolean isAuthorizated() {
        return false;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean hasRole(String var1) {
        return false;
    }

    @Override
    public boolean hasAllRoles(Collection<String> var1) {
        return false;
    }

    @Override
    public Object getRoles() {
        return null;
    }

    @Override
    public Object cloneImage() {
        return new ImageDefaultSubject(authenticated,principal,roles);
    }

    public  class ImageDefaultSubject implements Serializable {
        private boolean authenticated;
        private String principal;
        private String[] roles;

        private ImageDefaultSubject(boolean authenticated, String principal, String[] roles) {
            this.authenticated = authenticated;
            this.principal = principal;
            this.roles = roles;
        }

        public boolean isAuthenticated() {
            return authenticated;
        }

        public String[] getRoles() {
            return roles;
        }

        public String getPrincipal() {
            return principal;
        }

    }
}
