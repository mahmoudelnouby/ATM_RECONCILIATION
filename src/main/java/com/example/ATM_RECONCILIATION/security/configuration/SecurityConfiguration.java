package com.example.ATM_RECONCILIATION.security.configuration;

import com.example.ATM_RECONCILIATION.security.services.UserServices;
import lombok.RequiredArgsConstructor;
import com.example.ATM_RECONCILIATION.security.filters.RefererCheckFilter;
import com.example.ATM_RECONCILIATION.security.helpers.AuthenticationHandler;
import com.example.ATM_RECONCILIATION.security.services.CustomAuthenticationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration  {

    private static final String[] AUTH_WHITELIST = {
    		"/api/auth/**",
            "/api/auth/signin",
            "/api/v1/auth/signin",
			"/swagger-ui/**",
			"/v2/api-docs",
			"/v3/api-docs/**",
			"/swagger-resources/**",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/configuration/ui",
			"/configuration/security",
			"/swagger-ui.html",
			"/webjars/**",
			"/api/Mobile/**",
			"/public/**",
			"/login",
			"/",
			"/*.js",
			"/*.css",
			"/*.ttf",
			"/*.woff",
			"/assets/**",
			"/*.html",
			"/tgh2",
			"/j_security_check/**",
			"/getUserName",
			"/tgh2",
			"/*.png",
			"/*.ico",
			"/*.svg",
			"/*.xml",
			"/*.eot",
			"/setParam",
			"/signin"
    };

    @Value("${allowed.methods}")
    private String[] allowedMethods;
    
    @Value("${allowed.origins}")
    private String[] allowedOrigins;

    @Value("${ldap.flag}")
    private Integer ldapFlag;
    @Value("${mode}")
    private String mode;

    private final AuthenticationHandler authenticationHanddler;
    private final UserServices userService;



    
    @SuppressWarnings("rawtypes")
    @Autowired
    @Lazy
    AuthenticationUserDetailsService getUserDetailsService;



    @Autowired
    @Lazy
    AuthenticationManager authenticationManager;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("inside global config");
//        int ldapFlag=securityConfigRepo.FindLdapFlagByModuleId("System_Administration");
        // Returns LdapAuthenticationProviderConfigurer to allow customization of the
        // LDAP authentication
//        System.out.println(ldapFlag);
        if(ldapFlag==1)
        {
            auth.ldapAuthentication()
                    // Pass the LDAP patterns for finding the username.
                    // The key "{0}" will be substituted with the username
                    .userDnPatterns("cn={0},ou=people")
                    // Pass search base as argument for group membership searches.
                    //            .groupSearchBase("ou=groups")
                    // Configures base LDAP path context source
                    .contextSource().url("ldap://192.168.1.48:389/dc=maxcrc,dc=com")
                    // DN of the user who will bind to the LDAP server to perform the search
                    .managerDn("cn=manager,dc=maxcrc,dc=com")
                    // Password of the user who will bind to the LDAP server to perform the search
                    .managerPassword("secret").
                    // Configures LDAP compare operation of the user password to authenticate
                            and().passwordCompare().passwordEncoder(new LdapShaPasswordEncoder()).passwordAttribute("userPassword");
            // Specifies the attribute in the directory which contains the user password.
            // Defaults to "userPassword".
        }

    }
    @Bean
    public RefererCheckFilter refererCheckFilter() {
        return new RefererCheckFilter();
    }

    @SuppressWarnings("rawtypes")
    @Bean
    public AuthenticationUserDetailsService getUserDetailsService() {
        return new CustomAuthenticationUserDetailsService();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        if (mode.equals("devs")) {
            http.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.TRACE, "/**").permitAll().requestMatchers(AUTH_WHITELIST).permitAll())
                    .csrf(csrf -> csrf.disable());
        } else {
            if(ldapFlag==1)
            {
                System.out.println("Inside security filter chain If");

                http.csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**"),
                                        new AntPathRequestMatcher("/v3/api-docs/**"),new AntPathRequestMatcher("/swagger-ui/index.html#/"),
                                        new AntPathRequestMatcher("Template/**"),new AntPathRequestMatcher("/**"),
                                        new AntPathRequestMatcher("/swagger-resources"), new AntPathRequestMatcher("/swagger-resources/**"),
                                        new AntPathRequestMatcher("/configuration/ui"), new AntPathRequestMatcher("/swagger-ui.html"),
                                        new AntPathRequestMatcher("/swagger-ui/**"),
                                        new AntPathRequestMatcher("/v2/api-docs/**"))
                                .permitAll().anyRequest().authenticated())
                        .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS));
