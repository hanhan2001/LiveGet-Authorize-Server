package me.xiaoying.livegetauthorize.server.classification.classifications.displayboxs;

import me.xiaoying.livegetauthorize.core.classification.Classification;
import me.xiaoying.livegetauthorize.core.classification.DisplayPage;
import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.server.Application;
import me.xiaoying.livegetauthorize.server.constant.FileMessageConstant;
import me.xiaoying.livegetauthorize.server.factory.VariableFactory;

/**
 * DisplayPage preview
 */
public class PreviewDisplayPage extends DisplayPage {

    public PreviewDisplayPage(String name, Classification classification) {
        super(name, classification);
    }

    @Override
    public void enable(User user) {
        user.sendMessage(new VariableFactory(FileMessageConstant.MESSAGE_DISPLAY_PREVIEW_TITLE)
                        .title(Application.getServer().getOptionManager().getOption("preview_title").getValue().toString())
                        .date()
                        .toString());
    }
}