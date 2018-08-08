package com.neusoft.passwordserver.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;


@Component
public class LoginAuthenticationManager extends DaoAuthenticationProvider {

    private static final String SUCCESS = "SUCCESS";
    
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        
        Object salt = null;
 
        if (getSaltSource() != null) {
            salt = getSaltSource().getSalt(userDetails);
        }
        
        if (authentication.getPrincipal() == null
                || "".equals(authentication.getPrincipal())) {
            logger.debug("-----用户名不能为空！-----");
            
            throw new BadCredentialsException("-----用户名不能为空！-----");
        }
        
        if (authentication.getCredentials() == null
                || "".equals(authentication.getCredentials())) {
            logger.debug("-----密码不能为空！-----");

            throw new BadCredentialsException("-----密码不能为空！-----");
        }
        
        String presentedPassword = authentication.getCredentials().toString();
        
        boolean validResult = !getPasswordEncoder().isPasswordValid(userDetails.getPassword(), presentedPassword, salt);
        if (validResult) {
            logger.debug("---- 用户名或密码错误！-----");
            throw new BadCredentialsException("-----用户名或密码错误！-----");
        }
        
       
    }
    

}
