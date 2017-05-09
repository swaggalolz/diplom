package by.bsuir.diplom.logic;



import by.bsuir.diplom.draw.Content;
import by.bsuir.diplom.draw.ContentLine;
import by.bsuir.diplom.draw.Pixel;
import by.bsuir.diplom.utils.ColorRGB;

import java.util.*;

public final class GroupSeparator {

    private static final double RATIO_CONSTANT = 6.8d;

    public static List<Content> exclude(Content element, List<Content> elements) {
        final LinkedList<Content> temp = new LinkedList<>();

        for (Content content : elements) {
            if (content != null) {
                if (element.x < content.x + content.width &&
                        element.x + element.width > content.x &&
                        element.y < content.y + content.height &&
                        element.y + element.height > content.y)
                    temp.add(content);
            }
        }

        return temp;
    }

    /**
     * @return edge of input contents
     */
    public static Content analyzeContents(List<Content> contents) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Content c : contents) {

            if (minX > c.x)
                minX = c.x;

            if (minY > c.y)
                minY = c.y;

            if (maxX < c.x + c.width)
                maxX = c.x + c.width;

            if (maxY < c.y + c.height)
                maxY = c.y + c.height;

        }

        return new Content(minX, minY, maxX - minX, maxY - minY);
    }



    public static List<Content> getAllGroups(int width, int height, final int[] pixels, final int groupsCount) throws Exception {
        final List<Content> images = new LinkedList<>();

        for (int i = 0; i < groupsCount; i++) {
            images.add(getGroup(width, height, pixels, i));
        }

        return images;
    }

    public static List<Content> getAllGroups(int width, int height, final int[] pixels, final int groupsCount, final List<Integer> removingGroups) throws Exception {
        final List<Content> images = new LinkedList<>();

        for (int i = 0; i < groupsCount; i++)
            if (!removingGroups.contains(i))
                images.add(getGroup(width, height, pixels, i));


        return images;
    }

    public static List<ContentLine> getLines(List<Content> contents) {
        final List<ContentLine> lines = new LinkedList<>();

        for (Content content : contents) {
            if (content != null) {
                boolean isAdded = false;
                for (ContentLine line : lines) {
                    if (line.checkAndAdd(content)) {
                        isAdded = true;
                        break;
                    }
                }
                if (!isAdded) {
                    ContentLine line = new ContentLine();
                    line.add(content);
                    lines.add(line);
                }
            }
        }

        return lines;
    }

    public static List<Integer> deleteSmallGroups(final int[] pixels, int minGroupSize, final Map<Integer, Integer> stats) {
        final List<Integer> removingKeys = new ArrayList<>(stats.size());

        final Iterator<Integer> iterator = stats.keySet().iterator();
        while (iterator.hasNext()) {
            Integer key = iterator.next();
            if (stats.get(key) < minGroupSize)
                removingKeys.add(key);
        }

        for (int j = 0; j < pixels.length; j++)
            if (removingKeys.contains(pixels[j]))
                pixels[j] = ColorRGB.getWhiteColor();

        return removingKeys;
    }

    public static Map<Integer, Integer> getGroupStatistics(final int[] pixels, int groupsCount) {
        final Map<Integer, Integer> stats = new Hashtable<>(groupsCount);

        for (int i = 1; i <= groupsCount; i++) {
            int size = 0;
            for (int j = 0; j < pixels.length; j++)
                if (pixels[j] == i)
                    size++;
            stats.put(i, size);
        }

        return stats;
    }

    public static Content getGroup(int width, int height, final int[] pixels, final int groupNumber) {
        final Pixel left = new Pixel(width, 0);
        final Pixel top = new Pixel(0, height);
        final Pixel right = new Pixel(0, 0);
        final Pixel bottom = new Pixel(0, 0);

//        int minX = width, maxX = 0, maxY = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (pixels[y * width + x] == groupNumber) {
                    left.setLeft(x);
                    right.setRight(x);

                    top.setTop(y);
                    bottom.setBottom(y);
                }
            }
        }
        final int x = left.x;
        final int y = top.y;

        final int groupWidth = right.x - x;
        final int groupHeight = bottom.y - y;


        return (x > 0 && y > 0 && groupWidth > 0 && groupHeight > 0) ?
                new Content(x, y, groupWidth, groupHeight) :
                null;
    }

    public static int getMaxGroup(final int[] pixels, final int groupsCount) {
        int[] counts = new int[groupsCount + 1];
        final int white = ColorRGB.getWhiteColor();

        for (int pixel : pixels) {
            if (pixel != white && pixel > 0 && pixel < groupsCount) {
                counts[pixel]++;
            }
        }

        int maxSize = 0;
        int index = 0;

        for (int i = 0; i < groupsCount; i++) {
            if (maxSize < counts[i]) {
                maxSize = counts[i];
                index = i;
            }
        }

        return index;
    }


}
