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

    private static final String SECRET_KEY = "?::4390fsf4sdl6opf):";
    private static final int COUNT_3 = 3;

    /**
     *   json web token 签发
     * @param id 令牌ID
     * @param subject 用户ID
     * @param issuer 签发人
     * @param period 有效时间(毫秒)
     * @param roles 访问主张-角色
     * @param permissions 访问主张-权限
     * @param algorithm 加密算法
     * @return java.lang.String jwt
     */
    public static String issueJWT(String id, String subject, String issuer, Long period,
                                  List<String> roles, List<String> permissions,
                                  Boolean isRefresh, SignatureAlgorithm algorithm) {
        // 当前时间戳
        long currentTimeMillis = System.currentTimeMillis();
        // 秘钥
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
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
     * 验签JWT
     *
     * @param jwt json web token
     */
    public static Claims parseJwt(String jwt) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return  Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
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
}
