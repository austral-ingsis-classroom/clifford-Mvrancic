package edu.austral.ingsis.clifford.Commands;

import edu.austral.ingsis.clifford.CLI;
import edu.austral.ingsis.clifford.Command;

import java.util.List;

public class Cd implements Command {
  private final CLI cli;

  public Cd(CLI cli) {
    this.cli = cli;
  }
  @Override
  public String execute(List<String> parameters) {
    return cli.changeDirectory(parameters.getFirst());
  }
}
