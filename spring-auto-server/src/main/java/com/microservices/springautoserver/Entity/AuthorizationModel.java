package com.microservices.springautoserver.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Author :bert.hu
 * @Description:
 * @Date:Create in 15:20 2018/11/29
 * @Modified By:
 */
public class AuthorizationModel implements UserDetails {
    private  String userName;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;

    public String getUserName() {
        return userName;
    }

    public AuthorizationModel() {
    }

    public AuthorizationModel(String name, String pwd, Collection<? extends GrantedAuthority> roles, boolean isEnable){
        this.userName=name;
        this.password=pwd;
        this.authorities=roles;
        this.enabled=isEnable;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
