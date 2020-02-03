package chatsync;

import java.util.ArrayList;
import java.util.List;
import org.spicord.script.Function;
import eu.mcdb.universal.Server;

public class ChatListener {

    private final List<Function> handlers;

    public ChatListener() {
        this.handlers = new ArrayList<Function>();

        switch (Server.getServerType()) {
        case BUKKIT: new BukkitListener(this); break;
        case BUNGEECORD: new BungeeListener(this); break;
        case SPONGE: new SpongeListener(this); break;
        case VELOCITY: new VelocityListener(this); break;
        default:
            System.out.println("ChatSync only works when running on a minecraft server");
        }
    }

    public void handle(final String name, final String message) {
        this.handlers.forEach(h -> h.call(name, message));
    }

    public void onChat(Function func) {
        this.handlers.add(func);
    }
}
