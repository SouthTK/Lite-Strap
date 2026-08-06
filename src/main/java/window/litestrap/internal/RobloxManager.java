package window.litestrap.internal;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RobloxManager {
    /**
     * Check the newest Roblox version and install if not up-to-date
     * @return The newest version folder of Roblox
     */
    public static File getLatestVersion() {
        String latestVersion = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            URL url = new URL("https://clientsettingscdn.roblox.com/v2/client-version/WindowsPlayer");
            JsonNode rootNode = mapper.readTree(url);
            latestVersion = rootNode.get("clientVersionUpload").asText();

        } catch (Exception e) {
            System.err.println("Cannot fetch newest version");
            e.printStackTrace();
        }
        
        String localAppData = System.getenv("LOCALAPPDATA");
        File versionsFolder = new File(localAppData, "Roblox\\Versions");
        File latestVersionFolder = new File(versionsFolder, latestVersion);

        if (latestVersionFolder.exists() && latestVersionFolder.isDirectory()) {
            return latestVersionFolder;
        } else {
            // download new version
            System.out.println("Downloading new version");
            // blah blah, TO DO: implement this
            return null;
        }
    }
    /**
     * Read the ClientAppSettings.json from target folder, and copy to the ClientSettings folder
     * in the version folder 
     * @param versionFolder  The version folder where the ClientSettings is injected
     */
    public static void injectClientSettings(File versionFolder) {
        try {
            String jarPath = RobloxManager.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI().getPath();
            String jarDir = new File(jarPath).getParent();
            Path settingsPath = Paths.get(jarDir, "ClientAppSettings.json");

            if (!Files.exists(settingsPath)) {
                System.out.println("Cannot find " + settingsPath);
            } else {
                System.out.println("Start injecting at " + versionFolder);

                File clientSettingsFolder = new File(versionFolder, "ClientSettings");
                if (!clientSettingsFolder.exists()) {clientSettingsFolder.mkdirs();}

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