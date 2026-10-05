package soya.framework.markit.workshop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soya.framework.markit.workshop.service.FunctionService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/function")
@Tag(name = "Template Function")
public class FunctionController {

    @Autowired
    FunctionService functionService;

    @GetMapping(
            value = "/packages",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<String>> packages(@PathVariable String name) {
        return ResponseEntity.ok(functionService.packageNames());
    }

    @GetMapping(
            value = "/package/{name}",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> get(@PathVariable String name) {
        return ResponseEntity.ok(functionService.get(name));
    }

    @PutMapping(
            value = "/package/{name}",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> build(@PathVariable String name) {
        try {
            return ResponseEntity.ok(functionService.build(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
