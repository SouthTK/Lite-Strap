package window.litestrap.internal;

public class Terminal {

    public static void clear() {
        try {new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();} 
        catch (Exception e) {}
    }

    public static void printManual() {
        System.out.println("Press any key to go back.");
    }
}