package io.github.nosequel.queue.client.logger;

import io.github.nosequel.queue.shared.logger.QueueLogger;

public class TestClientLogger extends QueueLogger {


    public TestClientLogger(boolean debug, boolean warnings) {
        super(debug, warnings);
    }

    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
