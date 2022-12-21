import org.junit.jupiter.api.Test;
import the.kis.devs.g2i.TrigonometricHelper;

/**
 * @author _kisman_
 * @since 20:54 of 21.12.2022
 */
public class KismanGLTest {
    @Test
    public void rotateRectTest1() {
        int[][] map = new int[][] {
                { 1 - 10, 2 - 10, 3 - 10, 4 - 10, 5 - 10, 6 - 10, 7 - 10, 8 - 10, 9 - 10, 0 },
                { 1 - 10, 2 - 10, 3 - 10, 4 - 10, 5 - 10, 6 - 10, 7 - 10, 8 - 10, 9 - 10, 0 },
                { 1 - 10, 2 - 10, 3 - 10, 4 - 10, 5 - 10, 6 - 10, 7 - 10, 8 - 10, 9 - 10, 0 },
                { 1 - 10, 2 - 10, 3 - 10, 4 - 10, 5 - 10, 6 - 10, 7 - 10, 8 - 10, 9 - 10, 0 },
                { 1 - 10, 2 - 10, 3 - 10, 4 - 10, 5 - 10, 6 - 10, 7 - 10, 8 - 10, 9 - 10, 0 },
                { 1 - 10, 2 - 10, 3 - 10, 4 - 10, 5 - 10, 6 - 10, 7 - 10, 8 - 10, 9 - 10, 0 },
                { 1 - 10, 2 - 10, 3 - 10, 4 - 10, 5 - 10, 6 - 10, 7 - 10, 8 - 10, 9 - 10, 0 },
                { 1 - 10, 2 - 10, 3 - 10, 4 - 10, 5 - 10, 6 - 10, 7 - 10, 8 - 10, 9 - 10, 0 },
                { 1 - 10, 2 - 10, 3 - 10, 4 - 10, 5 - 10, 6 - 10, 7 - 10, 8 - 10, 9 - 10, 0 },
                { 1 - 10, 2 - 10, 3 - 10, 4 - 10, 5 - 10, 6 - 10, 7 - 10, 8 - 10, 9 - 10, 0 }
        };

        for(
                int y = 0;
                y < map.length;
                y++
        ) {
            for(
                    int x = 0;
                    x < map[y].length;
                    x++
            ) {
                int prevX = x + 1;
                int nextX = map[y][x];

                double[] rotatedPoint = TrigonometricHelper.rotatePointDeg(prevX, y, 90, true);

                boolean correct = rotatedPoint[0] == nextX;

                System.out.println("Rotating prev point(" + prevX + ";" + y + ") to next point(" + nextX + ";" + y + ") is " + (!correct ? "in" : "") + "correct!");
            }
        }
    }
}
