package window.litestrap.internal;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class PackageMap {

    private static final Map<String, Path> packageMap = Map.ofEntries(
        Map.entry("Libraries.zip",                  Paths.get("")),
        Map.entry("shaders.zip",                    Paths.get("shaders")),
        Map.entry("ssl.zip",                        Paths.get("ssl")),

        Map.entry("WebView2.zip",                  Paths.get("")),
        Map.entry("WebView2RuntimeInstaller.zip",  Paths.get("WebView2RuntimeInstaller")),

        Map.entry("content-avatar.zip",            Paths.get("content", "avatar")),
        Map.entry("content-configs.zip",           Paths.get("content", "configs")),
        Map.entry("content-fonts.zip",             Paths.get("content", "fonts")),
        Map.entry("content-sky.zip",               Paths.get("content", "sky")),
        Map.entry("content-sounds.zip",            Paths.get("content", "sounds")),
        Map.entry("content-textures2.zip",         Paths.get("content", "textures")),
        Map.entry("content-models.zip",            Paths.get("content", "models")),

        Map.entry("content-textures3.zip",         Paths.get("PlatformContent", "pc", "textures")),
        Map.entry("content-terrain.zip",           Paths.get("PlatformContent", "pc", "terrain")),
        Map.entry("content-platform-fonts.zip",    Paths.get("PlatformContent", "pc", "fonts")),

        Map.entry("extracontent-luapackages.zip",  Paths.get("ExtraContent", "LuaPackages")),
        Map.entry("extracontent-translations.zip", Paths.get("ExtraContent", "translations")),
        Map.entry("extracontent-models.zip",       Paths.get("ExtraContent", "models")),
        Map.entry("extracontent-textures.zip",     Paths.get("ExtraContent", "textures")),
        Map.entry("extracontent-places.zip",       Paths.get("ExtraContent", "places")),

        Map.entry("RobloxApp.zip",                Paths.get(""))

        // No need for Roblox Installer
    );

    public static Path get(String fileName) {
        // String localAppData = System.getenv("LOCALAPPDATA");
        // Path versionsFolder = Paths.get(localAppData, "Roblox", "Versions");
        // Path targetVersionDir = versionsFolder.resolve(version);
        Path resultedPath = packageMap.get(fileName);
        if (resultedPath == null) {return Paths.get("");}
        else {return resultedPath;}
    }
}