package me.xiaoying.livegetauthorize.server.factory;

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

    public VariableFactory email(String email) {
        this.string = this.string.replace("%email%", email);
        return this;
    }

    public VariableFactory name(String name) {
        this.string = this.string.replace("%name%", name);
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

    public VariableFactory telephone(String telephone) {
        this.string = this.string.replace("%telephone%", telephone);
        return this;
    }

    public VariableFactory telephone(long telephone) {
        this.string = this.string.replace("%telephone%", String.valueOf(telephone));
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