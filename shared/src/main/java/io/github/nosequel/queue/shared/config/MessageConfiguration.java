package io.github.nosequel.queue.shared.config;

import io.github.nosequel.config.Configuration;
import io.github.nosequel.config.ConfigurationFile;
import io.github.nosequel.config.annotation.Configurable;
import lombok.SneakyThrows;

public class MessageConfiguration extends Configuration {

    @Configurable(path = "queue.join")
    public static String QUEUE_JOIN = "&aYou have been added to the %queue_name% queue!";

    @Configurable(path = "queue.connecting")
    public static String QUEUE_CONNECTING = "&6Sending you to %server_name%...";

    @SneakyThrows
    public MessageConfiguration(ConfigurationFile file) {
        super(file);

        this.load();
        this.save();
    }
}
