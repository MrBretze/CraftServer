package fr.bretzel.craftserver.packet;

import fr.bretzel.craftserver.Packet;
import fr.bretzel.craftserver.util.ChatSerializer;
import fr.bretzel.craftserver.util.EnumTitle;
import fr.bretzel.craftserver.util.IChatSerializer;
import org.bukkit.Bukkit;

/**
 * Created by Loic on 01/05/2015.
 */

public class PacketTitle extends Packet {

    private int fadeInTime = -1;
    private int stayTime = -1;
    private int fadeOutTime = -1;

    private IChatSerializer title;
    private IChatSerializer subtitle;
    private Object[] action;

    private Object packetTitle;
    private Object packetsubtitle;
    private Object packetTimings;

    public PacketTitle(IChatSerializer title, IChatSerializer subtitle) {
        super(Bukkit.getServer());
        this.title = title;
        this.subtitle = subtitle;

        fadeInTime = 20;
        fadeOutTime = 20;
        stayTime = 20;

        init();
    }

    public PacketTitle(IChatSerializer title, IChatSerializer subtitle, int fadeInTime, int stayTime, int fadeOutTime) {
        super(Bukkit.getServer());
        this.title = title;
        this.subtitle = subtitle;
        this.fadeInTime = fadeInTime;
        this.fadeOutTime = fadeOutTime;
        this.stayTime = stayTime;

        init();
    }


    public PacketTitle(IChatSerializer msg, EnumTitle title, int fadeInTime, int stayTime, int fadeOutTime) {
        super(Bukkit.getServer());

        if(title == EnumTitle.SUBTITLE) {
            this.title = ChatSerializer.serialize(" ");
            this.subtitle = msg;
        } else {
            this.title = msg;
        }

        this.fadeInTime = fadeInTime;
        this.fadeOutTime = fadeOutTime;
        this.stayTime = stayTime;

        init();
    }

    public PacketTitle(IChatSerializer msg, EnumTitle title) {
        super(Bukkit.getServer());

        if(title == EnumTitle.SUBTITLE) {
            this.title = ChatSerializer.serialize(" ");
            this.subtitle = msg;
        } else {
            this.title = msg;
        }

        fadeInTime = 20;
        fadeOutTime = 20;
        stayTime = 20;

        init();
    }

    private void init() {
        try {

            Class<?> cpacket = getMinecraftServerClass("PacketPlayOutTitle");

            this.action = getMinecraftServerClass("PacketPlayOutTitle$EnumTitleAction").getEnumConstants();

            Object timings = cpacket.getConstructor(getMinecraftServerClass("PacketPlayOutTitle$EnumTitleAction"), ChatSerializer.getIChatBaseComponent(), Integer.TYPE, Integer.TYPE, Integer.TYPE)
                    .newInstance(action[2], null, fadeInTime, stayTime, fadeOutTime);

            setPacketTimings(timings);

            Object titlePacket = cpacket.getConstructor(getMinecraftServerClass("PacketPlayOutTitle$EnumTitleAction"), ChatSerializer.getIChatBaseComponent())
                    .newInstance(action[0], title.getJSonElement());

            setPacketTitle(titlePacket);

            if(subtitle != null) {
                Object subtitle = cpacket.getConstructor(getMinecraftServerClass("PacketPlayOutTitle$EnumTitleAction"), ChatSerializer.getIChatBaseComponent())
                        .newInstance(action[1], this.subtitle.getJSonElement());

                setPacketsubtitle(subtitle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Object getPacketTimings() {
        return packetTimings;
    }

    public Object getTitlePacket() {
        return this.packetTitle;
    }

    public Object getSubtitlePacket() {
        return this.packetsubtitle;
    }

    private void setPacketTitle(Object packetTitle) {
        this.packetTitle = packetTitle;
    }

    private void setPacketsubtitle(Object packetsubtitle) {
        this.packetsubtitle = packetsubtitle;
    }

    private void setPacketTimings(Object packetTimings) {
        this.packetTimings = packetTimings;
    }
}
