package me.xiaoying.livegetauthorize.server.controller;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.core.module.Module;
import me.xiaoying.livegetauthorize.core.module.Token;
import me.xiaoying.livegetauthorize.core.module.TokenManager;
import me.xiaoying.livegetauthorize.server.Application;
import me.xiaoying.livegetauthorize.server.constant.FileConfigConstant;
import me.xiaoying.livegetauthorize.server.constant.FileMessageConstant;
import me.xiaoying.livegetauthorize.server.factory.VariableFactory;
import me.xiaoying.livegetauthorize.server.module.ServerToken;
import me.xiaoying.livegetauthorize.server.utils.RandomUtil;
import me.xiaoying.livegetauthorize.server.utils.ServerUtil;
import me.xiaoying.livegetauthorize.server.utils.StringUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class TokenController {
    @GetMapping("/token/info")
    public String info(String token, String function, String object, String password) {
        List<String> parameters = new ArrayList<>();
        if (StringUtil.isEmpty(token))
            parameters.add("token");
        if (StringUtil.isEmpty(function))
            parameters.add("function");
        if (StringUtil.isEmpty(object))
            parameters.add("object");
        if (StringUtil.isEmpty(password))
            parameters.add("password");

        if (!parameters.isEmpty())
            return new VariableFactory(FileMessageConstant.ERROR_NEED_PARAMETER)
                    .parameter(parameters)
                    .date()
                    .toString();

        // 系统密码错误
        if (!ServerUtil.getEncryptPassword(password).equalsIgnoreCase(FileConfigConstant.SETTING_PASSWORD_PASSWORD))
            return new VariableFactory(FileMessageConstant.ERROR_PASSWORD_INVALID)
                    .date()
                    .toString();

        Module module = null;
        if (!object.equalsIgnoreCase("default")) {
            Module m = LACore.getServer().getModuleManager().getModule(function);
            if (m != null) module = m.getModuleChild(object);
        } else
            module = LACore.getServer().getModuleManager().getModule(function);

        // 模块不存在
        if (module == null)
            return new VariableFactory(FileMessageConstant.MESSAGE_MODULE_NOT_FOUND)
                    .date()
                    .toString();

        // 判断模块是否过期
        if (module.expire())
            return new VariableFactory(FileMessageConstant.MESSAGE_MODULE_EXPIRED)
                    .date()
                    .toString();

        TokenManager tokenManager = module.getTokenManager();
        // 授权码不存在
        if (!tokenManager.contains(token))
            return new VariableFactory(FileMessageConstant.MESSAGE_TOKEN_NOT_FOUND)
                    .date()
                    .toString();
        Token token1 = tokenManager.getToken(token);
        if (module.getParent() == null)
            return new VariableFactory(FileMessageConstant.MESSAGE_TOKEN_INFO)
                    .token(token1.getToken())
                    .uuid(token1.getOwner().getUUID())
                    .function(module.getName())
                    .object("default")
                    .save(token1.getSave())
                    .over(token1.getOver())
                    .lastUse(token1.getLastUse())
                    .machine(((ServerToken) token1).getMachine())
                    .date()
                    .toString();
        else
            return new VariableFactory(FileMessageConstant.MESSAGE_TOKEN_INFO)
                    .token(token1.getToken())
                    .uuid(token1.getOwner().getUUID())
                    .function(module.getParent().getName())
                    .object(module.getName())
                    .save(token1.getSave())
                    .over(token1.getOver())
                    .lastUse(token1.getLastUse())
                    .machine(((ServerToken) token1).getMachine())
                    .date()
                    .toString();
    }

    @GetMapping("/token/create")
    public String create(String token, String function, String object, String qq, String time, String password) {
        List<String> parameters = new ArrayList<>();
        if (StringUtil.isEmpty(token))
            parameters.add("token");
        if (StringUtil.isEmpty(function))
            parameters.add("function");
        if (StringUtil.isEmpty(object))
            parameters.add("object");
        if (StringUtil.isEmpty(password))
            parameters.add("password");

        if (!parameters.isEmpty())
            return new VariableFactory(FileMessageConstant.ERROR_NEED_PARAMETER)
                    .parameter(parameters)
                    .date()
                    .toString();

        // 系统密码错误
        if (!ServerUtil.getEncryptPassword(password).equalsIgnoreCase(FileConfigConstant.SETTING_PASSWORD_PASSWORD))
            return new VariableFactory(FileMessageConstant.ERROR_PASSWORD_INVALID)
                    .date()
                    .toString();

        Module module = null;
        if (!object.equalsIgnoreCase("default")) {
            Module m = LACore.getServer().getModuleManager().getModule(function);
            if (m != null) module = m.getModuleChild(object);
        } else
            module = LACore.getServer().getModuleManager().getModule(function);

        // 模块不存在
        if (module == null)
            return new VariableFactory(FileMessageConstant.MESSAGE_MODULE_NOT_FOUND)
                    .date()
                    .toString();

        // 判断模块是否过期
        if (module.expire())
            return new VariableFactory(FileMessageConstant.MESSAGE_MODULE_EXPIRED)
                    .date()
                    .toString();

        TokenManager tokenManager = module.getTokenManager();
        Date save = new Date();
        Date over = ((Date) save.clone());
        if (!StringUtil.isEmpty(time))
            over.setTime(save.getTime() + Long.parseLong(time) * 1000L);
        else
            over.setTime(save.getTime() + 999999999999999999L);

        User user = Application.getUserManager().getUser(Long.parseLong(qq));

        Token token1 = new ServerToken(token, "", "", save, over, save, user, module);
        tokenManager.create(token1);

        return new VariableFactory(FileMessageConstant.MESSAGE_TOKEN_INFO)
                .uuid(user.getUUID())
                .token(token1.getToken())
                .function(function)
                .object(object)
                .save(save)
                .over(over)
                .lastUse(save)
                .machine("未绑定")
                .date()
                .toString();
    }

    @GetMapping("/token/verify")
    public String verify(String token, String machine, String identification) {
        List<String> parameters = new ArrayList<>();
        if (StringUtil.isEmpty(token))
            parameters.add("token");
        if (StringUtil.isEmpty(machine))
            parameters.add("machine");
        if (StringUtil.isEmpty(identification))
            parameters.add("identification");

        if (!parameters.isEmpty())
            return new VariableFactory(FileMessageConstant.ERROR_NEED_PARAMETER)
                    .parameter(parameters)
                    .date()
                    .toString();

        Module module = LACore.getServer().getModuleManager().getModuleByIdentification(identification);
        if (module == null)
            return new VariableFactory(FileMessageConstant.MESSAGE_MODULE_NOT_FOUND)
                    .date()
                    .toString();

        // 判断授权码是否为子模块授权码
        if (token.contains("-")) {
            module = module.getModuleChild(token.split("-")[0]);
            token = token.split("-")[1];
        }

        // 判断模块是否过期
        if (module.expire())
            return new VariableFactory(FileMessageConstant.MESSAGE_MODULE_EXPIRED)
                    .date()
                    .toString();

        TokenManager tokenManager = module.getTokenManager();
        if (!tokenManager.contains(token))
            return new VariableFactory(FileMessageConstant.MESSAGE_TOKEN_NOT_FOUND)
                    .date()
                    .toString();

        ServerToken serverToken = (ServerToken) tokenManager.getToken(token);
        if (StringUtil.isEmpty(serverToken.getMachine()))
            serverToken.setMachine(machine);
        else if (!serverToken.getMachine().equalsIgnoreCase(machine))
            return new VariableFactory(FileMessageConstant.MESSAGE_TOKEN_ERROR_MACHINE)
                    .date()
                    .toString();
        serverToken.updateLastUse();
        return new VariableFactory(FileMessageConstant.MESSAGE_TOKEN_VERIFIED)
                .date()
                .toString();
    }

    @GetMapping("/token/random")
    public String random() {
        return RandomUtil.getCharAndNumr(24);
    }
}