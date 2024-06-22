package edu.austral.ingsis.clifford.Commands;

import edu.austral.ingsis.clifford.Command;
import edu.austral.ingsis.clifford.Directory;

public class Pwd implements Command {
  private final Directory root;
  private final Directory currentDirectory;

  public Pwd(Directory root, Directory currentDirectory) {
    this.root = root;
    this.currentDirectory = currentDirectory;
  }

  @Override
  public String execute() {
    return currentDirectory.getPath();
  }
}
