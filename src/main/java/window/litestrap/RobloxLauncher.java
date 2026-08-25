package window.litestrap;

import window.litestrap.roblox.RobloxManager;
import window.litestrap.roblox.RobloxInstaller;

public class RobloxLauncher {
    public static void launchRoblox(String arg) {
        String latestVersion = RobloxManager.getLatestVersion();
        boolean installStatus = RobloxInstaller.installRoblox(latestVersion);
        boolean cleanStatus = RobloxInstaller.clearOldVersion(latestVersion);

        if (installStatus && latestVersion != null) {
            RobloxManager.injectClientSettings(latestVersion);
            RobloxManager.startRoblox(latestVersion, arg);
        } else {System.out.println("Roblox Not Found.");}
        return;
    }
// need fix
    public static void joinPrivateServer(String arg) {
        //String uri = "roblox://placeId=2753915549&linkCode=91514910542598960805867636178552";
        String latestVersion = RobloxManager.getLatestVersion();
        boolean installStatus = RobloxInstaller.installRoblox(latestVersion);
        boolean cleanStatus = RobloxInstaller.clearOldVersion(latestVersion);

        RobloxManager.runRobloxUri(latestVersion, arg);
    }
}