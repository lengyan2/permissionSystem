package com.example.permissionsystem.domain;

import com.example.permissionsystem.entity.SysRole;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Data
@AllArgsConstructor
public class MyUserDetails implements UserDetails {
    private Integer id;

    private String  username;

    private String  password;

    private Boolean enabled;

    private Boolean AccountNonExpired;

    private Boolean  AccountNonLocked;

    private  Boolean CredentialsNonExpired;

    private Set<SysRole> roleSet;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (SysRole role: roleSet
             ) {
            if (StringUtils.isEmpty(role.getName()))continue;
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return AccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return AccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return CredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
