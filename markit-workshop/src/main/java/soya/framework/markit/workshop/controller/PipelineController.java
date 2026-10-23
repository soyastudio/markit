package soya.framework.markit.workshop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soya.framework.markit.workshop.service.PipelineService;

import java.io.IOException;

@RestController
@RequestMapping("/api/pipeline")
@Tag(name = "Template Pipeline")
public class PipelineController {

    @Autowired
    private PipelineService pipelineService;

    @GetMapping(
            value = "/{name}",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> get(@PathVariable String name) {
        return ResponseEntity.ok(pipelineService.get(name));
    }

    @PutMapping(
            value = "/{name}",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> process(@PathVariable String name) {
        try {
            return ResponseEntity.ok(pipelineService.process(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(
            value = "/annotator/{name}",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> addAnnotator(@PathVariable String name,
                                               @RequestHeader String title,
                                               @RequestHeader String format,
                                               @RequestHeader(required = false) String resolver,
                                               @RequestHeader String templateFormat,
                                               @RequestBody String template) {
        try {
            return ResponseEntity.ok(pipelineService.process(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
