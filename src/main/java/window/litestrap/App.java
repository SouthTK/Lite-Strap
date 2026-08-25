package window.litestrap;

import java.nio.file.Path;

import java.util.Scanner;

import window.litestrap.internal.ProtocolRegister;
import window.litestrap.internal.RobloxManager;
import window.litestrap.internal.RobloxInstaller;

public class App {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].startsWith("roblox-player:")) {
            // String latestVersion = RobloxManager.getLatestVersion();
            // boolean installStatus = RobloxInstaller.installRoblox(latestVersion);
            // boolean cleanStatus = RobloxInstaller.clearOldVersion(latestVersion);

            // // Inject settings and launch Roblox
            // if (installStatus && latestVersion != null) {
            //     RobloxManager.injectClientSettings(latestVersion);
            //     RobloxManager.launchRoblox(latestVersion, args);
            // } else {System.out.println("Roblox Not Found.");}
            // return;

        } else if (args.length > 0 && args[0].startsWith("bind-roblox")) {
            ProtocolRegister.registerProtocol();

        } else if (args.length > 0 && args[0].startsWith("unbind-roblox")) {
            // unbind

        } else if (args.length > 0 && args[0].startsWith("launch-ui")) {
            System.out.println("Not implemented");

        } else if (args.length > 0 && args[0].startsWith("install-test")) {
            String latestVersion = RobloxManager.getLatestVersion();
            boolean installStatus = RobloxInstaller.installRoblox(latestVersion);
            boolean cleanStatus = RobloxInstaller.clearOldVersion(latestVersion);

        } else if (args.length > 0 && args[0].startsWith("inject-test")) {
            String latestVersion = RobloxManager.getLatestVersion();
            if (latestVersion != null) {
                RobloxManager.injectClientSettings(latestVersion);
            } else {System.out.println("Error");}
            
        } else {
            System.out.println("  _       _   _                        ____    _  ");
            System.out.println(" | |     (_) | |_    ___              / ___|  | |_   _ __    __ _   _ __  ");
            System.out.println(" | |     | | | __|  / _ \\    _____    \\___ \\  | __| | '__|  / _` | | '_ \\ ");
            System.out.println(" | |___  | | | |_  |  __/   |_____|    ___) | | |_  | |    | (_| | | |_) |");
            System.out.println(" |_____| |_|  \\__|  \\___|             |____/   \\__| |_|     \\__,_| | .__/ ");
            System.out.println("                                                                   |_|");
            System.out.println("[1] Bind Roblox (not implemented)");
            System.out.println("[2] Unind Roblox (not implemented)");
            System.out.println("[3] Join Roblox Private Server");
            System.out.println("[4] Quit");

            int userInput = 0;
            while (userInput != 4) {
                try {
                    Scanner terminalScanner = new Scanner(System.in);
                    userInput = terminalScanner.nextInt();
                } catch (Exception e) {System.out.println("Can't read input.");}

                if (userInput == 1) {
                    // placeholder
                } else if (userInput == 2) {
                    // placeholder
                } else if (userInput == 3) {
                    System.out.println("Private server link:");

                    try {
                        Scanner terminalScanner = new Scanner(System.in);
                        String link = terminalScanner.nextLine();
                    } catch (Exception e) {System.out.println("Can't read input.");}

                    String latestVersion = RobloxManager.getLatestVersion();
                    boolean installStatus = RobloxInstaller.installRoblox(latestVersion);
                    boolean cleanStatus = RobloxInstaller.clearOldVersion(latestVersion);

                    // Inject settings and launch Roblox
                    if (installStatus && latestVersion != null) {
                        //RobloxManager.injectClientSettings(latestVersion);
                        //RobloxManager.launchRoblox(latestVersion, args);
                    } else {System.out.println("Roblox Not Found.");}
                    return;
                } else {return;}
            }
        }
    }
}