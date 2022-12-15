import org.junit.jupiter.api.Test;
import the.kis.devs.g2i.TrigonometricHelper;

/**
 * Examples of rotatePointDeg method
 *
 * @author _kisman_
 * @since 19:52 of 08.12.2022
 */

public class TrigonometricHelperTest {
//    @Test
//    public void arcsinTest() {
//        double b = 4;
//        double a = Math.sqrt(Math.pow(b, 2) * 2);//Marcsin

//        System.out.println("Degrees of a triangle is " + TrigonometricHelper.getDegrees(a, b, 50));
//    }

    @Test
    public void rotatorTest1() {
        int degrees = 45;

        double[] point = TrigonometricHelper.rotatePointDeg(1, 0,0, 0, degrees, true, true);

        System.out.println("Rotating point(" + 1 + ";" + 0 + ") to " + degrees + " degrees, x is " + point[0] + " and y is " + point[1]);
    }

    @Test
    public void rotatorTest2() {
        int degrees = 90;

        double[] point = TrigonometricHelper.rotatePointDeg(0, 1, 0, 0, degrees, true, true);

        System.out.println("Rotating point(" + 0 + ";" + 1 + ") to " + degrees + " degrees, x is " + point[0] + " and y is " + point[1]);
    }

    @Test
    public void rotatorTest3() {
        int degrees = 45;

        double[] point = TrigonometricHelper.rotatePointDeg(2, 0, 0, 1, degrees, true, true);

        System.out.println("Rotating point(" + 2 + ";" + 0 + ") to " + degrees + " degrees, x is " + point[0] + " and y is " + point[1]);
    }

    @Test
    public void rotatorTest4() {
        int degrees = 45;

        double[] point = TrigonometricHelper.rotatePointDeg(2, 0, 0, 1, degrees, true, false);

        System.out.println("Rotating point(" + 2 + ";" + 0 + ") to " + degrees + " degrees, non relative x is " + point[0] + " and non relative y is " + point[1]);
    }

    @Test
    public void rotatorTest5() {
        int degrees = -135;

        double[] point = TrigonometricHelper.rotatePointDeg(-1, 0, 0, 0, degrees, true, true);

        System.out.println("Rotating point(" + -1 + ";" + 0 + ") to " + degrees + " degrees, x is " + point[0] + " and y is " + point[1]);
    }

    @Test
    public void rotatorTest6() {
        int degrees = 90;

        double[] point = TrigonometricHelper.rotatePointDeg(10, 10, 0, 0, degrees, true, false);

        System.out.println("Rotating point(" + 10 + ";" + 10 + ") to " + degrees + " degrees, x is " + point[0] + " and y is " + point[1]);
    }
}
