package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CLI {
  private Directory currentDirectory;

  public CLI(Directory rootDirectory) {
    this.currentDirectory = rootDirectory;
  }

  public List<String> executeCommands(List<String> commands) {
    List<String> results = new ArrayList<>();
    for (String command : commands) {
      String result = executeCommand(command);
      results.add(result);
    }
    return results;
  }

  private String executeCommand(String command) {
    String[] parts = command.split("\\s+");
    String cmd = parts[0];
    String[] args = Arrays.copyOfRange(parts, 1, parts.length);

    switch (cmd) {
      case "ls":
        return listItems(args);
      case "mkdir":
        return makeDirectory(args);
      case "cd":
        return changeDirectory(args);
      case "pwd":
        return printWorkingDirectory();
      case "touch":
        return createFile(args);
      case "rm":
        return remove(args);
      default:
        return "Unknown command";
    }
  }

  private String listItems(String[] args) {
    if (args.length > 0 && args[0].equals("--ord=asc")) {
      return String.join(" ", currentDirectory.listItems("asc"));
    } else if (args.length > 0 && args[0].equals("--ord=desc")) {
      return String.join(" ", currentDirectory.listItems("desc"));
    } else {
      return String.join(" ", currentDirectory.listItems(null));
    }
  }

  private String makeDirectory(String[] args) {
    if (args.length > 0) {
      String name = args[0];
      Directory newDir = new Directory(name, currentDirectory);
      String result = currentDirectory.addChild(newDir);
      return result;
    }
    return "Invalid command";
  }

  private String changeDirectory(String[] args) {
    if (args.length > 0) {
      String dirName = args[0];
      Directory newDir;
      if (dirName.equals("..")) {
        newDir = currentDirectory.getParent();
      } else {
        newDir = currentDirectory.getChildByName(dirName);
      }

      if (newDir != null) {
        currentDirectory = newDir;
        return "moved to directory '" + dirName + "'";
      } else {
        return "'" + dirName + "' directory does not exist";
      }
    }
    return "Invalid command";
  }

  private String printWorkingDirectory() {
    return currentDirectory.getPath();
  }

  private String createFile(String[] args) {
    if (args.length > 0) {
      String fileName = args[0];
      // Assume creating a file here, adjust as per your File implementation
      File newFile = new File(fileName, currentDirectory);
      String result = currentDirectory.addChild(newFile);
      return result;
    }
    return "Invalid command";
  }

  private String remove(String[] args) {
    if (args.length > 0) {
      String name = args[0];
      FileSystem child = currentDirectory.findChildByName(name);
      if (child == null) {
        return "cannot remove '" + name + "', does not exist";
      } else if (child instanceof Directory) {
        if (args.length > 1 && args[1].equals("--recursive")) {
          currentDirectory.removeChild(child);
          return "'" + name + "' removed";
        } else {
          return "cannot remove '" + name + "', is a directory";
        }
      } else {
        currentDirectory.removeChild(child);
        return "'" + name + "' removed";
      }
    }
    return "Invalid command";
  }
}
