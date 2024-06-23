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
    File newFile = new File(filename, currentDirectory);
    currentDirectory.addChild(newFile);
    return "'" + filename + "' file created";
  }

  @Override
  public String execute(String[] args) {
    // Touch command does not require arguments
    return execute();
  }
}
