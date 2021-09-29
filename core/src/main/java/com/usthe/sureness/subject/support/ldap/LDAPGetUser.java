package com.usthe.sureness.subject.support.ldap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author Ed
 * @create 2021-08-15 0:04
 * @Description: LDAP认证获取用户信息
 */
public class LDAPGetUser {
    private String BASEDN;

    private String PASSWORD;

    private String URL;

    private String STAFFSEARCHNAME;

    private String GSTUDENTSEARCHNAME;

    private String STUDENTSEARCHNAME;

    private String PRINCIPLE;

    private LDAPUtil ldapUtil;

    public Map<String, Object> getUser(String uid, String pwd) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //连接LDAP
            LdapContext ctx = connetLDAP();
            //过滤条件
            String filter = "(&(objectClass=*)(uid=" + uid + "))";
            //要获取的字段信息
            String[] attrPersonArray = { "uid", "userPassword", "displayName", "cn", "sn", "mail", "description" };
            SearchControls searchControls = new SearchControls();//搜索控件
            searchControls.setSearchScope(2);//搜索范围
            searchControls.setReturningAttributes(attrPersonArray);
            //1.要搜索的上下文或对象的名称；2.过滤条件，可为null，默认搜索所有信息；3.搜索控件，可为null，使用默认的搜索控件
            NamingEnumeration<SearchResult> answer = ctx.search("ou=People,dc=uestc,dc=edu,dc=cn", filter.toString(),searchControls);

            while (answer.hasMore()) {
                SearchResult result = (SearchResult) answer.next();
                NamingEnumeration attrs = result.getAttributes().getAll();
                while (attrs.hasMore()) {
                    Attribute attr = (Attribute) attrs.next();
                    System.out.println(attr.getID() + "=" + attr.get());
                    map.put(attr.getID(), attr.get());
                }
            }
            // 在校研究生
            boolean flag = ldapUtil.connectLDAP(uid, pwd, GSTUDENTSEARCHNAME);
            if (!flag) {
                // 在校本科生
                flag = ldapUtil.connectLDAP(uid, pwd, STUDENTSEARCHNAME);
                if (!flag) {
                    // 在校教职工
                    flag = ldapUtil.connectLDAP(uid, pwd, STAFFSEARCHNAME);
                }
            }
            map.put("flag", Boolean.valueOf(flag));

        } catch (Exception e) {
            System.out.println("===认证失败===");
        }

        return map;
    }

    public LdapContext connetLDAP() throws NamingException {

        System.out.println("====管理员开始连接====");

        Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.SECURITY_PRINCIPAL, PRINCIPLE);//用户名
        env.put(Context.SECURITY_CREDENTIALS, PASSWORD);//密码
        env.put(Context.PROVIDER_URL, URL);//LDAP的地址：端口
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");//LDAP工厂类
        env.put(Context.SECURITY_AUTHENTICATION, "simple");//认证类型
        LdapContext ctxTDS = new InitialLdapContext(env, null);//连接

        return ctxTDS;
    }
}
