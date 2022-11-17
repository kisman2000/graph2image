package the.kis.devs.g2i;

/**
 * @author _kisman_
 * @since 22:48 of 17.11.2022
 */
public class Pair<A, B> {
    public A a;
    public B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Pair<?, ?>) {
            Pair<?, ?> other = (Pair<?, ?>) obj;

            return other.a.equals(a) && other.b.equals(b);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return a.hashCode() + b.hashCode();
    }
}
