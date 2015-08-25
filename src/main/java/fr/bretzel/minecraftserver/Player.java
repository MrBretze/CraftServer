package fr.bretzel.minecraftserver;

import fr.bretzel.craftserver.CraftPlayer;
import fr.bretzel.craftserver.Packet;
import fr.bretzel.craftserver.packet.PacketTitle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by MrBretzel on 25/08/2015.
 */
public class Player {

    private CraftPlayer craftPlayer;

    public Player(CraftPlayer craftPlayer) {
        this.craftPlayer = craftPlayer;
    }

    public void sendPacket(Packet packet) {
        try {
            Object getHandle = getHandle(craftPlayer.getPlayer());
            Object connection = getHandle.getClass().getDeclaredField("playerConnection").get(getHandle);
            Method sendPacket = getMethod(connection.getClass(), "sendPacket");
            if (packet instanceof PacketTitle) {
                PacketTitle titlep = (PacketTitle) packet;
                sendPacket.invoke(connection, titlep.getPacketTimings());
                sendPacket.invoke(connection, titlep.getTitlePacket());
                if (titlep.getSubtitlePacket() != null) {
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

    private Object getHandle(org.bukkit.entity.Player obj) {
        try {
            return getMethod(obj.getClass(), "getHandle").invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    public CraftPlayer getCraftPlayer() {
        return craftPlayer;
    }
}
