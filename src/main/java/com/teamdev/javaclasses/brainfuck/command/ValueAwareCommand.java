package com.teamdev.javaclasses.brainfuck.command;

abstract public class ValueAwareCommand extends AbstractCommand {

    private int value = 1;

    public ValueAwareCommand() {
    }

    public ValueAwareCommand(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ValueAwareCommand that = (ValueAwareCommand) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return value;
    }

}