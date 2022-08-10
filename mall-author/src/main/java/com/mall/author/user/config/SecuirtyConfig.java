package com.mall.author.user.config;

import com.mall.author.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
public class SecuirtyConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
    @Autowired
    UserService userService;
    @Autowired
    PersistentTokenRepository persistentTokenRepository;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置登录配置
        http.formLogin()
                .loginPage("/queryLogin").permitAll()
                .loginProcessingUrl("/login").permitAll()
                .successForwardUrl("/queryHome").permitAll();

        //记住我
        http.rememberMe()
                .tokenRepository(persistentTokenRepository)
                .tokenValiditySeconds(60)
                .userDetailsService(userService);

        //开放的地址
        http.authorizeRequests()
                .antMatchers("/queryLogin").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/oauth/**","/logout/**","/user/**").permitAll()
                .anyRequest().authenticated();

        http.csrf().disable();
    }




    /**
     * 记住我
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        jdbcTokenRepository.setDataSource(dataSource);
        //自动建表 自一次启动时开启
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


}
