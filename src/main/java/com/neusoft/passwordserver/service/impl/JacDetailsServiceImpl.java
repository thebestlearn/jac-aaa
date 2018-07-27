package com.neusoft.passwordserver.service.impl;

import com.neusoft.passwordserver.entity.SysUser;
import com.neusoft.passwordserver.repository.UserRepository;
import com.neusoft.passwordserver.service.JacDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class JacDetailsServiceImpl implements JacDetailsService {

    @Autowired
    private UserRepository userRepository;


//    private OauthAccessTokenRepository oauthAccessTokenRepository;

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SysUser> users = this.userRepository.query(username);
        if(users==null||users.size()==0) {
            throw new UsernameNotFoundException("用户"+username+"不存在！");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("111"));

//        List<Role> roles = user.getRoles();
//        for(Role role:roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getCode()));
//        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(users.get(0).getUsername(),users.get(0).getPassword(),authorities);
        return userDetails;
    }


    @Override
    public int revoke(String token) {
        return 0;
    }
}