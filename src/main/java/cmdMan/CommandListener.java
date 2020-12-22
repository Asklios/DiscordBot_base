package main.java.cmdMan;

import main.java.DiscordBot;
import main.java.PropertiesReader;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class CommandListener extends ListenerAdapter {

    private final String prefix = new PropertiesReader().getBotPrefix();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String messageString = event.getMessage().getContentDisplay(); //formatted message
        String[] messageSplit = messageString.substring(1).split("\\s+");

        if (event.isFromType(ChannelType.PRIVATE)) {
            PrivateChannel channel = event.getPrivateChannel();
            Message message = event.getMessage();
            User user = event.getAuthor();

            if (messageString.startsWith(prefix) && messageSplit.length > 0) {
                if (!DiscordBot.INSTANCE.getPrivCmdMan().perform(messageSplit[0], user, channel, message)) {
                    channel.sendMessage("unknown command").queue();
                }
            }
        }

        if (event.isFromType(ChannelType.TEXT)) {
            TextChannel channel = event.getTextChannel();

            if (messageString.startsWith(prefix) && messageSplit.length > 0) {
                if (!DiscordBot.INSTANCE.getServCmdMan().perform(messageSplit[0], event.getMember(), channel, event.getMessage())) {
                    channel.sendMessage("unknown command").queue(m -> m.delete().queueAfter(5, TimeUnit.SECONDS));
                }
            }
        }
    }
}
