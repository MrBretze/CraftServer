package fr.bretzel.craftserver;

import com.mojang.authlib.GameProfile;
import fr.bretzel.minecraftserver.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by MrBretzel on 04/06/2015.
 */

public class CraftServer {

    private Server server;
    private int opLevel = 0;
    private boolean overrideCommandBlock = false;
    private YamlConfiguration commandsConfiguration = null;
    private MinecraftServer minecraftServer = new MinecraftServer();

    public CraftServer(Server server) {
        this.server = server;
        this.opLevel = 4;
        try {
            commandsConfiguration = (YamlConfiguration) server.getClass().getDeclaredField("commandsConfiguration").get(server);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static String getBukkitVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }

    public boolean getCommandBlockOverride(String command) {
        return overrideCommandBlock || (commandsConfiguration.getStringList("command-block-overrides").contains(command));
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
}
