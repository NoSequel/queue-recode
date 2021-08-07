package io.github.nosequel.queue.shared.model.player;

import io.github.nosequel.queue.shared.model.ModelHandler;
import io.github.nosequel.storage.storage.StorageProvider;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerModelHandler extends ModelHandler<PlayerModel> {

    private PlayerProvider playerProvider;

    public PlayerModelHandler(StorageProvider<String, PlayerModel> provider) {
        super(provider);
    }

    /**
     * Create a new player model object
     *
     * @param uniqueId the unique identifier of the player
     * @param name     the name of the player
     * @return the newly created model
     */
    public PlayerModel createModel(UUID uniqueId, String name) {
        return this.createModel(new Object[] {
                uniqueId,
                name
        });
    }

    /**
     * Create a new model with provided data.
     *
     * @param data the data to use to instantiate the model
     * @return the newly created model
     */
    @Override
    public PlayerModel createModel(Object... data) {
        return this.cacheToStorage(new PlayerModel(
                this.provider,
                (UUID) data[0],
                (String) data[1]
        ));
    }
}
