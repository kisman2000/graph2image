package the.kis.devs.g2i;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author _kisman_
 * @since 22:10 of 17.11.2022
 */
public class Main {
    private static float round(float value) {
        return Float.parseFloat(String.format("%.1f", value));
    }
    
    private static float pow(float n, float coeff) {
        return (float) Math.pow(n, coeff);
    }
                     
    private static boolean canBackground(int x, int y, BufferedImage background) {
        return x <= background.getWidth() && y <= background.getHeight();
    }

    private static ArrayList<Pair<Integer, Integer>> line(int x1, int y1, int x2, int y2, boolean connect, boolean outside) {
        ArrayList<Pair<Integer, Integer>> positions = new ArrayList<>();
        double xDiff = x1 - x2;
        double zDiff = y1 - y2;
        double d = xDiff > zDiff ? zDiff / xDiff : xDiff / zDiff;
        double a = 0.5;
        int last = (int) (d * a);
        if(xDiff > zDiff){
            for(int i = 0; i <= ((int) xDiff); i++){
                double delta = d * a;
                if(connect && (int) delta > last){
                    if(outside) positions.add(new Pair<>(x1 + i, y1 + last));
                    else positions.add(new Pair<>(x1 + i - 1, y1 + (int) delta));
                }
                positions.add(new Pair<>(x1 + i, y1 + (int) delta));
                last = (int) delta;
                a += 1.0;
            }
        } else {
            for(int i = 0; i <= ((int) zDiff); i++){
                double delta = d * a;
                if(connect && (int) delta > last){
                    if(outside) positions.add(new Pair<>(x1 + last, y1 + i));
                    else positions.add(new Pair<>(x1 + y1 + (int) Math.round(delta), i - 1));
                }
                positions.add(new Pair<>(x1 + (int) Math.round(delta), y1 + i));
                a += 1.0;
            }
        }
        return positions;
    }

    public static HashMap<Pair<Integer, Integer>, Integer> points = new HashMap<>();
    public static int axisOffset;
    public static int axisWidth;
    public static int width;
    public static int height;

    public static void render() {
        drawString("QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm\nЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮйцукенгшщзхъфывапролджэячсмитьбю", 10, 15, -1, true);

        drawString("пон", 15, 30, -1, false);

        drawLine(30, 30, 60, 70, Color.CYAN.getRGB());

        drawRect(80, 40, 110, 70, Color.GREEN.getRGB(), false, 1);

        drawRect(150, 40, 110, 70, Color.BLUE.getRGB(), true, 1);

        drawFunction(Functions.Square, new FunctionArguments(0, 0, 0), axisOffset + axisWidth, height - axisOffset - axisWidth, 300, 10, Color.ORANGE.getRGB());

        drawFunction(Functions.Linear, new FunctionArguments(1, 0, 0), axisOffset + axisWidth, height - axisOffset - axisWidth, 300, 10, Color.RED.getRGB());

        drawFunction(Functions.Sqrt, new FunctionArguments(0, 0, 0), axisOffset + axisWidth, height - axisOffset - axisWidth, 300, 10, Color.PINK.getRGB());
    }
    
    public static void drawString(String string, int x, int y, int color, boolean shadow) {
        if(shadow) drawString(string, x + 1, y + 1, new Color(63, 63, 63, 255).getRGB(), false);

        FontRenderer.drawString(string).forEach(point -> {
            point.a = point.a + x;
            point.b = point.b + y;

            points.put(point, color);
        });
    }

    public static void drawLine(int x1, int y1, int x2, int y2, int color) {
        line(x1, y1, x2, y2, false, true).forEach(point -> points.put(point, color));
    }

    public static void drawRect(int x1, int y1, int x2, int y2, int color, boolean filled, int outlineWidth) {
        for(
                int x = Math.min(x1, x2);
                x <= Math.max(x1, x2);
                x++
        ) {
            for(
                    int y = Math.min(y1, y2);
                    y <= Math.max(y1, y2);
                    y++
            ) {
                Pair<Integer, Integer> pair = new Pair<>(x, y);

                int relativeX = x - Math.min(x1, x2);
                int relativeY = y - Math.min(y1, y2);

                int relativeReversedX = Math.max(x1, x2) - x;
                int relativeReversedY = Math.max(y1, y2) - y;

                if(filled || ((relativeX <= outlineWidth || relativeReversedX <= outlineWidth) || (relativeY <= outlineWidth || relativeReversedY <= outlineWidth))) {
                    points.put(pair, color);
                }
            }
        }
    }

