package com.usthe.sureness.subject.support.ldap;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.logging.Logger;

/**
 * @author Ed
 * @create 2021-08-14 10:47
 */
public class ldap {
    private static DirContext ctx;

    // LDAP服务器端口默认为389
    private static final String LDAP_URL = "ldap://127.0.0.1:389";

    // ROOT根据此参数确认用户组织所在位置
    private static final String LDAP_PRINCIPAL = "OU=CMA Users,DC=changan-mazda,DC=com,DC=cn";

    // LDAP驱动
    private static final String LDAP_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

    private static Logger logger = Logger.getLogger(String.valueOf(ldap.class));

    /**** 测试 ****/
    public static void main(String[] args) {
        ldap.getLoginContext();
        ldap.addUserLdap("10000", "123456");
        ldap.updatePasswordLdap("10000", "1234567");
        ldap.deleteUserLdap("10000");
    }

    // 通过连接LDAP服务器对用户进行认证，返回LDAP对象
    public static DirContext getLoginContext() {
        String account = "zhangsan"; // 模拟用户名
        String password = "123456"; // 模拟密码
        for (int i = 0; i < 5; i++) { // 验证次数
            Hashtable env = new Hashtable();
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_CREDENTIALS, password);
            // cn=属于哪个组织结构名称，ou=某个组织结构名称下等级位置编号
            env.put(Context.SECURITY_PRINCIPAL, "cn=" + account + ", ou=Level0" + i + "00," + LDAP_PRINCIPAL);
            env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_FACTORY);
            env.put(Context.PROVIDER_URL, LDAP_URL);
            try {
                // 连接LDAP进行认证
                ctx = new InitialDirContext(env);
                System.out.println("认证成功");
                logger.info("【" + account + "】用户于【" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "】登陆系统成功");
            } catch (javax.naming.AuthenticationException e) {
                System.out.println("认证失败");
            } catch (NamingException err) {
                logger.info("--------->>【" + account + "】用户验证失败【" + i + "】次");
            } catch (Exception e) {
                System.out.println("认证出错：");
                e.printStackTrace();
            }
        }
        return ctx;
    }

    // 将输入用户和密码进行加密算法后验证
    public static boolean verifySHA(String ldappw, String inputpw) {

        // MessageDigest 提供了消息摘要算法，如 MD5 或 SHA，的功能，这里LDAP使用的是SHA-1
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 取出加密字符
        if (ldappw.startsWith("{SSHA}")) {
            ldappw = ldappw.substring(6);
        } else if (ldappw.startsWith("{SHA}")) {
            ldappw = ldappw.substring(5);
        }

        // 解码BASE64
        byte[] ldappwbyte = Base64.decode(ldappw);
        byte[] shacode;
        byte[] salt;

        // 前20位是SHA-1加密段，20位后是最初加密时的随机明文
        if (ldappwbyte.length <= 20) {
            shacode = ldappwbyte;
            salt = new byte[0];
        } else {
            shacode = new byte[20];
            salt = new byte[ldappwbyte.length - 20];
            System.arraycopy(ldappwbyte, 0, shacode, 0, 20);
            System.arraycopy(ldappwbyte, 20, salt, 0, salt.length);
        }

        // 把用户输入的密码添加到摘要计算信息
        md.update(inputpw.getBytes());
        // 把随机明文添加到摘要计算信息
        md.update(salt);

        // 按SSHA把当前用户密码进行计算
        byte[] inputpwbyte = md.digest();

        // 返回校验结果
        return MessageDigest.isEqual(shacode, inputpwbyte);
    }

    // 添加用户
    public static boolean addUserLdap(String account, String password) {
        boolean success = false;
        try {
            ctx = ldap.getLoginContext();
            BasicAttributes attrsbu = new BasicAttributes();
            BasicAttribute objclassSet = new BasicAttribute("objectclass");
            objclassSet.add("person");
            objclassSet.add("top");
            objclassSet.add("organizationalPerson");
            objclassSet.add("inetOrgPerson");
            attrsbu.put(objclassSet);
            attrsbu.put("sn", account);
            attrsbu.put("uid", account);
            attrsbu.put("userPassword", password);
            ctx.createSubcontext("cn=" + account + ",ou=People", attrsbu);
            ctx.close();
            return true;
        } catch (NamingException ex) {
            try {
                if (ctx != null) {
                    ctx.close();
                }
            } catch (NamingException namingException) {
                namingException.printStackTrace();
            }
            logger.info("--------->>添加用户失败");
        }
        return false;
    }

    // 修改密码
    public static boolean updatePasswordLdap(String account, String password) {
        boolean success = false;
        try {
            ctx = ldap.getLoginContext();
            ModificationItem[] modificationItem = new ModificationItem[1];
            modificationItem[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", password));
            ctx.modifyAttributes("cn=" + account + ",ou=People", modificationItem);
            ctx.close();
            return true;
        } catch (NamingException ex) {
            try {
                if (ctx != null) {
                    ctx.close();
                }
            } catch (NamingException namingException) {
                namingException.printStackTrace();
            }
            logger.info("--------->>修改密码失败");
        }
        return success;
    }

    // 删除用户
    public static boolean deleteUserLdap(String account) {
        try {
            ctx = ldap.getLoginContext();
            ctx.destroySubcontext("cn=" + account);
        } catch (Exception ex) {
            try {
                if (ctx != null) {
                    ctx.close();
                }
            } catch (NamingException namingException) {
                namingException.printStackTrace();
            }
            logger.info("--------->>删除用户失败");
            return false;
        }
        return true;
    }

    // 关闭LDAP服务器连接
    public static void closeCtx() {
        try {
            ctx.close();
        } catch (NamingException ex) {
            logger.info("--------->> 关闭LDAP连接失败");
        }
    }
}
