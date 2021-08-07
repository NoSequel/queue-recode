package io.github.nosequel.queue.proxy;

import io.github.nosequel.queue.shared.QueueBootstrap;

import java.io.File;

public class QueueProxyMain {


    public static void main(String[] args) {
        // queue bootstrapping
        new QueueProxyBootstrap(new File("./")).load();
    }

}
