package fr.bretzel.craftserver.packet;

import fr.bretzel.craftserver.Packet;
import fr.bretzel.craftserver.util.ChatSerializer;
import fr.bretzel.craftserver.util.IChatSerializer;
import fr.bretzel.craftserver.util.Tab;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Loic on 30/04/2015.
 */

public class PacketHeaderAndFooterTab extends Packet {

    private Tab tab = Tab.UNKNOWN;
    private IChatSerializer header;
    private IChatSerializer footer;

    public PacketHeaderAndFooterTab(IChatSerializer header,  IChatSerializer footer) {
        super(Bukkit.getServer());
        this.footer = footer;
        this.header = header;
        init();
    }

    public PacketHeaderAndFooterTab(IChatSerializer object, Tab tab) {
        super(Bukkit.getServer());

        this.tab = tab;

        if(this.tab == Tab.FOOTER) {

            this.footer = object;
            this.header = ChatSerializer.serialize(" ");

        } else if(this.tab == Tab.HEADER) {

            this.header = object;
            this.footer = ChatSerializer.serialize(" ");
        }
        init();
    }

    private void init() {
        setClazz(getMinecraftServerClass("PacketPlayOutPlayerListHeaderFooter"));

        try {

            Object packet = getClazz().getConstructor().newInstance();

            setPacket(packet);

            if(header != null) {
                Field field = getClazz().getDeclaredField("a");
                field.setAccessible(true);
                field.set(packet, header.getJSonElement());
                field.setAccessible(false);
            }

            if(footer != null) {
                Field field = getClazz().getDeclaredField("b");
                field.setAccessible(true);
                field.set(packet, footer.getJSonElement());
                field.setAccessible(false);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
