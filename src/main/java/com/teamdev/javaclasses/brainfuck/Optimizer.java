package com.teamdev.javaclasses.brainfuck;

import com.teamdev.javaclasses.brainfuck.command.*;
import com.teamdev.javaclasses.brainfuck.command.ValueAwareCommand;

import java.util.ArrayList;
import java.util.List;

public class Optimizer implements CommandVisitor {

    private List<Command> optimizedCommands = new ArrayList<>();

    public List<Command> optimize(List<Command> commands) {

        for (Command command : commands) {
            command.acceptVisitor(this);
        }

        return optimizedCommands;
    }

    private void visitValueAwareCommand(ValueAwareCommand command) {
        if (!optimizedCommands.isEmpty()) {

            final Command lastCommand = optimizedCommands.get(
                    optimizedCommands.size() - 1);

            if (lastCommand.getClass().equals(command.getClass())) {

                ValueAwareCommand valueAwareCommand =
                        (ValueAwareCommand) lastCommand;

                valueAwareCommand.setValue(
                        valueAwareCommand.getValue() + 1);
                return;
            }
        }

        optimizedCommands.add(command);
    }

    @Override
    public void visit(IncrementCommand command) {
        visitValueAwareCommand(command);
    }

    @Override
    public void visit(DecrementCommand command) {
        visitValueAwareCommand(command);
    }

    @Override
    public void visit(MovePointerRightCommand command) {
        visitValueAwareCommand(command);
    }

    @Override
    public void visit(MovePointerLeftCommand command) {
        visitValueAwareCommand(command);
    }

    @Override
    public void visit(PrintCommand command) {
        optimizedCommands.add(command);
    }

    @Override
    public void visit(LoopCommand command) {
        optimizedCommands.add(command);

        final List<Command> justToKeep = optimizedCommands;

        optimizedCommands = new ArrayList<>();

        for (Command innerCommand: command.getCommands()) {
            innerCommand.acceptVisitor(this);
        }

        command.setCommands(optimizedCommands);

        optimizedCommands = justToKeep;

    }
}
