package me.xiaoying.livegetauthorize.server.classification;

import me.xiaoying.livegetauthorize.core.NamespacedKey;
import me.xiaoying.livegetauthorize.core.classification.Classification;
import me.xiaoying.livegetauthorize.core.classification.ClassificationManager;
import me.xiaoying.livegetauthorize.server.classification.classifications.PreviewClassification;

import java.util.*;

/**
 * Classification Simple
 */
public class SimpleClassificationManager implements ClassificationManager {
    private final Map<String, List<Classification>> knownClassification = new HashMap<>();

    public SimpleClassificationManager() {
        this.registerClassification("preview", new PreviewClassification("preview"));
    }

    @Override
    public void registerClassification(NamespacedKey namespacedKey, Classification classification) {
        List<Classification> list;
        if ((list = this.knownClassification.get(namespacedKey.toString())) == null)
            list = new ArrayList<>();

        if (list.contains(classification))
            return;

        list.add(classification);
    }

    public void registerClassification(String name, Classification classification) {
        List<Classification> list;
        if ((list = this.knownClassification.get(name.toLowerCase(Locale.ROOT))) == null)
            list = new ArrayList<>();

        if (list.contains(classification))
            return;

        list.add(classification);
    }

    @Override
    public Classification getClassification(String name, String classification) {
        return null;
    }

    @Override
    public void unregisterClassification(String name) {

    }

    @Override
    public void unregisterClassification(String name, String classification) {

    }

    @Override
    public List<Classification> getClassifications() {
        return Collections.emptyList();
    }
}