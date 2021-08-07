package io.github.nosequel.queue.shared.model.player;

import io.github.nosequel.queue.shared.model.Model;
import io.github.nosequel.storage.storage.StorageProvider;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerModel extends Model<String, PlayerModel> implements Comparable<PlayerModel> {

    private final UUID uniqueId;
    private final String name;

    private int priority;

    public PlayerModel(StorageProvider<String, PlayerModel> storageProvider, UUID uniqueId, String name) {
        super(storageProvider);
        this.uniqueId = uniqueId;
        this.name = name;
    }

    @Override
    public int compareTo(PlayerModel o) {
        return this.priority - o.priority;
    }
}
