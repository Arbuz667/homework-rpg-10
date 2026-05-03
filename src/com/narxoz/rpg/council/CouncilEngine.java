package com.narxoz.rpg.council;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.Captain;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.GuildMediator;
import com.narxoz.rpg.guild.Healer;
import com.narxoz.rpg.guild.Scout;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;

import java.util.List;

public class CouncilEngine {

    public CouncilRunResult runCouncil(List<Hero> party, QuestLog questLog, GuildMediator hall) {

        System.out.println("\n--- CouncilEngine: Party briefing ---");
        for (Hero h : party) {
            System.out.println("  Hero: " + h.getName()
                    + " | HP: " + h.getHp()
                    + " | ATK: " + h.getAttackPower()
                    + " | DEF: " + h.getDefense());
        }

        // ── Iterator 1: ordered traversal ──────────────────────────────────
        System.out.println("\n--- CouncilEngine: Quest log review (arrival order) ---");
        QuestIterator ordered = questLog.ordered();
        int questsTraversed = 0;
        while (ordered.hasNext()) {
            Quest q = ordered.next();
            questsTraversed++;
            System.out.println("  [" + questsTraversed + "] " + q);

            // dispatch a planning message per quest
            hall.dispatch("orders", null, "Plan quest: " + q.getTitle());
        }

        // ── Iterator 2: HIGH+ priority filter ──────────────────────────────
        System.out.println("\n--- CouncilEngine: Urgent quest review (HIGH+) ---");
        QuestIterator urgent = questLog.priorityAtLeast(QuestPriority.HIGH);
        int urgentCount = 0;
        while (urgent.hasNext()) {
            Quest q = urgent.next();
            urgentCount++;
            System.out.println("  [URGENT] " + q.getTitle());
            hall.dispatch("urgent", null, "Emergency prep for: " + q.getTitle());
        }

        int messagesRouted = questsTraversed + urgentCount;

        int membersNotified = 0;
        if (hall instanceof GuildHall gh) {
            membersNotified = gh.getTotalNotifications();
        }

        return new CouncilRunResult(questsTraversed, messagesRouted, membersNotified);
    }
}