//                .authenticationProvider(activeDirectoryAuthenticationProvider()).addFilterBefore(
//                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            }
            else {
                http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                        .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                        .exceptionHandling(handling -> handling.authenticationEntryPoint(authenticationHanddler))
                        .sessionManagement(management -> management.sessionCreationPolicy(STATELESS))
//                    Authentication setting in security 6
                        .authenticationProvider(authenticationProvider())
                        .authorizeHttpRequests(requests -> requests
                                .requestMatchers(AUTH_WHITELIST).permitAll()
                                .anyRequest().authenticated());
            }


            http.addFilterBefore(refererCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        }

        return http.build();
    }




    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        if(allowedOrigins.toString().charAt(0) == '*') {
        	configuration.addAllowedOrigin("*");
        	System.out.println("allowed");
        } else {
        	for (String allowedOrigin: allowedOrigins) {
        		configuration.addAllowedOrigin(allowedOrigin);
        	}        	
        }
        for (String allowedMethod: allowedMethods) {
        	configuration.addAllowedMethod(allowedMethod);
        }
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        System.out.println("inside Authentication Provider");
        System.out.println(ldapFlag);
        if(ldapFlag==0)
        {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userService.userDetailsService());
            authProvider.setPasswordEncoder(passwordEncoder());
            return authProvider;
        }
        return null;
    }


}

//deprecated in security 6
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        if (mode.equals("dev")) {
//            http
//            .csrf(csrf -> csrf.disable()).authorizeRequests(requests -> requests.requestMatchers(HttpMethod.TRACE, "/**").permitAll());
//        } else {
//            http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(csrf -> csrf.disable())
//                .exceptionHandling(handling -> handling.authenticationEntryPoint(authenticationHanddler))
//                .sessionManagement(management -> management
//                    .sessionCreationPolicy(IF_REQUIRED))
//                .authorizeRequests(requests -> requests
//                    .requestMatchers(AUTH_WHITELIST).permitAll()
//                    .anyRequest().authenticated());
//
//            http.addFilterBefore(refererCheckFilter(), UsernamePasswordAuthenticationFilter.class);
//        }
//
//    }
//    replaced with filterchain

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/**","/swagger-ui/**",
//                                "/v3/api-docs/**","/swagger-ui/index.html#/","Template/**","/**")
//                        .permitAll().anyRequest().authenticated())
//                .sessionManagement(manager -> manager.sessionCreationPolicy(IF_REQUIRED))
//                .authenticationProvider(authenticationProvider()).addFilterBefore(
//                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }

//    @Override
//    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
//    throws Exception {
//        authenticationManagerBuilder.authenticationProvider(wlsAuthenticationProvider);
//    }
////    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(wlsAuthenticationProvider);
//    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(AUTH_WHITELIST).permitAll()
//                        .anyRequest().authenticated()
//                )
//                .logout(logout -> logout.permitAll());
//        return http.build();
//    }

//    @Bean
//    public MappableAttributesRetriever webXmlRolesParser() {
//        return new WebXmlMappableAttributesRetriever();
//    }

//    @Bean
//    public Attributes2GrantedAuthoritiesMapper roles2GrantedAuthoritiesMapper() {
//        SimpleAttributes2GrantedAuthoritiesMapper
//        var = new SimpleAttributes2GrantedAuthoritiesMapper();
//        var.setAttributePrefix("");
//        return var;
//    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        //    Deprecated in version 3.3.1
//        return super.authenticationManagerBean();
//    }

//    @Autowired
//    @Lazy
//    Attributes2GrantedAuthoritiesMapper roles2GrantedAuthoritiesMapper;

//    @Autowired
//    @Lazy
//    MappableAttributesRetriever webXmlRolesParser;