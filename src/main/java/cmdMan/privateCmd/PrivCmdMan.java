package main.java.cmdMan.privateCmd;

import main.java.cmdMan.privateCmd.commands.ExampleCommandPrivate;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.concurrent.ConcurrentHashMap;

public class PrivCmdMan {
    public ConcurrentHashMap<String, PrivateCommand> commands;

    public PrivCmdMan() {

        this.commands = new ConcurrentHashMap<>();

        this.commands.put("example", new ExampleCommandPrivate());
    }

    public boolean perform(String command, User user, PrivateChannel channel, Message message) {

        PrivateCommand cmd;
        if ((cmd = this.commands.get(command.toLowerCase())) != null) {
            cmd.performCommand(user, channel, message);
            return true;
        }
        return false;
    }
}
