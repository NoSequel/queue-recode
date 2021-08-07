package io.github.nosequel.queue.shared.config;

import io.github.nosequel.config.Configuration;
import io.github.nosequel.config.ConfigurationFile;
import io.github.nosequel.config.annotation.Configurable;
import io.github.nosequel.queue.shared.config.model.ServerModelConfig;
import lombok.SneakyThrows;

public class ServerConfiguration extends Configuration {

    @Configurable(path = "local-server")
    public static ServerModelConfig LOCAL_SERVER = new ServerModelConfig("dev-1");

    @SneakyThrows
    public ServerConfiguration(ConfigurationFile file) {
        super(file);

        this.load();
        this.save();
    }
}
