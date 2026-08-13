package window.litestrap;

import java.nio.file.Path;

import window.litestrap.internal.ProtocolRegister;
import window.litestrap.internal.RobloxManager;
import window.litestrap.internal.RobloxInstaller;

public class App {

    public static void main(String[] args) {
        // Check if launched by Windows Browser Link
        if (args.length > 0 && args[0].startsWith("roblox-player:")) {
            // File latestVersion = RobloxManager.getLatestVersion();

            // // Inject settings and launch Roblox
            // if (latestVersion != null) {
            //     RobloxManager.injectClientSettings(latestVersion);
            //     RobloxManager.launchRoblox(latestVersion, args);
            // } else {System.out.println("Roblox not found");}
            // return;
            
        } else if (args.length > 0 && args[0].startsWith("install-test")) {
            String latestVersion = RobloxManager.getLatestVersion();
            boolean status = RobloxInstaller.installRoblox(latestVersion);
        }
        // Otherwise
        // } else {
        //     //File latestVersion = RobloxManager.getLatestVersion();
        //     // do the protocol binding or UI?
        // }
    }
}