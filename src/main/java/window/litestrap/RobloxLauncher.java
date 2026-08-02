package window.litestrap;

import java.io.File;

import window.litestrap.internal.RobloxManager;

public class RobloxLauncher {

    public static void main(String[] args) {
        // Find latest roblox version
        String localAppData = System.getenv("LOCALAPPDATA");
        File versionsFolder = new File(localAppData, "Roblox\\Versions");
        File latestVersion = RobloxManager.getLatestVersion(versionsFolder);

        // Inject settings and launch Roblox
        if (latestVersion != null) {
            RobloxManager.injectClientSettings(latestVersion);
            RobloxManager.launchRoblox(latestVersion, args);
        } else {System.out.println("Roblox not found");}
    }
}