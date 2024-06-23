package edu.austral.ingsis.clifford.Commands;

import edu.austral.ingsis.clifford.Command;
import edu.austral.ingsis.clifford.Directory;

public class Cd implements Command {
    private static Directory currentDirectory;
    private final Directory rootDirectory;

    public Cd(Directory currentDirectory, Directory rootDirectory) {
        Cd.currentDirectory = currentDirectory;
        this.rootDirectory = rootDirectory;
    }

    @Override
    public String execute(String[] args) {
        String dirName = args[0];
        if (dirName.equals("/")) {
            currentDirectory = rootDirectory;
            return "moved to directory '/'";
        }

        if (dirName.equals("..")) {
            if (currentDirectory.getParent() != null) {
                currentDirectory = currentDirectory.getParent();
                return "moved to directory '" + currentDirectory.getPath() + "'";
            } else {
                return "moved to directory '/'";
            }
        }

        if (dirName.contains("/")) {
            String[] path = dirName.split("/");
            Directory child = rootDirectory;
            for (String part : path) {
                Directory nextChild = child.findDirectoryByName(part);
                if (nextChild == null) {
                    return "'" + part + "' directory does not exist";
                }
                child = nextChild;
            }
            currentDirectory = child;
            dirName = child.getName();
            return "moved to directory '" + dirName + "'";
        } else {
            Directory child = currentDirectory.findDirectoryByName(dirName);
            if (child != null) {
                currentDirectory = child;
                return "moved to directory '" + dirName + "'";
            } else {
                return "'" + dirName + "' directory does not exist";
            }
        }
    }

    public static Directory getCurrentDirectory() {
        return currentDirectory;
    }
}