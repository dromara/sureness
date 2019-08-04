package com.usthe.sureness.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.List;

/**
 * json web token相关工具类
 * @author tomsun28
 * @date 16:29 2018/3/8
 */
public class JsonWebTokenUtil {

    /** 默认SUBJECT加密解密签名KEY **/
    private static final String DEFAULT_SECRET_KEY = "?::4390fsf4sdl6opf):";

    /** JWT格式3个点 **/
    private static final int COUNT_3 = 3;

    /** 加密解密签名 **/
    private static String secretKey;

    static {
        secretKey = DEFAULT_SECRET_KEY;
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
     * @param algorithm 加密算法
     * @return java.lang.String jwt
     */
    public static String issueJwt(String id, String subject, String issuer, Long period,
                                  List<String> roles, List<String> permissions,
                                  Boolean isRefresh, SignatureAlgorithm algorithm) {
        // 当前时间戳
        long currentTimeMillis = System.currentTimeMillis();
        // 秘钥
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
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
        jwtBuilder.signWith(algorithm, secretKeyBytes);
        return jwtBuilder.compact();
    }

    /**
     * 判断其是否是JWT，这里主要用格式来判断，不校验
     * @param jwt JWT TOKEN
     * @return 为JWT返回false 否则 true
     */
    public static boolean isNotJsonWebToken(String jwt) {
        // base64url_encode(Header) + '.' + base64url_encode(Claims) + '.' + base64url_encode(Signature)
        return jwt.split("\\.").length != COUNT_3;
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
    public static Claims parseJwt(String jwt) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return  Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(jwt)
                .getBody();

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
     * @param secretNowKey 签名KEY
     */
    public static void setSecretKey(String secretNowKey) {
        secretKey = secretNowKey;
    }
}
