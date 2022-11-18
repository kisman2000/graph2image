package the.kis.devs.g2i;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author _kisman_
 * @since 22:10 of 17.11.2022
 */
public class Main {
    private static float doLinearFunction(float x, float a, float b) {
        return round(x * a + b);
    }

    private static float doQuadFunction(float x) {
        return round(pow(x, 2));
    }

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

    public static void main(String[] args) throws IOException {
        if(args.length < 5 || args.length > 6) {
            System.out.println("Illegal arguments");
            return;
        }

        boolean hasBackground = args.length == 6;

        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        int color = Integer.parseInt(args[2]);
        float scaleCoeff = Float.parseFloat(args[3]);

        String output = args[4];
        String backgroundImage = hasBackground ? args[5] : null;

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

        ArrayList<Pair<Integer, Integer>> points2 = new ArrayList<>();

        int axisOffset = (int) (10f * scaleCoeff);
        int axisWidth = (int) (5f * scaleCoeff);

        int coordinateSystemZeroX = axisOffset + axisWidth;
        int coordinateSystemZeroY = height - axisOffset - axisWidth;
        int coordinateSystemXLength = width - axisOffset * 2 - axisWidth;
        int coordinateSystemYLength = height - axisOffset * 2 - axisWidth;
        float coordinateSystemSingleSection = 10f;

        for(
                int x = 0, nextX = 0;
                x < width;
                x++
        ) {
            nextX++;

            if(x > axisOffset + axisWidth) {
                float relativeX = round(((float) x - axisOffset - axisWidth) / coordinateSystemSingleSection);
                float relativeNextX = round(((float) nextX - axisOffset - axisWidth) / coordinateSystemSingleSection);

                float quadFunctionRelativeY = doQuadFunction(relativeX);
                float quadFunctionRelativeNextY = doQuadFunction(relativeNextX);

                ArrayList<Pair<Integer, Integer>> line = line(x, height - axisOffset - axisWidth - (int) (quadFunctionRelativeY * 10f), nextX, height - axisOffset - axisWidth - (int) (quadFunctionRelativeNextY * 10f), false, false);

                points2.addAll(line);
            }
        }


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
                    if(x > axisOffset + axisWidth && y < height - axisOffset - axisWidth) {
                        if(points2.contains(new Pair<>(x, y))) {
                            image.setRGB(x, y, color);
                            continue;
                        }
                    }
                }

                //Fixed: если бекграунд будет меньше графика то мб исключение будет
                if(hasBackground && canBackground(x, y, background)) image.setRGB(x, y, background.getRGB(x, y));
                else image.setRGB(x, y, -1);
            }
        }

        ImageIO.write(image, "png", file);
    }
}
