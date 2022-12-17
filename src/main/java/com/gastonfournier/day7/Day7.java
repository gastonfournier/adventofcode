package com.gastonfournier.day7;

import com.gastonfournier.utils.DailyChallenge;
import com.gastonfournier.utils.Solution;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day7 implements Solution<Integer, Integer> {
    private Pattern cdCmd = Pattern.compile("\\$\s+cd\s+(.*)$");

    public static void main(String[] args) {
        DailyChallenge challenge = new DailyChallenge(new Day7(), "day7.input.txt");
        challenge.run();
    }

    @Override
    public Integer part1(List<String> input) {
        Directory root = this.parse(input);
        return dirs(root.ls()).stream()
                .map(Directory::size)
                .filter(s -> s <= 100000)
                .reduce(Integer::sum).orElse(0);
    }

    private List<Directory> dirs(Collection<FSItem> items) {
        List<Directory> allDirs = new ArrayList<>();
        for (FSItem item : items) {
            if (item.isDir()) {
                Directory directory = (Directory) item;
                allDirs.add(directory);
                allDirs.addAll(dirs(directory.ls()));
            }
        }
        return allDirs;
    }

    private Directory parse(List<String> input) {
        final Directory root = new Directory("/", null);
        Directory current = root;
        for (String line : input) {
            if (line.startsWith("$")) {
                Matcher cmdMatcher = cdCmd.matcher(line);
                if (cmdMatcher.find()) {
                    String dirname = cmdMatcher.group(1);
                    if (dirname.equals("/")) {
                        current = root;
                    } else {
                        current = current.cd(dirname);
                    }
                }
            } else {
                // assume directory listing
                String[] parts = line.split(" ");
                if (parts[0].equals("dir")) {
                    current.add(new Directory(parts[1], current));
                } else {
                    int size = Integer.parseInt(parts[0]);
                    current.add(new File(parts[1], size));
                }
            }
        }
        System.out.println(root.print(0));
        return root;
    }

    @Override
    public Integer part2(List<String> input) {
        int total = 70000000;
        int required = 30000000;

        Directory root = this.parse(input);
        int threshold = required - (total - root.size());
        // all dirs including root
        int minSize = root.size(); // to avoid making the calculation twice
        for (Directory dir: dirs(Collections.singleton(root))) {
            int currentSize = dir.size();
            if (currentSize < minSize && currentSize >= threshold) {
                minSize = currentSize;
            }
        }
        return minSize;
    }

    private class Directory extends FSItem {

        private final Map<String, FSItem> content = new HashMap<>();
        private Directory parent;

        public Directory(String name, Directory parent) {
            super(name);
            this.parent = parent;
        }

        @Override
        int size() {
            return content.values().stream().map(FSItem::size).reduce(Integer::sum).orElse(0);
        }

        public FSItem add(FSItem item) {
            return content.putIfAbsent(item.name, item);
        }

        public Directory cd(String dir) {
            if (dir.equals("..")) {
                return parent;
            }
            Directory directory = new Directory(dir, this);
            return (Directory) this.add(directory);
        }

        public Collection<FSItem> ls() {
            return content.values();
        }

        public boolean isDir() {
            return true;
        }

        @Override
        public String print(int pad) {
            return "\t".repeat(pad) + this.name + " (dir, size=" + this.size() + ")\n" + content.values().stream().map(item -> item.print(pad + 1)).collect(Collectors.joining("\n"));
        }
    }

    private class File extends FSItem {
        private final int size;

        public File(String name, int size) {
            super(name);
            this.size = size;
        }

        @Override
        public String print(int pad) {
            return "\t".repeat(pad) + this.name + " (file, size=" + this.size + ")";
        }

        @Override
        int size() {
            return this.size;
        }
    }

    private abstract class FSItem {
        protected final String name;

        public FSItem(String name) {
            this.name = name;
        }

        abstract int size();

        public boolean isDir() {
            return false;
        }

        protected abstract String print(int i);
    }
}
