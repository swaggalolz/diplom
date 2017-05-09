package by.bsuir.diplom.logic;



import by.bsuir.diplom.draw.Content;
import by.bsuir.diplom.draw.ContentLine;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ContentAnalyzer {

    private static final int SMALL_GROUP_LIMIT = 25;
    private static final int SPAM_LINE_CONTENT = 4;

    public static List<ContentLine> getContentLines(int width, int height, int[] pixels) throws Exception {
        final GroupFinder finder = new GroupFinder(width, height, pixels);

        final int count = finder.fastFindGroups(); //вернет количество найденных групп

        final Map<Integer, Integer> stats = GroupSeparator.getGroupStatistics(finder.getPixels(), count);
        final List<Integer> removingGroups = GroupSeparator.deleteSmallGroups(finder.getPixels(), SMALL_GROUP_LIMIT, stats);

        final List<Content> contents = GroupSeparator.getAllGroups(width, height, finder.getPixels(), count, removingGroups);

        final List<ContentLine> lines = GroupSeparator.getLines(contents);
        final List<ContentLine> removingItems = new LinkedList<>();

        for (ContentLine line : lines) {
            if (line.size() < SPAM_LINE_CONTENT) {
                removingItems.add(line);
            }
        }
        lines.removeAll(removingItems);
        return lines;
    }
}
