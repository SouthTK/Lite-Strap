package window.litestrap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import window.litestrap.internal.ProtocolRegister;
import window.litestrap.internal.RobloxManager;

public class App {

    public static void main(String[] args) {
        // Check if launched by Windows Browser Link
        if (args.length > 0 && args[0].startsWith("roblox-player:")) {
            File latestVersion = RobloxManager.getLatestVersion();

            // Inject settings and launch Roblox
            if (latestVersion != null) {
                RobloxManager.injectClientSettings(latestVersion);
                RobloxManager.launchRoblox(latestVersion, args);
            } else {System.out.println("Roblox not found");}
            return;
        // Otherwise
        } else {
            //File latestVersion = RobloxManager.getLatestVersion();
            // do the protocol binding or UI?
        }
    }
}