package com.gastonfournier.day6;

import com.gastonfournier.DailyChallengeTest;
import com.gastonfournier.utils.Solution;
import org.assertj.core.util.Lists;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day6Test extends DailyChallengeTest {

    @Override
    public Solution solution() {
        return new Day6();
    }

    @Override
    public List<String> getInput() {
        return Arrays.stream("""
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
                7214296 k""".split("\n")).toList();
    }

    @Override
    public Integer expected1() {
        return 95437;
    }

    @Override
    public Integer expected2() {
        return 1;
    }
}