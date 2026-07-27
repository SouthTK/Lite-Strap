package window.litestrap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class RobloxLauncher {

    public static void main(String[] args) {
        // Find latest roblox version
        String localAppData = System.getenv("LOCALAPPDATA");
        File versionsFolder = new File(localAppData, "Roblox\\Versions");
        File latestVersionFolder = getLatestVersionFolder(versionsFolder);

        // Inject settings and launch Roblox
        if (latestVersionDir != null) {
            injectClientSettings(latestVersionFolder);
            //launchRoblox(latestVersionFolder, args);
        } else {System.out.println("Roblox not found");}
    }

    private static File getLatestVersionFolder(File parentDir) {
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

    private static void injectClientSettings(File versionFolder) {
        try {
            // Reading ClientSettings.json
            String jarPath = RobloxLauncher.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI().getPath();
            String jarDir = new File(jarPath).getParent();
            File settingsJson = new File(jarDir, "ClientAppSettings.json");

            // Injecting ClientSettings.json
            if (!settingsJson.exists()) {
                System.out.println("Cannot find " + settingsJson.getAbsolutePath());
                return;
            } else {
                System.out.println("Start injecting at " + versionFolder);

                //Checking ClientSettings folder existance
                File clientSettingsFolder = new File(versionFolder, "ClientSettings");
                if (!clientSettingsFolder.exists()) {clientSettingsFolder.mkdirs();}

                // Copy ClientAppSettings.json to ClientSettings folder
                Path jsonPath = settingsJson.toPath();
                Path destinationPath = clientSettingsFolder.toPath().resolve(jsonPath.getFileName());
                Files.copy(jsonPath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    private static void launchRoblox(File versionFolder, String[] args) {
        try {
            File exe = new File(versionFolder, "RobloxPlayerBeta.exe");
            ProcessBuilder pb = new ProcessBuilder();
            
            if (args.length > 0) {
                // Pass along Roblox launch parameters (e.g. roblox-player://...)
                pb.command(exe.getAbsolutePath(), args[0]);
            } else {
                pb.command(exe.getAbsolutePath());
            }

            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}