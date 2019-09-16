package com.company.project.web;

import com.company.project.core.response.Result;
import com.company.project.core.response.ResultGenerator;
import com.company.project.model.Account;
import com.company.project.module.jwt.JwtUtil;
import com.company.project.service.AccountService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
* Created by CodeGenerator on 2019/08/12.
*/
@Api("账户消息")
@RestController
@RequestMapping("/account")
public class AccountController {
    @Resource
    private AccountService accountService;
    @Resource
    private JwtUtil jwtUtil;

    @PostMapping("/token")
    public Result getToken(@RequestBody Account account) {
        ArrayList<GrantedAuthority> objects = new ArrayList<>();
        objects.add(new SimpleGrantedAuthority("role1"));
        String sign = jwtUtil.sign(account.getName(), objects);
        return ResultGenerator.genSuccessResult(sign);
    }

    @PostMapping
    public Result add(@RequestBody Account account) {
        accountService.save(account);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        accountService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody Account account) {
        accountService.update(account);
        return ResultGenerator.genSuccessResult();
    }
    @PreAuthorize("hasAuthority('role1')")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Account account = accountService.findById(id);
        return ResultGenerator.genSuccessResult(account);
    }
    @PreAuthorize("hasAuthority('role2')")
    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Account> list = accountService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
