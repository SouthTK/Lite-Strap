package window.litestrap.internal;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.net.URL;
import java.net.URI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RobloxManager {
    /**
     * Check the newest Roblox version and install if not up-to-date
     * @return The newest version folder of Roblox
     */
    public static String getLatestVersion() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String versionUri = "https://clientsettingscdn.roblox.com/v2/client-version/WindowsPlayer";
            URL versionUrl = URI.create(versionUri).toURL();

            JsonNode rootNode = mapper.readTree(versionUrl);
            return rootNode.get("clientVersionUpload").asText();

        } catch (Exception e) {
            System.err.println("Cannot fetch newest version");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Read the ClientAppSettings.json from target folder, and copy to the ClientSettings folder
     * in the version folder 
     * @param versionFolder  The version folder where the ClientSettings is injected
     */
    public static void injectClientSettings(String version) {
        try {
            String jarPath = RobloxManager.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI().getPath();
            String jarDir = new File(jarPath).getParent();
            Path settingsPath = Paths.get(jarDir, "ClientAppSettings.json"); //could change

            if (!Files.exists(settingsPath)) {
                System.out.println("Cannot find " + settingsPath);
            } else {
                System.out.println("Start injecting for " + version);

                String localAppData = System.getenv("LOCALAPPDATA");
                Path versionsFolder = Path.of(localAppData, "Roblox", "Versions");
                Path targetFolder = versionsFolder.resolve(version);

                Path clientSettingsPath = targetFolder.resolve("ClientSettings");
                Files.createDirectories(clientSettingsPath);

                Path destinationPath = clientSettingsPath.resolve("ClientAppSettings.json");
                Files.copy(settingsPath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                
                System.out.println("Injecting settings succeed");
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    public static void launchRoblox(File versionFolder, String[] args) {
        try {
            File exe = new File(versionFolder, "RobloxPlayerBeta.exe");
            ProcessBuilder pb = new ProcessBuilder();
            
            if (args.length > 0) {
                pb.command(exe.getAbsolutePath(), args[0]);
            } else {pb.command(exe.getAbsolutePath());}

            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}