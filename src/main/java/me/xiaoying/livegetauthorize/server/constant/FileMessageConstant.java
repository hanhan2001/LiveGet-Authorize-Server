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
            MESSAGE_ACCOUNT_NEED_LOGIN,
            MESSAGE_ACCOUNT_INFO;

    public static String MESSAGE_DISPLAY_PREVIEW_TITLE;

    public static String MESSAGE_PERMISSION_NO_PERMISSION;

    public static String ERROR_NORMAL;

    static {
        MESSAGE_ACCOUNT_LOGIN = "{\n" +
                "  \"code\": 200,\n" +
                "  \"type\": \"account_login\",\n" +
                "  \"account\": \"%account%\",\n" +
                "  \"token\": \"%token%\",\n" +
                "  \"message\": \"Login success.\",\n" +
                "  \"time\": \"%date%\"\n" +
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
                "  \"photo\": \"%photo%\",\n" +
                "  \"time\": \"%date%\"\n" +
                "}";
        MESSAGE_ACCOUNT_PASSWORD_INVALID = "{\n" +
                "  \"code\": 110,\n" +
                "  \"type\": \"account_password_invalid\",\n" +
                "  \"message\": \"Password invalid.\",\n" +
                "  \"time\": \"%date%\"\n" +
                "}";
        MESSAGE_ACCOUNT_NOT_FOUND = "{\n" +
                "  \"code\": 110,\n" +
                "  \"type\": \"account_not_found_user\",\n" +
                "  \"message\": \"Can't find user.\",\n" +
                "  \"time\": \"%date%\"\n" +
                "}";
        MESSAGE_ACCOUNT_EMAIL_EXISTED = "{\n" +
                "  \"code\": 110,\n" +
                "  \"type\": \"account_email_existed\",\n" +
                "  \"message\": \"Email already exists.\",\n" +
                "  \"time\": \"%date%\"\n" +
                "}";
        MESSAGE_ACCOUNT_USER_EXISTED = "{\n" +
                "  \"code\": 110,\n" +
                "  \"type\": \"account_user_existed\",\n" +
                "  \"message\": \"User already exists.\",\n" +
                "  \"time\": \"%date%\"\n" +
                "}";
        MESSAGE_ACCOUNT_NEED_LOGIN = "{\n" +
                "  \"code\": 110,\n" +
                "  \"type\": \"account_need_login\",\n" +
                "  \"message\": \"Token is over due, please re login.\",\n" +
                "  \"time\": \"%date%\"\n" +
                "}";

        //显示页面
        MESSAGE_DISPLAY_PREVIEW_TITLE = "{\n" +
                "  \"code\": 200,\n" +
                "  \"type\": \"display_preview_title\",\n" +
                "  \"message\": \"%title%\",\n" +
                "  \"time\": \"%date%\"\n" +
                "}";

        //权限信息
        MESSAGE_PERMISSION_NO_PERMISSION = "{\n" +
                "  \"code\": 110,\n" +
                "  \"type\": \"permission_no\",\n" +
                "  \"message\": \"No permission.\",\n" +
                "  \"time\": \"%date%\"\n" +
                "}";

        // 错误信息
        ERROR_NORMAL = "{\n" +
                "  \"code\": -1,\n" +
                "  \"type\": \"error\",\n" +
                "  \"message\": \"An error has occurred, please send this page to the website administrator.\",\n" +
                "  \"time\": \"%date%\"\n" +
                "}";
    }
}