package Coding_GO.codingGO.domain.problem.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;

@Getter
@AllArgsConstructor
public enum Tier {
    UNRANKED("Unranked"),
    BRONZE_V("Bronze V"), BRONZE_IV("Bronze IV"), BRONZE_III("Bronze III"), BRONZE_II("Bronze II"), BRONZE_I("Bronze I"),
    SILVER_V("Silver V"), SILVER_IV("Silver IV"), SILVER_III("Silver III"), SILVER_II("Silver II"), SILVER_I("Silver I"),
    GOLD_V("Gold V"), GOLD_IV("Gold IV"), GOLD_III("Gold III"), GOLD_II("Gold II"), GOLD_I("Gold I"),
    PLATINUM_V("Platinum V"), PLATINUM_IV("Platinum IV"), PLATINUM_III("Platinum III"), PLATINUM_II("Platinum II"), PLATINUM_I("Platinum I"),
    DIAMOND_V("Diamond V"), DIAMOND_IV("Diamond IV"), DIAMOND_III("Diamond III"), DIAMOND_II("Diamond II"), DIAMOND_I("Diamond I"),
    RUBY_V("Ruby V"), RUBY_IV("Ruby IV"), RUBY_III("Ruby III"), RUBY_II("Ruby II"), RUBY_I("Ruby I"),
    MASTER("Master");

    private final String description;

    public static Tier fromLevel(int level) {
        if (level < 0 || level >= values().length) return UNRANKED;
        return values()[level];
    }

}
