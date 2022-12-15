package the.kis.devs.g2i;

import java.util.HashMap;
import java.util.Map;

/**
 * @author _kisman_
 * @since 18:23 of 15.12.2022
 */
public class KismanGL {
    public static boolean inMatrix = false;
    public static HashMap<Pair<Integer, Integer>, Integer> cache = new HashMap<>();

    public static boolean onAddPoint(int pointX, int pointY, int color) {
        if(inMatrix) {
            cache.put(new Pair<>(pointX, pointY), color);

            return false;
        }

        return true;
    }

    /**
     * if "normalized" is true, we will think that lengthX and lengthY is in non-y-reversed coordinate plane
     * */
    public static void translate(int lengthX, int lengthY, boolean normalized) {
        if(inMatrix) {
            throw new IllegalStateException("Matrix is not pushed yet! You can use this method only in matrix!");
        }

        if(normalized) {
            lengthY = -lengthY;
        }


        for(Pair<Integer, Integer> pos : cache.keySet()) {
            pos.a += lengthX;
            pos.b += lengthY;
        }
    }

    public static void rotateDeg(double degrees, int zeroX, int zeroY) {
        HashMap<Pair<Integer, Integer>, Integer> cacheNew = new HashMap<>();

        int xSum = 0;
        int ySum = 0;

        for(Map.Entry<Pair<Integer, Integer>, Integer> entry : cache.entrySet()) {
            xSum += entry.getKey().a;
            ySum += entry.getKey().b;
        }

        int averageX = (int) Math.round((double) (xSum / cache.size()));
        int averageY = (int) Math.round((double) (ySum / cache.size()));

        System.out.println(averageX + " " + averageY);

        for(Map.Entry<Pair<Integer, Integer>, Integer> entry : cache.entrySet()) {
//            int coeff = entry.getKey().a - averageX <= 0 ? -1 : 1;

//            double[] rotatedPoint = TrigonometricHelper.rotatePointDeg(entry.getKey().a, entry.getKey().b, zeroX, zeroY, /*360 - */degrees/* * coeff*/, false, false);

//            int pointX = (int) rotatedPoint[0];
//            int pointY = (int) rotatedPoint[1];

            int relativeX = entry.getKey().a - zeroX;
            int relativeY = entry.getKey().b - zeroY;

            double[] rotatedPoint = TrigonometricHelper.rotatePointDeg(relativeX, relativeY, 0, 0, /*360 - */degrees/* * coeff*/, true, true);

            int pointX = (int) Math.round(rotatedPoint[0]) + zeroX;
            int pointY = (int) Math.round(rotatedPoint[1]) + zeroY;

            System.out.println("> " + relativeX + " " + relativeY + " | " + pointX + " " + pointY + " | " + Math.round(rotatedPoint[0]) + " " + rotatedPoint[1]);

            cacheNew.put(new Pair<>(pointX, pointY), entry.getValue());
        }

        cache = cacheNew;
    }

    public static void pushMatrix() {
        if(inMatrix) {
            throw new IllegalStateException("Matrix is already pushed!");
        }

        inMatrix = true;
        cache.clear();
    }

    public static void popMatrix() {
        if(!inMatrix) {
            throw new IllegalStateException("Matrix is already popped!");
        }

        Main.points.putAll(cache);

        inMatrix = false;
        cache.clear();
    }
}
