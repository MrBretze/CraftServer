package fr.bretzel.craftserver.util;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by MrBretzel on 26/08/2015.
 */
public class ReflectionUtil {

    public static Field getFiel(String name, Object clazz) {
        for (Field f : clazz.getClass().getDeclaredFields()) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        return null;
    }

    public static Method getMethod(String name, Object clazz, Class<?>... parm) {
        for (Method m : clazz.getClass().getDeclaredMethods()) {
            if (m.getName().equals(name) && m.getParameterTypes().length == parm.length) {
                int i = 0;
                for (Class<?> type : m.getParameterTypes()) {
                    Class<?> c = parm[1];
                    if (type.toString().equals(c.toString())) {
                        return m;
                    }
                    i++;
                }
            }
        }
        return null;
    }

    public static Object[] getEnumeration(Class<?> clazz) {
        if (clazz.isEnum()) {
            return clazz.getEnumConstants();
        }
        return null;
    }

    public static Class<?> getMinecraftServerClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getBukkitVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class getCraftBukkitClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + getBukkitVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBukkitVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }
}
