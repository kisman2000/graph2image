package the.kis.devs.g2i;

import java.util.HashMap;

/**
 * @author _kisman_
 * @since 19:32 of 08.12.2022
 */
public class TrigonometricHelper {
    public static HashMap<Double, Double> sinCache = new HashMap<>();
    public static HashMap<Double, Double> cosCache = new HashMap<>();
    public static HashMap<Double, Double> asinCache = new HashMap<>();
    public static HashMap<Double, Double> acosCache = new HashMap<>();

    public static double[] rotatePointDeg(int pointX, int pointY, int zeroX, int zeroY, double degrees, boolean round, boolean relative) {
        int relativePointX = Math.abs(pointX - zeroX);
        int relativePointY = Math.abs(pointY - zeroY);

        double scaleCoeff = Math.max(relativePointX, relativePointY);
        double scaleCoeffRaw = Math.max(pointX - zeroX, pointY - zeroX);

        relativePointX *= (scaleCoeffRaw <= 0 ? -1 : 1);
        relativePointY *= (scaleCoeffRaw <= 0 ? -1 : 1);

        double extraDegrees = relativePointX < 0 && relativePointY == 0 ? 180 : (relativePointX == 0 && relativePointY != 0 ? (relativePointY > 0 ? 90 : 270) : toDegrees(Math.atan(Math.abs(relativePointY / relativePointX))));

        double rotatedX = cos(toRadians(degrees + extraDegrees)) * scaleCoeff + (relative ? 0 : zeroX);
        double rotatedY = sin(toRadians(degrees + extraDegrees)) * scaleCoeff + (relative ? 0 : zeroY);

        if(round) {
            rotatedX = MathHelper.round(rotatedX);
            rotatedY = MathHelper.round(rotatedY);
        }

        return new double[] { rotatedX, rotatedY };
    }

    //TODO: rotatePointRad method

    public static double sin(double radians) {
        if(sinCache.containsKey(radians)) {
            return sinCache.get(radians);
        }

        double sin = Math.sin(radians);

        sinCache.put(radians, sin);

        return sin;
    }

    public static double cos(double radians) {
        if(cosCache.containsKey(radians)) {
            return cosCache.get(radians);
        }

        double cos = Math.cos(radians);

        cosCache.put(radians, cos);

        return cos;
    }

    public static double cos(double a, double b, double c) {
        return (a * a + c * c - b * b) / 2 * a * c;
    }

    public static double asin(double sin) {
        if(asinCache.containsKey(sin)) {
            return asinCache.get(sin);
        }

        double asin = Math.asin(sin);

        asinCache.put(sin, asin);

        return asin;
    }

    public static double acos(double cos) {
        if(acosCache.containsKey(cos)) {
            return acosCache.get(cos);
        }

        double acos = Math.acos(cos);

        acosCache.put(cos, acos);

        return acos;
    }

    /*public static double arcsin(double sin, int iterations) {
        double arcsin = 0;

        arcsin += sin;

        int lastA = 1;
        int lastB = 2;
        int lastNumber = 2;

        for(
                int i = 0;
                i < iterations;
                i++
        ) {
            int a = lastA,
                    b = lastB,
                    degree = lastNumber + 1;

            System.out.println(a + " " + b + " " + degree + " " + arcsin);


            arcsin += ((double) a / (double) b) * (Math.pow(sin, degree) / degree);

            lastA *= degree;
            lastB *= degree + 1;
            lastNumber = degree + 1;
        }

        return arcsin;
    }*/

    public static double toDegrees(double radians) {
        return (180 * radians) / Math.PI;
    }

    public static double toRadians(double degrees) {
        return (Math.PI * degrees) / 180;
    }
}
