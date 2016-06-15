package com.teamdev.javaclasses.brainfuck.command;

import com.teamdev.javaclasses.brainfuck.CommandVisitor;

public class IncrementCommand extends ValueAwareCommand {

    public IncrementCommand() {
    }

    public IncrementCommand(int value) {
        super(value);
    }

    @Override
    public void acceptVisitor(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
