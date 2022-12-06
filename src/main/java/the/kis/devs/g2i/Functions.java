package the.kis.devs.g2i;

/**
 * @author _kisman_
 * @since 23:02 of 18.11.2022
 */
public enum Functions {
    Linear {
        @Override
        float f(float x, float...args) {
            return x * args[0] + args[1];
        }

        @Override
        float f(float x) {
            return f(x, 1, 0);
        }
    },
    Sqrt {
        @Override
        float f(float x, float... args) {
            return (float) Math.sqrt(x + args[0]) + args[1];
        }

        @Override
        float f(float x) {
            return f(x, 0, 0);
        }
    },
    Square {
        @Override
        float f(float x, float... args) {
            return (float) (Math.pow(x + args[0], 2) + args[1]);
        }

        @Override
        float f(float x) {
            return f(x, 0, 0);
        }
    },

    Cattyn {
        @Override
        float f(float x, float... args) {
            return /*MathHelper.clamp*/(float) ((1 - (.5 * Math.sin(Math.PI * x + Math.PI / 2) + .5)/*, args[0], args[1]*/) * args[0]);
        }

        @Override
        float f(float x) {
            return f(x, 1);
        }
    }
    ;

    abstract float f(float x, float... args);
    abstract float f(float x);

    float f(float x, FunctionArguments args) {
        return f(x, args.a, args.b, args.c);
    }
}
