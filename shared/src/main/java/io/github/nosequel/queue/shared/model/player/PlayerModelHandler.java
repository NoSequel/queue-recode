package io.github.nosequel.queue.shared.model.player;

import io.github.nosequel.queue.shared.model.ModelHandler;
import io.github.nosequel.storage.storage.StorageProvider;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PlayerModelHandler extends ModelHandler<PlayerModel> {

    private final PlayerProvider playerProvider;

    public PlayerModelHandler(StorageProvider<String, PlayerModel> provider, PlayerProvider playerProvider) {
        super(provider);
        this.playerProvider = playerProvider;
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
        return new PlayerModel(
                this.provider,
                (UUID) data[0],
                (String) data[1]
        );
    }
}
