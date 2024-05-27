package me.xiaoying.livegetauthorize.server.controller;

import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.server.Application;
import me.xiaoying.livegetauthorize.server.constant.FileMessageConstant;
import me.xiaoying.livegetauthorize.server.factory.VariableFactory;
import me.xiaoying.livegetauthorize.server.utils.StringUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/user/info")
    public String info(String qq, String email, String token, String password) {
        String type = "", account = "";
        if (!StringUtil.isEmpty(qq)) {
            type = "qq";
            account = qq;
        } else if (!StringUtil.isEmpty(email)) {
            type = "email";
            account = email;
        }

        User user = null;
        switch (type) {
            case "qq":
                user = Application.getUserManager().getUser(Long.parseLong(qq));
                break;
            case "email":
                user = Application.getUserManager().getUser(account);
                break;
        }

        if (user == null)
            return FileMessageConstant.MESSAGE_ACCOUNT_NOT_FOUND;

        User orderUser = Application.getUserManager().getLoginUser(token);
        if (orderUser == null)
            return FileMessageConstant.MESSAGE_ACCOUNT_NEED_LOGIN;

        if (!orderUser.hasPermission("user.query.other"))
            return FileMessageConstant.MESSAGE_PERMISSION_NO_PERMISSION;
        return new VariableFactory(FileMessageConstant.MESSAGE_ACCOUNT_INFO)
                .qq(user.getQQ())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .uuid(user.getUUID())
                .name(user.getName())
                .ip(user.getIP())
                .registerTime(user.getRegisterTime())
                .lastLoginTime(user.getLastLoginTime())
                .photo(user.getPhotoBase64())
                .toString();
    }
}