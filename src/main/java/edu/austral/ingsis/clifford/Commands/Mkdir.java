package edu.austral.ingsis.clifford.Commands;

import edu.austral.ingsis.clifford.Command;
import edu.austral.ingsis.clifford.Directory;

public class Mkdir implements Command {
  private final Directory currentDirectory;

  public Mkdir(Directory currentDirectory) {
    this.currentDirectory = currentDirectory;
  }

  @Override
  public String execute(String[] args) {
    if (args.length > 0) {
      String name = args[0];
      Directory newDir = new Directory(name, currentDirectory);
      return currentDirectory.addChild(newDir);
    }
    return "Invalid command";
  }
}
