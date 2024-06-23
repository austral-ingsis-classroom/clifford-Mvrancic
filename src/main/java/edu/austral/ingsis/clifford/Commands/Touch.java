package edu.austral.ingsis.clifford.Commands;

import edu.austral.ingsis.clifford.Command;
import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.File;

public class Touch implements Command {
  private final Directory currentDirectory;

  public Touch(Directory currentDirectory) {
    this.currentDirectory = currentDirectory;
  }

  @Override
  public String execute(String[] args) {
    if (args.length > 0) {
      String fileName = args[0];
      File newFile = new File(fileName, currentDirectory);
      return currentDirectory.addChild(newFile);
    }
    return "Invalid command";
  }
}
