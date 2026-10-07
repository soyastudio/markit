package sorya.framework.markita;

public class TemplateExecutorException extends RuntimeException {
    public TemplateExecutorException() {
    }

    public TemplateExecutorException(String message) {
        super(message);
    }

    public TemplateExecutorException(String message, Throwable cause) {
        super(message, cause);
    }

    public TemplateExecutorException(Throwable cause) {
        super(cause);
    }
}
