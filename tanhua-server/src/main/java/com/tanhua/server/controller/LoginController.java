package com.tanhua.server.controller;

import com.tanhua.domain.db.UserInfo;
import com.tanhua.domain.vo.UserInfoVo;
import com.tanhua.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 根据手机号查询用户
     */
    @RequestMapping(value = "findUser", method = RequestMethod.GET)
    public ResponseEntity findUser(String mobie) {
        return userService.findByMobile(mobie);

    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody Map<String, Object> param) {
        String mobile = (String) param.get("mobile");
        String password = (String) param.get("password");
        return userService.saveUser(mobile, password);


    }
    /**
     * 用户登陆发送验证码
     * @param param
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity sendValidatedCode(@RequestBody Map<String,String> param){
        String phone=param.get("phone");
        userService.sendValidateCode(phone);
        return ResponseEntity.ok(null);
    }
    /**
     * 登陆验证码校验
     * @param param
     * @return
     */
    @PostMapping("/loginVerification")
    public ResponseEntity loginVerification(@RequestBody Map<String,String> param){
        String phone = param.get("phone");
        String verificationCode = param.get("verificationCode");
        Map<String, Object> map = userService.loginVerification(phone, verificationCode);
        return ResponseEntity.ok(map);
    }
/**
 * 新用户，填写资料
 *
 */
   @RequestMapping(value = "loginReginfo",method = RequestMethod.POST)
    public ResponseEntity loginReginfo(@RequestBody UserInfoVo userInfoVo,@RequestHeader("Authorization") String token){
       userService.loginReginfo(userInfoVo,token);
       return ResponseEntity.ok(null);
   }
    /**
     * 新用户---2选取头像
     */
    @RequestMapping(value = "/loginReginfo/head", method = RequestMethod.POST)
    public ResponseEntity loginReginfoHead(MultipartFile headPhoto, @RequestHeader("Authorization") String token) {
        userService.loginReginfoHead(headPhoto,token);
        return ResponseEntity.ok(null);
    }
}

