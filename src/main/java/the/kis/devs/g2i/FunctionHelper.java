package the.kis.devs.g2i;

/**
 * @author _kisman_
 * @since 21:05 of 06.12.2022
 */
public class FunctionHelper {
    public static FunctionArguments getLinearArguments(int x1, int y1, int x2, int y2) {
        float a = (float) (y2 - y1) / (float) (x2 - x1);
        float b = y1 - a *  x1;

        return new FunctionArguments(a, b);
    }
}
