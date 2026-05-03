package com.narxoz.rpg.guild;

public class Healer extends GuildMember {

    public Healer(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void prepareAid(String topic, String payload) {
        System.out.println("[" + getName() + "] Preparing aid — topic: " + topic + " | " + payload);
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        System.out.println("[" + getName() + "] Received on '" + topic
                + "' from " + from.getName() + ": " + payload
                + " → Stocking potions for the mission.");
    }
}