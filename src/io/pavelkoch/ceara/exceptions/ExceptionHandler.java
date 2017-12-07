package io.pavelkoch.ceara.exceptions;

public class ExceptionHandler {
    public static void render(Exception e) {
        System.out.println(e.getMessage());
        System.exit(1);
    }
}
