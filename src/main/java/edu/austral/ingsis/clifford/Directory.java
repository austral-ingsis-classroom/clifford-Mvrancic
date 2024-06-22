package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.List;

public class Directory implements FileSystem{
    private final String name;
    private final Directory parent;
    private final List<FileSystem> children;

    public Directory(String name, Directory parent) {
        this.name = name;
        parent.addChild(this);
        this.parent = parent;
        this.children = new ArrayList<>();
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
        return parent.getPath() + "/" + name;
    }
    public Directory getParent() {
        return parent;
    }
    public List<FileSystem> getChildren() {
        return children;
    }
    public String addChild(FileSystem fileSystem) {
        for(FileSystem child : children) {
            if(child.getName().equals(fileSystem.getName())) {
                return "Error: Directory already exists";
            }
        }
        children.add(fileSystem);
        return "'" + fileSystem.getName() + "' directory created";
    }
    public void removeChild(FileSystem fileSystem) {
        children.remove(fileSystem);
    }
    public Directory getChildByName(String name) {
        for (FileSystem child : children) {
            if (child.getName().equals(name)) {
                return (Directory) child;
            }
        }
        return null;
    }
}
