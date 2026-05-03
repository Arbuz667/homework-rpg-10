package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.council.CouncilRunResult;
import com.narxoz.rpg.guild.*;
import com.narxoz.rpg.quest.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== Homework 10 Demo: Iterator + Mediator ===");

        // ── 1. Heroes ──────────────────────────────────────────────────────
        Hero aria  = new Hero("Aria the Swift",  120, 40, 80, 15, 200);
        Hero borin = new Hero("Borin Ironshield", 200, 10, 25, 30, 150);
        List<Hero> party = List.of(aria, borin);

        // ── 2. Quest log ───────────────────────────────────────────────────
        QuestLog log = new QuestLog();
        log.add(new Quest("Slay the Forest Troll",        QuestPriority.NORMAL, 300,  false));
        log.add(new Quest("Escort the Merchant Caravan",  QuestPriority.LOW,    150,  false));
        log.add(new Quest("Retrieve the Stolen Amulet",   QuestPriority.HIGH,   500,  false));
        log.add(new Quest("Purge the Cursed Ruins",       QuestPriority.URGENT, 900,  true));
        log.add(new Quest("Scout the Northern Pass",      QuestPriority.NORMAL, 200,  false));
        log.add(new Quest("Defeat the Bandit King",       QuestPriority.HIGH,   700,  true));

        // ── 3. Guild hall & members ────────────────────────────────────────
        GuildHall hall = new GuildHall();
        Quartermaster rex    = new Quartermaster("Rex",    hall);
        Scout         mira   = new Scout("Mira",           hall);
        Healer        solana = new Healer("Solana",        hall);
        Captain       draven = new Captain("Draven",       hall);
        Loremaster    calyx  = new Loremaster("Calyx",     hall);   // Part 4

        // ── 4a. Ordered traversal (Iterator 1) ────────────────────────────
        System.out.println("\n--- Quest Log: Arrival Order ---");
        QuestIterator ordered = log.ordered();
        while (ordered.hasNext()) {
            System.out.println("  " + ordered.next());
        }

        // ── 4b. Reverse traversal (Iterator 2) ────────────────────────────
        System.out.println("\n--- Quest Log: Reverse Order ---");
        QuestIterator reverse = log.reverse();
        while (reverse.hasNext()) {
            System.out.println("  " + reverse.next());
        }

        // ── 4c. Priority filter (Iterator 3) ──────────────────────────────
        System.out.println("\n--- Quest Log: HIGH priority and above ---");
        QuestIterator highPlus = log.priorityAtLeast(QuestPriority.HIGH);
        while (highPlus.hasNext()) {
            System.out.println("  " + highPlus.next());
        }

        // ── 4d. Reward-sorted (Iterator 4, Part 4) ────────────────────────
        System.out.println("\n--- Quest Log: By Reward (desc) ---");
        QuestIterator byReward = new RewardSortedQuestIterator(log);
        while (byReward.hasNext()) {
            System.out.println("  " + byReward.next());
        }

        // ── 5. Mediator messages ───────────────────────────────────────────
        System.out.println("\n--- Guild Hall: Coordinating the campaign ---");
        draven.issueOrder("orders",   "All units prepare to march at dawn.");
        mira.reportRoute("scouting",  "Northern pass is clear, one troll camp spotted.");
        rex.requestSupplies("supplies", "Need 20 healing potions and iron rations for 6 days.");
        solana.prepareAid("healing",  "Field kits ready, reserve potions allocated.");
        draven.issueOrder("urgent",   "Cursed Ruins mission is priority one — move now.");
        calyx.shareLore("lore",       "Ancient ward in the ruins weakens undead by 30%.");

        // ── 6. Council engine ──────────────────────────────────────────────
        CouncilEngine engine = new CouncilEngine();
        CouncilRunResult result = engine.runCouncil(party, log, hall);

        // ── 7. Final result ────────────────────────────────────────────────
        System.out.println("\n=== War Council Complete ===");
        System.out.println("  Quests traversed : " + result.getQuestsTraversed());
        System.out.println("  Messages routed  : " + result.getMessagesRouted());
        System.out.println("  Members notified : " + result.getMembersNotified());
        System.out.println(result);
    }
}