package window.litestrap.internal;

import java.io.InputStream;

import java.nio.file.Files; 
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PackageInstaller {

    public static void installPackage(String version, String fileName) throws Exception {

        String localAppData = System.getenv("LOCALAPPDATA");
        Path versionsFolder = Paths.get(localAppData, "Roblox", "Versions");
        Path latestVersionFolder = versionsFolder.resolve(version);
        Path targetFolder = latestVersionFolder.resolve(PackageMap.get(fileName)); 

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        // Check if latest version folder did not exist, create
        if (!Files.exists(latestVersionFolder.resolve("RobloxPlayerBeta.exe"))) {
            Files.createDirectories(latestVersionFolder);
            System.out.println("Created directory: " + latestVersionFolder);
        }

        String zipUrl = "https://setup.rbxcdn.com/" + version + "-" + fileName;
        System.out.println("Downloading from: " + zipUrl);

        HttpRequest downloadReq = HttpRequest.newBuilder(URI.create(zipUrl)).GET().build();
        HttpResponse<InputStream> response = client.send(downloadReq, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to download zip! HTTP Status Code: " + response.statusCode());
        }

        try (ZipInputStream zis = new ZipInputStream(response.body())) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path filePath = targetFolder.resolve(entry.getName()).normalize();
                
                System.out.println("Target folder: " + targetFolder);
                System.out.println("File : " + entry.getName());
                System.out.println("Will be in " + filePath);

                // Zip Slip (path traversal) vulnerability & "\" check
                if (!filePath.startsWith(targetFolder)) {continue;}

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