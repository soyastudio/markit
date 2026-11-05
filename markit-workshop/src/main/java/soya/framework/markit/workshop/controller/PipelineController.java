package soya.framework.markit.workshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sorya.framework.markita.support.PipelineMetadata;
import soya.framework.markit.workshop.service.PipelineService;

import java.io.IOException;

@RestController
@RequestMapping("/api/pipeline")
@Tag(name = "Pipeline")
public class PipelineController {

    @Autowired
    private PipelineService pipelineService;

    @PostMapping(value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@RequestBody PipelineMetadata pipeline) {
        return ResponseEntity.ok(pipelineService.create(pipeline));
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @Operation(
            operationId = "get",
            summary = "Processing project configuration markdown file.",
            description = "Initialize ticket in markdown format for configuring."
    )
    public ResponseEntity<String> forward(@PathVariable String id) {
        try {
            return ResponseEntity.ok(pipelineService.get(id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

/*
    @PostMapping(
            value = "/{id}",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @Operation(
            operationId = "create or update project",
            summary = "Processing project configuration markdown file.",
            description = "Initialize ticket in markdown format for configuring."
    )
    public ResponseEntity<String> process(@PathVariable String id) {
        try {
            return ResponseEntity.ok(pipelineService.process(id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

}
