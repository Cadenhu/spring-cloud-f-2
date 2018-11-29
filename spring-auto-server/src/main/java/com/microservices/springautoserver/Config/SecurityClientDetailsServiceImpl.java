package com.example.authorizationserverdemo.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Collection;
import java.util.HashSet;


/**
 * @Author :bert.hu
 * @Description:
 * @Date:Create in 10:48 2018/11/15
 * @Modified By:
 */
public class SecurityClientDetailsServiceImpl implements ClientDetailsService {

    private  static  final Logger logger= LoggerFactory.getLogger(SecurityClientDetailsServiceImpl.class);

    /**
     * @Author :bert.hu
     * @Description:
     * resourceIds 授权的服务实例ResourceID
     * granttypes 该client允许的授权类型
     * scopes  允许的授权范围
     * authorities
     * @Date:Create in 11:28 2018/11/15
     * @Modified By:
     * @parmer:
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        BaseClientDetails baseClientDetails = new BaseClientDetails("client", "", "all", "", "", "");
        Collection<String> scopes = new HashSet<>();
        Collection<String> grantTypes = new HashSet<>();
        scopes.add("all");
        grantTypes.add("password");
        grantTypes.add("client_credentials");
        //grantTypes.add("authorization_code");
        grantTypes.add("refresh_token");
        baseClientDetails.setScope(scopes);
        baseClientDetails.setAuthorizedGrantTypes(grantTypes);
        baseClientDetails.setClientId("client");
        baseClientDetails.setClientSecret("{bcrypt}$2a$10$USWfBLLxNAYdugloXC/kUeErPaBn.CNduVbT.IZJq1gNOeQJoC7XK");//
        baseClientDetails.setAccessTokenValiditySeconds(30);//设置过期时间
        return baseClientDetails;
    }

}
