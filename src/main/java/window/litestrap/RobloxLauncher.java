package window.litestrap;

import window.litestrap.roblox.RobloxManager;
import window.litestrap.roblox.RobloxInstaller;
import window.litestrap.roblox.UriMap;

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

    public static boolean launchRobloxWithLink(String link) {
        String uri = UriMap.getUri(link);

        if (uri != null ) {
            String latestVersion = RobloxManager.getLatestVersion();
            boolean installStatus = RobloxInstaller.installRoblox(latestVersion);
            boolean cleanStatus = RobloxInstaller.clearOldVersion(latestVersion);
            RobloxManager.runRobloxUri(latestVersion, uri);
            return true;
            
        } else {return false;}

    }
}