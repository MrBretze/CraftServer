package fr.bretzel.craftserver;


import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

/**
 * Created by Loic on 30/04/2015.
 */

public abstract class Packet {

    private Server server;
    private Class clazz;
    private Object packet;

    public Packet(Plugin plugin) {
        setServer(plugin.getServer());
    }

    public Packet(Server server) {
        setServer(server);
    }

    public Packet() {};

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }

    public Class<?> getMinecraftServerClass(String name) {
        try {

            if(getBukkitVersion().equalsIgnoreCase("v1_8_R1") && name.equalsIgnoreCase("PacketPlayOutTitle$EnumTitleAction")) {
                name = "EnumTitleAction";
            }

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

    public Object getPacket() {
        return packet;
    }

    public void setPacket(Object packet) {
        this.packet = packet;
    }

    public static String getBukkitVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }
}
