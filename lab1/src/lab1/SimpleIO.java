package lab1;
import org.w3c.dom.ls.LSOutput;

import java.util.Scanner;
import java.util.Locale;

public class SimpleIO {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("podaj string");
        String s = scan.next();
        System.out.println("podaj int");
        int i = scan.nextInt();
        System.out.println("podaj double");
        double d = scan.nextDouble();
        System.out.printf(Locale.US, "Wczytano %s , %d, %f", s, i, d);
        }
}