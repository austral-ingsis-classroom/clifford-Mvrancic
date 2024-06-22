package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.Commands.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CLI {
  private Directory root;
  private Directory currentDirectory;

  public CLI(Directory root) {
    this.root = root;
    this.currentDirectory = root;
  }

  public String executeCommand(String input) {
    String[] parts = input.split(" ");
    String commandName = parts[0];
    String[] args = new String[parts.length - 1];
    System.arraycopy(parts, 1, args, 0, parts.length - 1);

    try {
      Command command = null;
      switch (commandName) {
        case "ls":
          String order = args.length > 0 && args[0].startsWith("--ord=") ? args[0].split("=")[1] : null;
          command = new Ls(currentDirectory, order);
          break;
        case "cd":
          if (args.length != 1) {
            throw new IllegalArgumentException("Invalid arguments");
          }
          command = new Cd(root, currentDirectory, args[0]);
          break;
        case "touch":
          if (args.length != 1) {
            throw new IllegalArgumentException("Invalid arguments");
          }
          command = new Touch(currentDirectory, args[0]);
          break;
        case "mkdir":
          if (args.length != 1) {
            throw new IllegalArgumentException("Invalid arguments");
          }
          command = new Mkdir(currentDirectory, args[0]);
          break;
        case "rm":
          boolean recursive = false;
          String name;
          if (args.length == 2 && args[0].equals("--recursive")) {
            recursive = true;
            name = args[1];
          } else if (args.length == 1) {
            name = args[0];
          } else {
            throw new IllegalArgumentException("Invalid arguments");
          }
          command = new Rm(currentDirectory, name, recursive);
          break;
        case "pwd":
          command = new Pwd(root, currentDirectory);
          break;
        default:
          throw new IllegalArgumentException("Unknown command");
      }

      if (command != null) {
        String output = command.execute();
        if (output != null && !output.isEmpty()) {
          System.out.println(output);
        }
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    return "";
  }

  public List<String> list(String directoryName) {
    Directory directory = getDirectory(directoryName);
    return directory.getChildren().stream()
            .map(FileSystem::getName)
            .collect(Collectors.toList());
  }

  public String changeDirectory(String path) {
    if (path.equals("..")) {
      if (currentDirectory.getParent() != null) {
        currentDirectory = currentDirectory.getParent();
        return "Moved to directory '" + currentDirectory.getName() + "'";
      } else {
        return "Error: No parent directory";
      }
    }
    if (currentDirectory == null) {
      return "Error: Directory not found";
    }
    String[] pathArray = path.split("/");
    boolean directoryFound = Arrays.stream(pathArray).allMatch(dir -> {
      Directory nextDirectory = currentDirectory.getChildByName(dir);
      if (nextDirectory != null) {
        currentDirectory = nextDirectory;
        return true;
      } else {
        return false;
      }
    });
    if (directoryFound) {
      return "Moved to directory '" + currentDirectory.getName() + "'";
    } else {
      return "Error: Directory not found";
    }
  }

  public void createFile(String fileName) {
    File file = new File(fileName, this.currentDirectory);
    this.currentDirectory.addChild(file);
  }

  public String createDirectory(String directoryName) {
    Directory directory = new Directory(directoryName, this.currentDirectory);
    return this.currentDirectory.addChild(directory);
  }

  public void remove(String name) {
    FileSystem item = getItem(name);
    this.currentDirectory.removeChild(item);
  }

  private Directory getDirectory(String name) {
    return this.currentDirectory.getChildren().stream()
            .filter(item -> item.getName().equals(name) && item instanceof Directory)
            .map(item -> (Directory) item)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Directory not found: " + name));
  }

  private FileSystem getItem(String name) {
    return this.currentDirectory.getChildren().stream()
            .filter(item -> item.getName().equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Item not found: " + name));
  }
}
