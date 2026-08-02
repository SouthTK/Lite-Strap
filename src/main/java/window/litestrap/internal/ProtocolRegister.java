package window.litestrap.internal;

import java.io.IOException;

public class ProtocolRegister {

    public static void registerProtocol() {
        String appPath = "PLACEHOLDER";
        //appPath will be path of RobloxLauncher class
        String[] protocols = {"roblox-player", "roblox"};
        // String javaPath = "C:\\Program Files\\Java\\jdk-17\\bin\\javaw.exe"; // Or path to user's javaw
        // String jarPath = "C:\\path\\to\\your\\myapp.jar";
        // String mainClass = "com.example.MainClass";

        // // Notice how we structure the quotes so arguments pass correctly
        // String commandValue = "\"" + javaPath + "\" -cp \"" + jarPath + "\" " + mainClass + " \"%1\"";

        for (String protocol : protocols) {
            try {
                //runRegCommand("reg add HKCU\\Software\\Classes\\" + protocol + " /v \"URL Protocol\" /t REG_SZ /d \"\" /f");
                //String commandValue = "\"" + appPath + "\" \"%1\"";
                //runRegCommand("reg add HKCU\\Software\\Classes\\" + protocol + "\\shell\\open\\command /ve /t REG_SZ /d \"" + commandValue + "\" /f");
                System.out.println("Successfully registered protocol: " + protocol);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void unregisterProtocol() {
        String[] protocols = {"roblox-player", "roblox"};
        for (String protocol : protocols) {
            try {
                runRegCommand("reg delete HKCU\\Software\\Classes\\" + protocol + " /f");
                System.out.println("Successfully removed protocol: " + protocol);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void runRegCommand(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
        return;
    }
}