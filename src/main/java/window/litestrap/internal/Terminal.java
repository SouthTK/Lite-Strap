package window.litestrap.internal;

import java.util.Scanner;

public class Terminal {

    public static void clear() {
        try {new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();} 
        catch (Exception e) {}
    }

    public static void printHelpOption() {
        Terminal.clear();

        System.out.println("For help with using the programm, please read at the link below.");
        System.out.println("https://github.com/SouthTK/Lite-Strap/blob/main/README.md");
        System.out.println("Press 'Enter' to go back.");

        Scanner terminalScanner = new Scanner(System.in);
        String input = terminalScanner.nextLine();

    }

    public static void printUrlOption() {
        boolean validity = true; 
        Terminal.clear();

        // while (true) {
        //     System.out.println("To return, please press 'Enter'");
        //     System.out.println("To join Roblox with URL, please enter the URL");

        //     try {
        //         Scanner terminalScanner = new Scanner(System.in);
        //         String link = terminalScanner.nextLine();

        //         if (link.equals("")) {
        //             return;
        //         } else {
        //             if (!RobloxLauncher.launchRobloxWithLink(link)) {
        //                 System.out.println("Invalid URL");}
        //         }


        //     } catch (Exception e) {}
        // }

    }
}