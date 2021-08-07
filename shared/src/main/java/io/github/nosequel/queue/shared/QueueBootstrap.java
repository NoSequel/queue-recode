package io.github.nosequel.queue.shared;

import io.github.nosequel.config.json.JsonConfigurationFile;
import io.github.nosequel.queue.shared.config.ServerConfiguration;

import java.io.File;

public abstract class QueueBootstrap {

    public void load() {
        new ServerConfiguration(new JsonConfigurationFile(new File("servers.yml")));
    }
}