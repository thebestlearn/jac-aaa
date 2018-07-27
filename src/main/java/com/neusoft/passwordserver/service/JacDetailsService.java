package com.neusoft.passwordserver.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface JacDetailsService extends UserDetailsService{

    int revoke(String token);


}