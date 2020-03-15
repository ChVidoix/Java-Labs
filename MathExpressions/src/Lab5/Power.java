package Lab5;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Power extends Node {
    private Node arg;
    private double p;

    Power(Node arg, double pow) {
        this.arg = arg;
        this.p = pow;
        this.sign = arg.sign;
        if (arg instanceof Variable) {
            sign = 1;
        }
    }

    @Override
    public double evaluate() {
        return Math.pow(arg.evaluate(), p);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        boolean negative = sign < 0;
        int cnt = arg.getArgumentsCount();
        boolean useBracket = false;

        if (negative)
            b.append("-");


        if (negative || cnt > 1)
            useBracket = true;

        String argString = arg.toString();

        if (useBracket)
            b.append("(");

        b.append(argString);

        if (useBracket)
            b.append(")");

        if (p != 1) {
            b.append("^");
            DecimalFormat format = new DecimalFormat("0.#####", new DecimalFormatSymbols(Locale.US));
            b.append(format.format(p));
        }
        return b.toString();
    }

    @Override
    public Node diff(Variable var) {
        Prod r = new Prod(sign * p, new Power(arg, p - 1));
        r.mul(arg.diff(var));
        return r;
    }

    @Override
    int getArgumentsCount() {
        return 1;
    }
}