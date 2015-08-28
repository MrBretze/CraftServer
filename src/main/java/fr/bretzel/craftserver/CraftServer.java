package fr.bretzel.craftserver;

import com.mojang.authlib.GameProfile;
import fr.bretzel.craftserver.util.ReflectionUtil;
import fr.bretzel.minecraftserver.MinecraftServer;
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
            commandsConfiguration = (YamlConfiguration) ReflectionUtil.getFiel("commandsConfiguration", server.getClass()).get(server);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean getCommandBlockOverride(String command) {
        return overrideCommandBlock || (commandsConfiguration.getStringList("command-block-overrides").contains(command));
    }

    public void addOp(GameProfile gameProfile) {
        try {
            Object  playerList = server.getClass().getDeclaredField("playerList").get(server.getClass());
            Method addop = ReflectionUtil.getMethod("addOp", playerList.getClass(), GameProfile.class);
            addop.invoke(playerList.getClass(), gameProfile);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Server getServer() {
        return server;
    }
}
