package com.usthe.sureness.subject.support.ldap;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import java.util.Hashtable;

/**
 * @author Ed
 * @create 2021-08-12 23:51
 */
public class LDAPUtil {

    private String LDAPURL = "LDAP://localhost:389/";

    private String BASEDN = "dc=test,dc=com";

    public boolean connectLDAP(String userName, String passwd, String SearchName) {
        Hashtable<String, String> env = new Hashtable<String, String>();
        System.out.println("===" + userName + "开始认证LDAP===");
        System.out.println("password:" + passwd);

        boolean result = false;

        env.put(Context.SECURITY_PRINCIPAL, "uid=" + userName + "," + SearchName);//用户名
        System.out.println("uid=" + userName + "," + SearchName);
        env.put(Context.SECURITY_CREDENTIALS, passwd);//密码
        env.put(Context.PROVIDER_URL, LDAPURL + BASEDN);//连接LDAP的URL和端口（这里的BASEDN可以不用，只要LDAPURL）
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");//JNDI Context工厂类
        env.put(Context.SECURITY_AUTHENTICATION, "simple");//认证类型

        try {
            new InitialLdapContext(env, null);//开始连接
            result = true;
            System.out.println("===认证成功===");
        } catch (NamingException e) {
            System.out.println("===认证失败===");
        }
        return result;
    }

    public static void main(String[] args) {

    }
}
