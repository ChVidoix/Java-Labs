package Lab5;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Constant extends Node {
    private Double value;

    Constant(Double value) {
        this.value = Math.abs(value);
        this.sign = value < 0 ? -1 : 1;
    }

    @Override
    public double evaluate() {
        return value * sign;
    }

    @Override
    int getSign() {
        return sign;
    }

    @Override
    public String toString() {
        DecimalFormat format = new DecimalFormat("0.#####", new DecimalFormatSymbols(Locale.US));
        return format.format(value);
    }

    @Override
    public Node diff(Variable var) {
        return new Constant(0.0);
    }
}