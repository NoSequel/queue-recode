package io.github.nosequel.queue.shared.model;

import io.github.nosequel.storage.storage.StorageProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class Model<T> {

    protected transient final StorageProvider<String, T> storageProvider;
    protected final String identifier;

    /**
     * Update the queue to the {@link StorageProvider}.
     *
     * @param provider the provider to update it to
     */
    public void updateToStorage(StorageProvider<String, T> provider) {
        provider.setEntry(this.identifier, (T) this);
    }
}