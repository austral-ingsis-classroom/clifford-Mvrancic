package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Directory implements FileSystem {
    private final String name;
    private final Directory parent;
    private final List<FileSystem> children;

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
        this.children = new ArrayList<>();
        if (parent != null) {
            parent.addChild(this);
        }
    }

    public Directory(String name) {
        this.name = name;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        return parent == null ? "/" : parent.getPath() + "/" + name;
    }

    public Directory getParent() {
        return parent;
    }

    public List<FileSystem> getChildren() {
        return children;
    }

    public String addChild(FileSystem fileSystem) {
        for (FileSystem child : children) {
            if (child.getName().equals(fileSystem.getName())) {
                return "Error: Item already exists";
            }
        }
        children.add(fileSystem);
        return "'" + fileSystem.getName() + "' created";
    }

    public void removeChild(FileSystem fileSystem) {
        children.remove(fileSystem);
    }

    public Directory getChildByName(String name) {
        for (FileSystem child : children) {
            if (child.getName().equals(name) && child instanceof Directory) {
                return (Directory) child;
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
            if (order.equals("asc")) {
                Collections.sort(items);
            } else if (order.equals("desc")) {
                Collections.sort(items, Collections.reverseOrder());
            }
        }
        return items;
    }
}
