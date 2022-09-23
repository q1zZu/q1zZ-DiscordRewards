package me.q1zz.discordrewards.data.configuration.serializers;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class EmbedMessageSerializer implements ObjectSerializer<MessageEmbed> {

    @Override
    public boolean supports(@NonNull Class<? super MessageEmbed> type) {
        return MessageEmbed.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull MessageEmbed embedMessage, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {

        if(embedMessage.isEmpty()) {
            return;
        }

        data.add("title", embedMessage.getTitle());
        data.add("description", embedMessage.getDescription());

        if(embedMessage.getColor() != null) {
            data.add("color", "#" + Integer.toHexString(embedMessage.getColor().getRGB()).substring(2));
        }

        if(embedMessage.getThumbnail() != null) {
            data.add("thumbnail_url", embedMessage.getThumbnail().getUrl());
        }

        if(embedMessage.getImage() != null) {
            data.add("image_url", embedMessage.getImage().getUrl());
        }

    }

    @Override
    public MessageEmbed deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {

        String title = data.get("title", String.class);
        String description = data.get("description", String.class);

        String color = data.containsKey("color") ? data.get("color", String.class) : "#000000";
        String thumbnail_url = data.containsKey("thumbnail_url") ? data.get("thumbnail_url", String.class) : null;
        String image_url = data.containsKey("image_url") ? data.get("image_url", String.class) : null;

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(title);
        embedBuilder.setDescription(description);
        embedBuilder.setColor(Color.decode(color));

        if(thumbnail_url != null && thumbnail_url.length() > 0) embedBuilder.setThumbnail(thumbnail_url);
        if(image_url != null && image_url.length() > 0) embedBuilder.setImage(image_url);

        return embedBuilder.build();
    }
}
