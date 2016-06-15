package com.teamdev.javaclasses.brainfuck;

import java.io.*;
import java.util.*;


import com.teamdev.javaclasses.brainfuck.command.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaScriptCodeGenerator implements CommandVisitor{

    private static final Logger log = LoggerFactory.getLogger(JavaCodeGenerator.class);

    StringBuilder jsCode = new StringBuilder();


    public String generate(List<Command> commands) {

        log.info("Start code generating ...");
        for (Command command: commands) {
            command.acceptVisitor(this);
        }
        log.info("JavaScript code is generated");
        return jsCode.toString();
    }

    @Override
    public void visit(IncrementCommand command) {
        jsCode.append("memory[pointer]++;\n");
    }

    @Override
    public void visit(DecrementCommand command) {
        jsCode.append("memory[pointer]--;\n");
    }

    @Override
    public void visit(MovePointerRightCommand command) {
        jsCode.append("pointer++;\n");
    }

    @Override
    public void visit(MovePointerLeftCommand command) {
        jsCode.append("pointer--;\n");

    }

    @Override
    public void visit(PrintCommand command) {
        jsCode.append("result += String.fromCharCode(memory[pointer]);\n");

    }

    @Override
    public void visit(LoopCommand command) {
        jsCode.append("while (memory[pointer] > 0) {\n");
        for (Command innerCommand: command.getCommands()) {
            innerCommand.acceptVisitor(this);
        }
        jsCode.append( "        }\n");
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

        String jsProgram = new JavaScriptCodeGenerator().generate(commands);


        Map<String, Object> inputData = new HashMap<>();
        inputData.put("data", jsProgram);


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
            template = cfg.getTemplate("html_template.ftl");
        } catch (IOException e) {
            log.error("Error in getting template", e.getMessage());
        }


        Writer fileWriter = null;
        try {
            log.debug("Writing to file...");
            fileWriter = new FileWriter(new File(
                    "src\\main\\java\\com\\teamdev\\javaclasses\\brainfuck\\programs\\BrainfuckJavaScript.html"));
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