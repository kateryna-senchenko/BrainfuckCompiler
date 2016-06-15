package com.teamdev.javaclasses.brainfuck.command;

import com.teamdev.javaclasses.brainfuck.Command;
import com.teamdev.javaclasses.brainfuck.CommandVisitor;

import java.util.List;

public class LoopCommand extends AbstractCommand {

    private List<Command> commands;

    public LoopCommand(List<Command> commands) {
        this.commands = commands;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoopCommand that = (LoopCommand) o;

        return commands != null ? commands.equals(that.commands) : that.commands == null;

    }

    @Override
    public int hashCode() {
        return commands != null ? commands.hashCode() : 0;
    }

    @Override
    public void acceptVisitor(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
