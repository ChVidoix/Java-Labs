package Lab5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sum extends Node {
    private List<Node> args = new ArrayList<>();

    Sum(){}

    Sum(Node... nodes) {
        for (Node n : nodes) {
            this.add(n);
        }
    }

    Sum add(Node n) {
        if (n instanceof Constant) {
            this.add(n.evaluate());
            return this;
        }
        args.add(n);
        return this;
    }

    Sum add(double d, Node n) {
        args.add(new Constant(d));
        args.add(n);
        return this;
    }

    Sum add(Node ...nodes){
        args.addAll(Arrays.asList(nodes));
        return this;
    }

    Sum add(double c){
        if (c == 0)
            return this;

        args.add(new Constant(c));
        return this;
    }

    Sum addProd(double c, Node n) {
        if (c == 0) return this;

        Node mul = new Prod(c,n);
        args.add(mul);
        return this;
    }

    @Override
    public double evaluate() {
        this.simplify();
        return args.stream().mapToDouble(Node::evaluate).sum();
    }

    private void simplify() {
        double d = 0.0;
        List<Node> pr = new ArrayList<>();

        for(Node n : args){
            if (!(n instanceof Constant)) {
                if (!n.toString().equals("0"))
                    pr.add(n);
            } else {
                d += n.evaluate();
            }
        }
        this.args = pr;
        this.add(d);

    }

    @Override
    public String toString() {
        this.simplify();
        if (getArgumentsCount() == 0)
            return "0";

        if (getArgumentsCount() == 1)
            return args.get(0).toString();

        StringBuilder builder = new StringBuilder();

        if (getSign() < 0) {
            builder.append("-(");
        } else {
            builder.append('(');
        }

        for (Node n : args) {
            if (n.getSign() < 0) {
                builder.append(" - ");
            } else if (n != args.get(0)) {
                builder.append(" + ");
            }
            builder.append(n.toString());
        }

        return builder.append(')').toString();
    }

    @Override
    public Node diff(Variable var) {
        this.simplify();
        Sum sum = new Sum();
        for (Node n : args) {
            sum.add(n.diff(var));
        }
        return sum;
    }

    @Override
    int getArgumentsCount(){
        return args.size();
    }
}