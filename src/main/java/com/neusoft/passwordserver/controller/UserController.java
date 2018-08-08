package com.neusoft.passwordserver.controller;

import com.neusoft.passwordserver.config.ResourceServerConfiguration;
import com.neusoft.passwordserver.entity.SysUser;
import com.neusoft.passwordserver.pojo.response.RevokeResult;
import com.neusoft.passwordserver.service.JacDetailsService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
    @Autowired
    @Qualifier("consumerTokenServices")
    ConsumerTokenServices consumerTokenServices;

    @Autowired
    private JacDetailsService jacDetailsService;

    @CrossOrigin
    @RequestMapping("/oauth/profile")
    public ResponseEntity<SysUser> getUerInfo() {
        SysUser userInfo = new SysUser();
        User user = null;
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(obj instanceof User){
            user = (User) obj;
        }
        if(user!=null){
            UserDetails userDetails = jacDetailsService.loadUserByUsername(user.getUsername());
            logger.info("password:{}",userDetails.getPassword());
            logger.info("username:{}",userDetails.getUsername());
            userInfo.setUsername(user.getUsername());
        }

        return ResponseEntity.ok(userInfo);
    }

    @CrossOrigin
    @RequestMapping("/oauth/revoke")
    @ResponseBody
    public ResponseEntity<?> revoke(HttpServletRequest request,HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
            SecurityContextHolder.clearContext();
            String[] tokens= request.getHeader("Authorization").split(ResourceServerConfiguration.RESOURCE_ID+" ");
            if(tokens!=null&&tokens.length>1){
                consumerTokenServices.revokeToken(tokens[1]);
            }
        }
        RevokeResult revokeResult = new RevokeResult();
        revokeResult.setRevokeToken(true);
        return ResponseEntity.ok(revokeResult);
    }

}