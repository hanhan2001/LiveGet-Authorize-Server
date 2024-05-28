package me.xiaoying.livegetauthorize.server.utils;

import me.xiaoying.livegetauthorize.core.event.*;
import me.xiaoying.livegetauthorize.core.plugin.IllegalPluginAccessException;
import me.xiaoying.livegetauthorize.core.utils.Preconditions;
import me.xiaoying.livegetauthorize.server.constant.FileConfigConstant;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Util Server
 */
public class ServerUtil {
    public static String getEncryptPassword(String password) {
        switch (FileConfigConstant.SETTING_PASSWORD_ENCRYPT.toUpperCase()) {
            case "SHA256":
                password = EncryptUtil.SHA256Encrypt(password);
                break;
            case "MD5":
                password = EncryptUtil.md5Encrypt(password);
                break;
            case "BASE64":
                password = EncryptUtil.base64Encrypt(password);
                break;
        }
        return password;
    }

    public static void registerEvent(Listener listener) {
        for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : createRegisteredListeners(listener).entrySet())
            getEventListeners(getRegistrationClass(entry.getKey())).registerAll(entry.getValue());
    }

    private static Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener listener) {
        Preconditions.checkArgument(listener != null, "Listener can not be null");
        Map<Class<? extends Event>, Set<RegisteredListener>> ret = new HashMap<>();

        HashSet<Method> methods;
        try {
            Method[] publicMethods = listener.getClass().getMethods();
            Method[] privateMethods = listener.getClass().getDeclaredMethods();
            methods = new HashSet<>(publicMethods.length + privateMethods.length, 1.0F);
            Method[] var11 = publicMethods;
            int var10 = publicMethods.length;

            Method method;
            int var9;
            for(var9 = 0; var9 < var10; ++var9) {
                method = var11[var9];
                methods.add(method);
            }

            var11 = privateMethods;
            var10 = privateMethods.length;

            for(var9 = 0; var9 < var10; ++var9) {
                method = var11[var9];
                methods.add(method);
            }
        } catch (NoClassDefFoundError e) {
            return ret;
        }

        Iterator<Method> iterator = methods.iterator();

        while(true) {
            while(true) {
                Method method;
                EventHandler eh;
                do {
                    do {
                        do {
                            if (!iterator.hasNext())
                                return ret;

                            method = iterator.next();
                            eh = method.getAnnotation(EventHandler.class);
                        } while (eh == null);
                    } while (method.isBridge());
                } while (method.isSynthetic());

                Class<?> checkClass;
                if (method.getParameterTypes().length != 1 || !Event.class.isAssignableFrom(checkClass = method.getParameterTypes()[0]))
                    return null;

                final
                Class<? extends Event> eventClass = checkClass.asSubclass(Event.class);
                method.setAccessible(true);
                Set<RegisteredListener> eventSet = ret.get(eventClass);
                if (eventSet == null) {
                    eventSet = new HashSet<>();
                    ret.put(eventClass, eventSet);
                }
                EventExecutor executor = (listener1, event) -> {};
                eventSet.add(new RegisteredListener(listener, executor, EventPriority.LOWEST, null));
            }
        }
    }


    private static HandlerList getEventListeners(Class<? extends Event> type) {
        try {
            Method method = getRegistrationClass(type).getDeclaredMethod("getHandlerList", new Class[0]);
            method.setAccessible(true);
            if (!Modifier.isStatic(method.getModifiers()))
                throw new IllegalAccessException("getHandlerList must be static");

            return (HandlerList)method.invoke(null);
        } catch (Exception var3) {
            throw new IllegalPluginAccessException("Error while registering listener for event type " + type.toString() + ": " + var3);
        }
    }

    private static Class<? extends Event> getRegistrationClass(Class<? extends Event> clazz) {
        try {
            clazz.getDeclaredMethod("getHandlerList", new Class[0]);
            return clazz;
        } catch (NoSuchMethodException noSuchMethodException) {
            if (clazz.getSuperclass() != null &&
                    !clazz.getSuperclass().equals(Event.class) &&
                    Event.class.isAssignableFrom(clazz.getSuperclass())) {
                return getRegistrationClass(clazz.getSuperclass().asSubclass(Event.class));
            }
            throw new IllegalPluginAccessException("Unable to find handler list for event " + clazz.getName() + ". Static getHandlerList method required!");
        }
    }
}