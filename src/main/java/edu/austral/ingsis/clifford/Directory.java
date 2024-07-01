package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Directory implements FileSystem {
  private final String name;
  private final Directory parent;
  private final List<FileSystem> children;

  public Directory(String name, Directory parent) {
    this.name = (name != null) ? name.trim() : "";
    this.parent = parent;
    this.children = new ArrayList<>();
  }

  public Directory(String name) {
    this.name = (name != null) ? name.trim() : "";
    this.parent = null;
    this.children = new ArrayList<>();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getPath() {
    if (parent == null) {
      return "/" + name;
    } else {
      // Check if parent path is root ("/"), if so, don't add an extra "/"
      if (parent.getPath().equals("/")) {
        return parent.getPath() + name;
      } else {
        return parent.getPath() + "/" + name;
      }
    }
  }

  public Directory getParent() {
    return parent;
  }

  public String addChild(FileSystem fileSystem) {
    if (findChildByName(fileSystem.getName()) != null) {
      return "Error: Directory or file already exists";
    }

    if (fileSystem instanceof Directory) {
      children.add(fileSystem);
      return "'" + fileSystem.getName() + "' directory created";
    } else if (fileSystem instanceof File) {
      children.add(fileSystem);
      return "'" + fileSystem.getName() + "' file created";
    }

    return "Unknown type";
  }

  public void removeChild(FileSystem fileSystem) {
    children.remove(fileSystem);
  }

  public Directory getChildByName(String name) {
    return (Directory) findChildByName(name);
  }

  public FileSystem findChildByName(String name) {
    for (FileSystem child : children) {
      if (child.getName().equals(name)) {
        return child;
      }
    }
    return null;
  }

  public List<String> listItems(String order) {
    List<String> items = new ArrayList<>();
    for (FileSystem child : children) {
      items.add(child.getName());
    }
    if (order != null) {
      switch (order) {
        case "asc":
          Collections.sort(items);
          break;
        case "desc":
          Collections.sort(items, Collections.reverseOrder());
          break;
        default:
          break;
      }
    }
    return items;
  }

  public Directory findDirectoryByName(String name) {
    for (FileSystem child : children) {
      if (child instanceof Directory && child.getName().equals(name)) {
        return (Directory) child;
      }
    }
    return null;
  }
}
