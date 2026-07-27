package window.litestrap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RobloxLauncher {

    public static void main(String[] args) {
        // Find latest roblox version
        String localAppData = System.getenv("LOCALAPPDATA");
        File versionsFolder = new File(localAppData, "Roblox\\Versions");
        File latestVersionDir = getLatestVersionFolder(versionsFolder);

        if (latestVersionDir != null) {
            injectClientSettings(latestVersionDir);
            //launchRoblox(latestVersionDir, args);
        } else {
            System.err.println("Could not locate a valid Roblox installation.");
        }
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
            File clientSettings = new File(jarDir, "ClientSettings.json");

            // Injecting ClientSettings.json
            if (!clientSettings.exists()) {
                System.out.println("Cannot find " + clientSettings.getAbsolutePath());
                return;
            } else {
                System.out.println("Start injecting at " + versionFolder);
                File clientSettingsDir = new File(versionFolder, "ClientSettings");
                if (!clientSettingsDir.exists()) {clientSettingsDir.mkdirs();}
            }

        } catch (Exception e) {e.printStackTrace();}

        

        // File settingsFile = new File(clientSettingsDir, "ClientAppSettings.json");

        // // Your custom JSON content/FastFlags // fix this to read from somewhere
        // String jsonContent = "{\n" +
        //         "  \"DFIntTaskSchedulerTargetFps\": 144\n" +
        //         "}";

        // try (FileWriter writer = new FileWriter(settingsFile)) {
        //     writer.write(jsonContent);
        //     System.out.println("Injected ClientAppSettings into: " + settingsFile.getAbsolutePath());
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
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