    public static void drawFunction(Functions function, FunctionArguments args, int zeroX, int zeroY, int xLimit, int singleSection, int color) {
        for (
                int x = 0, nextX = 0;
                x < xLimit;
                x++
        ) {
            nextX++;


            float relativeX = round(x) / singleSection;
            float relativeNextX = round(nextX) / singleSection;

            float functionRelativeY = round(function.f(relativeX, args));
            float functionRelativeNextY = round(function.f(relativeNextX, args));

            drawLine(zeroX + x, zeroY - Math.round(functionRelativeY * singleSection), zeroX + nextX, zeroY - Math.round(functionRelativeNextY * singleSection), color);
        }
    }


    public static void main(String[] args) throws IOException {
        if(args.length < 5 || args.length > 6) {
            System.out.println("Illegal arguments");
            return;
        }

        boolean hasBackground = args.length == 6;

        width = Integer.parseInt(args[0]);
        height = Integer.parseInt(args[1]);
        int color = Integer.parseInt(args[2]);
        float scaleCoeff = Float.parseFloat(args[3]);

        String output = args[4];
        String backgroundImage = hasBackground ? args[5] : null;

        System.out.println("Initializing font renderer");

        FontRenderer.init();

        System.out.println("Initializing out and background files");

        File file = new File(output);
        File backgroundFile = hasBackground ? new File(backgroundImage) : null;

        if(!backgroundFile.exists()) hasBackground = false;

        if(!file.exists()) file.delete();

        file.createNewFile();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage background = null;

        if(hasBackground) {
            background = ImageIO.read(backgroundFile);
        }

        axisOffset = (int) (10f * scaleCoeff);
        axisWidth = (int) (5f * scaleCoeff);

        //Processing functions
        /*{
            System.out.println("Processing functions");

            float coordinateSystemSingleSection = 10f;

            for (
                    int x = 0, nextX = 0;
                    x < width;
                    x++
            ) {
                nextX++;

                if (x > axisOffset + axisWidth) {
                    float relativeX = round(((float) x - axisOffset - axisWidth) / coordinateSystemSingleSection);
                    float relativeNextX = round(((float) nextX - axisOffset - axisWidth) / coordinateSystemSingleSection);

                    //It's just an example
                    for (Functions function : Functions.values()) {
                        float functionRelativeY = function.f(relativeX);
                        float functionRelativeNextY = function.f(relativeNextX);

                        ArrayList<Pair<Integer, Integer>> line = line(x, height - axisOffset - axisWidth - (int) (functionRelativeY * 10f), nextX, height - axisOffset - axisWidth - (int) (functionRelativeNextY * 10f), false, true);

                        line.forEach(point -> points.put(point, -1));
                    }
                }
            }
        }*/

        //Processing font renderer
        /*{
            System.out.println("Processing font renderer");

            //Processing 1 usage of drawString method
            {
                FontRenderer.drawString("QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm\nЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮйцукенгшщзхъфывапролджэячсмитьбю").forEach(point -> points.put(point, -1));
            }
        }*/

        System.out.println("Buffering objects!");

        render();

        System.out.println("Drawing the image");

        for(
                int x = 0;
                x < width;
                x++
        ) {
            for(
                    int y = 0;
                    y < height;
                    y++
            ) {
                {
                    if(x > axisOffset && x < axisOffset + axisWidth && y > axisOffset && y < height - axisOffset) {
                        image.setRGB(x, y, Color.BLACK.getRGB());
                        continue;
                    }
                }

                {
                    if(x > axisOffset && x < width - axisOffset && y > height - axisOffset - axisWidth && y < height - axisOffset) {
                        image.setRGB(x, y, Color.BLACK.getRGB());
                        continue;
                    }
                }

                {
                    if(points.containsKey(new Pair<>(x, y))) {
                        image.setRGB(x, y, points.get(new Pair<>(x, y)));
                        continue;
                    }
                }

                //Fixed: если бекграунд будет меньше графика то мб исключение будет
                if(hasBackground && canBackground(x, y, background)) image.setRGB(x, y, background.getRGB(x, y));
                else image.setRGB(x, y, -1);
            }
        }

        System.out.println("Writing image!");

        ImageIO.write(image, "png", file);
    }
}
