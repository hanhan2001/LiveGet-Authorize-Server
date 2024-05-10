package me.xiaoying.livegetauthorize.server.message;

import com.alibaba.fastjson.JSONObject;
import me.xiaoying.livegetauthorize.core.message.MessageExecutor;
import me.xiaoying.livegetauthorize.server.Application;

/**
 * MessageExecutor User Quit
 */
public class UserQuitMessageExecutor implements MessageExecutor {
    @Override
    public void execute(JSONObject jsonObject, Object... objects) {
        JSONObject object = jsonObject.getJSONObject("user");
        String token = object.getString("token");
        if (Application.getUserManager().getLoginUser(token) == null)
            Application.getUserManager().removeLoginUser(token);
    }
}