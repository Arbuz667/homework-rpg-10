package com.narxoz.rpg.guild;

public class Captain extends GuildMember {

    public Captain(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void issueOrder(String topic, String payload) {
        System.out.println("[" + getName() + "] Issuing order — topic: " + topic + " | " + payload);
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        System.out.println("[" + getName() + "] Received on '" + topic
                + "' from " + from.getName() + ": " + payload
                + " → Updating campaign plan.");
    }
}