
package pakkaajaMain;


public class MathUtils {

    public static int twoToPower(int power) {
        if (power < 0) {
            return 0;
        }
        int n = 1;
        for (int i = 1; i <= power; i++) {
            n *= 2;
        }
        return n;
    }
}
