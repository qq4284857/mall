package com.mall.author.user.config;

import com.mall.author.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


/**
 * 授权服务器
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    @Qualifier("jwtTokenStore")
    TokenStore tokenStore;
    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    JwtTokenEnhancer jwtTokenEnhancer;
    @Autowired
    DataSource source;


    /**
     * 密码模式
     *
     * @param endpoints
     * @throws Exception
     */

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain chain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter);
        chain.setTokenEnhancers(delegates);
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService)
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(chain);

    }

    /**
     * 授权码模式
     *
     * @param clients
     * @throws Exception
     */

//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

//        clients.inMemory()
//                //客户端ID
//                .withClient("client")
//                //秘钥
//
//                .secret(passwordEncoder.encode("112233"))
//                //重定向地址
//                .redirectUris("http://localhost:8083/login")
//                //授权范围
//                .scopes("all")
//                //accessToken失效时间
//                .accessTokenValiditySeconds(60)
//                //refresh_token失效时间
//                .refreshTokenValiditySeconds(86400)
//                //自动授权
//                .autoApprove(true)
//
//                /**
//                 * 授权类型
//                 * authorization_code:授权码模式
//                 * password:密码模式
//                 * refresh_token：刷新令牌过期时间
//                 */
//                .authorizedGrantTypes("authorization_code", "password","refresh_token");
//    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }
    public ClientDetailsService clientDetailsService(){
        return new JdbcClientDetailsService(source);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security.tokenKeyAccess("isAuthenticated()");
    }
}
