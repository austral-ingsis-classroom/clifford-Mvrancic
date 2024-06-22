package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.Commands.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CLI {
    public Directory root;
    public Directory currentDirectory;
    public final Map<String, Command> commands;

    public CLI(Directory root) {
        this.root = root;
        this.currentDirectory = root;
        this.commands = Map.of(
                "ls", new Ls(),
                "cd", new Cd(this),
                "mkdir", new Mkdir(this),
                "touch", new Touch(),
                "rm", new Rm()
        );
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
    boolean directoryFound = Arrays.stream(pathArray).allMatch(dir -> {Directory nextDirectory = currentDirectory.getChildByName(dir);
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

    public String createDirectory(String directoryName, Directory parent) {
      Directory directory = new Directory(directoryName, parent);
      return root.addChild(directory);
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