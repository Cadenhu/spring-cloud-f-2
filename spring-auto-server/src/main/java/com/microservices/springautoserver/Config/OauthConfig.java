package com.microservices.springautoserver.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author :bert.hu
 * @Description:
 * @Date:Create in 14:48 2018/11/29
 * @Modified By:
 */
@Configuration
@EnableAuthorizationServer
public class OauthConfig  extends AuthorizationServerConfigurerAdapter {

    @Value("${security.signing-key}")
    private String publicKey;

    AuthenticationManager authenticationManager;

    @Bean
    public SecurityClientDetailsServiceImpl securityClientDetailsService(){
        return  new SecurityClientDetailsServiceImpl();
    }

    @Bean
    public SecurityUserDetailsServiceImpl securityUserDetailsService(){
        return  new SecurityUserDetailsServiceImpl();
    }

    /**
     * @Author :bert.hu
     * @Description: JWT 编码token
     * @Date:Create in 15:48 2018/11/29
     * @Modified By:
     * @parmer:
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                String userName = authentication.getUserAuthentication().getName();
                final Map<String, Object> additionalInformation = new HashMap<>();
                additionalInformation.put("user_name", userName);
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                OAuth2AccessToken token = super.enhance(accessToken, authentication);
                return token;
            }
        };
        converter.setSigningKey(publicKey);
        return converter;
    }
    public OauthConfig(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * @Author :bert.hu
     * @Description: 实现客户端自定义配置
     * @Date:Create in 15:48 2018/11/29
     * @Modified By:
     * @parmer:
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(securityClientDetailsService());
    }

    /**
     * @Author :bert.hu
     * @Description: 令牌端点的存储方式
     * @Date:Create in 15:47 2018/11/29
     * @Modified By:
     * @parmer:
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints
    ) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(securityUserDetailsService())
                .accessTokenConverter(accessTokenConverter())
                .tokenStore(new JwtTokenStore(accessTokenConverter()));

    }

    /**
     * @Author :bert.hu
     * @Description:
     *              permitAll() 让本身的oauth的访问不需要授权
     *              isAuthenticated()检查access_token需要进行授权
     * @Date:Create in 15:47 2018/11/29
     * @Modified By:
     * @parmer:
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

}
