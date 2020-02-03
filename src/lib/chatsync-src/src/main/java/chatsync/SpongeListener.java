package chatsync;

import java.util.Optional;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent.Chat;
import org.spongepowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.Player;

public class SpongeListener {

    private final ChatListener listener;

    public SpongeListener(final ChatListener listener) {
        this.listener = listener;
        final Optional<PluginContainer> oSpicord = Sponge.getPluginManager().getPlugin("spicord");

        if (oSpicord.isPresent()) {
            final PluginContainer spicord = oSpicord.get();
            Sponge.getEventManager().registerListeners(spicord.getInstance().get(), this);
        } else {
            System.out.println("Spicord plugin not found, ChatSync will not work");
        }
    }

    @Listener
    public void onChat(Chat event, @First Player sender) {
        listener.handle(sender.getUsername(), event.getRawMessage().toString());
    }
}
