package the.kis.devs.g2i;

/**
 * @author _kisman_
 * @since 12:35 of 26.11.2022
 */
public class FunctionArguments {
    public float a;
    public float b;
    public float c;

    public FunctionArguments(float a, float b, float c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public FunctionArguments(float a, float b) {
        this.a = a;
        this.b = b;
        this.c = Float.NaN;
    }
}
