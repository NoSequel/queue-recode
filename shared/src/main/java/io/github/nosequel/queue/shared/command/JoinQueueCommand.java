package io.github.nosequel.queue.shared.command;

import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.executor.CommandExecutor;
import io.github.nosequel.queue.shared.config.MessageConfiguration;
import io.github.nosequel.queue.shared.model.player.PlayerModel;
import io.github.nosequel.queue.shared.model.player.PlayerModelHandler;
import io.github.nosequel.queue.shared.model.queue.QueueHandler;
import io.github.nosequel.queue.shared.model.queue.QueueModel;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ForkJoinPool;

@RequiredArgsConstructor
public class JoinQueueCommand {

    private final PlayerModelHandler playerModelHandler;
    private final QueueHandler queueHandler;

    @Command(label = "join", aliases = {"queue", "joinqueue"}, permission = "queue.join", userOnly = true)
    public void joinQueue(CommandExecutor executor, QueueModel model) {
        ForkJoinPool.commonPool().execute(() -> {
            final PlayerModel playerModel = playerModelHandler
                    .fetchModel(this.playerModelHandler.getPlayerProvider()
                            .getUniqueId(executor).toString());

            model.addEntry(playerModel);
            executor.sendMessage(MessageConfiguration.QUEUE_JOIN
                    .replace("%queue_name%", model.getIdentifier())
            );
        });
    }
}