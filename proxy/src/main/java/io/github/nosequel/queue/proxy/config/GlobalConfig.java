package io.github.nosequel.queue.proxy.config;

import io.github.nosequel.config.Configuration;
import io.github.nosequel.config.ConfigurationFile;
import io.github.nosequel.config.annotation.Configurable;
import lombok.SneakyThrows;

public class GlobalConfig extends Configuration {

    @Configurable(path = "logging.warnings")
    public static Boolean LOG_WARNINGS = true;

    @Configurable(path = "logging.debug")
    public static Boolean LOG_DEBUG = false;

    @SneakyThrows
    public GlobalConfig(ConfigurationFile file) {
        super(file);

        this.load();
        this.save();
    }
}
