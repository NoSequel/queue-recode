package io.github.nosequel.queue.shared.logger;

import lombok.Getter;

public abstract class QueueLogger {

    @Getter
    private static QueueLogger instance;

    private final boolean debug;
    private final boolean warnings;

    public QueueLogger(boolean debug, boolean warnings) {
        instance = this;

        this.debug = debug;
        this.warnings = warnings;
    }

    public abstract void log(String message);

    public void warn(String message) {
        if (this.warnings) {
            this.log("[WARN] " + message);
        }
    }

    public void debug(String message) {
        if (this.debug) {
            this.log("[DEBUG] " + message);
        }
    }
}
