package sorya.framework.markita.support;

import sorya.framework.markita.Pipeline;

public interface PipelineInitializer<T> {
    T getResource();

    void init(Pipeline pipeline);
}
