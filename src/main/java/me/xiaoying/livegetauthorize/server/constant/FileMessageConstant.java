package me.xiaoying.livegetauthorize.server.constant;

/**
 * Constant FileMessage
 */
public class FileMessageConstant {
    public static String MESSAGE_ACCOUNT_LOGIN ,
            MESSAGE_ACCOUNT_NOT_FOUND,
            MESSAGE_ACCOUNT_PASSWORD_INVALID,
            MESSAGE_ACCOUNT_ALREADY_EXISTS;

    static {
        MESSAGE_ACCOUNT_LOGIN = "{\n" +
                "  \"code\": 200,\n" +
                "  \"account\": \"%account%\",\n" +
                "  \"token\": \"%token%\",\n" +
                "  \"message\": \"Login success.\"\n" +
                "}";
        MESSAGE_ACCOUNT_NOT_FOUND = "{\n" +
                "  \"code\": 101,\n" +
                "  \"message\": \"can't find user.\"\n" +
                "}";
        MESSAGE_ACCOUNT_PASSWORD_INVALID = "{\n" +
                "  \"code\": 301,\n" +
                "  \"message\": \"Password invalid.\"\n" +
                "}";
        MESSAGE_ACCOUNT_ALREADY_EXISTS = "{\n" +
                "  \"code\": 102,\n" +
                "  \"message\": \"User already exists.\"\n" +
                "}";
    }
}