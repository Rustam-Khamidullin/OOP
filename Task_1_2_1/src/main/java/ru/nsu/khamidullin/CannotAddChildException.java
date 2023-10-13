package ru.nsu.khamidullin;

public class CannotAddChildException extends RuntimeException{
    public CannotAddChildException(String massage) {
        super(massage);
    }
}
