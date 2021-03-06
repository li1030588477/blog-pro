package com.dgut.blog.controller.admin;

import com.dgut.blog.dto.ResponseDTO;
import com.dgut.blog.entity.Role;
import com.dgut.blog.entity.User;
import com.dgut.blog.parm.AddUserInfoPARM;
import com.dgut.blog.service.RoleService;
import com.dgut.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author: lishengdian | 932978775@qq.com
 * @version: 1.0.0
 * @description: 管理员模块 - 管理用户
 * @createDate: 2021/2/25
 */
@RestController
@RequestMapping("/admin")
public class UserManaController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;


    /**
     * 根据用户别名获取用户列表
     * @param nickname
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> getUserByNickname(String nickname) {
        return userService.getUsersByNickname(nickname);
    }

    /**
     * 根据Id获取用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable Long id) {
        return userService.getUserByUserId(id);
    }

    /**
     * 获取用户所有角色
     * @return
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public List<Role> getAllRole() {
        return roleService.getAllRole();
    }

    /**
     * 设置用户状态
     * @param enabled
     * @param uid
     * @return
     */
    @RequestMapping(value = "/user/enabled", method = RequestMethod.PUT)
    public ResponseDTO updateUserEnabled(Boolean enabled, Long uid) {
        if (userService.updateUserState(enabled, uid)) {
            return new ResponseDTO("success", "更新成功!");
        } else {
            return new ResponseDTO("error", "更新失败!");
        }
    }

    /**
     * 删除指定用户
     * @param uid
     * @return
     */
    @RequestMapping(value = "/user/{uid}", method = RequestMethod.DELETE)
    public ResponseDTO deleteUserById(@PathVariable Long uid) {
        if (userService.deleteUserByUserId(uid)) {
            return new ResponseDTO("success", "删除成功!");
        } else {
            return new ResponseDTO("error", "删除失败!");
        }
    }

    /**
     * 更新用户角色
     * @param rids
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/role", method = RequestMethod.PUT)
    public ResponseDTO updateUserRoles(Long[] rids, Long id) {
        System.out.println("要更新的角色的角色id为： " + rids);
        if (userService.updateUserRoles(Arrays.asList(rids), id)) {
            return new ResponseDTO("success", "更新成功!");
        } else {
            return new ResponseDTO("error", "更新失败!");
        }
    }


    /**
     * 更新用户角色
     * @param userInfoPARM
     * @return
     */
    @PostMapping(value = "/user/register")
    public ResponseDTO regesterUser(AddUserInfoPARM userInfoPARM) {
        System.out.println("要增加的用户的信息为： " + userInfoPARM);

        User exitUser = userService.getUserByUserName(userInfoPARM.getUserName());
        if (exitUser != null){
            return new ResponseDTO("error", "用户名已存在");
        }
        if(userService.registerUser(userInfoPARM)) {
            return new ResponseDTO("success", "注册成功");
        }
        return new ResponseDTO("error", "注册失败");
    }

    /***
     * 转化参数类为实体类
     * @param userInfoPARM
     */
    public User changeUserInfo(AddUserInfoPARM userInfoPARM){
        User user = new User();
        user.setEnabled(userInfoPARM.getEnabled());
        user.setEmail(userInfoPARM.getEmail());
        user.setIcon(userInfoPARM.getIcon());
        user.setNickname(userInfoPARM.getNickname());
        user.setUserName(userInfoPARM.getUserName());
        // 加密
        user.setPassword(passwordEncoder
                .encode(userInfoPARM
                        .getPassword()));
        // 设置用户Id列表
         roleService.getRoleIdsByRoleName(userInfoPARM.getRoles());
        return user;
    }
}
