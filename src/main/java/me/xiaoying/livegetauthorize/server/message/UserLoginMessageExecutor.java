package me.xiaoying.livegetauthorize.server.message;

import com.alibaba.fastjson.JSONObject;
import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.core.message.MessageExecutor;
import me.xiaoying.livegetauthorize.server.Application;
import me.xiaoying.livegetauthorize.server.constant.FileMessageConstant;
import me.xiaoying.livegetauthorize.server.entity.ServerUser;
import me.xiaoying.livegetauthorize.server.factory.VariableFactory;
import org.java_websocket.WebSocket;

/**
 * Message Executor User info
 */
public class UserLoginMessageExecutor implements MessageExecutor {
    @Override
    public void execute(JSONObject jsonObject, Object... objects) {
        JSONObject object = jsonObject.getJSONObject("user");
        User user = Application.getUserManager().getLoginUser(object.getString("token"));
        if (user == null)
            return;

        ServerUser serverUser = (ServerUser) user;
        serverUser.setWebsocket((WebSocket) objects[0]);
        serverUser.sendMessage(new VariableFactory(FileMessageConstant.MESSAGE_ACCOUNT_INFO)
                .qq(user.getQQ())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .uuid(user.getUUID())
                .name(user.getName())
                .ip(user.getIP())
                .registerTime(user.getRegisterTime())
                .lastLoginTime(user.getLastLoginTime())
                .toString());
    }
}