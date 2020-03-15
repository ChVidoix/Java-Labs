package Lab5;

import java.util.ArrayList;
import java.util.List;

public class Prod extends Node {

    private List<Node> args = new ArrayList<>();

    private Prod() {
    }

    Prod(Node... nodes){
        for (Node n : nodes) {
            this.mul(n);
        }
    }

    Prod(double c){
        args.add(new Constant(c));
    }

    Prod(double c, Node n) {
        if (c == 0) {
            args.clear();
        }
        if (c == 1)
            this.mul(n);

        this.mul(new Constant(c)).mul(n);
        if (c < 0) minus();
    }

    Prod mul(Node n){
        if (n instanceof Constant)
            return this.mul(n.evaluate());
        args.add(n);
        return this;
    }

    Prod mul(double c){
        if (c == 0) {
            args.clear();
            return this;
        }
        if (c == 1)
            return this;

        args.add(new Constant(c));
        return this;
    }

    @Override
    public double evaluate() {
        this.simplify();
        return args.stream().
                mapToDouble(Node::evaluate).reduce(1, (a, b) -> a * b);
    }

    @Override
    public String toString() {
        this.simplify();

        if (getArgumentsCount() == 0) return "0";
        if (getArgumentsCount() == 1) return args.get(0).toString();

        StringBuilder b = new StringBuilder().append('(');

        for (Node n : args) {
            if (n.evaluate() == 1.0) continue;
            b.append(' ').append(n.toString()).append(" *");
        }

        b.deleteCharAt(1).delete(b.lastIndexOf(" *"), b.lastIndexOf(" *") + 2).append(')');
        return b.toString();
    }

    @Override
    public Node diff(Variable var) {
        Sum sum = new Sum();

        for (int i = 0; i < args.size(); i++) {
            Prod m = new Prod();
            for (int j = 0; j < args.size(); j++) {
                Node f = args.get(j);
                if (j == i) m.args.add(f.diff(var));
                else m.args.add(f);
            }
            sum.add(m);
        }
//        sum.simplify();
        return sum;
    }

    private Node simplify() {
        double d = 1.0;
        List<Node> pr = new ArrayList<>();

        for (Node n : args) {
            if (!(n instanceof Constant)) {
                if (!n.toString().equals("0"))
                    pr.add(n);
            } else {
                d *= n.evaluate();
            }
        }
        if (d == 0) {
            pr.clear();
            return this;
        }

        //Put constant first
        if (d != 1.0) {
            pr.add(0, new Constant(d));
        }

        this.args = pr;
        return this;
    }

    @Override
    int getArgumentsCount(){
        return args.size();
    }
}