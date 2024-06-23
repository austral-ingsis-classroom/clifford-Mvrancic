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
    Directory newDir = new Directory(dirname);
    currentDirectory.addChild(newDir);
    return "'" + dirname + "' directory created";
  }

  @Override
  public String execute(String[] args) {
    return execute();
  }
}
