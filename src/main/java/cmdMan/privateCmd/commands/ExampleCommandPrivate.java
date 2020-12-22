package main.java.cmdMan.privateCmd.commands;

import main.java.cmdMan.privateCmd.PrivateCommand;
import net.dv8tion.jda.api.entities.*;

public class ExampleCommandPrivate implements PrivateCommand {

    @Override
    public void performCommand(User user, PrivateChannel channel, Message message) {
        channel.sendMessage("example").queue();
    }
}
