package com.gastonfournier.day10;

import com.gastonfournier.utils.DailyChallenge;
import com.gastonfournier.utils.Point;
import com.gastonfournier.utils.Solution;

import java.util.*;

public class Day10 implements Solution<Integer, String> {

    public static void main(String[] args) {
        DailyChallenge challenge = new DailyChallenge(new Day10(), "day10.input.txt");
        challenge.run();
    }

    @Override
    public Integer part1(List<String> input) {
        int signalStrength = 0;
        Iterator<String> it = input.listIterator();
        CPU cpu = new CPU();
        while (it.hasNext()) {
            int currentCycle;
            int currentX = cpu.registry;
            if (cpu.isIdle()) {
                currentCycle = cpu.exec(it.next());
            } else {
                currentCycle = cpu.cycle();
            }
            if (currentCycle == 20 || (currentCycle - 20) % 40 == 0) {
                int strength = currentCycle * currentX;
                System.out.println("=== " + currentCycle + " strength " + strength);
                signalStrength += strength;
            }
        }
        return signalStrength;
    }

    @Override
    public String part2(List<String> input) {
        Iterator<String> it = input.listIterator();
        int CRTWide = 40;
        int CRTHigh = 6;
        StringBuilder stringBuilder = new StringBuilder();
        CPU cpu = new CPU();

        while (it.hasNext()) {
            int currentCycle;
            int spritePos = cpu.registry;
            if (cpu.isIdle()) {
                currentCycle = cpu.exec(it.next());
            } else {
                currentCycle = cpu.cycle();
            }

            int xPos = (currentCycle - 1) % CRTWide;
            if (xPos >= spritePos - 1 && xPos <= spritePos + 1) {
                stringBuilder.append("#");
            } else {
                stringBuilder.append(".");
            }
            if (currentCycle % CRTWide == 0) {
                stringBuilder.append("\n");
            }
        }

        String result = stringBuilder.toString();
        System.out.println(result);
        return result.trim();
    }

    private class CPU {
        private Integer cycle = 0;
        private Integer registry = 1;

        private CPUState state = CPUState.idle();

        /**
         * Consumes one cycle executing the operation saving the state if the operation requires more cycles
         */
        public int exec(String op) {
            System.out.println(this.cycle + ": " + op + "\t\t X="+this.registry);
            if ("noop".equals(op)) {
                this.state = CPUState.idle();
                return ++cycle;
            } else if (op.startsWith("addx")) {
                int n = Integer.parseInt(op.replace("addx ", ""));
                this.state = CPUState.addx(n);
                return ++cycle;
            }
            throw new RuntimeException("Unknown op: "+op);
        }

        public boolean isIdle() {
            return this.state.isIdle();
        }

        public int cycle() {
            if (state.adding() != null) {
                this.registry = this.registry + state.adding();
                this.state = CPUState.idle();
                return ++cycle;
            }
            throw new RuntimeException("Invalid state!");
        }

        private static class CPUState {
            private static final int IDLE = 1;
            private static final int ADDING = 2;

            private Integer state;
            private Integer addingNumber;

            public CPUState(int state) {
                this(state, null);
            }

            public CPUState(int state, Integer addingNumber) {
                this.state = state;
                this.addingNumber = addingNumber;
            }

            public static CPUState addx(int n) {
                return new CPUState(ADDING, n);
            }

            public static CPUState idle() {
                return new CPUState(IDLE);
            }

            public boolean isIdle() {
                return this.state == IDLE;
            }

            public Integer adding() {
                return addingNumber;
            }
        }
    }
}
