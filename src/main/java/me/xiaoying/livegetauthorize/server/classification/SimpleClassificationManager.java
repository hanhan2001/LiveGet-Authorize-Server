package me.xiaoying.livegetauthorize.server.classification;

import me.xiaoying.livegetauthorize.core.NamespacedKey;
import me.xiaoying.livegetauthorize.core.classification.Classification;
import me.xiaoying.livegetauthorize.core.classification.ClassificationManager;

import java.util.List;

/**
 * Classification Simple
 */
public class SimpleClassificationManager implements ClassificationManager {
    @Override
    public void registerClassification(NamespacedKey namespacedKey, Classification classification) {

    }

    @Override
    public Classification getClassification(String s, String s1) {
        return null;
    }

    @Override
    public void unregisterClassification(String s) {

    }

    @Override
    public void unregisterClassification(String s, String s1) {

    }

    @Override
    public List<Classification> getClassifications() {
        return null;
    }
}