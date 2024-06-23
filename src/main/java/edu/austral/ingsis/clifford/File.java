package edu.austral.ingsis.clifford;

public class File implements FileSystem {
    private final String name;
    private final Directory parent;

    public File(String name, Directory parent) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        this.name = name.trim();
        this.parent = parent;
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
            return parent.getPath() + "/" + name;
        }
    }
}
