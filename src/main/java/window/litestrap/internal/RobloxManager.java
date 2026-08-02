package window.litestrap.internal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class RobloxManager {

    public static File getLatestVersion(File parentDir) {
        File[] files = parentDir.listFiles();
        File latest = null;
        long lastModified = 0;

        if (files != null) {
            for (File file : files) {
                File exe = new File(file, "RobloxPlayerBeta.exe");
                if (file.isDirectory() && exe.exists()) {
                    if (file.lastModified() > lastModified) {
                        lastModified = file.lastModified();
                        latest = file;
                    }
                }
            }
        }
        return latest;
    }

    public static void injectClientSettings(File versionFolder) {
        try {
            // Get current directory of the JAR file & path of ClientAppSettings.json
            String jarPath = RobloxManager.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI().getPath();
            String jarDir = new File(jarPath).getParent();
            Path settingsPath = Paths.get(jarDir, "ClientAppSettings.json");

            if (!Files.exists(settingsPath)) {
                System.out.println("Cannot find " + settingsPath);
            } else {
                System.out.println("Start injecting at " + versionFolder);

                //Checking ClientSettings folder existance
                File clientSettingsFolder = new File(versionFolder, "ClientSettings");
                if (!clientSettingsFolder.exists()) {clientSettingsFolder.mkdirs();}

                // Copy ClientAppSettings.json from current location to ClientSettings folder
                Path destinationPath = clientSettingsFolder.toPath().resolve("ClientAppSettings.json");
                Files.copy(settingsPath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    public static void launchRoblox(File versionFolder, String[] args) {
        try {
            File exe = new File(versionFolder, "RobloxPlayerBeta.exe");
            ProcessBuilder pb = new ProcessBuilder();
            
            if (args.length > 0) {
                // Pass along Roblox launch parameters (e.g. roblox-player://...)
                pb.command(exe.getAbsolutePath(), args[0]);
            } else {pb.command(exe.getAbsolutePath());}

            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}