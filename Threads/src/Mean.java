import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Mean {
    static double[] array;
    static BlockingQueue<Double> results = new ArrayBlockingQueue<>(100);
    static void initArray(int size){
        array = new double[size];
        for(int i=0;i<size;i++){
            array[i]= Math.random()*size;
        }
    }

    static class MeanCalc extends Thread{
        private final int start;
        private final int end;
        double mean = 0;

        MeanCalc(int start, int end){
            this.start = start;
            this.end = end;
        }



        public void run() {
            double sum = 0;
            for (int i = start; i < end; ++i) {
                sum += array[i];
            }
            mean = sum / (end - start);
            try {
                results.put(mean);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf(Locale.US, "%d-%d mean=%f\n", start, end, mean);
        }
    }

    /**
     * Oblicza średnią wartości elementów tablicy array uruchamiając równolegle działające wątki.
     * Wypisuje czasy operacji
     * @param cnt - liczba wątków
     */

    static void parallelMean(int cnt) throws InterruptedException {
        MeanCalc[] threads = new MeanCalc[cnt];
        for(int i = 0; i < cnt; ++i){
            threads[i] = new MeanCalc(i*array.length/cnt, (i+1)*(array.length/cnt));
        }

        double t1 = System.nanoTime()/1e6;

        for (MeanCalc thread : threads) {
            thread.start();
        }

        double t2 = System.nanoTime()/1e6;

//        for(MeanCalc thread:threads) {
//            thread.join();
//        }

        double sum = 0;
        for (MeanCalc thread : threads) {
//            sum += thread.mean;
            sum += results.take();
        }

        final double mean = sum/cnt;

        double t3 = System.nanoTime()/1e6;

        System.out.printf(Locale.US,"size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n",
                array.length,
                cnt,
                t2-t1,
                t3-t1,
                mean);
    }

    public static void main(String[] args) throws InterruptedException {
        initArray(100000000);
        parallelMean(4);
    }
}