package io.github.nosequel.queue.shared.config.model;

import io.github.nosequel.queue.shared.model.server.ServerModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ServerModelConfig {

    private final String identifier;

    public ServerModel createModel() {
        return new ServerModel(identifier);
    }

}
