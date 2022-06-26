package fr.florent.mjmaker.injection;

public class DependencyInjectionException extends RuntimeException{
    public DependencyInjectionException(String message) {
        super(message);
    }

    public DependencyInjectionException(String message, Exception exception) {
        super(message, exception);
    }
}
