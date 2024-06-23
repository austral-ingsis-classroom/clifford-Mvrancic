package edu.austral.ingsis.clifford;

public interface Command {
    String execute();

    String execute(String[] args);
}
