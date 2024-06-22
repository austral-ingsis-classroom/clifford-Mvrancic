package edu.austral.ingsis.clifford.Commands;

import edu.austral.ingsis.clifford.CLI;
import edu.austral.ingsis.clifford.Command;

import java.util.List;

public class Mkdir implements Command {
  private final CLI cli;

  public Mkdir(CLI cli) {
    this.cli = cli;
  }

  @Override
  public String execute(List<String> parameters) {
    return cli.createDirectory(parameters.getFirst(), cli.currentDirectory);
  }
}
