package io.github.nosequel.queue.spigot.logger;

import io.github.nosequel.queue.shared.logger.QueueLogger;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class SpigotQueueLogger extends QueueLogger {
    public SpigotQueueLogger(boolean debug, boolean warnings) {
        super(debug, warnings);
    }

    @Override
    public void log(String message) {
        Bukkit.getLogger().log(Level.INFO, message);
    }

    @Override
    public void warn(String message) {
        if (this.warnings) {
            Bukkit.getLogger().log(Level.WARNING, message);
        }
    }

    @Override
    public void debug(String message) {
        if (this.debug) {
            Bukkit.getLogger().log(Level.ALL, "[DEBUG] " + message);
        }
    }
}
