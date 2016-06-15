package com.teamdev.javaclasses.brainfuck;

import com.teamdev.javaclasses.brainfuck.command.*;

import java.util.List;


public class JavaRunner implements CommandVisitor {

    private final int[] memory = new int[100];
    private int pointer;




    public void execute(List<Command> commands) {
        for (Command command: commands) {
            command.acceptVisitor(this);
        }
    }

    @Override
    public void visit(IncrementCommand command) {
        memory[pointer] += command.getValue();
    }

    @Override
    public void visit(DecrementCommand command) {
        memory[pointer] -= command.getValue();
    }

    @Override
    public void visit(MovePointerRightCommand command) {
        pointer += command.getValue();
    }

    @Override
    public void visit(MovePointerLeftCommand command) {
        pointer -= command.getValue();
    }

    @Override
    public void visit(PrintCommand command) {
        System.out.print((char) memory[pointer]);
    }

    @Override
    public void visit(LoopCommand command) {
        while (memory[pointer] > 0) {
            for (Command innerCommand: command.getCommands()) {
                innerCommand.acceptVisitor(this);
            }
        }
    }

    public static void main(String[] args) {
        final List<Command> commands = new Analyser().
                parseProgram(
                        "++++++++[>++++[>++>+++>+++>+<<<<-]>+>" +
                                "+>->>+[<]<-]>>.>---.+++++++..+" +
                                "++.>>.<-.<.+++.------.--------." +
                                ">>+.>++.");

        final List<Command> optimizedCommands =
                new Optimizer().optimize(commands);


        new JavaRunner().execute(optimizedCommands);
    }
}