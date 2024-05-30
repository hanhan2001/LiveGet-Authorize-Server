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
    private final Map<String, Classification> knownClassification = new HashMap<>();

    public SimpleClassificationManager() {
        this.registerClassification("preview", new PreviewClassification("preview"));
    }

    @Override
    public void registerClassification(NamespacedKey namespacedKey, Classification classification) {
        this.knownClassification.put(namespacedKey.toString(), classification);
    }

    public void registerClassification(String name, Classification classification) {
        this.knownClassification.put(name.toLowerCase(Locale.ROOT), classification);
    }

    @Override
    public Classification getClassification(String name) {
        return this.knownClassification.get(name);
    }

    @Override
    public void unregisterClassification(String name) {
        this.knownClassification.remove(name);
    }

    @Override
    public List<Classification> getClassifications() {
        return new ArrayList<>(this.knownClassification.values());
    }
}