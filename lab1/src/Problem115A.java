import java.util.Scanner;

public class Problem115A {
    public static void main(String[] args) {
        int n, ans = 0, count;
        Scanner buf = new Scanner(System.in);
        System.out.println("podaj liczbe pracownikow");
        n = buf.nextInt();
        int[] a = new int[n + 1];
        for(int i = 1; i <= n; ++i) {
            System.out.println("podaj przelozolego " + i + " pracownika");
            a[i] = buf.nextInt();
        }
        for(int i = 1 ; i <= n ; ++i)
        {
            count = 0;
            int x = a[i];
            while(x != -1)
            {
                x = a[x];
                count++;
            }
            if(count > ans) ans = count;
        }
        System.out.println(ans + 1);
    }
}
