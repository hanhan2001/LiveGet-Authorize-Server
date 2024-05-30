package me.xiaoying.livegetauthorize.server.option;

import me.xiaoying.livegetauthorize.core.option.Option;
import me.xiaoying.livegetauthorize.core.option.OptionManager;

import java.util.*;

/**
 * Manager Option
 */
public class SimpleOptionManager implements OptionManager {
    private final Map<String, Option> knownOptions = new HashMap<>();

    @Override
    public void registerOption(Option option) {
        this.knownOptions.put(option.getName(), option);
    }

    /**
     * 注册 Option
     *
     * @param name Option name
     * @param value Option value
     */
    public void registerOption(String name, String value) {
        this.knownOptions.put(name, new Option(name, value));
    }

    /**
     * 取消注册 Option
     *
     * @param name Option name
     */
    public void unregisterOption(String name) {
        this.knownOptions.remove(name);
    }

    /**
     * 获取 Option
     *
     * @param name Option name
     * @return Option
     */
    public Option getOption(String name) {
        return this.knownOptions.get(name);
    }

    /**
     * 获取所有 Option
     *
     * @return ArrayList
     */
    public List<Option> getOptions() {
        return new ArrayList<>(this.knownOptions.values());
    }
}