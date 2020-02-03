package chatsync;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventHandler;

public class BungeeListener implements Listener {

    private final ChatListener listener;

    public BungeeListener(ChatListener listener) {
        this.listener = listener;
        final PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        final Plugin plugin = pluginManager.getPlugin("Spicord");
        pluginManager.registerListener(plugin, this);
    }

    @EventHandler
    public void onChat(final ChatEvent event) {
        if (event.getSender() instanceof ProxiedPlayer) {
            listener.handle(((ProxiedPlayer) event.getSender()).getName(), event.getMessage());
        }
    }
}
