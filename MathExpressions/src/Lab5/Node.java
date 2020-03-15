package Lab5;

abstract public class Node {
    int sign =1;

    Node minus(){
        sign = -1;
        return this;
    }

    Node plus(){
        sign = 1;
        return this;
    }

    int getSign(){
        return sign;
    }

    abstract public double evaluate();

    @Override
    public abstract String toString();

    abstract public Node diff(Variable var);

    int getArgumentsCount(){return 0;}

}