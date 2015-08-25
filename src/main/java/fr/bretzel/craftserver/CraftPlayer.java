package fr.bretzel.craftserver;


import com.mojang.authlib.GameProfile;
import org.bukkit.entity.Player;

/**
 * Created by MrBretzel on 29/04/2015.
 */

public class CraftPlayer  {

    private Player player;
    private GameProfile profile;
    private CraftServer server;
    private fr.bretzel.minecraftserver.Player handle;

    public CraftPlayer(Player player) {
        this.player = player;
        this.profile = new GameProfile(player.getUniqueId(), player.getName());
        this.server = new CraftServer(this.getPlayer().getServer());
        this.handle = new fr.bretzel.minecraftserver.Player(this);
    }

    public void setOp(boolean b) {
        getServer().addOp(getGameProfile());
    }

    public GameProfile getGameProfile() {
        return profile;
    }

    public fr.bretzel.minecraftserver.Player getHandle() {
        return handle;
    }

    public CraftServer getServer() {
        return server;
    }

    public Player getPlayer() {
        return player;
    }
}
