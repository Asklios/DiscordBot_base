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
        int messageSplitLength = 0;
        String messageSplitZero = "";

        try {
            String[] messageSplit = messageString.substring(1).split("\\s+");
            messageSplitLength = messageSplit.length;
            messageSplitZero = messageSplit[0];
        } catch (StringIndexOutOfBoundsException e) {
            // join message
        }

        if (event.isFromType(ChannelType.PRIVATE)) {
            PrivateChannel channel = event.getPrivateChannel();
            Message message = event.getMessage();
            User user = event.getAuthor();

            if (messageString.startsWith(prefix) && messageSplitLength > 0) {
                if (!DiscordBot.INSTANCE.getPrivCmdMan().perform(messageSplitZero, user, channel, message)) {
                    channel.sendMessage("unknown command").queue();
                }
            }
        }

        if (event.isFromType(ChannelType.TEXT)) {
            TextChannel channel = event.getTextChannel();

            if (messageString.startsWith(prefix) && messageSplitLength > 0) {
                if (!DiscordBot.INSTANCE.getServCmdMan().perform(messageSplitZero, event.getMember(), channel, event.getMessage())) {
                    channel.sendMessage("unknown command").queue(m -> m.delete().queueAfter(5, TimeUnit.SECONDS));
                }
            }
        }
    }
}
