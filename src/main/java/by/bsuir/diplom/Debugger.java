package by.bsuir.diplom;

import by.bsuir.diplom.draw.Content;
import by.bsuir.diplom.draw.ContentLine;
import by.bsuir.diplom.utils.ImageHelper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class Debugger {


    public static void debugContentLines(BufferedImage image, List<ContentLine> lines, String outPath) throws IOException {
        for (int j = 0; j < lines.size(); j++) {
            File lineFolder = new File(outPath + "/" + j + "/");
            lineFolder.mkdir();
            for (Content content : lines.get(j).getLine()) {
                BufferedImage result = ImageHelper.getSubImage(image, content.x, content.y, content.width, content.height);
                ImageHelper.saveImage(result, lineFolder.getPath() + "/" + j + ".bmp");
            }
        }
        System.out.println("TOTAL LINES: " + lines.size());
    }
}
