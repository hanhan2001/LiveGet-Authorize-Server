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

    public VariableFactory qq(String qq) {
        this.string = this.string.replace("%qq%", qq);
        return this;
    }

    public VariableFactory qq(long qq) {
        this.string = this.string.replace("%qq%", String.valueOf(qq));
        return this;
    }

    public VariableFactory token(String token) {
        this.string = this.string.replace("%token%", token);
        return this;
    }

    @Override
    public String toString() {
        return this.string;
    }
}