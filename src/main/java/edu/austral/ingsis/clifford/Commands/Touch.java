package edu.austral.ingsis.clifford.Commands;

import edu.austral.ingsis.clifford.Command;
import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.File;

public class Touch implements Command {
  private final Directory currentDirectory;
  private final String filename;

  public Touch(Directory currentDirectory, String filename) {
    this.currentDirectory = currentDirectory;
    this.filename = filename;
  }

  @Override
  public String execute() {
    if (filename.contains(" ") || filename.contains("/")) {
      throw new IllegalArgumentException("Invalid filename");
    }
    File newFile = new File(filename, currentDirectory);
    return currentDirectory.addChild(newFile);
  }
}
