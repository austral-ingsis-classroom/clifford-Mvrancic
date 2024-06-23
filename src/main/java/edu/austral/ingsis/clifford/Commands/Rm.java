package edu.austral.ingsis.clifford.Commands;

import edu.austral.ingsis.clifford.Command;
import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystem;

public class Rm implements Command {
  private final Directory currentDirectory;
  private final String name;
  private boolean recursive;

  public Rm(Directory currentDirectory, String name, boolean recursive) {
    this.currentDirectory = currentDirectory;
    this.name = name;
    this.recursive = recursive;
  }

  @Override
  public String execute() {
    FileSystem item = currentDirectory.getChildByName(name);
    if (item == null) {
      throw new IllegalArgumentException("'" + name + "' not found");
    }
    if (!recursive) {
      throw new IllegalArgumentException("Cannot remove directory without --recursive");
    }
    currentDirectory.removeChild(item);
    return "'" + name + "' removed";
  }

  @Override
  public String execute(String[] args) {
    // Check for recursive flag
    if (args.length == 1 && args[0].equals("--recursive")) {
      this.recursive = true;
      return execute();
    } else {
      throw new IllegalArgumentException("Invalid arguments for 'rm'");
    }
  }
}
