package window.litestrap.internal;

import java.util.Scanner;

public class Terminal {

    public static void clear() {
        try {new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();} 
        catch (Exception e) {}
    }

    public static void printManual() {
        Terminal.clear();

        System.out.println("For help with using the programm, please read at the link below.");
        System.out.println("https://github.com/SouthTK/Lite-Strap/blob/main/README.md");
        System.out.println("Press 'Enter' to go back.");

        Scanner terminalScanner = new Scanner(System.in);
        String input = terminalScanner.nextLine();

    }
}