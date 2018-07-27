package com.neusoft.passwordserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by fpf
 * Created date 2018/7/26
 */
@Entity(name = "sys_user")
public class SysUser {

    /**
     * 用户名
     */
    @Id
    @Column(name="username")
    private String username;

    /**
     * 密码
     */
    @Column(name="password")
    private String password;

    /**
     * 邮箱
     */
    @Column(name="email")
    private String email;

    /**
     * 地址
     */
    @Column(name="address")
    private String address;

    /**
     * 年龄
     */
    @Column(name="age")
    private Integer age;

    /**
     * 是否过期 1过期，0正常
     */
    @Column(name="expired")
    private Integer expired;
    /**
     * 是否不可用，1不可用，0可用
     */
    @Column(name="disabled")
    private Integer disabled;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }
}
