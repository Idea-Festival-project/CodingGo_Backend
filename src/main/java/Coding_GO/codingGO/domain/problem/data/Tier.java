package Coding_GO.codingGO.domain.problem.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;

@Getter
@AllArgsConstructor
public enum Tier {
    UNRATED(0,"Unrated"),
    BRONZE(1,"Bronze"),
    SILVER(6,"Silver"),
    GOLD(11,"Gold"),
    PLATINUM(16,"Platinum"),
    DIAMOND(21,"Diamond"),
    RUBY(26, "Ruby");

    private final int startLevel;
    private final String name;

    public static String getDescription(int level){
        if (level ==0) return UNRATED.name;
        Tier tier = Arrays.stream(values()).filter(t -> level >= t.startLevel)
                .max(Comparator.comparingInt(Tier::getStartLevel)).orElse(UNRATED);
        int detail = 5 - ((level - tier.startLevel) % 5);
        return tier.name + " " + detail;
    }
}
