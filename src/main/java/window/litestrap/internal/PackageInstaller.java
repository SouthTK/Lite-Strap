package window.litestrap.internal;

import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.nio.file.Files; 
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.net.URL;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PackageInstaller {
    // this will be the installer
    public static boolean installRoblox(String version) {
        String localAppData = System.getenv("LOCALAPPDATA");
        Path versionsFolder = Path.of(localAppData, "Roblox", "Versions");
        Path targetFolder = versionsFolder.resolve(version);
        Path temporaryFolder = versionsFolder.resolve(version + "-temp");

        if (Files.isDirectory(targetFolder)) {
            System.out.println(version + " is already installed.");
            return true;
        } else {
            try {
                String manifestUri = "https://setup.rbxcdn.com/" + version + "-rbxPkgManifest.txt";
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
                            PackageInstaller.installPackage(version, name, temporaryFolder);
                        } catch (Exception e) {
                            System.err.println("Failed to download " + name);
                            e.printStackTrace();
                            }
                    }
                } catch (Exception e) {
                    System.err.println("Failed to read rbxPkgManifest.txt.");
                    return false;
                }
            } catch (Exception e) {
                System.err.println("Failed to get rbxPkgManifest.txt.");
                return false;
                }

            try {Files.move(temporaryFolder, targetFolder);} catch (IOException e) {
                // try rename again?
                // clear the folder?
            }
            return true;
        }
    }

    /**
     * Install and unzip the Zip file of the chosen version into the targetRootPath
     * @param version  The version
     * @param fileName  The name of the Zip file being installed
     * @param targetRootPath  The root where the installation happened (should be the version folder)
     */
    public static void installPackage(String version, String fileName, Path targetRootPath) throws Exception {

        if (!Files.exists(targetRootPath.resolve("RobloxPlayerBeta.exe"))) {
            Files.createDirectories(targetRootPath);
            System.out.println("Created directory: " + targetRootPath);
        }

        Path relativePath = PackageMap.get(fileName);
        if (relativePath == null || !Files.exists(targetRootPath)) {return;}
        Path targetPath = targetRootPath.resolve(relativePath);

        String zipUrl = "https://setup.rbxcdn.com/" + version + "-" + fileName;
        System.out.println("Downloading from: " + zipUrl);

        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
        HttpRequest downloadReq = HttpRequest.newBuilder(URI.create(zipUrl)).GET().build();
        HttpResponse<InputStream> response = client.send(downloadReq, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Error occur. HTTP Code: " + response.statusCode());
        }
        // might need check if a zip
        try (ZipInputStream zis = new ZipInputStream(response.body())) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path filePath = targetPath.resolve(entry.getName()).normalize();

                // Zip Slip (path traversal) vulnerability & "\" check
                if (!filePath.startsWith(targetPath)) {continue;}

                if (entry.isDirectory()) {
                    Files.createDirectories(filePath);
                } else {
                    Files.createDirectories(filePath.getParent());
                    Files.copy(zis, filePath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println(filePath);
                }
                zis.closeEntry();
            }
        } catch (Exception e) {}
    }

}