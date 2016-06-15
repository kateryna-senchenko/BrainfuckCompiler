package com.teamdev.javaclasses.brainfuck;

import com.teamdev.javaclasses.brainfuck.command.LoopCommand;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class AbstractCompilerTest extends Assert {

    public static void assertCommandList(List<Command> expectedCommands,
                                         List<Command> actualCommands) {

        assertEquals("Wrong command count.",
                expectedCommands.size(),
                actualCommands.size());

        assertEquals("Wrong command parsed.",
                expectedCommands, actualCommands);
    }


}