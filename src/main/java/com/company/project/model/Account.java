package com.company.project.model;

import java.util.Date;
import javax.persistence.*;

public class Account {
    /**
     * 账户Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 账户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册时间
     */
    @Column(name = "register_time")
    private Date registerTime;

    /**
     * 上一次登录时间
     */
    @Column(name = "login_time")
    private Date loginTime;

    /**
     * 获取账户Id
     *
     * @return id - 账户Id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置账户Id
     *
     * @param id 账户Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取账户名
     *
     * @return name - 账户名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置账户名
     *
     * @param name 账户名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取注册时间
     *
     * @return register_time - 注册时间
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * 设置注册时间
     *
     * @param registerTime 注册时间
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * 获取上一次登录时间
     *
     * @return login_time - 上一次登录时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 设置上一次登录时间
     *
     * @param loginTime 上一次登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}