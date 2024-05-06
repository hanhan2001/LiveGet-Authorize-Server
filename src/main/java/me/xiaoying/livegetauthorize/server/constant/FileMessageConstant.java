package me.xiaoying.livegetauthorize.server.constant;

/**
 * Constant FileMessage
 */
public class FileMessageConstant {
    public static String MESSAGE_ACCOUNT_LOGIN ,
            MESSAGE_ACCOUNT_NOT_FOUND,
            MESSAGE_ACCOUNT_PASSWORD_INVALID,
            MESSAGE_ACCOUNT_USER_ALREADY_EXISTS,
            MESSAGE_ACCOUNT_EMAIL_ALREADY_EXISTS,
            MESSAGE_ACCOUNT_NEED_RE_LOGIN,
            MESSAGE_ACCOUNT_INFO;

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
        MESSAGE_ACCOUNT_USER_ALREADY_EXISTS = "{\n" +
                "  \"code\": 102,\n" +
                "  \"message\": \"User already exists.\"\n" +
                "}";
        MESSAGE_ACCOUNT_EMAIL_ALREADY_EXISTS = "{\n" +
                "  \"code\": 103,\n" +
                "  \"message\": \"Email already exists.\"\n" +
                "}";
        MESSAGE_ACCOUNT_NEED_RE_LOGIN = "{\n" +
                "  \"code\": 104,\n" +
                "  \"message\": \"Token is over due, please re login.\"\n" +
                "}";
        MESSAGE_ACCOUNT_INFO = "{\n" +
                "  \"code\": 105,\n" +
                "  \"qq\": %qq%,\n" +
                "  \"email\": \"%email%\",\n" +
                "  \"telephone\": %telephone%,\n" +
                "  \"uuid\": %uuid%,\n" +
                "  \"name\": \"%name%\",\n" +
                "  \"ip\": \"%ip%\",\n" +
                "  \"registerTime\": \"%registerTime%\",\n" +
                "  \"lastLoginTime\": \"%lastLoginTime%\"\n" +
                "}";
    }
}