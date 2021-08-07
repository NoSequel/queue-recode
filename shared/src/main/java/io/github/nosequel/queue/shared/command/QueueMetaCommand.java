package io.github.nosequel.queue.shared.command;

import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.executor.CommandExecutor;
import io.github.nosequel.queue.shared.model.player.PlayerModel;
import io.github.nosequel.queue.shared.model.player.PlayerModelHandler;
import io.github.nosequel.queue.shared.model.queue.QueueHandler;
import io.github.nosequel.queue.shared.model.queue.QueueModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QueueMetaCommand {

    private final PlayerModelHandler playerHandler;
    private final QueueHandler queueHandler;

    @Command(label = "queuemeta", aliases = {"meta", "qmeta"}, permission = "queue.meta", userOnly = false)
    public void meta(CommandExecutor executor) {
        final PlayerModel model = playerHandler.fetchModel(playerHandler.getPlayerProvider().getUniqueId(executor).toString());

        for (QueueModel queue : queueHandler.getQueues(model.getUniqueId())) {
            executor.sendMessage(queue.getIdentifier() + " - " + queue.getPosition(model));
        }
    }
}