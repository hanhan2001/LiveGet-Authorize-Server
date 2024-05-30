package me.xiaoying.livegetauthorize.server.message.user;

import com.alibaba.fastjson.JSONObject;
import me.xiaoying.livegetauthorize.core.classification.DisplayPage;
import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.core.event.user.UserOpenDisplayPageEvent;
import me.xiaoying.livegetauthorize.core.message.MessageExecutor;
import me.xiaoying.livegetauthorize.server.Application;

/**
 * MessageExecutor user open display
 */
public class UserOpenDisplayMessageExecutor implements MessageExecutor {
    @Override
    public void execute(JSONObject jsonObject, Object... objects) {
        JSONObject userObject = jsonObject.getJSONObject("user");
        User user = Application.getUserManager().getLoginUser(userObject.getString("token"));
        DisplayPage displayPage = Application.getServer().getClassificationManager().getClassification(jsonObject.getString("classification")).getPage(jsonObject.getString("displayPage"));
        if (displayPage == null)
            return;
        Application.getServer().getPluginManager().callEvent(new UserOpenDisplayPageEvent(user, displayPage));
        displayPage.enable(user);
    }
}