package io.github.nosequel.storage;

import io.github.nosequel.storage.serialization.Serializer;
import io.github.nosequel.storage.serialization.impl.GsonSerializer;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class StorageHandler {

    private final Map<Class<?>, Serializer<?>> serializerMap = new HashMap<>();

    /**
     * Get a {@link Serializer} object by the provided {@link Class} object.
     * <p>
     * By default returns a new {@link GsonSerializer}
     * if no other serializer could be found.
     * </p>
     *
     * @param clazz the class to get the serializer from
     * @param <T>   the type of the serializer
     * @return the serializer
     */
    @SuppressWarnings("unchecked")
    public <T> Serializer<T> getSerializer(Class<T> clazz) {
        return (Serializer<T>) this.serializerMap.getOrDefault(clazz, new GsonSerializer<>(clazz));
    }
}
