package main.java;

import lombok.Getter;

import java.io.*;
import java.util.Properties;

@Getter
public class PropertiesReader {

    private String botToken = "yourBotToken";
    private String[] defIds;
    private String defIdsString = "id1,id2,id3,...";
    private String botPrefix = "%";

    public PropertiesReader(){
        Properties props = new Properties();
        try (InputStream inStream = new FileInputStream("config.properties")) {
            props.load(inStream);
            try (OutputStream output = new FileOutputStream("config.properties")) {

                if (props.containsKey("botToken"))
                    botToken = props.getProperty("botToken");
                else
                    props.setProperty("botToken", botToken);

                if (props.containsKey("defIds"))
                    defIdsString = props.getProperty("defIds");
                else
                    props.setProperty("defIds", defIdsString);

                if (props.containsKey("botPrefix"))
                    botPrefix = props.getProperty("botPrefix");
                else
                    props.setProperty("botPrefix", botPrefix);

                props.store(output, null);

            } catch (IOException io) {
                io.printStackTrace();
            }

        } catch (IOException e) {
            createNewPropertiesFile();
        }
    }

    private void createNewPropertiesFile() {
        try (OutputStream output = new FileOutputStream("config.properties")) {
            Properties props = new Properties();
            props.setProperty("botToken", botToken);
            props.setProperty("defIds", defIdsString);
            props.setProperty("botPrefix", botPrefix);
            props.store(output, null);
            System.out.println("config.properties created");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
