package io.github.nosequel.queue.shared.model;

import io.github.nosequel.storage.storage.StorageProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public abstract class ModelHandler<T extends Model<?>> {

    protected final StorageProvider<String, T> provider;

    /**
     * Create a new model with provided data.
     *
     * @param data the data to use to instantiate the model
     * @return the newly created model
     */
    public abstract T createModel(Object... data);

    /**
     * Cache a provided object
     *
     * @param object the object to cache
     * @return the cached object
     */
    public T cacheToStorage(T object) {
        this.provider.setEntry(object.getIdentifier(), object);
        return object;
    }

    /**
     * Fetch all {@link Model} objects from the {@link StorageProvider}
     *
     * @return the fetched models
     */
    public Set<T> fetchModels() {
        return new HashSet<>(this.provider.fetchAllEntries().join().values());
    }

    /**
     * Fetch a {@link Model} object from the {@link StorageProvider}
     *
     * @param key the key to get the model by
     * @return the found model, or null.
     */
    public T fetchModel(String key) {
        return this.provider.fetchEntry(key).join();
    }

}
