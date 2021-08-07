package io.github.nosequel.queue.proxy;

import io.github.nosequel.queue.shared.QueueBootstrap;

public class QueueProxyMain extends QueueBootstrap {

    public static void main(String[] args) {
        // queue bootstrapping
        new QueueProxyBootstrap().load();
    }

}
