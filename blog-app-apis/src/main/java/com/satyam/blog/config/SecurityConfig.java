package com.satyam.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.satyam.blog.security.CustomUserDetailService;
import com.satyam.blog.security.JwtAuthenticationEntryPoint;
import com.satyam.blog.security.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)  // without using requestMatcher assging permission for admin only delete user in controller  
@EnableWebMvc  // for swwager
public class SecurityConfig //extends WebSecurityConfigurerAdpter
{
	
	public static final String[] PUBLIC_URLS= 
		{
				"/api/v1/auth/**",
				"/v3/api-docs",
				"/swagger-resources/**",
				"/swagger-ui/**",
				"/webjars/**",
				"/v2/api-docs"
		};
	
	
//	
	// -->depricated method by dugesh sir 
//	configure(HttpSecurity http) throws Exception
//	{
//		http
//	.csrf().disable()
//	.authorizeHttpRequests()
//	.anyRequest()//sari request ko authoried kar do 
//	.authenticated().and()
//	.httpBasic();// kon se type ka authentication chahiye 
	
	// itna kar ne ke bad ab form based authentication se apni ab basic authentication ho gayi hai
	//matlab javascript box aye ga or humpostman se bhi verification kr sakte hai
//	}
//	appln pro.
//#logging.level.org.springframework.security=DEBUG
//#spring.security.user.name=Satyam
//#spring.security.user.password=Satyam@197026
//#spring.security.user.roles=ADMIN
//
//	@Autowired
//	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Bean
	public UserDetailsService getUserDetailService() 
	{
		return new CustomUserDetailService();
		
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder()
    { 
		
		return new BCryptPasswordEncoder();
    }
	
	
   
    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
    	// when we are using hhtp basic 
//		http.csrf((csrf)->csrf.disable())
//		.authorizeHttpRequests(authorize->authorize.anyRequest().authenticated())
//		.httpBasic(Customizer.withDefaults()); 
//		DefaultSecurityFilterChain defaultSecurityFilterChain=http.build();
//		return defaultSecurityFilterChain;	
		
		http.csrf((csrf)->csrf.disable())
		.authorizeHttpRequests(authorize->authorize.anyRequest().authenticated())
		.authorizeHttpRequests(authorize-> authorize.requestMatchers(PUBLIC_URLS).permitAll())
		//.authorizeHttpRequests(authorize-> authorize.requestMatchers(HttpMethod.GET).permitAll())
		.exceptionHandling(exceptionHandling->exceptionHandling.authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
		.sessionManagement(sessionManagement->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		DefaultSecurityFilterChain defaultSecurityFilterChain=http.build();
		return defaultSecurityFilterChain;	
		
		
		
	} 
    
    @Bean
	public DaoAuthenticationProvider authenticationProvider() 
	{
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());
		
		daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
		
      return daoAuthenticationProvider;

	}
    
    
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		//in this we have to define which type of authentication we are using  dao, inmemory auth..
		//auth.inMemoryAuthentication();
		auth.authenticationProvider(authenticationProvider());
		
	}
    
	@Bean
	public AuthenticationManager  authenticationManagerBean(AuthenticationConfiguration configuration)throws Exception
	{
		return configuration.getAuthenticationManager();
	}

	
	
	
	
}
