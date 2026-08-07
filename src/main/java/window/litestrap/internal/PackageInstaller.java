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
        Path targetVersionFolder = latestVersionFolder.resolve(PackageMap.get(fileName)); 

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        // // Check if latest version folder already exists, else create
        if (Files.exists(latestVersionFolder.resolve("RobloxPlayerBeta.exe"))) {
            System.out.println("Version " + version + " is already installed!");
            return;
        } else {
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

        //TO DO: Recheck and implement these, might need fix
        // try (ZipInputStream zis = new ZipInputStream(response.body())) {
        //     ZipEntry entry;
        //     while ((entry = zis.getNextEntry()) != null) {
        //         // Resolve entry destination path safely
        //         Path filePath = targetVersionFolder.resolve(entry.getName()).normalize();

        //         // Zip Slip vulnerability check
        //         if (!filePath.startsWith(targetVersionFolder)) {
        //             throw new SecurityException("Bad entry path in zip: " + entry.getName());
        //         }

        //         if (entry.isDirectory()) {
        //             Files.createDirectories(filePath);
        //         } else {
        //             // Ensure parent directory exists before writing file
        //             Files.createDirectories(filePath.getParent());
        //             Files.copy(zis, filePath, StandardCopyOption.REPLACE_EXISTING);
        //         }
        //         zis.closeEntry();
        //     }
        // }
    }

}