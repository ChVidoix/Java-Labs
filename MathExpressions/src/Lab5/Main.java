package Lab5;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        buildAndPrint();
    }

    static void buildAndPrint() {
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(2.1, new Power(x, 3))
                .add(new Power(x, 2))
                .add(-2, x)
                .add(7);
        System.out.println(exp.toString());

    }

    static void buildAndEvaluate() {
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(new Power(x, 3))
                .add(-2, new Power(x, 2))
                .add(-1, x)
                .add(2);
        for (double i = -5; i < 5; i += 0.1) {
            x.setValue(i);
            System.out.printf(Locale.US, "f(%f)=%f\n", i, exp.evaluate());
        }
    }
}