package edu.austral.ingsis.clifford.Commands;

import edu.austral.ingsis.clifford.Command;
import edu.austral.ingsis.clifford.Directory;

public class Mkdir implements Command {
  private final Directory currentDirectory;
  private final String dirname;

  public Mkdir(Directory currentDirectory, String dirname) {
    this.currentDirectory = currentDirectory;
    this.dirname = dirname;
  }

  @Override
  public String execute() {
    if (dirname.contains(" ") || dirname.contains("/")) {
      throw new IllegalArgumentException("Invalid directory name");
    }
    Directory newDir = new Directory(dirname, currentDirectory);
    return currentDirectory.addChild(newDir);
  }
}
