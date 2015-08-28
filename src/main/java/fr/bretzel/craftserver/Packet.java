package fr.bretzel.craftserver;


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

    public Packet() {
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Object getPacket() {
        return packet;
    }

    public void setPacket(Object packet) {
        this.packet = packet;
    }
}
