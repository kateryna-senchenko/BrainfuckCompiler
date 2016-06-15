package com.teamdev.javaclasses.brainfuck;

import com.teamdev.javaclasses.brainfuck.command.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;


public class AnalyserTest extends AbstractCompilerTest {

    private final Analyser analyser = new Analyser();

    @Test
    public void testAnalyseIncrementCommand() {

        final List<Command> actualCommands = analyser.parseProgram("+");

        assertCommandList(Collections.singletonList(new IncrementCommand()), actualCommands);


    }

    @Test
    public void testAnalyseLoopCommand() {

        final List<Command> actualCommands = analyser.parseProgram("[]");

        assertCommandList(Collections.singletonList(new LoopCommand(emptyList())), actualCommands);


    }

    @Test
    public void testAnalyseNestedLoops() {

        final List<Command> actualCommands =
                analyser.parseProgram("[[][]]");

        assertCommandList(
                Collections.singletonList(
                        new LoopCommand(
                                asList(new LoopCommand(
                                                emptyList()),
                                        new LoopCommand(
                                                emptyList()))
                        )
                ),
                actualCommands);

    }

}
