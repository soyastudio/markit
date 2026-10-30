package sorya.framework.markita.templates;

import sorya.framework.markita.TemplateExecutor;
import sorya.framework.markita.TemplateExecutorException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PythonExecutor implements TemplateExecutor {
    @Override
    public Object execute(String template, Map<String, Object> parameters) throws TemplateExecutorException {

        List<String> command = new ArrayList<>();
        command.add("python3"); // or "python" depending on your environment OS
        command.add("-c");
        command.add(pythonCode(template, parameters));

        ProcessBuilder processBuilder = new ProcessBuilder(command);

        // Merge error stream into standard output stream to capture Python stack traces cleanly
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();

            // Read the output from Python's print statements
            String output;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                output = reader.lines().collect(Collectors.joining("\n"));
            }

            // Set a hard timeout so an infinite loop in the String script won't crash your server
            boolean finished = process.waitFor(15, TimeUnit.SECONDS);

            if (!finished) {
                process.destroyForcibly();
                throw new RuntimeException("Python script execution timed out.");
            }

            int exitCode = process.exitValue();
            if (exitCode != 0) {
                throw new RuntimeException("Python script failed with exit code " + exitCode + ". Output: " + output);
            }

            return output;

        } catch (Exception e) {
            return "Execution Error: " + e.getMessage();

        }
    }

    private String pythonCode(String template, Map<String, Object> parameter) {
        return "";
    }
}
