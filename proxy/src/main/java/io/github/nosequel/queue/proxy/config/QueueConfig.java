package io.github.nosequel.queue.proxy.config;

import io.github.nosequel.config.Configuration;
import io.github.nosequel.config.ConfigurationFile;
import io.github.nosequel.config.annotation.Configurable;
import io.github.nosequel.queue.proxy.config.model.QueueModelConfig;
import lombok.SneakyThrows;

public class QueueConfig extends Configuration {

    @Configurable(path = "queues")
    public static QueueModelConfig[] MODELS = new QueueModelConfig[]{
            new QueueModelConfig("hcteams-eu", "dev-1")
    };

    @SneakyThrows
    public QueueConfig(ConfigurationFile file) {
        super(file);

        this.load();
        this.save();
    }
}
