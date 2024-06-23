package edu.austral.ingsis.clifford.Commands;

import edu.austral.ingsis.clifford.Command;
import edu.austral.ingsis.clifford.Directory;

import java.util.List;

public class Ls implements Command {
    private final Directory currentDirectory;

    public Ls(Directory currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    @Override
    public String execute(String[] args) {
        if (args.length > 0 && args[0].equals("--ord=asc")) {
            return String.join(" ", currentDirectory.listItems("asc"));
        } else if (args.length > 0 && args[0].equals("--ord=desc")) {
            return String.join(" ", currentDirectory.listItems("desc"));
        } else {
            return String.join(" ", currentDirectory.listItems(null));
        }
    }
}
