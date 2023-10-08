package cloudcode.pedidos.security;

import cloudcode.pedidos.security.services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

//   private static final  Logger log = getLogger(WebSecurityConfig.class);


        @Autowired
        CustomUserDetailService userDetailsService;

        @Autowired
        private AuthEntryPoint unauthorizedHandler;

//        @Autowired
//        CustomAccessDeniedHandler accessDeniedHandler;
        @Bean
        public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}



    @Bean
    public DaoAuthenticationProvider authProvider(){
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService);
            authProvider.setPasswordEncoder(passwordEncoder());
            return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
            throws Exception{
            return authConfig.getAuthenticationManager();
    }




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
//            .exceptionHandling(exception -> exception
//                    .authenticationEntryPoint(unauthorizedHandler)
//                    .accessDeniedHandler(accessDeniedHandler)
//            )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(
                            "/api/v1/**",
                            "/**",
                            "/api/auth/**",
                            "/api/test/**",
                            "/css/**",
                            "/favicon.ico",
                            "/js/**"
                    ).permitAll()
                    .requestMatchers(HttpMethod.POST, "/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/admin/**").hasRole("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/api/admin/**")).hasRole("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/api/user/**")).hasAnyRole("USER", "ADMIN")

                    .anyRequest().authenticated()

            )

            .formLogin(formLogin -> formLogin
                    .loginPage("/api/v1/login")
                    .loginProcessingUrl("/login")
                    .failureUrl("/api/v1/login-error")
                    .defaultSuccessUrl("/api/user/mesas", true)


            )
            .logout(logout -> logout
                    .logoutUrl("/api/logout")
                    .logoutSuccessUrl("/api/v1/index")
            )

            ;
    http.authenticationProvider(authProvider());
    return http.build();

    }



}
