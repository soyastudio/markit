package sorya.framework.markita.support;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.commonmark.ext.front.matter.YamlFrontMatterBlock;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterNode;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import sorya.framework.markita.*;
import sorya.framework.markita.util.MarkdownUtils;
import sorya.framework.markita.util.ObjectBuilder;
import sorya.framework.markita.util.TextBuilder;
import sorya.framework.markita.util.UriUtils;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.*;

public class DefaultTemplate implements Template {

    private final transient TemplateMetadata metadata = new TemplateMetadata();
    private final List<Parameter> parameters = new ArrayList<>();
    private final LinkedHashMap<String, AnnotatorBuilder> annotatorBuilders = new LinkedHashMap<>();
    private final LinkedHashMap<String, EvaluatorBuilder> evaluatorBuilders = new LinkedHashMap<>();
    private final LinkedHashMap<String, RendererBuilder> rendererBuilders = new LinkedHashMap<>();

    protected DefaultTemplate() {

    }

    public DefaultTemplate(String markdown) {
        parse(markdown);
    }

    private void parse(String md) {
        Parser parser = Parser.builder()
                .extensions(Collections.singletonList(YamlFrontMatterExtension.create()))
                .build();
        Node document = parser.parse(md);
        document.accept(new TemplateParser());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(this));
    }

    private void init() {
        Arrays.stream(metadata.getParameters()).forEach(e -> {
            parameters.add(fromURI(e, Parameter.class));
        });

        Arrays.stream(metadata.getAnnotators()).forEach(a -> {
            AnnotatorBuilder builder = fromURI(a, AnnotatorBuilder.class);
            annotatorBuilders.put(builder.name, builder);
        });

        Arrays.stream(metadata.getEvaluators()).forEach(e -> {
            EvaluatorBuilder builder = fromURI(e, EvaluatorBuilder.class);
            evaluatorBuilders.put(builder.name, builder);
        });

        Arrays.stream(metadata.getRenderers()).forEach(r -> {
            RendererBuilder builder = fromURI(r, RendererBuilder.class);
            rendererBuilders.put(builder.name, builder);
        });
    }

    private <T> T fromURI(String uri, Class<T> type) {
        Map<String, String> params = UriUtils.getQueryParams(uri);
        String name = uri.contains("?") ? uri.substring(0, uri.indexOf("?")) : uri;
        params.put("name", name);
        return ObjectBuilder.create(type, params);
    }

    @Override
    public String getName() {
        return metadata.getName();
    }

    @Override
    public String toString() {
        TextBuilder textBuilder = TextBuilder.builder().setIndent(2);
        return textBuilder.toString();
    }

    public List<Parameter> parameters() {
        return parameters;
    }

    public LinkedHashMap<String, AnnotatorBuilder> annotatorBuilders() {
        return annotatorBuilders;
    }

    public LinkedHashMap<String, EvaluatorBuilder> evaluatorBuilders() {
        return evaluatorBuilders;
    }

    public LinkedHashMap<String, RendererBuilder> rendererBuilders() {
        return rendererBuilders;
    }

    @Override
    public TemplatePipeline create() {
        return new DefaultTemplatePipeline(this);
    }


    public static class Parameter implements TemplateParameter {
        private String name;
        private String type;
        private boolean required;
        private String defaultValue;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getType() {
            return type;
        }

        @Override
        public boolean isRequired() {
            return required;
        }

        @Override
        public String getDefaultValue() {
            return defaultValue;
        }
    }

    public static class AnnotatorBuilder extends TemplateProcessorBuilder<TemplateAnnotator> {

        @Override
        TemplateAnnotator create() {
            return null;
        }
    }

    public static class EvaluatorBuilder extends TemplateProcessorBuilder<TemplateEvaluator> {

        @Override
        TemplateEvaluator create() {
            return null;
        }
    }

    public static class RendererBuilder extends TemplateProcessorBuilder<TemplateRenderer> {

        @Override
        TemplateRenderer create() {
            return null;
        }
    }

    public abstract static class TemplateProcessorBuilder<T extends TemplateProcessor> {

        protected String name;
        protected String format;

        protected String title;
        protected transient List<String> descriptions = new ArrayList<>();

        protected String templateFormat;
        protected String template;
        protected String resolver;

        abstract T create();

    }

    private class TemplateParser extends AbstractVisitor {
        @Override
        public void visit(CustomBlock customBlock) {
            if (customBlock instanceof YamlFrontMatterBlock) {
                Node node = customBlock.getFirstChild();
                while (node != null) {
                    if (node instanceof YamlFrontMatterNode yn) {
                        if (!yn.getValues().isEmpty()) {
                            try {
                                Field field = TemplateMetadata.class.getDeclaredField(yn.getKey());
                                field.setAccessible(true);
                                if (field.getType().isArray()) {
                                    field.set(metadata, yn.getValues().toArray(new String[0]));
                                } else {
                                    field.set(metadata, yn.getValues().get(0));
                                }
                            } catch (Exception e) {
                                if (yn.getValues().size() == 1) {
                                    metadata.getMetadata().put(yn.getKey(), yn.getValues().get(0));
                                } else {
                                    metadata.getMetadata().put(yn.getKey(), yn.getValues().toArray(new String[0]));
                                }
                            }
                        }
                    }

                    node = node.getNext();
                }

                init();

            }
        }

        @Override
        public void visit(Heading heading) {
            if (heading.getLevel() == 2) {
                Node node = heading.getFirstChild();
                if (node instanceof Link link) {
                    TemplateProcessorBuilder<? extends TemplateProcessor> builder = null;

                    if (link.getFirstChild() instanceof Text txt) {
                        String title = txt.getLiteral();
                        String dest = link.getDestination();
                        URI uri = URI.create(dest);

                        if ("annotator".equalsIgnoreCase(uri.getScheme())) {
                            builder = annotatorBuilders.get(uri.getHost());
                            if (builder != null) {
                                builder.title = title;
                            }
                        }
                    }

                    if (builder != null) {
                        Node sibling = heading.getNext();
                        while (sibling != null) {
                            if (sibling instanceof Heading h2 && h2.getLevel() >= 2) {
                                // navigate to next builder:
                                break;
                            }

                            if (sibling instanceof FencedCodeBlock fencedCodeBlock) {
                                builder.templateFormat = fencedCodeBlock.getInfo();
                                builder.template = fencedCodeBlock.getLiteral();

                            } else if (sibling instanceof Paragraph paragraph) {
                                builder.descriptions.add(MarkdownUtils.render(paragraph));

                            } else if (sibling instanceof HtmlBlock htmlBlock) {
                                String contents = htmlBlock.getLiteral();
                                System.out.println(contents);

                            }

                            sibling = sibling.getNext();
                        }

                    }
                }
            }
        }
    }
}
