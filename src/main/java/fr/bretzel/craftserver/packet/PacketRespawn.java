package fr.bretzel.craftserver.packet;

import fr.bretzel.craftserver.Packet;
import fr.bretzel.craftserver.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Loic on 14/05/2015.
 */

public class PacketRespawn extends Packet {

    public PacketRespawn() {
        init();
    }

    private void init() {
        try {
            setClazz(ReflectionUtil.getMinecraftServerClass("PacketPlayOutRespawn"));
            Object packet = getClazz().getConstructor().newInstance();
            setPacket(packet);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
