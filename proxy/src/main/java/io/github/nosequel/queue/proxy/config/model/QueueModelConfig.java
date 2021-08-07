package io.github.nosequel.queue.proxy.config.model;

import io.github.nosequel.queue.shared.model.queue.QueueModel;
import io.github.nosequel.queue.shared.model.server.ServerHandler;
import io.github.nosequel.queue.shared.model.server.ServerModel;
import io.github.nosequel.storage.storage.StorageProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QueueModelConfig {

    private final String identifier;
    private final String serverId;

    public QueueModel createModel(StorageProvider<String, QueueModel> provider, ServerHandler serverHandler) {
        final QueueModel queueModel = new QueueModel(provider, identifier);
        final ServerModel serverModel = serverHandler.fetchModel(this.serverId);

        if (serverModel == null) {
            queueModel.setTargetServer(serverHandler.createModel(this.serverId));
        } else {
            queueModel.setTargetServer(serverModel);
        }

        return queueModel;
    }
}