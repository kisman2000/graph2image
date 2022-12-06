package the.kis.devs.g2i;

/**
 * @author _kisman_
 * @since 21:05 of 06.12.2022
 */
public class FunctionHelper {
    public static FunctionArguments getLinearArguments(int x1, int y1, int x2, int y2) {
        int a = (y2 - y1) / (x2 - x1);
        int b = y1 - a * x1;

        return new FunctionArguments(a, b);
    }
}
