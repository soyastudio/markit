package sorya.framework.markita.util;

import org.apache.commons.text.StringSubstitutor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Map;

public final class TextBuilder {

    public static final String INDENT = "\t";

    private final StringBuilder builder;

    private int currentIndents;

    public StringBuilder getBuilder() {
        return builder;
    }

    private String indent = INDENT;

    public int currentIndents() {
        return currentIndents;
    }

    private TextBuilder() {
        this.builder = new StringBuilder();
    }

    private TextBuilder(String start) {
        this.builder = new StringBuilder(start);
    }

    public static TextBuilder builder() {
        return new TextBuilder();
    }

    public static TextBuilder builder(String start) {
        return new TextBuilder(start);
    }

    public static TextBuilder builder(String start, int startIndent) {
        TextBuilder textBuilder = new TextBuilder(start);
        textBuilder.currentIndents = startIndent;
        return textBuilder;
    }

    public TextBuilder setIndent(int spaceNum) {
        if(spaceNum >= 2) {
            this.indent = " ".repeat(spaceNum);
        }
        return this;
    }

    public TextBuilder indentRight() {
        if (currentIndents >= 0) {
            this.currentIndents++;
        }
        return this;
    }

    public TextBuilder indentRight(int n) {
        if (currentIndents >= 0 && n > 0) {
            for (int i = 0; i < n; i++) {
                currentIndents++;
            }
        }
        return this;
    }

    public TextBuilder indentLeft() {
        if (currentIndents > 0) {
            currentIndents--;
        }
        return this;
    }

    public TextBuilder append(String str) {
        if (str != null) {
            builder.append(str);
        }
        return this;
    }

    public TextBuilder appendOptional(String value, String defaultValue) {
        if (value != null) {
            builder.append(value);
        } else if (defaultValue != null) {
            builder.append(defaultValue);
        }
        return this;
    }

    public TextBuilder append(String[] arr, String separator) {
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                builder.append(separator);
            }

            builder.append(arr[i]);
        }
        return this;
    }

    public TextBuilder append(Object o) {
        builder.append(o);
        return this;
    }

    public TextBuilder appendMessageFormat(String pattern, Object... arguments) {
        builder.append(MessageFormat.format(pattern, arguments));
        return this;
    }

    public TextBuilder appendStringSubstitution(String template, Map<String, ?> params) {
        if (params != null) {
            builder.append(new StringSubstitutor(params).replace(params));
        }
        return this;
    }

    public TextBuilder appendElEvaluation(String template, String varName, Object value) {
        // TODO:
        return this;
    }

    public TextBuilder newLine() {
        builder.append("\n");
        return this;
    }

    public TextBuilder newLine(String str) {
        return newLine().append(str);
    }

    public TextBuilder appendLine(String st) {
        builder.append(st).append("\n");
        return this;
    }

    public TextBuilder newLineWithCurrentIndents() {
        builder.append("\n")
                .append(indent.repeat(Math.max(0, currentIndents)));
        return this;
    }

    public TextBuilder newLineWithCurrentIndents(String str) {
        return newLineWithCurrentIndents().append(str);
    }

    public TextBuilder deleteCharAt(int index) {
        if (index > 0) {
            builder.deleteCharAt(index);
        } else {
            builder.deleteCharAt(builder.length() + index);
        }
        return this;
    }

    public String toString() {
        return this.builder.toString();
    }

    public void writeToFile(File file) throws IOException {
        Files.writeString(Path.of(file.toURI()), toString());
    }

}
