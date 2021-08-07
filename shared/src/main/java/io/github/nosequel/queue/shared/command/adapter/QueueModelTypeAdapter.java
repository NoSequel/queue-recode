package io.github.nosequel.queue.shared.command.adapter;

import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.executor.CommandExecutor;
import io.github.nosequel.queue.shared.model.queue.QueueHandler;
import io.github.nosequel.queue.shared.model.queue.QueueModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QueueModelTypeAdapter implements TypeAdapter<QueueModel> {

    private final QueueHandler queueHandler;

    @Override
    public QueueModel convert(CommandExecutor commandExecutor, String s) throws Exception {
        return this.queueHandler.fetchModel(s);
    }
}
