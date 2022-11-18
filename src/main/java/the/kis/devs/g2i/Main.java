package the.kis.devs.g2i;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author _kisman_
 * @since 22:10 of 17.11.2022
 */
public class Main {
    private static HashMap<Integer, Integer> getPoints() {
        HashMap<Integer, Integer> map = new HashMap<>();

        map.put(10, 10);
        map.put(20, 30);
        map.put(30, 30);
        map.put(40, 50);
        map.put(61, 5);

        return map;
    }

    private static float doLinearFunction(float x, float a, float b) {
        return round(x * a + b);
    }

    private static float doQuadFunction(float x) {
        return round(pow(x, 2));
    }

    private static float round(float value) {
        return Float.parseFloat(String.format("%.3f", value));
    }
    
    private static float pow(float n, float coeff) {
        return (float) Math.pow(n, coeff);
    }
                     
    private static boolean canBackground(int x, int y, BufferedImage background) {
        return x <= background.getWidth() && y <= background.getHeight();
    }

    public static void main(String[] args) throws IOException {
        if(args.length < 5 && args.length > 6) {
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

        int counter = 0;

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
                int axisOffset = (int) (10f * scaleCoeff);
                int axisWidth = (int) (5f * scaleCoeff);

                int coordinateSystemZeroX = axisOffset + axisWidth;
                int coordinateSystemZeroY = height - axisOffset - axisWidth;
                int coordinateSystemXLength = width - axisOffset * 2 - axisWidth;
                int coordinateSystemYLength = height - axisOffset * 2 - axisWidth;
                float coordinateSystemSingleSection = 10f;


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
                        float relativeX = round(((float) x - axisOffset - axisWidth) / coordinateSystemSingleSection);
                        float relativeY = round((coordinateSystemYLength - ((float) y - axisOffset)) / coordinateSystemSingleSection);

                        float linearFunctionRelativeY = doLinearFunction(relativeX, 0.5f, -3);

                        float quadFunctionRelativeY = doQuadFunction(relativeX);

                        if(counter < 10) {
                            System.out.println(linearFunctionRelativeY + " | " + relativeY);

                            counter++;
                        }


                        if(linearFunctionRelativeY == relativeY) {
                            image.setRGB(x, y, color);
                            continue;
                        }

                        if(quadFunctionRelativeY == relativeY) {
                            image.setRGB(x, y, Color.CYAN.getRGB());
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
