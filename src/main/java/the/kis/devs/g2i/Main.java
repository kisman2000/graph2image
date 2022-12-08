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

    public static void clear(ArrayList<Pair<Integer, Integer>> positions, int xCoeff, int yCoeff, double diff, boolean connect) {
        //TODO: типо после этого график красивый будет
    }

    private static ArrayList<Pair<Integer, Integer>> line2(int x1, int y1, int x2, int y2, boolean outside) {
        ArrayList<Pair<Integer, Integer>> positions = new ArrayList<>();
        positions.add(new Pair<>(x1, y1));
        positions.add(new Pair<>(x2, y2));
        double xDiff = Math.abs(x1 - x2);
        double yDiff = Math.abs(y1 - y2);
        double diff = xDiff > yDiff ? yDiff / xDiff : xDiff / yDiff;
        double coeff = 0.5;

        int last = (int) Math.round(diff * coeff);
        int xCoeff = x1 - x2 < 0 ? -1 : 1;
        int yCoeff = y1 - y2 < 0 ? -1 : 1;

        if(xDiff > yDiff) {
            for(
                    int i = 0;
                    i < xDiff;
                    i++
            ) {
                int delta = (int) Math.round(diff * coeff);

                if(outside) positions.add(new Pair<>(x1 + i * xCoeff + (x2 - x1), y1 + last * yCoeff - (y1 - y2)));
                else positions.add(new Pair<>(x1 + i * xCoeff + (x2 - x1), y1 + delta * yCoeff - (y1 - y2)));

                last = delta;
                coeff += 1;
            }
        } else {
            for(
                    int i = 0;
                    i < yDiff;
                    i++
            ) {
                int delta = (int) Math.round(diff * coeff);

                if(outside) positions.add(new Pair<>(x1 + last * xCoeff + (x2 - x1), y1 + i * yCoeff - (y1 - y2)));
                else positions.add(new Pair<>(x1 + delta * xCoeff + (x2 - x1), y1 + i * yCoeff - (y1 - y2)));

                last = delta;
                coeff += 1;
            }
        }

        clear(positions, xCoeff, yCoeff, diff, outside);

        return positions;
    }

    private static ArrayList<Pair<Integer, Integer>> line3(int x1, int y1, int x2, int y2) {
        ArrayList<Pair<Integer, Integer>> positions = new ArrayList<>();
        FunctionArguments args = FunctionHelper.getLinearArguments(Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2));

        //TODO: i will need it to fix one little bug
