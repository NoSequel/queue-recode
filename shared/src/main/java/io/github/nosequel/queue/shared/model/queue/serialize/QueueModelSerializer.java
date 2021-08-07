package io.github.nosequel.queue.shared.model.queue.serialize;

import com.google.gson.*;
import io.github.nosequel.queue.shared.model.player.PlayerModel;
import io.github.nosequel.queue.shared.model.queue.QueueModel;
import io.github.nosequel.queue.shared.model.server.ServerHandler;
import io.github.nosequel.storage.serialization.Serializer;
import io.github.nosequel.storage.storage.StorageProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class QueueModelSerializer implements Serializer<QueueModel> {

    private final Gson gson = new Gson();
    private final JsonParser parser = new JsonParser();

    private ServerHandler serverHandler;
    private StorageProvider<String, QueueModel> storageProvider;

    /**
     * Deserialize a {@link QueueModel} object to a {@link String}
     *
     * @param source the string to deserialize
     * @return the object
     */
    @Override
    public QueueModel deserialize(String source) {
        final JsonObject object = parser.parse(source).getAsJsonObject();

        final String identifier = object.get("identifier").getAsString();
        final String serverIdentifier = object.get("server").getAsString();

        final QueueModel model = new QueueModel(storageProvider, identifier);

        model.setTargetServer(serverHandler.fetchModel(serverIdentifier));

        for (JsonElement entry : object.get("entries").getAsJsonArray()) {
            model.getEntries().add(this.gson.fromJson(entry, PlayerModel.class));
        }

        return model;
    }

    /**
     * Serialize a {@link QueueModel} object to a {@link String}
     *
     * @param model the object to serialize
     * @return the serialized object
     */
    @Override
    public String serialize(QueueModel model) {
        final JsonObject object = new JsonObject();

        final JsonArray metadatum = new JsonArray();
        final JsonArray entries = new JsonArray();

        object.addProperty("identifier", model.getIdentifier());

        if (model.getTargetServer() != null) {
            object.addProperty("server", model.getTargetServer().getIdentifier());
        } else {
            object.addProperty("server", "null");
        }

        for (PlayerModel entry : model.getEntries()) {
           entries.add(this.parser.parse(this.gson.toJson(entry)));
        }

        object.add("entries", entries);
        object.add("metadatum", metadatum);

        return object.toString();
    }
}
