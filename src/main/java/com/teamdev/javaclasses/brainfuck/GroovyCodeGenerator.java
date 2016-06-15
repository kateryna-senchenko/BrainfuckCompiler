package com.teamdev.javaclasses.brainfuck;

import com.teamdev.javaclasses.brainfuck.command.*;

import java.io.*;
import java.util.*;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroovyCodeGenerator implements CommandVisitor{

    private static final Logger log = LoggerFactory.getLogger(JavaCodeGenerator.class);

    StringBuilder groovyCode = new StringBuilder();


    public String generate(List<Command> commands) {

        log.info("Start code generating ...");
        for (Command command: commands) {
            command.acceptVisitor(this);
        }
        log.info("Groovy code is generated");
        return groovyCode.toString();
    }

    @Override
    public void visit(IncrementCommand command) {
        groovyCode.append("memory[pointer]++\n");
    }

    @Override
    public void visit(DecrementCommand command) {
        groovyCode.append("memory[pointer]--\n");
    }

    @Override
    public void visit(MovePointerRightCommand command) {
        groovyCode.append("pointer++\n");
    }

    @Override
    public void visit(MovePointerLeftCommand command) {
        groovyCode.append("pointer--\n");

    }

    @Override
    public void visit(PrintCommand command) {
        groovyCode.append("print((char) memory[pointer])\n");


    }

    @Override
    public void visit(LoopCommand command) {
        groovyCode.append("while (memory[pointer] > 0) {\n");
        for (Command innerCommand: command.getCommands()) {
            innerCommand.acceptVisitor(this);
        }
        groovyCode.append( "        }\n");
    }



    public static void main(String[] args) {

        log.info("Start program parsing ...");
        final List<Command> commands = new Analyser().
                parseProgram(
                        "++++++++[>++++[>++>+++>+++>+<<<<-]>+>" +
                                "+>->>+[<]<-]>>.>---.+++++++..+" +
                                "++.>>.<-.<.+++.------.--------." +
                                ">>+.>++.");
        log.info("Program is parsed");

        /*final List<Command> optimizedCommands =
                new Optimizer().optimize(commands);*/

        String groovyProgram = new GroovyCodeGenerator().generate(commands);


        Map<String, Object> inputData = new HashMap<>();
        inputData.put("data", groovyProgram);


        Configuration cfg = new Configuration();
        try {
            log.debug("Setting directory for template loading...");
            cfg.setDirectoryForTemplateLoading(new File(
                    "src\\main\\resources\\templates"));
        } catch (IOException e) {
            log.error("Error in setting directory for template loading", e.getMessage());
        }


        Template template = null;
        try {
            log.debug("Getting template...");
            template = cfg.getTemplate("groovy_template.ftl");
        } catch (IOException e) {
            log.error("Error in getting template", e.getMessage());
        }


        Writer fileWriter = null;
        try {
            log.debug("Writing to file...");
            fileWriter = new FileWriter(new File(
                    "src\\main\\java\\com\\teamdev\\javaclasses\\brainfuck\\programs\\BrainfuckGroovy.groovy"));
        } catch (IOException e) {
            log.error("Error in file writing", e.getMessage());
        }

        try {
            log.debug("Processing template...");
            template.process(inputData, fileWriter);
        } catch (IOException|TemplateException e){
        log.error("Error in file processing", e.getMessage());
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                log.error("Error closing file", e.getMessage());
            }
        }

    }
}
