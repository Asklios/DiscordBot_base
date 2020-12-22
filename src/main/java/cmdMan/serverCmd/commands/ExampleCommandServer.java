package main.java.cmdMan.serverCmd.commands;

import main.java.cmdMan.serverCmd.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class ExampleCommandServer implements ServerCommand {

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {
        channel.sendMessage("example").queue();
    }
}
