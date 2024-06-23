package edu.austral.ingsis;

import edu.austral.ingsis.clifford.CLI;

import java.util.ArrayList;
import java.util.List;

public class MyTestRunner implements FileSystemRunner {
  private final CLI cli;

  public MyTestRunner(CLI cli) {
    this.cli = cli;
  }

  @Override
  public List<String> executeCommands(List<String> commands) {
    return cli.executeCommands(commands);
  }

}
