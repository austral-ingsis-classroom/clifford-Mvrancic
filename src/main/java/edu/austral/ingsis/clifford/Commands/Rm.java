package edu.austral.ingsis.clifford.Commands;

import edu.austral.ingsis.clifford.Command;
import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystem;

public class Rm implements Command {
  private final Directory currentDirectory;
  private final String name;
  private final boolean recursive;

  public Rm(Directory currentDirectory, String name, boolean recursive) {
    this.currentDirectory = currentDirectory;
    this.name = name;
    this.recursive = recursive;
  }

  @Override
  public String execute() {
    FileSystem item = currentDirectory.getChildByName(name);
    if (item != null && !recursive) {
      throw new IllegalArgumentException("Cannot remove directory without --recursive");
    }
    currentDirectory.removeChild(item);
    return "'" + name + "' removed";
  }
}
