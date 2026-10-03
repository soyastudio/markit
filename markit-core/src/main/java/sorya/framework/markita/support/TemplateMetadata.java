package sorya.framework.markita.support;

import java.util.LinkedHashMap;
import java.util.Map;

public class TemplateMetadata {

    private String name;
    private String owner;
    private String description;
    private String[] tags = new String[0];

    private String[] functions = new String[0];
    private String[] parameters = new String[0];
    private String[] annotators = new String[0];
    private String[] evaluators = new String[0];
    private String[] renderers = new String[0];

    private final Map<String, Object> metadata = new LinkedHashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public String[] getFunctions() {
        return functions;
    }

    public void setFunctions(String[] functions) {
        this.functions = functions;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public String[] getAnnotators() {
        return annotators;
    }

    public void setAnnotators(String[] annotators) {
        this.annotators = annotators;
    }

    public String[] getEvaluators() {
        return evaluators;
    }

    public void setEvaluators(String[] evaluators) {
        this.evaluators = evaluators;
    }

    public String[] getRenderers() {
        return renderers;
    }

    public void setRenderers(String[] renderers) {
        this.renderers = renderers;
    }

}
