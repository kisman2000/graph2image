package the.kis.devs.g2i;

/**
 * @author _kisman_
 * @since 22:01 of 06.12.2022
 */
public class MathHelper {
    //TODO: does not work
    public static float clamp(float a, float min, float max) {
        System.out.println(a);
        return Math.max(Math.min(a, min), max);
    }

    //TODO: does not work
    public static float clamp(double a, double min, double max) {
        return clamp((float) a, (float) min, (float) max);
    }

    //float pow method(very useful)
    public static float pow(float n, float coeff) {
        return (float) Math.pow(n, coeff);
    }

    public static float round(float value) {
        return Float.parseFloat(String.format("%.1f", (Math.round(value * 10.0) / 10.0)));
    }

    public static double round(double value) {
        return Double.parseDouble(String.format("%.1f", (Math.round(value * 10.0) / 10.0)));
    }
}
