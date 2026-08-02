package window.litestrap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import window.litestrap.internal.ProtocolRegister;

public class App {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No arguments were provided.");
            return;
        } else if (args[0].equals("register")) {
            ProtocolRegister.registerProtocol();
        } else if (args[0].equals("unregister")) {
            ProtocolRegister.unregisterProtocol();
        }
        
    }
}