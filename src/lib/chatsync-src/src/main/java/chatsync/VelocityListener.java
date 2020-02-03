package chatsync;

import java.lang.reflect.Field;
import java.util.Optional;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;

public class VelocityListener {

    private final ChatListener listener;

    public VelocityListener(final ChatListener listener) {
        this.listener = listener;
        final Optional<ProxyServer> oProxyServer = getProxyServer();

        if (oProxyServer.isPresent()) {
            final ProxyServer server = oProxyServer.get();
            final Optional<PluginContainer> oSpicord = server.getPluginManager().getPlugin("spicord");

            if (oSpicord.isPresent()) {
                final PluginContainer spicord = oSpicord.get();
                server.getEventManager().register(spicord.getInstance().get(), this);
            } else {
                System.out.println("Spicord plugin not found, ChatSync will not work");
            }
        } else {
            System.out.println("Cannot access the server instance, ChatSync will not work");
        }
    }

    @Subscribe
    public void onPlayerChat(PlayerChatEvent event) {
        listener.handle(event.getPlayer().getUsername(), event.getMessage());
    }

    private Optional<ProxyServer> getProxyServer() {
        try {
            final Class<?> velocityServerClass = Class.forName("eu.mcdb.universal.VelocityServer");
            final Field handleField = velocityServerClass.getDeclaredField("handle");
            handleField.setAccessible(true);
            final Object handle = handleField.get(null);
            if (handle != null)
                return Optional.of((ProxyServer) handle);
        } catch (Exception e) {}
        return Optional.empty();
    }
}
