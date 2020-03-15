import static java.lang.StrictMath.sqrt;
import static org.junit.Assert.*;

public class MatrixTest {

    @org.junit.Test
    public void testMatrix_1() {
        Matrix test = new Matrix(2, 3);
        assertEquals(test.rows, 2);
        assertEquals(test.cols, 3);
    }

    @org.junit.Test
    public void testMatrix_2() {
        Matrix test = new Matrix(new double[][]{{1,2,3,4,5},{3,4,2,4},{6,5,3,1}});
        double [][] arr = test.asArray();
        assertEquals(arr[1].length, test.cols);
        assertEquals(arr[1][4], 0, 0);
    }

    @org.junit.Test
    public void testGet() {
        Matrix test = new Matrix(new double[][]{{1,2},{3,4}});
        assertEquals(test.get(0,0), 1, 0);
    }

    @org.junit.Test
    public void testSet() {
        Matrix test = new Matrix(new double[][]{{1,2},{3,4}});
        test.set(0,1,5);
        assertEquals(test.get(0,1), 5, 0);
    }

    @org.junit.Test
    public void testAsArray() {
        double[][] d = new double[][]{{1,2},{3,4},{5,6}};
        Matrix test = new Matrix(d);
        for(int i=0; i < test.rows; ++i){
            assertArrayEquals(test.asArray()[i], d[i], 0);
        }
    }

    @org.junit.Test
    public void testToString() {
        String s= "[[1.0,2.3,4.56], [12.3,  45, 21.8]]";
        s = s.replaceAll("(\\[|]|\\s)+","");
        String[] t = s.split("(,)+");
        for(String x:t){
            System.out.println(String.format("\'%s\'",x ));
        }

        double[] d = new double[t.length];
        for(int i = 0;i < t.length; ++i) {
            d[i] = Double.parseDouble(t[i]);
        }

        double[][] arr = new double[1][];
        arr[0]=d;

        for (double[] doubles : arr) {
            for (double aDouble : doubles) {
                System.out.println(aDouble);
            }
        }
    }

    @org.junit.Test
    public void testReshape() {

    }

    @org.junit.Test
    public void testAdd_1() {
        Matrix test = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        assertEquals(test.add(5).get(1, 1), 10, 0);
    }

    @org.junit.Test
    public void testAdd_2() {
        Matrix test1 = new Matrix(new double[][]{{1,2},{3,4}});
        Matrix test2 = new Matrix(new double[][]{{4,3},{2,1}});
        test1.add(test2);
        double[] arr = {5, 5, 5, 5};
        assertArrayEquals(arr, test1.add(test2).data, 0);
    }

    @org.junit.Test
    public void testSub_1() {
        Matrix test = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        assertEquals(test.sub(4).get(1, 0), 0, 0);
    }

    @org.junit.Test
    public void testSub_2() {
        Matrix test = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        assertEquals(test.sub(test).frobenius(), 0, 0);
    }

    @org.junit.Test
    public void testMul_1() {
        Matrix test = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        assertEquals(test.mul(-1).add(test).frobenius(), 0, 0);
    }

    @org.junit.Test
    public void testMul_2() {
        Matrix test = Matrix.eye(4);
        assertArrayEquals(test.mul(test).data, test.data, 0);
    }

    @org.junit.Test
    public void testDiv_1() {
        Matrix test = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        assertEquals(test.div(4).get(1,1), 1.25,0);
    }

    @org.junit.Test
    public void testDiv_2() {
        Matrix test = new Matrix(new double[][]{{1,2,3},{4,5,6}});
        assertEquals(test.div(test).frobenius(), sqrt(6),0);
    }

    @org.junit.Test
    public void testDot() {
        Matrix testMatrix1 = new Matrix(new double[][]{{1,2,3},{4,5}});
        Matrix testMatrix2 = new Matrix(new double[][]{{1,2},{3,3},{5,4}});
        double[] testTab = new double[]{22, 20, 19, 23};
        assertArrayEquals(testMatrix1.dot(testMatrix2).data, testTab, 0);
    }

    @org.junit.Test
    public void testEye() {
        assertEquals(Matrix.eye(6).frobenius(), sqrt(6), 0);
    }

}