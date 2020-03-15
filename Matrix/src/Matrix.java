import java.util.Arrays;
import java.util.Random;
import static java.lang.StrictMath.sqrt;

public class Matrix {
    double[] data;
    int rows;
    int cols;
    Matrix(int row, int col){
        rows = row;
        cols = col;
        data = new double[rows*cols];
    }

    Matrix(double[][] tab){
        rows = tab.length;
        cols = 0;

        for(int i = 0; i < rows; ++i){
            if(cols < tab[i].length) cols = tab[i].length;
        }

        data = new double[rows * cols];

        Arrays.fill(data, 0);
        for(int i = 0; i < tab.length; ++i){
            for(int j = 0; j < tab[i].length; ++j){
                data[i*cols + j] = tab[i][j];
            }
        }
    }
    double[][] asArray(){
        double[][] tab = new double[rows][cols];
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                tab[i][j] = data[i * cols + j];
        return tab;
    }

    double get(int row, int col){
        return data[(row)*cols + col];
    }

    void set(int row, int column, double value){
        if(row >= 0 && row < rows && column >= 0 && column < cols){
            double old = data[(row)*cols + column];
            data[(row)*cols + column] = value;
            ++row;
            ++column;
            System.out.println("replaced " + old + " with " + value + " on " + row + " row and " + column + " column");
        }
        else System.out.println("index out of range");
    }

    public String toString(){
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for(int i = 0; i < rows; ++i){
            buf.append("[");
            for(int j = 0; j < cols-1; ++j) {
                buf.append(data[i * cols + j]);
                buf.append(", ");
            }
            buf.append(data[i*cols + cols-1]);
            if(i == rows-1) buf.append("]");
            else buf.append("], ");
        }
        buf.append("]");
        return buf.toString();
    }

    void reshape(int newRows,int newCols){
        if(rows*cols != newRows*newCols)
            throw new RuntimeException(String.format("%d x %d matrix can't be reshaped to %d x %d",
                    rows,cols,newRows,newCols));
        else{
            rows = newRows;
            cols = newCols;
        }
    }

    int[] shape(){
        int[] shape = new int[2];
        shape[0] = rows;
        shape[1] = cols;
        return shape;
    }

    Matrix add(Matrix matrix){
        if(this.rows != matrix.rows && this.cols != matrix.cols)
            throw new RuntimeException(String.format("%d x %d matrix can't be added to %d x %d",
                    this.rows, this.cols, matrix.rows, matrix.cols));
        Matrix sum = new Matrix(rows, cols);
        for (int i = 0; i < data.length; ++i) {
            sum.data[i] = this.data[i] + matrix.data[i];
        }
        return sum;
    }

    Matrix add(double value){
        Matrix sum = new Matrix(rows, cols);
        for(int i = 0; i < data.length; ++i){
            sum.data[i] = this.data[i] + value;
        }
        return sum;
    }

    Matrix sub(Matrix matrix){
        if(this.rows != matrix.rows && this.cols != matrix.cols)
            throw new RuntimeException(String.format("%d x %d matrix can't be added to %d x %d",
                    this.rows, this.cols, matrix.rows, matrix.cols));
        Matrix dif = new Matrix(rows, cols);
        for (int i = 0; i < data.length; ++i) {
            dif.data[i] = this.data[i] - matrix.data[i];
        }
        return dif;
    }

    Matrix sub(double value){
        Matrix dif = new Matrix(rows, cols);
        for(int i = 0; i < data.length; ++i){
            dif.data[i] = this.data[i] - value;
        }
        return dif;
    }

    Matrix mul(Matrix matrix){
        if(this.rows != matrix.rows && this.cols != matrix.cols)
            throw new RuntimeException(String.format("%d x %d matrix can't be added to %d x %d",
                    this.rows,this.cols,matrix.rows,matrix.cols));
        Matrix pro = new Matrix(rows, cols);
        for (int i = 0; i < data.length; ++i) {
            pro.data[i] = this.data[i] * matrix.data[i];
        }
        return pro;
    }

    Matrix mul(double value){
        Matrix pro = new Matrix(rows, cols);
        for(int i = 0; i < data.length; ++i){
            pro.data[i] = this.data[i] * value;
        }
        return pro;
    }

    Matrix div(Matrix matrix){
        if(this.rows != matrix.rows && this.cols != matrix.cols)
            throw new RuntimeException(String.format("%d x %d matrix can't be divided by %d x %d",
                    this.rows, this.cols, matrix.rows, matrix.cols));
        Matrix quo = new Matrix(rows, cols);
        for (int i = 0; i < data.length; ++i) {
            quo.data[i] = this.data[i] / matrix.data[i];
        }
        return quo;
    }

    Matrix div(double value){
        if(value == 0) System.out.println("can't divide by 0");
        Matrix quo = new Matrix(rows, cols);
        for(int i = 0; i < data.length; ++i){
            quo.data[i] = this.data[i] / value;
        }
        return quo;
    }

    Matrix dot(Matrix matrix){
        if(this.cols != matrix.rows)
            throw new RuntimeException("cant multiply, wrong dimensions");
        Matrix pro = new Matrix(rows, matrix.cols);
        for(int i = 0; i < pro.cols; ++i){
            for(int j = 0; j < pro.rows; ++j){
                for(int k = 0; k < this.cols; ++k){
                    pro.data[i * pro.cols + j] += this.data[i * this.cols + k] *
                            matrix.data[k * matrix.cols + j];
                }
            }
        }
        return pro;
    }

    double frobenius(){
        double sum = 0;
        for(int i = 0; i < data.length; ++i){
            sum += data[i]*data[i];
        }
        return sqrt(sum);
    }

    public static Matrix random(int rows, int cols){
        Matrix m = new Matrix(rows,cols);
        Random r = new Random();
        for(int i = 0; i < cols; ++i){
            for(int j = 0; j < rows; ++j){
                m.set(j, i, r.nextInt(100));
            }
        }
        return m;
    }

    public static Matrix eye(int n){
        Matrix m = new Matrix(n,n);
        for(int i = 0; i < n; i += 1){
                m.set(i, i, 1);
        }
        return m;
    }

    Matrix sumCols(){
        Matrix m = new Matrix(new double[rows][1]);

        for(int i = 0; i < rows; ++i){
            for(int j = 0; j < cols; ++j){
                m.data[i] += this.get(i, j);
            }
        }

        return m;
    }
}
