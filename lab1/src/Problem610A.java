import java.util.Scanner;

public class Problem610A {
    public static void main(String[] args) {
        Scanner buf = new Scanner(System.in);
        System.out.println("podaj int");
        int n = buf.nextInt();
        if(n % 2 == 1){
            System.out.println("0");
            return;
        }
        if(n % 4 == 0) {
            System.out.printf("%d", n/4 - 1);
            return;
        }
        System.out.printf("%d", n/4);
    }
}
