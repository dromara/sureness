package com.usthe.sureness.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.*;
import io.jsonwebtoken.security.SignatureException;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * json web token相关工具类
 * use hmac algorithm, can change the secretKey by setDefaultSecretKey
 * @author tomsun28
 * @date 16:29 2018/3/8
 */
public class JsonWebTokenUtil {

    /** 默认SUBJECT加密解密签名KEY **/
    private static final String DEFAULT_SECRET_KEY =
            "MIIEowIBAl+f/dKhaX0csgOCTlCxq20yhmUea6H6JIpST3ST1SE2Rwp" +
            "LnfKefTjsIfJLBa2YkhEqE/GtcHDTNe4CU6+9y/S5z50Kik70LsP43r" +
            "RnLN7XNn4wARoQXizIv6MHUsIV+EFfiMw/x7R0ntu4aWr/CWuApcFaj" +
            "4mWEa6EwrPHTZmbT5Mt45AM2UYhzDHK+0F0rUq3MwH+oXsm+L3F/zjj" +
            "M6EByXIO+SV5+8tVt4bisXQ13rbN0oxhUZR73+LDj9mxa6rFhMW+lfx" +
            "CyaFv0bwq2Eik0jdrKUtsA6bx3sDJeFV643R+YYzGMRIqcBIp6AKA98" +
            "GM2RIqcBIp6-?::4390fsf4sdl6opf)4ZI:tdQMtcQQ14pkOAQdQ546";

    /** JWT格式3个点 **/
    private static final int COUNT_3 = 3;

    /** 判断是否是base64串 **/
    private static final Pattern BASE64_PATTERN =
            Pattern.compile("^([A-Za-z0-9+/_-]+)(=*)$");

    /** 加密解密签名 **/
    private static Key secretKey;

    static {
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(DEFAULT_SECRET_KEY);
        secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
    }


    /**
     *   json web token 签发
     * @param id 令牌ID
     * @param subject 用户ID
     * @param issuer 签发人
     * @param period 有效时间(毫秒)
     * @param roles 访问主张-角色
     * @param permissions 访问主张-权限
     * @param isRefresh 是否是刷新token
     * @return java.lang.String jwt
     */
    public static String issueJwt(String id, String subject, String issuer, Long period,
                                  List<String> roles, List<String> permissions,
                                  Boolean isRefresh) {
        // 当前时间戳
        long currentTimeMillis = System.currentTimeMillis();
        JwtBuilder jwtBuilder = Jwts.builder();
        if (id != null) {
            jwtBuilder.setId(id);
        }
        if (subject != null) {
            jwtBuilder.setSubject(subject);
        }
        if (issuer != null) {
            jwtBuilder.setIssuer(issuer);
        }
        // 设置签发时间
        jwtBuilder.setIssuedAt(new Date(currentTimeMillis));
        // 设置到期时间
        if (null != period) {
            jwtBuilder.setExpiration(new Date(currentTimeMillis + period * 1000));
        }
        if (roles != null) {
            jwtBuilder.claim("roles", roles);
        }
        if (permissions != null) {
            jwtBuilder.claim("perms", permissions);
        }
        if (isRefresh != null) {
            jwtBuilder.claim("isRefresh", isRefresh);
        }
        // 压缩，可选GZIP
        jwtBuilder.compressWith(CompressionCodecs.DEFLATE);
        // 加密设置
        jwtBuilder.signWith(secretKey);
        return jwtBuilder.compact();
    }

    /**
     * 判断其是否是JWT，这里主要用格式来判断，不校验
     * @param jwt JWT TOKEN
     * @return 为JWT返回false 否则 true
     */
    public static boolean isNotJsonWebToken(String jwt) {
        // base64url_encode(Header) + '.' + base64url_encode(Claims) + '.' + base64url_encode(Signature)
        String[] jwtArr = jwt.split("\\.");
        if (jwtArr.length != COUNT_3) {
            return true;
        }
        for (String jwtTmp : jwtArr) {
            if (!BASE64_PATTERN.matcher(jwtTmp).matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param jwt json web token
     * @return 解签实体
     * @throws ExpiredJwtException token过期
     * @throws UnsupportedJwtException 不支持的TOKEN
     * @throws MalformedJwtException 参数格式形变等异常
     * @throws SignatureException 签名异常
     * @throws IllegalArgumentException 非法参数
     */
    public static Claims parseJwt(String jwt) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException {

        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(jwt).getBody();

        // 令牌ID -- claims.getId()
        // 客户标识 -- claims.getSubject()
        // 客户标识
        // 签发者 -- claims.getIssuer()
        // 签发时间 -- claims.getIssuedAt()
        // 接收方 -- claims.getAudience()
        // 访问主张-角色 -- claims.get("roles", String.class)
        // 访问主张-权限 -- claims.get("perms", String.class)
    }

    /**
     * 设置新的JWT加密解密签名
     * @param secretNowKeyValue key value
     */
    public static void setDefaultSecretKey(String secretNowKeyValue) {
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretNowKeyValue);
        secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
    }
}
