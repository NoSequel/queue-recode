package io.github.nosequel.queue.shared.model.server;

import io.github.nosequel.queue.shared.model.player.PlayerProvider;
import io.github.nosequel.queue.shared.model.queue.QueueModel;
import io.github.nosequel.storage.storage.StorageProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ServerModel {

    private final String identifier;

    public void setupSubscriber(StorageProvider<String, QueueModel> storageProvider, PlayerProvider playerProvider) {
        if (this.identifier == null) {
            throw new IllegalStateException("Unable to setup subscriber update whilst #identifier is null");
        }

        storageProvider.getStorageHandler().subscribe(this.identifier + "-move", message -> {
            // do movement stuff
        });
    }

}