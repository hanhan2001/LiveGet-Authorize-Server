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

    // Module
    public static String MESSAGE_MODULE_INFO,
            MESSAGE_MODULE_NOT_FOUND,
            MESSAGE_MODULE_OVERDUE,
            MESSAGE_MODULE_EXISTED;

    public static String MESSAGE_TOKEN_INFO,
            MESSAGE_TOKEN_EXISTED,
            MESSAGE_TOKEN_NOT_FOUND,
            MESSAGE_TOKEN_OVERDUE;

    // Permission
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

        // Module
        MESSAGE_MODULE_INFO = "{\n" +
                "  \"code\": 200,\n" +
                "  \"type\": \"module_info\",\n" +
                "  \"function\": \"%function%\",\n" +
                "  \"object\": \"%object%\",\n" +
                "  \"save\": \"%save%\",\n" +
                "  \"over\": \"%over%\",\n" +
                "  \"owner\": \"%owner%\",\n" +
                "  \"description\": \"%description%\",\n" +
                "  \"identification\": \"%identification%\",\n" +
                "  \"permission\": \"%permission%\"\n" +
                "}";
        MESSAGE_MODULE_NOT_FOUND = "{\n" +
                "  \"code\": 110,\n" +
                "  \"type\": \"module_not_found\",\n" +
                "  \"message\": \"Can't find module.\"";
        MESSAGE_MODULE_OVERDUE = "{\n" +
                "  \"code\": 110,\n" +
                "  \"type\": \"module_overdue\",\n" +
                "  \"Message\": \"This module overdue.\"\n" +
                "}";
        MESSAGE_MODULE_EXISTED = "{\n" +
                "  \"code\": 110,\n" +
                "  \"type\": \"module_existed\",\n" +
                "  \"message\": \"This module existed.\"\n" +
                "}";

        // Token
        MESSAGE_TOKEN_INFO = "{\n" +
                "  \"code\": 200,\n" +
                "  \"type\": \"token_info\",\n" +
                "  \"token\": \"%token%\",\n" +
                "  \"function\": \"%token_function%\",\n" +
                "  \"object\": \"%object%\",\n" +
                "  \"save\": \"%token_save%\",\n" +
                "  \"over\": \"%token_over%\",\n" +
                "  \"machine\": \"%token_machine%\",\n" +
                "  \"lastUse\": \"%token_lastUse%\",\n" +
                "  \"time\": \"%date%\"\n" +
                "}";
        MESSAGE_TOKEN_OVERDUE = "{\n" +
                "  \"code\": 110,\n" +
                "  \"type\": \"token_overdue\",\n" +
                "  \"message\": \"This token overdue.\"\n" +
                "}";
        MESSAGE_TOKEN_EXISTED = "{\n" +
                "  \"code\": 110,\n" +
                "  \"type\": \"token_existed\",\n" +
                "  \"message\": \"This token existed.\"\n" +
                "}";
        MESSAGE_TOKEN_NOT_FOUND = "{\n" +
                "  \"code\": 110,\n" +
                "  \"type\": \"token_not_found\",\n" +
                "  \"message\": \"Can't find token.\"\n" +
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