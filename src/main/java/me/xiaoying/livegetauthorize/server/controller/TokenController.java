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
        if (StringUtil.isEmpty(password))
            parameters.add("password");
        if (StringUtil.isEmpty(object))
            object = "default";

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

        // 获取模块
        Module module;
        if (!object.equalsIgnoreCase("default")) {
            module = LACore.getServer().getModuleManager().getModule(function);
            if (module != null) module = module.getModuleChild(object);
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

        // 设置返回信息
        String result = new VariableFactory(FileMessageConstant.MESSAGE_TOKEN_INFO)
                .token(token1.getToken())
                .save(token1.getSave())
                .over(token1.getOver())
                .lastUse(token1.getLastUse())
                .machine(((ServerToken) token1).getMachine())
                .date()
                .toString();

        if (module.getParent() == null)
            result = new VariableFactory(result)
                    .function(module.getName())
                    .object("default")
                    .toString();
        else
            result = new VariableFactory(result)
                    .function(module.getParent().getName())
                    .object(module.getName())
                    .toString();

        return result;
    }

    @GetMapping("/token/create")
    public String create(String token, String function, String object, String qq, String time, String password) {
        List<String> parameters = new ArrayList<>();
        if (StringUtil.isEmpty(token))
            parameters.add("token");
        if (StringUtil.isEmpty(function))
            parameters.add("function");
        if (StringUtil.isEmpty(password))
            parameters.add("password");
        if (StringUtil.isEmpty(qq))
            parameters.add("qq");
        if (StringUtil.isEmpty(object))
            object = "default";

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

        // 获取模块
        Module module;
        if (!object.equalsIgnoreCase("default")) {
            module = LACore.getServer().getModuleManager().getModule(function);
            if (module != null) module = module.getModuleChild(object);
        } else
            module = LACore.getServer().getModuleManager().getModule(function);

        // 判断模块是否存在
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

        // 若用户不存在则创建一个新的用户
        if (user == null)
            user = Application.getUserManager().createUser(Long.parseLong(qq), qq + "@qq.com", "");

        // 判断是否已存在授权码
        if (tokenManager.contains(token))
            return new VariableFactory(FileMessageConstant.MESSAGE_TOKEN_EXISTED)
                    .date()
                    .toString();

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

    @GetMapping("/token/delete")
    public String delete(String token, String function, String object, String password) {
        List<String> parameters = new ArrayList<>();
        if (StringUtil.isEmpty(token))
            parameters.add("token");
        if (StringUtil.isEmpty(function))
            parameters.add("function");
        if (StringUtil.isEmpty(password))
            parameters.add("password");
        if (StringUtil.isEmpty(object))
            object = "default";

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

        // 获取模块
        Module module;
        if (!object.equalsIgnoreCase("default")) {
            module = LACore.getServer().getModuleManager().getModule(function);
            if (module != null) module = module.getModuleChild(object);
        } else
            module = LACore.getServer().getModuleManager().getModule(function);

        // 判断模块是否存在
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
        if (!tokenManager.contains(token))
            return new VariableFactory(FileMessageConstant.MESSAGE_TOKEN_NOT_FOUND)
                    .date()
                    .toString();

        Token token1 = tokenManager.getToken(token);
        tokenManager.delete(token);

        if (token1.getModule().getParent() == null)
            return new VariableFactory(FileMessageConstant.MESSAGE_TOKEN_DELETE)
                    .token(token)
                    .function(token1.getModule().getName())
                    .object("default")
                    .date()
                    .toString();
        else
            return new VariableFactory(FileMessageConstant.MESSAGE_TOKEN_DELETE)
                    .token(token)
                    .function(token1.getModule().getParent().getName())
                    .object(token1.getModule().getName())
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
            if (module == null)
                return new VariableFactory(FileMessageConstant.MESSAGE_MODULE_NOT_FOUND)
                        .date()
                        .toString();
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

    @GetMapping("/token/setMachine")
    public String setMachine(String token, String function, String object, String machine, String password) {
        List<String> parameters = new ArrayList<>();
        if (StringUtil.isEmpty(token))
            parameters.add("token");
        if (StringUtil.isEmpty(function))
            parameters.add("function");
        if (StringUtil.isEmpty(password))
            parameters.add("password");
        if (StringUtil.isEmpty(object))
            object = "default";
        if (StringUtil.isEmpty(machine))
            machine = "";

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

        // 获取模块
        Module module;
        if (!object.equalsIgnoreCase("default")) {
            module = LACore.getServer().getModuleManager().getModule(function);
            if (module != null) module = module.getModuleChild(object);
        } else
            module = LACore.getServer().getModuleManager().getModule(function);

        // 判断模块是否存在
        if (module == null)
            return new VariableFactory(FileMessageConstant.MESSAGE_MODULE_NOT_FOUND)
                    .date()
                    .toString();

        TokenManager tokenManager = module.getTokenManager();
        // 判断授权码是否存在
        if (!tokenManager.contains(token))
            return new VariableFactory(FileMessageConstant.MESSAGE_TOKEN_NOT_FOUND)
                    .date()
                    .toString();

        ServerToken serverToken = (ServerToken) tokenManager.getToken(token);
        serverToken.setMachine(machine);

        // 设置返回信息
        String result = new VariableFactory(FileMessageConstant.MESSAGE_TOKEN_INFO)
                .token(serverToken.getToken())
                .save(serverToken.getSave())
                .over(serverToken.getOver())
                .lastUse(serverToken.getLastUse())
                .machine(serverToken.getMachine())
                .date()
                .toString();

        if (module.getParent() == null)
            result = new VariableFactory(result)
                    .function(module.getName())
                    .object("default")
                    .toString();
        else
            result = new VariableFactory(result)
                    .function(module.getParent().getName())
                    .object(module.getName())
                    .toString();

        return result;
    }

    @GetMapping("/token/renew")
    public String renew(String token, String function, String object, String time, String password) {
        List<String> parameters = new ArrayList<>();
        if (StringUtil.isEmpty(token))
            parameters.add("token");
        if (StringUtil.isEmpty(function))
            parameters.add("function");
        if (StringUtil.isEmpty(password))
            parameters.add("password");
        if (StringUtil.isEmpty(time))
            parameters.add("time");
        if (StringUtil.isEmpty(object))
            object = "default";

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

        // 获取模块
        Module module;
        if (!object.equalsIgnoreCase("default")) {
            module = LACore.getServer().getModuleManager().getModule(function);
            if (module != null) module = module.getModuleChild(object);
        } else
            module = LACore.getServer().getModuleManager().getModule(function);

        // 判断模块是否存在
        if (module == null)
            return new VariableFactory(FileMessageConstant.MESSAGE_MODULE_NOT_FOUND)
                    .date()
                    .toString();

        TokenManager tokenManager = module.getTokenManager();
        // 判断授权码是否存在
        if (!tokenManager.contains(token))
            return new VariableFactory(FileMessageConstant.MESSAGE_TOKEN_NOT_FOUND)
                    .date()
                    .toString();

        ServerToken serverToken = (ServerToken) tokenManager.getToken(token);
        // 当授权码已过期
        if (serverToken.expire()) {
            Date date = new Date();
            date.setTime(date.getTime() + Long.parseLong(time) * 1000);
            serverToken.setOver(date);
        } else {
            Date date = (Date) serverToken.getOver().clone();
            date.setTime(date.getTime() + Long.parseLong(time) * 1000);
            serverToken.setOver(date);
        }

        // 设置返回信息
        String result = new VariableFactory(FileMessageConstant.MESSAGE_TOKEN_INFO)
                .token(serverToken.getToken())
                .uuid(serverToken.getOwner().getUUID())
                .save(serverToken.getSave())
                .over(serverToken.getOver())
                .lastUse(serverToken.getLastUse())
                .machine(serverToken.getMachine())
                .date()
                .toString();

        if (module.getParent() == null)
            result = new VariableFactory(result)
                    .function(module.getName())
                    .object("default")
                    .toString();
        else
            result = new VariableFactory(result)
                    .function(module.getParent().getName())
                    .object(module.getName())
                    .toString();

        return result;
    }

    @GetMapping("/token/random")
    public String random() {
        return RandomUtil.getCharAndNumr(24);
    }
}