package io.github.nosequel.queue.proxy;

import java.io.File;

public class QueueProxyMain {


    public static void main(String[] args) {
        // queue bootstrapping
        new QueueProxyBootstrap(new File("./configurations/")).load();
    }

}
