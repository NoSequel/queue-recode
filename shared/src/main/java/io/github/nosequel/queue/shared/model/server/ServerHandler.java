package io.github.nosequel.queue.shared.model.server;

import io.github.nosequel.queue.shared.model.ModelHandler;
import io.github.nosequel.storage.storage.StorageProvider;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServerHandler extends ModelHandler<ServerModel> {

    private ServerModel localServer;

    public ServerHandler(StorageProvider<String, ServerModel> provider) {
        super(provider);
    }

    /**
     * Create a new model with provided data.
     *
     * @param data the data to use to instantiate the model
     * @return the newly created model
     */
    @Override
    public ServerModel createModel(Object... data) {
        return this.cacheToStorage(new ServerModel(
                this.provider,
                (String) data[0]
        ));
    }
}