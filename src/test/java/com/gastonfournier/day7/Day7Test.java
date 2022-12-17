package com.gastonfournier.day7;

import com.gastonfournier.DailyChallengeTest;
import com.gastonfournier.utils.Solution;

class Day7Test extends DailyChallengeTest {

    @Override
    public Solution solution() {
        return new Day7();
    }

    @Override
    public String getStringInput() {
        return """
        $ cd /
        $ ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        $ cd a
        $ ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        $ cd e
        $ ls
        584 i
        $ cd ..
        $ cd ..
        $ cd d
        $ ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
        """;
    }

    @Override
    public Integer expected1() {
        return 95437;
    }

    @Override
    public Integer expected2() {
        return 24933642;
    }
}