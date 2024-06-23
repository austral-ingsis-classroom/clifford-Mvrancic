package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.Commands.*;

import java.util.*;

public class CLI {
    private Directory currentDirectory;
    private final Map<String, Command> commands;

    public CLI(Directory rootDirectory) {
        this.currentDirectory = rootDirectory;
        this.commands = new HashMap<>();
        this.commands.put("ls", new Ls(currentDirectory));
        this.commands.put("mkdir", new Mkdir(currentDirectory));
        this.commands.put("cd", new Cd(currentDirectory, rootDirectory));
        this.commands.put("pwd", new Pwd(currentDirectory));
        this.commands.put("touch", new Touch(currentDirectory));
        this.commands.put("rm", new Rm(currentDirectory));
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

        Command commandToExecute = commands.get(cmd);
        if (commandToExecute != null) {
            String result = commandToExecute.execute(args);
            if (commandToExecute instanceof Cd) {
                currentDirectory = Cd.getCurrentDirectory();
                commands.replace("ls", new Ls(currentDirectory));
                commands.replace("mkdir", new Mkdir(currentDirectory));
                commands.replace("pwd", new Pwd(currentDirectory));
                commands.replace("touch", new Touch(currentDirectory));
                commands.replace("rm", new Rm(currentDirectory));
            }
            return result;
        } else {
            return "Unknown command";
        }
    }
}