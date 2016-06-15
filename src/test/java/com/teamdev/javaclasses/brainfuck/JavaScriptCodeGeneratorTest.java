package com.teamdev.javaclasses.brainfuck;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class JavaScriptCodeGeneratorTest {

    private final Analyser analyser = new Analyser();

    @Test
    public void testIncrementCommand(){

        final List<Command> commands = analyser.parseProgram("+");
        assertEquals("Command is not correctly generated", "memory[pointer]++;\n",
                new JavaScriptCodeGenerator().generate(commands));
    }

    @Test
    public void testDecrementCommand(){

        final List<Command> commands = analyser.parseProgram("-");
        assertEquals("Command is not correctly generated", "memory[pointer]--;\n",
                new JavaScriptCodeGenerator().generate(commands));
    }

    @Test
    public void MovePointerRightCommand(){

        final List<Command> commands = analyser.parseProgram(">");
        assertEquals("Command is not correctly generated", "pointer++;\n",
                new JavaScriptCodeGenerator().generate(commands));
    }

    @Test
    public void MovePointerLeftCommand(){

        final List<Command> commands = analyser.parseProgram("<");
        assertEquals("Command is not correctly generated", "pointer--;\n",
                new JavaScriptCodeGenerator().generate(commands));
    }

    @Test
    public void PrintCommand(){

        final List<Command> commands = analyser.parseProgram(".");
        assertEquals("Command is not correctly generated", "result += String.fromCharCode(memory[pointer]);\n",
                new JavaScriptCodeGenerator().generate(commands));
    }

    @Test
    public void LoopCommand(){

        final List<Command> commands = analyser.parseProgram("[]");
        assertEquals("Command is not correctly generated", "while (memory[pointer] > 0) {\n        }\n",
                new JavaScriptCodeGenerator().generate(commands));
    }

    @Test
    public void TestJavaFile()  {

        Scanner infileModel = null;
        try {
            infileModel = new Scanner(new File(
                    "src\\test\\resources\\benchmarks\\BrainfuckJavaScript.html"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Scanner infileActual = null;
        try {
            infileActual = new Scanner(new File(
                    "src\\main\\java\\com\\teamdev\\javaclasses\\brainfuck\\programs\\BrainfuckJavaScript.html"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (infileModel.hasNext()){
            assertEquals("Lines in files are different", infileModel.nextLine(), infileActual.nextLine());
        }

    }
}

