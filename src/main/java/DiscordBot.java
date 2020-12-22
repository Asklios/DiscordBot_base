package main.java;

import lombok.Getter;
import main.java.cmdMan.CommandListener;
import main.java.cmdMan.privateCmd.PrivCmdMan;
import main.java.cmdMan.serverCmd.ServCmdMan;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletionException;

public class DiscordBot {

    public static DiscordBot INSTANCE;
    public JDA jda;

    @Getter
    private ServCmdMan servCmdMan;
    @Getter
    private PrivCmdMan privCmdMan;

    public static void main(String[] args) {
        try {
            new DiscordBot();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public DiscordBot() throws IllegalArgumentException {
        INSTANCE = this;

        final String botToken = new PropertiesReader().getBotToken();

        JDABuilder builder = JDABuilder.createDefault(botToken);

        builder.addEventListeners(new CommandListener());

        try {
            jda = builder.build();
        } catch (LoginException | CompletionException e) {
            System.err.println("Could not start bot. Check the Bot-Token in config.properties");
            return;
        }
        System.out.println("Bot Status: online");

        jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.playing("hello world"));

        this.servCmdMan = new ServCmdMan();
        this.privCmdMan = new PrivCmdMan();

        shutdown();
    }

    public void shutdown() {
        new Thread(() -> {

            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while ((line = reader.readLine()) != null) {

                    if (line.equalsIgnoreCase("exit")) {

                        jda.shutdown();

                        System.exit(0);
                    } else {
                        System.out.println("Use 'exit' to shutdown.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void shutdownCode() {
        if (jda != null) {
            jda.shutdown();
            System.out.println("Bot Status: offline");
        }
    }
}
