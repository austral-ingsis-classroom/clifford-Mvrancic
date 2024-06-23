package edu.austral.ingsis.clifford.Commands;

import edu.austral.ingsis.clifford.Command;
import edu.austral.ingsis.clifford.Directory;

public class Pwd implements Command {
    private final Directory currentDirectory;

    public Pwd(Directory currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(String[] args) {
        return currentDirectory.getPath();
    }
}