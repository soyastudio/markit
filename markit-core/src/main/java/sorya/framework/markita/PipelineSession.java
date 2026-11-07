package sorya.framework.markita;

import sorya.framework.markita.support.PipelineMetadata;

import java.util.Map;

public interface PipelineSession {
    Object getResource();

    PipelineMetadata getPipelineMetadata();

    Map<String, Object> getAnnotations();
}
