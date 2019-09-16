package com.company.project.module.jwt;


import com.company.project.module.rsa.RsaUtils;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Json web token 工具 验证、生成 token
 *
 * @author Zoctan
 * @date 2018/05/27
 */
@Slf4j
@Component
public class JwtUtil {
  @Resource private JwtConfigurationProperties jwtProperties;

  @Value("${rsa.public-key}")
  String publicKey;
  @Value("${rsa.private-key}")
  String privateKey;


  private Claims getClaims(final String token) {
    final Jws<Claims> jws = this.parseToken(token);
    return jws == null ? null : jws.getBody();
  }

  /** 根据 token 得到账户名 */
  public String getName(final String token) {
    final Claims claims = this.getClaims(token);
    return claims == null ? null : claims.getSubject();
  }

  /**
   * 签发 token
   *
   * @param name 账户名
   * @param grantedAuthorities 账户权限信息[ADMIN, TEST, ...]
   */
  public String sign(
      final String name, final Collection<? extends GrantedAuthority> grantedAuthorities) {
    // 函数式创建 token，避免重复书写
    final Supplier<String> createToken = () -> this.createToken(name, grantedAuthorities);

    // 重新签发
    return createToken.get();
  }



  /** 从请求头或请求参数中获取 token */
  public String getTokenFromRequest(final HttpServletRequest httpRequest) {
    final String header = this.jwtProperties.getHeader();
    final String token = httpRequest.getHeader(header);
    return StringUtils.isNotBlank(token) ? token : httpRequest.getParameter(header);
  }

  /** 返回账户认证 */
  public UsernamePasswordAuthenticationToken getAuthentication(
      final String name, final String token) {
    // 解析 token 的 payload
    final Claims claims = this.getClaims(token);

    // 因为 JwtAuthenticationFilter 拦截器已经检查过 token 有效，所以可以忽略 get 空指针提示
    assert claims != null;
    final String claimKeyAuth = this.jwtProperties.getClaimKeyAuth();
    // 账户角色列表
    final List<String> authList = Arrays.asList(claims.get(claimKeyAuth).toString().split(","));
    // 将元素转换为 GrantedAuthority 接口集合
    final Collection<? extends GrantedAuthority> authorities =
        authList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    final User user = new User(name, "", authorities);
    return new UsernamePasswordAuthenticationToken(user, null, authorities);
  }



  /** 生成 token */
  private String createToken(
      final String name, final Collection<? extends GrantedAuthority> grantedAuthorities) {
    // 获取账户的角色字符串，如 USER,ADMIN
    final String authorities =
        grantedAuthorities
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
    log.debug("==> Account<{}> authorities: {}", name, authorities);

    // 过期时间

    final Duration expireTime = Duration.ofSeconds(jwtProperties.getExpireTime());
    // 当前时间 + 有效时长
    final Date expireDate = new Date(System.currentTimeMillis() + expireTime.toMillis());
    // 创建 token，比如 "Bearer abc1234"


    final String token =
        this.jwtProperties.getTokenType()
            + " "
            + Jwts.builder()
                // 设置账户名
                .setSubject(name)
                // 添加权限属性
                .claim(this.jwtProperties.getClaimKeyAuth(), authorities)
                // 设置失效时间
                .setExpiration(expireDate)
                // 私钥加密生成签名
                .signWith(SignatureAlgorithm.RS256, RsaUtils.stingToKey(privateKey,2))
                // 使用LZ77算法与哈夫曼编码结合的压缩算法进行压缩
                .compressWith(CompressionCodecs.DEFLATE)
                .compact();

    return token;
  }

  /** 解析 token */
  private Jws<Claims> parseToken(final String token) {
    try {
      System.out.println("===>"+token.replace(this.jwtProperties.getTokenType(), ""));
      return Jwts.parser()
          // 公钥解密
          .setSigningKey( RsaUtils.stingToKey(publicKey,1))
          .parseClaimsJws(token.replace(this.jwtProperties.getTokenType(), ""));
    } catch (final SignatureException e) {
      // 签名异常
      log.debug("Invalid JWT signature");
    } catch (final MalformedJwtException e) {
      // 格式错误
      log.debug("Invalid JWT token");
    } catch (final ExpiredJwtException e) {
      // 过期
      log.debug("Expired JWT token");
    } catch (final UnsupportedJwtException e) {
      // 不支持该JWT
      log.debug("Unsupported JWT token");
    } catch (final IllegalArgumentException e) {
      // 参数错误异常
      log.debug("JWT token compact of handler are invalid");
    }
    return null;
  }
}
