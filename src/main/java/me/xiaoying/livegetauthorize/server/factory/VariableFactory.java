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

    public VariableFactory token(String token) {
        this.string = this.string.replace("%token%", token);
        return this;
    }

    @Override
    public String toString() {
        return this.string;
    }
}