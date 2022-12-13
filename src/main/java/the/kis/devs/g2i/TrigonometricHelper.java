package the.kis.devs.g2i;

/**
 * @author _kisman_
 * @since 19:32 of 08.12.2022
 */
public class TrigonometricHelper {
    public static double[] rotatePointDeg(int pointX, int pointY, int zeroX, int zeroY, double degrees, boolean round, boolean relative) {
        int relativePointX = Math.abs(pointX - zeroX);
        int relativePointY = Math.abs(pointY - zeroY);

        int scaleCoeff = Math.max(relativePointX, relativePointY);

        boolean negativeX = pointX < 0;
        boolean negativeY = pointY < 0;

        double extraDegrees = pointY != 0 ? toDegrees(relativePointX == 0 ? Math.acos(relativePointX) : Math.asin(relativePointY))/*toDegrees(Math.acos(*//*1.0 / *//*relativePointX))*/ : 0.0;

        double rotatedX = Math.cos(toRadians(degrees + extraDegrees)) * scaleCoeff;
        double rotatedY = Math.sin(toRadians(degrees + extraDegrees)) * scaleCoeff;

        if(round) {
            rotatedX = MathHelper.round(rotatedX);
            rotatedY = MathHelper.round(rotatedY);
        }

        if(negativeX) {
            rotatedX = -rotatedX;
        }

        if(negativeY) {
            rotatedY = -rotatedY;
        }

        if(!relative) {
            rotatedX += zeroX;
            rotatedY += zeroY;
        }

        return new double[] { rotatedX, rotatedY };
    }

    //TODO: rotatePointRad method

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
