package fr.bretzel.craftserver.util;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Loic on 30/04/2015.
 */

public class ChatSerializer {

    private static Class<?> chatSerializer;
    private static Class<?> ibaseComponent;

    public static IChatSerializer serialize(String msg) {
        loadClass();

        IChatSerializer serializer = null;

        try {
            Object o = chatSerializer.getDeclaredMethod("a", String.class).invoke(null, "{\"text\": \"" + msg + "\"}");
            serializer = new IChatSerializer(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return serializer;
    }


    private static void loadClass() {

        String clazz = "IChatBaseComponent$ChatSerializer";

        if (ReflectionUtil.getBukkitVersion().equalsIgnoreCase("v1_8_R1")) {
            clazz = "ChatSerializer";
        }

        chatSerializer = ReflectionUtil.getMinecraftServerClass(clazz);
        ibaseComponent = ReflectionUtil.getMinecraftServerClass("IChatBaseComponent");
    }

    public static Class<?> getChatSerializer() {
        loadClass();
        return chatSerializer;
    }

    public static Class<?> getIChatBaseComponent() {
        loadClass();
        return ibaseComponent;
    }
}
