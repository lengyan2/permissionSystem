package com.example.permissionsystem.service.impl;

import com.example.permissionsystem.domain.MyUserDetails;
import com.example.permissionsystem.entity.SysRole;
import com.example.permissionsystem.entity.SysUser;
import com.example.permissionsystem.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Set;

@Service
public class MyUserService implements UserDetailsService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.findByUsername(username);
        Set<SysRole> roles = sysUserMapper.getRoles(sysUser.getId());
        return  new MyUserDetails(sysUser.getId(),sysUser.getUsername(),
                sysUser.getPassword(),sysUser.getEnabled(),true,
                true,true,roles);
    }
}
