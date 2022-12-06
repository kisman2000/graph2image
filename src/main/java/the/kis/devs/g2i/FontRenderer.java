package the.kis.devs.g2i;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author _kisman_
 * @since 16:19 of 21.11.2022
 */
@SuppressWarnings("ConstantConditions")
public class FontRenderer {
    private static final String TEXTURE = "/font.png";
    private static final int TEXTURE_SIZE = 128;

    public static final int CHAR_SIZE = 8;
    private static final int CHAR_OFFSET = 1;
    private static final int CHAR_MAX_WIDTH = 5;//точное только для цифр и букв да пон
    private static final char[][] CHARS_MAP = new char[][] {
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', '!', '"', '#', '$', '%', '&', '`', '(', ')', '*', '+', ',', '-', '.', '/' },
            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?' },
            { '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', '0' },
            { 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_' },
            { 'ё', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o' },
            { 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~', 'Ё' },
            { 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П' },
            { 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я' },
            { 'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п' },
            { 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я' },
            { 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П' },
            { 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я' },
            { 'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п' },
            { 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я' }
    };
    private static final HashMap<Character, Pair<ArrayList<Pair<Integer, Integer>>, Integer>> CHAR_MAP = new HashMap<>();

    public static void init() throws IOException {
        BufferedImage image = ImageIO.read(Main.class.getResource(TEXTURE));

        for(
                int y = 0;
                y < TEXTURE_SIZE;
                y += CHAR_SIZE
        ) {
            for(
                    int x = 0;
                    x < TEXTURE_SIZE;
                    x += CHAR_SIZE
            ) {
                char currentChar = CHARS_MAP[y / 8][x / 8];

                if(!CHAR_MAP.containsKey(currentChar)) {
                    ArrayList<Pair<Integer, Integer>> pixels = new ArrayList<>();
                    int charWidth = currentChar == ' ' ? CHAR_SIZE - 2 : 0;

                    for (
                            int charX = 0;//, relativeCharX = 0;
                            charX < CHAR_SIZE;
                            charX++
                    ) {
//                        boolean flag = false;

                        for (
                                int charY = 0;
                                charY < CHAR_SIZE;
                                charY++
                        ) {
                            if (image.getRGB(x + charX, y + charY) == -1) {
                                pixels.add(new Pair<>(charX, charY));

//                                if(!flag) {
//                                    relativeCharX++;
//                                }

                                if(/*relativeCharX*/charX > charWidth) {
                                    charWidth = charX + 1/*relativeCharX*/;
//                                    charWidth += 1;
//                                    flag = true;
                                }
                            }
                        }
                    }

                    CHAR_MAP.put(currentChar, new Pair<>(pixels, Math.max(CHAR_MAX_WIDTH, charWidth)));
                }
            }
        }
    }

    public static int getWidth(String string) {
        int width = 0;

        for(char ch : string.toCharArray()) {
            if(CHAR_MAP.containsKey(ch)) {
                width += CHAR_MAP.get(ch).b + CHAR_OFFSET;
            }
        }

        return width - CHAR_OFFSET;
    }

    public static ArrayList<Pair<Integer, Integer>> drawString(String string) {
        ArrayList<Pair<Integer, Integer>> points = new ArrayList<>();

        int xOffset = 0;
        int yOffset = 0;

        for(char ch : string.toCharArray()) {
            if(CHAR_MAP.containsKey(ch)) {
                for(Pair<Integer, Integer> point : CHAR_MAP.get(ch).a) {
                    points.add(new Pair<>(point.a + xOffset, point.b + yOffset));
                }
                xOffset += (CHAR_MAP.get(ch).b + /*2 * */CHAR_OFFSET);
            } else if(ch == '\n') {
                xOffset = 0;
                yOffset += CHAR_OFFSET + CHAR_SIZE;
                        
            }
        }

        return points;
    }
}
