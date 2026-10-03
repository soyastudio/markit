package soya.framework.markit.workshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soya.framework.markit.workshop.service.ProjectService;

import java.io.IOException;

@RestController
@RequestMapping("/api/project")
@Tag(name = "Project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping(
            value = "/{id}",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @Operation(
            operationId = "get",
            summary = "Processing project configuration markdown file.",
            description = "Initialize ticket in markdown format for configuring."
    )
    public ResponseEntity<String> forward(@PathVariable String id,
                                          @RequestParam(required = false) String template,
                                          @RequestParam(required = false) boolean update) {
        try {
            return ResponseEntity.ok(projectService.get(id, template, update));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
            return ResponseEntity.ok(projectService.process(id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
