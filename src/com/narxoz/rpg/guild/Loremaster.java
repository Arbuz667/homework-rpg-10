package com.narxoz.rpg.guild;

public class Loremaster extends GuildMember {

    public Loremaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void shareLore(String topic, String payload) {
        System.out.println("[" + getName() + "] Sharing lore — topic: " + topic + " | " + payload);
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        System.out.println("[" + getName() + "] Received on '" + topic
                + "' from " + from.getName() + ": " + payload
                + " → Recording in the Grand Archive.");
    }
}