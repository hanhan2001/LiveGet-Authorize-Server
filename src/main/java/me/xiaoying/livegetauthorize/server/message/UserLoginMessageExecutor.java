package me.xiaoying.livegetauthorize.server.message;

import com.alibaba.fastjson.JSONObject;
import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.core.message.MessageExecutor;
import me.xiaoying.livegetauthorize.server.Application;
import me.xiaoying.livegetauthorize.server.entity.ServerUser;
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
    }
}