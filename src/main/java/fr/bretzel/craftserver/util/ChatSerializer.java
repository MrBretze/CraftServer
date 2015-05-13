package fr.bretzel.craftserver.util;


import fr.bretzel.craftserver.Packet;

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
        chatSerializer = getMinecraftServerClass("IChatBaseComponent$ChatSerializer");
        ibaseComponent = getMinecraftServerClass("IChatBaseComponent");
    }

    private static Class<?> getMinecraftServerClass(String name) {
        try {
            Class<?> clazz;
            String version = Packet.getBukkitVersion();

            if(version.equalsIgnoreCase("v1_8_R1") && name.equalsIgnoreCase("IChatBaseComponent$ChatSerializer")) {
                name = "ChatSerializer";
            }
            clazz = Class.forName("net.minecraft.server." + version + "." + name);
            return clazz;

        } catch (ClassNotFoundException e) {
            e.fillInStackTrace();
        }
        return null;
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
