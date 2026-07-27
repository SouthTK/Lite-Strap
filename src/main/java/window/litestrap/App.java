package window.litestrap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

    public static void main(String[] args) {
        String jarPath = "Test";
        ProtocolRegister.registerProtocol(jarPath);
    }
}