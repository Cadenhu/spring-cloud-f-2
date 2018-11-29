package com.microservices.springautoserver.Config;

import com.microservices.springautoserver.Entity.AuthorizationModel;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * @Author :bert.hu
 * @Description:
 * @Date:Create in 9:56 2018/11/15
 * @Modified By:
 */
public class SecurityUserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String lowcaseUsername = username.toLowerCase();
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("findclient"));
        AuthorizationModel user = new AuthorizationModel("system", "{bcrypt}$2a$10$7jl/XEtraHjyVX7zsdMz5O9QVs7H5YJkj7BvSfj9DrjWKPVh0qbrS", grantedAuthorities, true);
        return user;
    }
}
