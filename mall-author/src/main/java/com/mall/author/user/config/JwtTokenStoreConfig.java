package com.mall.author.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class JwtTokenStoreConfig {



    @Bean
    public TokenStore jwtTokenStore(){

        return new JwtTokenStore(jwtAccessTokenConverter());
    }
    @Bean
    public JwtTokenEnhancer jwtTokenEnhancer(){

        return new JwtTokenEnhancer();
    }



    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter=new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("token_key");
        return jwtAccessTokenConverter;
    }


}
