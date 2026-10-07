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


    // =================== Packages:
    @GetMapping(
            value = "/packages",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<String>> packages() {
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

    // =================== Functions
    @GetMapping(
            value = "/{name}",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> getFunction(@PathVariable String name) {
        return ResponseEntity.ok(functionService.getFunction(name));
    }

    @PostMapping(
            value = "/{name}",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> createFunction(@PathVariable String name,
                                                 @RequestHeader(required = false, defaultValue = "freemarker") String templateFormat,
                                                 @RequestHeader(required = false, defaultValue = "yaml") String schemaFormat,
                                                 @RequestBody(required = false) String template) {
        return ResponseEntity.ok(functionService.create(name, templateFormat, schemaFormat, template));
    }

    @PostMapping(
            value = "/generate/{name}",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> generateFunction(@PathVariable String name,
                                                   @RequestHeader(defaultValue = "freemarker") String templateFormat,
                                                   @RequestHeader(defaultValue = "yaml") String schemaFormat,
                                                   @RequestBody String markdown) {
        return ResponseEntity.ok(functionService.generate(name, templateFormat, schemaFormat, markdown));
    }

    @PutMapping(
            value = "/save-or-update",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> saveFunction(@RequestBody String markdown) {
        return ResponseEntity.ok(functionService.saveOrUpdate(markdown));
    }

    @PostMapping(
            value = "/invoke/{name}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> call(@PathVariable String name, @RequestBody String input) {
        return ResponseEntity.ok(functionService.invoke(name, input));
    }

}
