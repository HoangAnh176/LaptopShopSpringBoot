package vn.spring.laptopshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import jakarta.servlet.DispatcherType;
import vn.spring.laptopshop.service.CustomUserDetailsService;
import vn.spring.laptopshop.service.UserService;


@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(UserService userService) {
    return new CustomUserDetailsService(userService);
  }

  @Bean
  public DaoAuthenticationProvider authProvider(
      PasswordEncoder passwordEncoder,
      UserDetailsService userDetailsService) {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService); 
    authProvider.setPasswordEncoder(passwordEncoder); 
    return authProvider;
  }

  @Bean
  public AuthenticationSuccessHandler customSuccessHandler() {
    return new CustomSuccessHandler();
  }

  @Bean
  public SpringSessionRememberMeServices rememberMeServices() {
    SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
    rememberMeServices.setAlwaysRemember(true); 
    return rememberMeServices;
  }

  // Cấu hình bảo mật chính với HttpSecurity
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http

        .authorizeHttpRequests(authorize -> authorize
            .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.INCLUDE).permitAll()
            .requestMatchers("/", "/login", "/product/**", "/client/**", "/css/**", "/js/**", "/images/**" , "/register").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated())

        // Cấu hình session
        .sessionManagement(sessionManagement -> sessionManagement
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) 
            .invalidSessionUrl("/logout?expired") 
            .maximumSessions(1)
            .maxSessionsPreventsLogin(false)) 

        // Cấu hình logout
        .logout(logout -> logout
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true)) 

        // Cấu hình remember-me
        .rememberMe(r -> r.rememberMeServices(rememberMeServices()))

        // Cấu hình form đăng nhập
        .formLogin(formLogin -> formLogin
            .loginPage("/login")
            .failureUrl("/login?error")
            .successHandler(customSuccessHandler()) 
            .permitAll())

        // Xử lý lỗi truy cập trái phép
        .exceptionHandling(ex -> ex.accessDeniedPage("/access-deny"));

    // Trả về chuỗi filter đã cấu hình
    return http.build();
  }
}