//        System.out.println("\n> " + args.a + " " + args.b + " | " + x1 + " " + y1 + " | " + x2 + " " + y2);

        for(
                float relativeFunctionX = 0;
                relativeFunctionX <= Math.abs(x1 - x2);
                relativeFunctionX += 0.1
        ) {
            float functionX = x1 + relativeFunctionX;

            float functionY = Functions.Linear.f(functionX, args);

            int x3 = Math.round(round(functionX)) + (x1 - x2);
            int x = x3 < x1 ? x3 + Math.abs(x1 - x2) * 2 : x3;

            //TODO: i will need it to fix one little bug
//            System.out.println(functionX + "(" + (x) + ")" + " | " + functionY + "(" + (y1 - y2 > 0 ? -1 : 1) * (/*zeroY - */(/*height -*//*axisOffset + axisWidth + */Math.round(round(functionY)))) + ")");

            positions.add(new Pair<>(x, (y1 - y2 > 0 ? -1 : 1) * -Math.round(round(functionY))));

        }

        positions.add(new Pair<>(x1, y1));
        positions.add(new Pair<>(x2, y2));

        return positions;
    }

    public static HashMap<Pair<Integer, Integer>, Integer> points = new HashMap<>();
    public static int axisOffset;
    public static int axisWidth;
    public static int width;
    public static int height;

    public static void render() {
        drawString("QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm\nЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮйцукенгшщзхъфывапролджэячсмитьбю", 50, 15, -1, true);

        drawString("пон", 50, 30, -1, false);

        drawRect(80, 40, 110, 70, Color.GREEN.getRGB(), false, 1);

        drawRect(150, 40, 110, 70, Color.BLUE.getRGB(), true, 1);

        drawFunction(Functions.Square, new FunctionArguments(0, 0, 0), axisOffset + axisWidth, height - axisOffset - axisWidth, 300, 10, Color.ORANGE.getRGB());

        drawFunction(Functions.Cattyn, new FunctionArguments(10, 10, 0), axisOffset + axisWidth, height - axisOffset - axisWidth, 300, 10, Color.MAGENTA.getRGB());
        drawFunction(Functions.Cattyn, new FunctionArguments(1, 1, 0), axisOffset + axisWidth, height - axisOffset - axisWidth - 100, 300, 10, Color.PINK.getRGB());

        drawFunction(Functions.Linear, new FunctionArguments(1, 0, 0), axisOffset + axisWidth, height - axisOffset - axisWidth, 300, 10, Color.RED.getRGB());
        drawFunction(Functions.Linear, new FunctionArguments(0.3f, 0, 0), axisOffset + axisWidth, height - axisOffset - axisWidth, 300, 10, Color.RED.getRGB());

        drawFunction(Functions.Sqrt, new FunctionArguments(0, 0, 0), axisOffset + axisWidth, height - axisOffset - axisWidth, 300, 10, Color.PINK.getRGB());

        drawGraph(
                new int[][] {
                        {10, 0},
                        {11, 1},
                        {13, 4},
                        {15, 6},
                        {20, 6}
                },
                axisOffset + axisWidth, height - axisOffset - axisWidth, 10, Color.GRAY.getRGB()
        );

        drawLine2(30, 30, 60, 70, Color.CYAN.getRGB());
        drawLine2(30, 100, 200, 100, Color.CYAN.getRGB());

        //Buffering sections of axis
        {
            int singleSection = 10;

            //X axis
            {
                int axisLength = width - axisOffset * 2 - axisWidth;

                drawRect(axisOffset + 1, height - axisOffset - 1, width - axisOffset, height - axisOffset - axisWidth/* + 1*/, Color.BLACK.getRGB(), true, 1);


                for (
                        int i = 0;
                        i <= axisLength;
                        i++
                ) {
                    int section = i * singleSection;
                    int x = axisOffset + axisWidth + section;

                    drawString(String.valueOf(i), x - FontRenderer.getWidth(String.valueOf(i)) / 2, height - axisOffset + 1 - (i % 2 == 0 ? 0 : /*-1 + */axisWidth + FontRenderer.CHAR_SIZE + 1), Color.BLACK.getRGB(), false);
                    drawLine2(x, height - axisOffset - axisWidth - 1, x, height - axisOffset, Color.BLACK.getRGB());
                }
            }

            //Y axis
            {
                int axisLength = height - axisOffset * 2 - axisWidth;

                drawRect(axisOffset + 1, height - axisOffset - 1, axisOffset + axisWidth, axisOffset + axisWidth, Color.BLACK.getRGB(), true, 1);

                for (
                        int i = 0;
                        i <= axisLength;
                        i++
                ) {
                    int section = i * singleSection;
                    int y = height - axisOffset - axisWidth - section;//axisOffset + axisWidth + section;

                    drawString(String.valueOf(i), axisOffset + (i % 2 == 0 ? -FontRenderer.getWidth(String.valueOf(i)) : axisWidth + FontRenderer.getWidth(String.valueOf(i)) + 1) - 1, y - FontRenderer.CHAR_SIZE / 2, Color.BLACK.getRGB(), false);
                    drawLine2(axisOffset, y, axisOffset + axisWidth + 1, y, Color.BLACK.getRGB());
                }
            }
        }

    }

    public static void drawGraph(int[][] points, int zeroX, int zeroY, int singleSection, int color) {
        for(
                int i = 0;
                i < points.length;
                i++
        ) {
            int x = points[i][0];
            int y = points[i][1];

            int nextX = i + 1 != points.length ? points[i + 1][0] : x;
            int nextY = i + 1 != points.length ? points[i + 1][1] : y;

            drawLine3(zeroX + Math.round(x * singleSection), zeroY - Math.round(y * singleSection), zeroX + Math.round(nextX * singleSection), zeroY - Math.round(nextY * singleSection), color);
        }
    }

    public static void drawPoint(int x, int y, int color) {
        points.put(new Pair<>(x, y), color);
    }
    
    public static void drawString(String string, int x, int y, int color, boolean shadow) {
        if(shadow) drawString(string, x + 1, y + 1, new Color(63, 63, 63, 255).getRGB(), false);

        FontRenderer.drawString(string).forEach(point -> {
            point.a = point.a + x;
            point.b = point.b + y;

            points.put(point, color);
        });
    }

    public static void drawLine2(int x1, int y1, int x2, int y2, int color) {
        line2(x1, y1, x2, y2, true).forEach(point -> points.put(point, color));
    }

    public static void drawLine3(int x1, int y1, int x2, int y2, int color) {
        line3(x1, y1, x2, y2).forEach(point -> points.put(point, color));
    }

    public static void drawRectWH(int x, int y, int w, int h, int color, boolean filled, int outlineWidth) {
        drawRect(x, y, x + w, y + h, color, filled, outlineWidth);
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

            drawLine3(zeroX + x, zeroY - Math.round(functionRelativeY * singleSection), zeroX + nextX, zeroY - Math.round(functionRelativeNextY * singleSection), color);

//            drawPoint(zeroX + x, zeroY - Math.round(functionRelativeY * singleSection), -1);
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
