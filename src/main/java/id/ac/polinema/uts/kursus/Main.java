
package id.ac.polinema.uts.kursus;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Academy academy = new Academy("Polinema Academy");

        AcademyApp app = new AcademyApp(
                academy,
                in,
                System.out
        );

        app.run();
    }
}
