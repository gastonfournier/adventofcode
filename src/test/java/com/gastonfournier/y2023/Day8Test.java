package com.gastonfournier.y2023;


import com.gastonfournier.utils.Reader;
import com.gastonfournier.y2023.day7.CardLabel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.gastonfournier.y2023.day7.CardLabel.*;
import static org.assertj.core.api.Assertions.assertThat;

public class Day8Test extends DailyTest<Integer, Long> {
    public Day8Test() {
        super(new Day8(), List.of(
              """
              RL
                            
              AAA = (BBB, CCC)
              BBB = (DDD, EEE)
              CCC = (ZZZ, GGG)
              DDD = (DDD, DDD)
              EEE = (EEE, EEE)
              GGG = (GGG, GGG)
              ZZZ = (ZZZ, ZZZ)
              """,
              Reader.readFileAsString("y2023/day8.input.txt"),
              """
              LR
                            
              11A = (11B, XXX)
              11B = (XXX, 11Z)
              11Z = (11B, XXX)
              22A = (22B, XXX)
              22B = (22C, 22C)
              22C = (22Z, 22Z)
              22Z = (22B, 22B)
              XXX = (XXX, XXX)
              """,
              Reader.readFileAsString("y2023/day8.input.txt"),
                """
                LLR
                
                AAA = (BBB, BBB)
                BBB = (AAA, ZZZ)
                ZZZ = (ZZZ, ZZZ)
                """
        ), List.of(2, 17141, 6L, 10818234074807L, 6L));
    }
}
