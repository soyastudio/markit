package sorya.framework.markita.support;

import sorya.framework.markita.Pipeline;

public interface PipelineResourceExtractor<T> {
    T getResource();

    void init(Pipeline pipeline);
}
