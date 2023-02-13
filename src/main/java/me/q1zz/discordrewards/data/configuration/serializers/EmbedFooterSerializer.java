package me.q1zz.discordrewards.data.configuration.serializers;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class EmbedFooterSerializer implements ObjectSerializer<MessageEmbed.Footer> {

    @Override
    public boolean supports(@NonNull Class<? super MessageEmbed.Footer> type) {
        return MessageEmbed.Footer.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(MessageEmbed.@NonNull Footer footer, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {

        if(footer.getText() != null) {
            data.add("text", footer.getText());
        }

        if(footer.getIconUrl() != null) {
            data.add("icon", footer.getIconUrl());
        }

    }

    @Override
    public MessageEmbed.Footer deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {

        String text = data.get("text", String.class);
        String icon = data.get("icon", String.class);


        return new EmbedBuilder().setFooter(text, icon).build().getFooter();
    }
}
