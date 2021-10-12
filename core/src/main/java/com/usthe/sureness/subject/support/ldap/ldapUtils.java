package com.usthe.sureness.subject.support.ldap;

import com.alibaba.fastjson.JSONObject;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

/**
 * @author Ed
 * @create 2021-08-13 0:09
 */
public class ldapUtils {
    /**
     * 管理员连接
     *
     * @Description:
     * @param principle
     * @param password
     * @param url
     * @return
     */
    public static LdapContext connectLdapAdmin(String principle, String password, String url) {
        LdapContext ctxTDS = null;
        Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.SECURITY_PRINCIPAL, principle);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.PROVIDER_URL, url);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        try {
            ctxTDS = new InitialLdapContext(env, null);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return ctxTDS;
    }

    /**
     * ldap
     */
    public static boolean connectLdap(String userName, String password, String SearchName, String ldapUrl,
                                      String baseDN) {
        Hashtable<String, String> env = new Hashtable<String, String>();
        boolean result = false;
        env.put(Context.SECURITY_PRINCIPAL, "uid=" + userName + "," + SearchName);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.PROVIDER_URL, ldapUrl + baseDN);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        try {
            new InitialLdapContext(env, null);
            result = true;
        } catch (NamingException e) {
            System.out.println("userName：" + userName + ",SearchName:" + SearchName + "登录失败");
        }
        return result;
    }

    public static void main(String[] args) {
        String certId = "";
        String password = "";
        String principle = "";
        String passwordLdap = "";
        String url = "";
        String teacherSearch = "";
        String studentSearch = "";
        String baseDN = "";
        try {
            LdapContext ctx = ldapUtils.connectLdapAdmin(principle, passwordLdap, url);
            String filter = "(&(objectClass=*)(uid=" + certId + "))";
            String[] attrPersonArray = { "uid", "employeeType", "displayName"};
            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(2);
            searchControls.setReturningAttributes(attrPersonArray);
            NamingEnumeration<SearchResult> answer = ctx.search("ou=Users,dc=test,dc=edu,dc=cn", filter.toString(),
                    searchControls);
            JSONObject resultJson = new JSONObject();
            while (answer.hasMore()) {
                SearchResult result = (SearchResult) answer.next();
                NamingEnumeration attrs = result.getAttributes().getAll();
                while (attrs.hasMore()) {
                    Attribute attr = (Attribute) attrs.next();
                    resultJson.put(attr.getID(), attr.get());
                }
            }
            // 在校学生
            boolean studentFlag = ldapUtils.connectLdap(certId, password, studentSearch, url, baseDN);
            if (studentFlag) {
                System.out.println("ldap用户信息：" + resultJson.toJSONString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
