package me.xiaoying.livegetauthorize.server.classification.classifications;

import me.xiaoying.livegetauthorize.core.LACore;
import me.xiaoying.livegetauthorize.core.classification.Classification;
import me.xiaoying.livegetauthorize.core.classification.DisplayPage;
import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.core.event.user.UserOpenClassificationEvent;
import me.xiaoying.livegetauthorize.server.classification.classifications.displayboxs.PreviewDisplayPage;

import java.util.HashMap;
import java.util.Map;

/**
 * Classification preview
 */
public class PreviewClassification extends Classification {
    private final Map<String, DisplayPage> knownDisplayPage = new HashMap<>();

    public PreviewClassification(String name) {
        super(name);
        this.registerPage("preview", new PreviewDisplayPage("preview", this));
    }

    public void registerPage(String name, DisplayPage displayPage) {
        this.knownDisplayPage.put(name, displayPage);
    }

    @Override
    public DisplayPage getPage(String name) {
        return this.knownDisplayPage.get(name);
    }

    @Override
    public void enable(User user) {
        DisplayPage displayPage = this.getPage("preview");
        displayPage.enable(user);
        LACore.getPluginManager().callEvent(new UserOpenClassificationEvent(user, this));
    }

    @Override
    public void enable(User user, DisplayPage displayPage) {
        displayPage.enable(user);
        LACore.getPluginManager().callEvent(new UserOpenClassificationEvent(user, this));
    }
}