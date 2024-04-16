package me.xiaoying.livegetauthorize.server.permission;

import me.xiaoying.livegetauthorize.core.entity.User;
import me.xiaoying.livegetauthorize.core.permission.PermissionManager;

/**
 * Permission manager
 */
public class SimplePermissionManager implements PermissionManager {
    @Override
    public boolean hasPermission(User user, String s) {
        return false;
    }
}