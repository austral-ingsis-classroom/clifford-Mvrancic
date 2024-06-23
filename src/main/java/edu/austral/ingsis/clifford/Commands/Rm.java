package edu.austral.ingsis.clifford.Commands;

import edu.austral.ingsis.clifford.Command;
import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystem;

public class Rm implements Command {
  private final Directory currentDirectory;

  public Rm(Directory currentDirectory) {
    this.currentDirectory = currentDirectory;
  }

  @Override
  public String execute(String[] args) {
    if (args.length == 0) {
      return "Invalid command";
    }

    String name = args[args.length - 1];
    boolean recursive = args.length > 1 && args[0].equals("--recursive");

    FileSystem child = currentDirectory.findChildByName(name);
    if (child == null) {
      return "cannot remove '" + name + "', does not exist";
    } else if (child instanceof Directory && !recursive) {
      return "cannot remove '" + name + "', is a directory";
    } else {
      currentDirectory.removeChild(child);
      return "'" + name + "' removed";
    }
  }
}
