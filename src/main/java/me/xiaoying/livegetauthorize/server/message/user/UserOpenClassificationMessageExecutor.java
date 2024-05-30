package me.xiaoying.livegetauthorize.server.message.user;

import com.alibaba.fastjson.JSONObject;
import me.xiaoying.livegetauthorize.core.classification.Classification;
import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.core.event.user.UserOpenClassificationEvent;
import me.xiaoying.livegetauthorize.core.message.MessageExecutor;
import me.xiaoying.livegetauthorize.server.Application;

public class UserOpenClassificationMessageExecutor implements MessageExecutor {
    @Override
    public void execute(JSONObject jsonObject, Object... objects) {
        JSONObject userObject = jsonObject.getJSONObject("user");
        User user = Application.getUserManager().getLoginUser(userObject.getString("token"));
        Classification classification = Application.getServer().getClassificationManager().getClassification(jsonObject.getString("classification"));
        Application.getServer().getPluginManager().callEvent(new UserOpenClassificationEvent(user, classification));
        classification.enable(user);
    }
}