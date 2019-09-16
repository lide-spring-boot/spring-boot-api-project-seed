package testBase.web;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;


import java.util.ArrayList;
import java.util.Collection;

/**
 * 设置用户登陆时的 SecurityContext
 *
 * @author Zoctan
 * @date 2018/11/29
 */
public class WithCustomSecurityContextFactory
    implements WithSecurityContextFactory<WithCustomUser> {

  /**
   * 根据用户名获取权限
   * @param customUser
   * @return
   */
  @Override
  public SecurityContext createSecurityContext(final WithCustomUser customUser) {
    final SecurityContext context = SecurityContextHolder.createEmptyContext();
    final UserDetails userDetails = new UserDetails() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> objects = new ArrayList<>();
        objects.add(new SimpleGrantedAuthority("role1"));
        return objects;
      }

      @Override
      public String getPassword() {
        return null;
      }

      @Override
      public String getUsername() {
        return null;
      }

      @Override
      public boolean isAccountNonExpired() {
        return false;
      }

      @Override
      public boolean isAccountNonLocked() {
        return false;
      }

      @Override
      public boolean isCredentialsNonExpired() {
        return false;
      }

      @Override
      public boolean isEnabled() {
        return false;
      }

    };

    final Authentication auth =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    context.setAuthentication(auth);
    return context;
  }
}
