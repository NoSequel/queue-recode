package io.github.nosequel.queue.shared.model;

import io.github.nosequel.storage.storage.StorageProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class Model<K, V> {

    protected transient final StorageProvider<K, V> storageProvider;

}
