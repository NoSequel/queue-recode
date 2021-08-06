package io.github.nosequel.queue.shared.model.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class PlayerModel implements Comparable<PlayerModel> {

    private final UUID uniqueId;
    private final String name;

    private int priority;

    @Override
    public int compareTo(PlayerModel o) {
        return this.priority - o.priority;
    }
}
