package ua.goit.hw5.view;

import java.util.Scanner;

public class Console implements View {
    private final Scanner scanner;

    public Console(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }
}
