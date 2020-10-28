package com.usthe.sureness.processor.support;

import com.usthe.sureness.processor.BaseProcessor;
import com.usthe.sureness.processor.exception.*;
import com.usthe.sureness.provider.SurenessAccount;
import com.usthe.sureness.provider.SurenessAccountProvider;
import com.usthe.sureness.subject.Subject;
import com.usthe.sureness.subject.support.DigestSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * process digest auth
 * @author tomsun28
 * @date 2020-10-28 23:17
 */
public class DigestProcessor extends BaseProcessor {

    private static final Logger logger = LoggerFactory.getLogger(DigestProcessor.class);

    private static final String DEFAULT_REALM = "sureness_realm";
    private static final String DEFAULT_QOP = "auth";
    private static final String HEX_LOOKUP = "0123456789abcdef";
    private static MessageDigest md5Digest;
    private static String realm;
    private static String qop;
    private SurenessAccountProvider accountProvider;

    static {
        try {
            md5Digest = MessageDigest.getInstance("MD5");
            realm = DEFAULT_REALM;
            qop = DEFAULT_QOP;
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean canSupportAuTokenClass(Class<?> var) {
        return var == DigestSubject.class;
    }

    @Override
    public Class<?> getSupportAuTokenClass() {
        return DigestSubject.class;
    }

    @Override
    public Subject authenticated(Subject var) throws SurenessAuthenticationException {
        if (var.getPrincipal() == null || var.getCredentials() == null) {
            String authenticate = getAuthenticate();
            throw new NeedDigestInfoException("you should try once with digest auth information", authenticate);
        }
        String appId = (String) var.getPrincipal();
        SurenessAccount account = accountProvider.loadAccount(appId);
        if (account == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("PasswordProcessor authenticated fail, no this user: {}",
                        var.getPrincipal());
            }
            throw new UnknownAccountException("do not exist the account: " + appId);
        }
        DigestSubject digestSubject = (DigestSubject) var;
        //A1 = MD5("username:realm:password");
        String a1 = calcDigest(appId, digestSubject.getRealm(), account.getPassword());
        //A2 = MD5("httpMethod:uri");
        String a2 = calcDigest(digestSubject.getHttpMethod(), digestSubject.getUri());
        //response = MD5("A1:nonce:nc:cNonce:qop:A2");
        String oriResponse = calcDigest(a1, digestSubject.getNonce(), digestSubject.getNc(), digestSubject.getCnonce(),
                digestSubject.getQop(), a2);
        if (!oriResponse.equals(digestSubject.getCredentials())) {
            throw new IncorrectCredentialsException("incorrect password");
        }
        if (account.isDisabledAccount()) {
            throw new DisabledAccountException("account is disabled");
        }
        if (account.isExcessiveAttempts()) {
            throw new ExcessiveAttemptsException("account is disable due to many time authenticated, try later");
        }
        return DigestSubject.builder(var)
                .setOwnRoles(account.getOwnRoles())
                .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void authorized(Subject var) throws SurenessAuthorizationException {
        List<String> ownRoles = (List<String>)var.getOwnRoles();
        List<String> supportRoles = (List<String>)var.getSupportRoles();
        if (supportRoles == null || supportRoles.isEmpty() || supportRoles.stream().anyMatch(ownRoles::contains)) {
            return;
        }
        throw new UnauthorizedException("do not have the role to access resource");
    }

    private String getAuthenticate(){
        String nonce = calcDigest(String.valueOf(System.currentTimeMillis()));
        return "Digest " + "realm=" + realm + ",nonce=" + nonce + ",qop=" + qop;
    }

    private String calcDigest(String first, String ... args){
        StringBuilder stringBuilder = new StringBuilder(first);
        if (args != null) {
            for (String str : args){
                stringBuilder.append(':').append(str);
            }
        }
        md5Digest.reset();
        md5Digest.update(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
        return bytesToHexString(md5Digest.digest());
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(HEX_LOOKUP.charAt((aByte & 0xF0) >> 4));
            sb.append(HEX_LOOKUP.charAt((aByte & 0x0F)));
        }
        return sb.toString();
    }

    public void setAccountProvider(SurenessAccountProvider provider) {
        this.accountProvider = provider;
    }

    public static void setRealm(String realm) {
        DigestProcessor.realm = realm;
    }

    public static void setQop(String qop) {
        DigestProcessor.qop = qop;
    }
}
