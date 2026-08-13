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