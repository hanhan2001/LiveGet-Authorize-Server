package me.xiaoying.livegetauthorize.server.constant;

/**
 * Constant FileMessage
 */
public class FileMessageConstant {
    public static String MESSAGE_ACCOUNT_LOGIN ,
            MESSAGE_ACCOUNT_NOT_FOUND,
            MESSAGE_ACCOUNT_PASSWORD_INVALID,
            MESSAGE_ACCOUNT_USER_EXISTED,
            MESSAGE_ACCOUNT_EMAIL_EXISTED,
            MESSAGE_ACCOUNT_NEED_RE_LOGIN,
            MESSAGE_ACCOUNT_INFO;
    public static String MESSAGE_PERMISSION_NO_PERMISSION;

    static {
        MESSAGE_ACCOUNT_LOGIN = "{\n" +
                "  \"code\": 200,\n" +
                "  \"account\": \"%account%\",\n" +
                "  \"token\": \"%token%\",\n" +
                "  \"message\": \"Login success.\"\n" +
                "}";
        MESSAGE_ACCOUNT_INFO = "{\n" +
                "  \"code\": 200,\n" +
                "  \"type\": \"account_info\",\n" +
                "  \"qq\": \"%qq%\",\n" +
                "  \"email\": \"%email%\",\n" +
                "  \"telephone\": \"%telephone%\",\n" +
                "  \"uuid\": \"%uuid%\",\n" +
                "  \"name\": \"%name%\",\n" +
                "  \"ip\": \"%ip%\",\n" +
                "  \"registerTime\": \"%registerTime%\",\n" +
                "  \"lastLoginTime\": \"%lastLoginTime%\",\n" +
                "  \"photo\": \"%photo%\"\n" +
                "}";
        MESSAGE_ACCOUNT_PASSWORD_INVALID = "{\n" +
                "  \"code\": 110,\n" +
                "  \"message\": \"Password invalid.\"\n" +
                "}";
        MESSAGE_ACCOUNT_NOT_FOUND = "{\n" +
                "  \"code\": 110,\n" +
                "  \"message\": \"Can't find user.\"\n" +
                "}";
        MESSAGE_ACCOUNT_EMAIL_EXISTED = "{\n" +
                "  \"code\": 110,\n" +
                "  \"message\": \"Email already exists.\"\n" +
                "}";
        MESSAGE_ACCOUNT_USER_EXISTED = "{\n" +
                "  \"code\": 110,\n" +
                "  \"message\": \"User already exists.\"\n" +
                "}";
        MESSAGE_ACCOUNT_NEED_RE_LOGIN = "{\n" +
                "  \"code\": 110,\n" +
                "  \"message\": \"Token is over due, please re login.\"\n" +
                "}";

        //权限信息
        MESSAGE_PERMISSION_NO_PERMISSION = "{\n" +
                "  \"code\": 201,\n" +
                "  \"Message\": \"No permission.\"\n" +
                "}";
    }
}