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
    public static Path getLatestVersion() {
        String latestVersion = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            String versionUri = "https://clientsettingscdn.roblox.com/v2/client-version/WindowsPlayer";
            URL versionUrl = URI.create(versionUri).toURL();

            JsonNode rootNode = mapper.readTree(versionUrl);
            latestVersion = rootNode.get("clientVersionUpload").asText();

        } catch (Exception e) {
            System.err.println("Cannot fetch newest version");
            e.printStackTrace();
            return null;
        }
        
        String localAppData = System.getenv("LOCALAPPDATA");
        Path versionsFolder = Path.of(localAppData, "Roblox", "Versions");
        Path temporaryFolder = versionsFolder.resolve(latestVersion + "-temp");
        Path latestVersionFolder = versionsFolder.resolve(latestVersion);

        if (Files.isDirectory(latestVersionFolder)) {
            return latestVersionFolder;
        } else {
            try {
                String manifestUri = "https://setup.rbxcdn.com/" + latestVersion + "-rbxPkgManifest.txt";
                URL manifestUrl = URI.create(manifestUri).toURL();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(manifestUrl.openStream()))) {

                    String header = reader.readLine(); 
                    if (header == null || !header.trim().equalsIgnoreCase("v0")) {
                        throw new IllegalStateException("Invalid manifest format or header: " + header);
                    }

                    String nameLine;
                    while ((nameLine = reader.readLine()) != null) {
                        if (nameLine.trim().isEmpty()) continue;

                        String name = nameLine.trim();
                        String md5 = reader.readLine().trim();
                        long compressedSize = Long.parseLong(reader.readLine().trim());
                        long uncompressedSize = Long.parseLong(reader.readLine().trim());

                        try {
                            PackageInstaller.installPackage(latestVersion, name, temporaryFolder);
                        } catch (Exception e) {
                            System.err.println("Failed to download " + name);
                            e.printStackTrace();
                            }
                    }
                } catch (Exception e) {
                    System.err.println("Failed to read manifest");
                    return null;
                }
            } catch (Exception e) {
                System.err.println("Failed to get manifest");
                return null;
                }

            try {Files.move(temporaryFolder, latestVersionFolder);} catch (IOException e) {
                // try rename again?
                // clear the folder?
            }

            return latestVersionFolder;
        }
    }

    // public static void deleteOldVersion() {

    // }

    /**
     * Read the ClientAppSettings.json from target folder, and copy to the ClientSettings folder
     * in the version folder 
     * @param versionFolder  The version folder where the ClientSettings is injected
     */
    public static void injectClientSettings(File versionFolder) {
        try {
            String jarPath = RobloxManager.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI().getPath();
            String jarDir = new File(jarPath).getParent();
            Path settingsPath = Paths.get(jarDir, "ClientAppSettings.json");

            if (!Files.exists(settingsPath)) {
                System.out.println("Cannot find " + settingsPath);
            } else {
                System.out.println("Start injecting at " + versionFolder);

                //Path clientSettingsPath = versionPath.resolve(ClientSettings);
                //Files.createDirectories(targetRootPath);
                File clientSettingsFolder = new File(versionFolder, "ClientSettings");
                if (!clientSettingsFolder.exists()) {clientSettingsFolder.mkdirs();}

                Path destinationPath = clientSettingsFolder.toPath().resolve("ClientAppSettings.json");
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