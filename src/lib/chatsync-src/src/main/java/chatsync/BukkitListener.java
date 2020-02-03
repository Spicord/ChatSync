package chatsync;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class BukkitListener implements Listener {

    private final ChatListener listener;

    public BukkitListener(ChatListener listener) {
        this.listener = listener;
        final PluginManager pluginManager = Bukkit.getPluginManager();
        final Plugin plugin = pluginManager.getPlugin("Spicord");
        pluginManager.registerEvents(this, plugin);
    }

    @EventHandler
    public void onAsyncPlayerChat(final AsyncPlayerChatEvent event) {
        listener.handle(event.getPlayer().getName(), event.getMessage());
    }
}
