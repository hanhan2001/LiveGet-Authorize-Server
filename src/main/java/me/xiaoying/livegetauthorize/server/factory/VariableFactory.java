package me.xiaoying.livegetauthorize.server.factory;

import me.xiaoying.livegetauthorize.server.constant.FileConfigConstant;
import me.xiaoying.livegetauthorize.server.utils.DateUtil;
import me.xiaoying.livegetauthorize.server.utils.FileUtil;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Factory Variable
 */
public class VariableFactory {
    private String string;

    public VariableFactory(String string) {
        this.string = string;
    }

    public VariableFactory account(String account) {
        this.string = this.string.replace("%account%", account);
        return this;
    }

    public VariableFactory date() {
        this.string = this.string.replace("%date%", DateUtil.newStringDate(FileConfigConstant.SETTING_DATEFORMAT));
        return this;
    }

    public VariableFactory email(String email) {
        this.string = this.string.replace("%email%", email);
        return this;
    }

    public VariableFactory function(String function) {
        this.string = this.string.replace("%function%", function);
        return this;
    }

    public VariableFactory title(String title) {
        this.string = this.string.replace("%title%", title);
        return this;
    }

    public VariableFactory ip(String ip) {
        this.string = this.string.replace("%ip%", ip);
        return this;
    }

    public VariableFactory lastLoginTime(String lastLoginTime) {
        this.string = this.string.replace("%lastLoginTime%", lastLoginTime);
        return this;
    }

    public VariableFactory lastLoginTime(Date date) {
        this.string = this.string.replace("%lastLoginTime%", DateUtil.dateToString(date, FileConfigConstant.SETTING_DATEFORMAT));
        return this;
    }

    public VariableFactory lastUse(String lastUse) {
        this.string = this.string.replace("%lastUse%", lastUse);
        return this;
    }

    public VariableFactory lastUse(Date lastUse) {
        this.string = this.string.replace("%lastUse%", DateUtil.dateToString(lastUse, FileConfigConstant.SETTING_DATEFORMAT));
        return this;
    }

    public VariableFactory name(String name) {
        this.string = this.string.replace("%name%", name);
        return this;
    }

    public VariableFactory object(String object) {
        this.string = this.string.replace("%object%", object);
        return this;
    }

    public VariableFactory parameter(List<String> parameter) {
        if (parameter.isEmpty())
            return this;

        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < parameter.size(); i++) {
            if (i != 0)
                stringBuilder.append(", ");

            stringBuilder.append("\"").append(parameter.get(i)).append("\"");
        }
        stringBuilder.append("]");
        this.string = this.string.replace("%parameter%", stringBuilder.toString());
        return this;
    }

    public VariableFactory photo(String photo) {
        this.string = this.string.replace("%photo%", photo);
        return this;
    }

    public VariableFactory photo(File file) {
        this.string = this.string.replace("%photo%", FileUtil.fileToBase64(file.getPath()));
        return this;
    }

    public VariableFactory qq(String qq) {
        this.string = this.string.replace("%qq%", qq);
        return this;
    }

    public VariableFactory qq(long qq) {
        this.string = this.string.replace("%qq%", String.valueOf(qq));
        return this;
    }

    public VariableFactory registerTime(String registerTime) {
        this.string = this.string.replace("%registerTime%", registerTime);
        return this;
    }

    public VariableFactory registerTime(Date date) {
        this.string = this.string.replace("%registerTime%", DateUtil.dateToString(date, FileConfigConstant.SETTING_DATEFORMAT));
        return this;
    }

    public VariableFactory telephone(String telephone) {
        this.string = this.string.replace("%telephone%", telephone);
        return this;
    }

    public VariableFactory telephone(long telephone) {
        this.string = this.string.replace("%telephone%", String.valueOf(telephone));
        return this;
    }

    public VariableFactory time(Date date) {
        this.string = this.string.replace("%time%", DateUtil.dateToString(date, FileConfigConstant.SETTING_DATEFORMAT));
        return this;
    }

    public VariableFactory time(String date) {
        this.string = this.string.replace("%time%", date);
        return this;
    }

    public VariableFactory save(String save) {
        this.string = this.string.replace("%save%", save);
        return this;
    }

    public VariableFactory save(Date save) {
        this.string = this.string.replace("%save%", DateUtil.dateToString(save, FileConfigConstant.SETTING_DATEFORMAT));
        return this;
    }

    public VariableFactory over(String over) {
        this.string = this.string.replace("%over%", over);
        return this;
    }

    public VariableFactory over(Date over) {
        this.string = this.string.replace("%over%", DateUtil.dateToString(over, FileConfigConstant.SETTING_DATEFORMAT));
        return this;
    }

    public VariableFactory token(String token) {
        this.string = this.string.replace("%token%", token);
        return this;
    }

    public VariableFactory uuid(String uuid) {
        this.string = this.string.replace("%uuid%", uuid);
        return this;
    }

    public VariableFactory uuid(long uuid) {
        this.string = this.string.replace("%uuid%", String.valueOf(uuid));
        return this;
    }

    @Override
    public String toString() {
        return this.string;
    }
}