package fr.bretzel.craftserver;


import com.mojang.authlib.GameProfile;
import fr.bretzel.craftserver.packet.PacketTitle;

import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Loic on 29/04/2015.
 * @author Loic
 */

public class CraftPlayer  {

    private Player player;
    private GameProfile profile;
    private CraftServer server;

    public CraftPlayer(Player player) {
        this.player = player;
        this.profile = new GameProfile(player.getUniqueId(), player.getName());
        this.server = new CraftServer(this.getPlayer().getServer());
    }

    private void setOp(boolean b) {
        getServer().addOp(getGameProfile());
    }

    public GameProfile getGameProfile() {
        return profile;
    }

    public void sendPacket(Packet packet) {
        try {
            Object getHandle = getHandle(player);

            Object connection = getHandle.getClass().getDeclaredField("playerConnection").get(getHandle);

            Method sendPacket = getMethod(connection.getClass(), "sendPacket");


            if(packet instanceof PacketTitle) {
                PacketTitle titlep = (PacketTitle) packet;

                sendPacket.invoke(connection, titlep.getPacketTimings());

                sendPacket.invoke(connection, titlep.getTitlePacket());

                if(titlep.getSubtitlePacket() != null) {
                    sendPacket.invoke(connection, titlep.getSubtitlePacket());
                }

            } else {
                sendPacket.invoke(connection, packet.getPacket());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Object getHandle(Object obj) {
        try {
            return getMethod(obj.getClass(), "getHandle").invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CraftServer getServer() {
        return server;
    }

    private Method getMethod(Class<?> clazz, String name, Class<?>... args) {
        for (Method m : clazz.getMethods())
            if (m.getName().equals(name)
                    && (args.length == 0 || ClassListEqual(args,
                    m.getParameterTypes()))) {
                m.setAccessible(true);
                return m;
            }
        return null;
    }

    private boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
        boolean equal = true;
        if (l1.length != l2.length)
            return false;
        for (int i = 0; i < l1.length; i++)
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        return equal;
    }

    public Player getPlayer() {
        return player;
    }
}
