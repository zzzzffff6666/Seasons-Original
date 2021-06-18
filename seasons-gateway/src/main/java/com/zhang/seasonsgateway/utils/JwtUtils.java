package com.zhang.seasonsgateway.utils;

import com.zhang.seasonsgateway.model.SeasonsAuthenticationToken;
import com.zhang.seasonsgateway.service.AccountService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

@Slf4j
public class JwtUtils {
    /**过期时间---24 hour*/
    private static final int EXPIRATION_TIME = 60 * 60 * 24;
    /**自己设定的秘钥*/
    private static final String SECRET = "02s8j10me3ji171c5a4587*9ee6581508b9d03ad39a74fc0c9a9cce604743367c9646b";
    /**前缀*/
    public static final String TOKEN_PREFIX = "Bearer ";
    /**表头授权*/
    public static final String AUTHORIZATION = "Authorization";

    /**
     *
     * @author: xxm
     * 功能描述:创建Token
     * @date: 2020/5/28 16:09
     * @param:
     * @return:
     */
    public static String generateToken(SeasonsAuthenticationToken auth) {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        // 设置签发时间
        calendar.setTime(new Date());
        // 设置过期时间
        // 添加秒钟
        calendar.add(Calendar.SECOND, EXPIRATION_TIME);
        Date time = calendar.getTime();

        Map<String, Object> map = new HashMap<>();

        //将授权信息录入
        map.put("id", auth.getId());
        map.put("type", auth.getType());
        map.put("principal", auth.getName());

        String jwt = Jwts.builder()
                .setClaims(map)
                //签发时间
                .setIssuedAt(now)
                //过期时间
                .setExpiration(time)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        //jwt前面一般都会加Bearer
        return TOKEN_PREFIX + jwt;
    }
    /**
     *
     * @author: xxm
     * 功能描述: 解密Token
     * @date: 2020/5/28 16:18
     * @param:
     * @return:
     */
    public static SeasonsAuthenticationToken validateToken(String token) {
        try {
            // parse the token.
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            int id = (int) body.get("id");
            String type = body.get("type").toString();
            String principal = body.get("principal").toString();
            List<SimpleGrantedAuthority> authorities = AccountService.getAuths(type);

            return new SeasonsAuthenticationToken(principal, "", type, id, authorities);
        }  catch (Exception e){
            log.info(e.getMessage());
            return null;
        }
    }

    public static boolean checkToken(String token) {
        try {
            // parse the token.
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            Integer.parseInt(body.get("id").toString());
            String type = body.get("type").toString();
            String principal = body.get("principal").toString();
            return type != null && principal != null;
        }  catch (Exception e){
            log.info(e.getMessage());
            return false;
        }
    }
}
