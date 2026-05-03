package com.narxoz.rpg.guild;

public class Scout extends GuildMember {

    public Scout(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void reportRoute(String topic, String payload) {
        System.out.println("[" + getName() + "] Sending route report — topic: " + topic + " | " + payload);
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        System.out.println("[" + getName() + "] Received on '" + topic
                + "' from " + from.getName() + ": " + payload
                + " → Updating patrol map.");
    }
}