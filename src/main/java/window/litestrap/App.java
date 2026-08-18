package window.litestrap;

import java.nio.file.Path;

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
            
        } 
    }
}