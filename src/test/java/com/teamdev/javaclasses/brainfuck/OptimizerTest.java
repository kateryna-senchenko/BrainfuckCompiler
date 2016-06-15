package com.teamdev.javaclasses.brainfuck;

import com.teamdev.javaclasses.brainfuck.command.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

public class OptimizerTest extends AbstractCompilerTest {

    @Test
    public void testIncrementCommandOptimization() {

        final ArrayList<Command> commands = new ArrayList<>();
        commands.add(new IncrementCommand());
        commands.add(new IncrementCommand());
        commands.add(new IncrementCommand());

        final List<Command> optimizedList =
                new Optimizer().optimize(commands);

        assertCommandList(singletonList(
                new IncrementCommand(3)),
                optimizedList);
    }

    @Test
    public void testDecrementCommandOptimization() {

        final ArrayList<Command> commands = new ArrayList<>();
        commands.add(new DecrementCommand());
        commands.add(new DecrementCommand());
        commands.add(new DecrementCommand());

        final List<Command> optimizedList =
                new Optimizer().optimize(commands);

        assertCommandList(singletonList(
                new DecrementCommand(3)),
                optimizedList);
    }

}