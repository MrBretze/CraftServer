package fr.bretzel.craftserver;

import com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Created by MrBretzel on 04/06/2015.
 */

public class CraftServer {

    private Server server;
    private int opLevel;

    public CraftServer(Server server) {
        this.server = server;
        this.opLevel = 4; //TODO: Change to get a real value.
    }

    public void addOp(GameProfile gameProfile) {
        try {
            Object  playerList = server.getClass().getDeclaredField("playerList").get(server.getClass());
            Method addop = playerList.getClass().getMethod("addOp", GameProfile.class);
            addop.invoke(playerList.getClass(), gameProfile);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Server getServer() {
        return server;
    }

    public Class<?> getMinecraftServerClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getBukkitVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class getCraftBukkitClass(String name) {
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
