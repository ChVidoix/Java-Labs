import java.util.Scanner;

public class Fibo {
    public static void main(String[] args) {
        Scanner buf = new Scanner(System.in);
        int x;
        do{
            System.out.println("podaj int z zakresu 1-45");
            x = buf.nextInt();
        } while (x < 1 || x > 45);
        int[] tab = new int[x];
        tab[0] = 0;
        if(x > 0){
            tab[1] = 1;
            for(int i = 2; i < tab.length; ++i){
                tab[i] = tab[i-2] + tab[i-1];
            }
        }
        for(int i = 0; i < tab.length; ++i){
            if(i == tab.length - 1) System.out.print(tab[i]);
            else System.out.print(tab[i] + ", ");
        }
    }
}
