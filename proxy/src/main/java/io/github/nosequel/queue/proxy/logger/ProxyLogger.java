package io.github.nosequel.queue.proxy.logger;

import io.github.nosequel.queue.shared.logger.QueueLogger;

public class ProxyLogger extends QueueLogger {

    public ProxyLogger(boolean debug, boolean warnings) {
        super(debug, warnings);
    }

    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
