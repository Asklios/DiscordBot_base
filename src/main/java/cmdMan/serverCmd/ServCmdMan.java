package main.java.cmdMan.serverCmd;

import lombok.Getter;
import main.java.cmdMan.serverCmd.commands.ExampleCommandServer;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.ConcurrentHashMap;

@Getter
public class ServCmdMan {

    public ConcurrentHashMap<String, ServerCommand> commands;

    public ServCmdMan() {

        this.commands = new ConcurrentHashMap<>();

        this.commands.put("example", new ExampleCommandServer());
    }

    public boolean perform(String command, Member member, TextChannel channel, Message message) {

        ServerCommand cmd;
        if ((cmd = this.commands.get(command.toLowerCase())) != null) {
            cmd.performCommand(member, channel, message);
            return true;
        }
        return false;
    }
}
