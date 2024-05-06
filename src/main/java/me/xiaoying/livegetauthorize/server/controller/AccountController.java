package me.xiaoying.livegetauthorize.server.controller;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.core.event.user.UserLoginEvent;
import me.xiaoying.livegetauthorize.server.Application;
import me.xiaoying.livegetauthorize.server.constant.FileConfigConstant;
import me.xiaoying.livegetauthorize.server.constant.FileMessageConstant;
import me.xiaoying.livegetauthorize.server.entity.ServerUser;
import me.xiaoying.livegetauthorize.server.factory.JwtFactory;
import me.xiaoying.livegetauthorize.server.factory.VariableFactory;
import me.xiaoying.livegetauthorize.server.utils.LongUtil;
import me.xiaoying.livegetauthorize.server.utils.ServerUtil;
import org.jose4j.json.JsonUtil;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AccountController {
    @PostMapping("/login")
    public String login(@RequestParam("account") String account, @RequestParam("password") String password) {
        // get login type
        if (!LongUtil.isLong(account) && account.contains("@"))
            return null;

        User user;
        if (LongUtil.isLong(account))
            user = Application.getUserManager().getUser(Long.parseLong(account));
        else
            user = Application.getUserManager().getUser(account);

        if (user == null)
            return FileMessageConstant.MESSAGE_ACCOUNT_NOT_FOUND;

        if (!ServerUtil.getEncryptPassword(password).equals(user.getPassword()))
            return FileMessageConstant.MESSAGE_ACCOUNT_PASSWORD_INVALID;

        JwtFactory jwtFactory = new JwtFactory();
        jwtFactory.setSubject("user-login")
                .parameter("account", account)
                .setExpirationTime(60 * 60 * 24);
        String token = jwtFactory.toString();
        user.setToken(token);
        Application.getUserManager().setLoginUser(token, user);
        Application.getServer().getPluginManager().callEvent(new UserLoginEvent(user));
        LACore.getLogger().info("&b登录&e>> &f{}", user.getUUID());
        return new VariableFactory(FileMessageConstant.MESSAGE_ACCOUNT_LOGIN)
                .account(account)
                .token(user.getToken()).toString();
    }

    @PostMapping("/register")
    public String register(HttpServletRequest request, @RequestParam("account") String account, @RequestParam("email") String email, @RequestParam("password") String password) {
        if (Application.getUserManager().getUser(Long.parseLong(account)) != null)
            return FileMessageConstant.MESSAGE_ACCOUNT_USER_ALREADY_EXISTS;
        if (Application.getUserManager().getUser(email) != null)
            return FileMessageConstant.MESSAGE_ACCOUNT_EMAIL_ALREADY_EXISTS;

        String encryptPassword = ServerUtil.getEncryptPassword(password);
        ServerUser user = (ServerUser) Application.getUserManager().createUser(Long.parseLong(account), email, encryptPassword);
        user.setIP(request.getRemoteAddr());
//        Application.getServer().getPluginManager().callEvent(new UserRegisterEvent(user));
        LACore.getLogger().info("&6注册&e>> &f{}", user.getUUID());
        return this.login(account, password);
    }

    @PostMapping("/verify")
    public String verify(HttpServletRequest request, String token) {
        if (Application.getUserManager().getLoginUser(token) == null)
            return FileMessageConstant.MESSAGE_ACCOUNT_NEED_RE_LOGIN;

        try {
            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setRequireExpirationTime()
                    .setAllowedClockSkewInSeconds(30)
                    .setRequireSubject()
                    .setVerificationKey(new RsaJsonWebKey(JsonUtil.parseJson(FileConfigConstant.SETTING_JWT_PUBLIC_KEY)).getPublicKey())
                    .setJwsAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.RSA_USING_SHA256))
                    .build();

            JwtClaims claims = jwtConsumer.processToClaims(token);
            if (claims == null) {
                if (Application.getUserManager().getLoginUser(token) != null)
                    Application.getUserManager().removeLoginUser(token);
                return FileMessageConstant.MESSAGE_ACCOUNT_NEED_RE_LOGIN;
            }

            User user = Application.getUserManager().getLoginUser(token);
            user.updateSurvival();
            return "";
        } catch (JoseException | InvalidJwtException e) {
            return FileMessageConstant.MESSAGE_ACCOUNT_NEED_RE_LOGIN;
        }
    }
}