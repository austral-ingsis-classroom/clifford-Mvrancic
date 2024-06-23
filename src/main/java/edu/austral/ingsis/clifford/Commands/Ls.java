package edu.austral.ingsis.clifford.Commands;

import edu.austral.ingsis.clifford.Command;
import edu.austral.ingsis.clifford.Directory;

import java.util.List;

public class Ls implements Command {
  private final Directory currentDirectory;
  private final String order;

  public Ls(Directory currentDirectory, String order) {
    this.currentDirectory = currentDirectory;
    this.order = order;
  }

  @Override
  public String execute() {
    List<String> items = currentDirectory.listItems(order);
    return String.join(" ", items);
  }

  @Override
  public String execute(String[] args) {
    if (args.length > 0 && args[0].startsWith("--ord=")) {
      String order = args[0].substring(6);
      List<String> items = currentDirectory.listItems(order);
      return String.join(" ", items);
    } else {
      return execute();
    }
  }
}
