package com.teamdev.javaclasses.brainfuck;

public interface Command {

    void acceptVisitor(CommandVisitor visitor);
}
