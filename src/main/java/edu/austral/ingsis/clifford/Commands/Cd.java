package edu.austral.ingsis.clifford.Commands;

import edu.austral.ingsis.clifford.Command;
import edu.austral.ingsis.clifford.Directory;

public class Cd implements Command {
  private final Directory root;
  private Directory currentDirectory;
  private final String path;

  public Cd(Directory root, Directory currentDirectory, String path) {
    this.root = root;
    this.currentDirectory = currentDirectory;
    this.path = path;
  }

  @Override
  public String execute() {
    Directory targetDirectory = resolvePath(path);
    if (targetDirectory != null) {
      currentDirectory = targetDirectory;
      return "Moved to directory: '" + targetDirectory.getName() + "'";
    } else {
      throw new IllegalArgumentException("Invalid directory");
    }
  }

  private Directory resolvePath(String path) {
    if (path.equals("..")) {
      return currentDirectory.getParent();
    } else if (path.equals(".")) {
      return currentDirectory;
    } else {
      return currentDirectory.getChildByName(path);
    }
  }
}
