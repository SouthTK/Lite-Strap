package window.litestrap;

import java.nio.file.Path;

import java.util.Scanner;

import window.litestrap.internal.ProtocolRegister;
import window.litestrap.internal.Terminal;
import window.litestrap.roblox.RobloxManager;
import window.litestrap.roblox.RobloxInstaller;

public class App {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].startsWith("roblox-player:")) {
            RobloxLauncher.launchRoblox(args[0]);

        } else if (args.length > 0 && args[0].startsWith("terminal")) {

            int userInput = 0;
            while (userInput != 6) {
                Terminal.clear();
                System.out.println("  _       _   _                        ____    _  ");
                System.out.println(" | |     (_) | |_    ___              / ___|  | |_   _ __    __ _   _ __  ");
                System.out.println(" | |     | | | __|  / _ \\    _____    \\___ \\  | __| | '__|  / _` | | '_ \\ ");
                System.out.println(" | |___  | | | |_  |  __/   |_____|    ___) | | |_  | |    | (_| | | |_) |");
                System.out.println(" |_____| |_|  \\__|  \\___|             |____/   \\__| |_|     \\__,_| | .__/ ");
                System.out.println("                                                                   |_|");
                System.out.println("[1] Bind Roblox");
                System.out.println("[2] Unind Roblox");
                System.out.println("[3] Apply settings");
                System.out.println("[4] Join with URL");
                System.out.println("[5] Help");
                System.out.println("[6] Quit");

                try {
                    Scanner terminalScanner = new Scanner(System.in);
                    userInput = terminalScanner.nextInt();
                } catch (Exception e) {}

                if (userInput == 1) {
                    // not implemented
                } else if (userInput == 2) {
                    // not implemented
                    
                } else if (userInput == 3) {
                    // try finding json?
                    // install if outdated
                    // inject into Roblox

                } else if (userInput == 4) {
                    Terminal.clear();
                    System.out.println("To return, please press 'Enter'");
                    System.out.println("To join Roblox with URL, please enter the URL");

                    try {
                        Scanner terminalScanner = new Scanner(System.in);
                        String link = terminalScanner.nextLine();

                        if (!link.equals("")) {RobloxLauncher.launchRobloxWithLink(link);}

                    } catch (Exception e) {System.err.println("Invalid input");}

                } else if (userInput == 5) {
                    Terminal.printManual();
                } else {return;}
            }

/**
 * Code below is test only
*/

        } else if (args.length > 0 && args[0].startsWith("install-test")) {
            String latestVersion = RobloxManager.getLatestVersion();
            boolean installStatus = RobloxInstaller.installRoblox(latestVersion);
            boolean cleanStatus = RobloxInstaller.clearOldVersion(latestVersion);

        } else if (args.length > 0 && args[0].startsWith("inject-test")) {
            String latestVersion = RobloxManager.getLatestVersion();
            if (latestVersion != null) {
                RobloxManager.injectClientSettings(latestVersion);
            } else {System.out.println("Error");}
            
        } 
    }
}