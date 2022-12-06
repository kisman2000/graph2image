package the.kis.devs.g2i;

/**
 * @author _kisman_
 * @since 22:01 of 06.12.2022
 */
public class MathHelper {
    public static float clamp(float a, float min, float max) {
        System.out.println(a);
        return Math.max(Math.min(a, min), max);
    }

    public static float clamp(double a, double min, double max) {
        return clamp((float) a, (float) min, (float) max);
    }
}
