package window.litestrap.internal;

import java.io.IOException;

public class ProtocolRegister {

    public static void registerProtocol(String appPath) {
        String[] protocols = {"roblox-player", "roblox"};

        for (String protocol : protocols) {
            try {
                // need to fix this
                //runRegCommand("reg add HKCU\\Software\\Classes\\" + protocol + " /v \"URL Protocol\" /t REG_SZ /d \"\" /f");
                // String commandValue = "\"" + appPath + "\" \"%1\"";
                // runRegCommand("reg add HKCU\\Software\\Classes\\" + protocol + "\\shell\\open\\command /ve /t REG_SZ /d \"" + commandValue + "\" /f");
                
                System.out.println("Successfully registered protocol: " + protocol);
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