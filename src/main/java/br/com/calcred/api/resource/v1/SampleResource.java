package br.com.calcred.api.resource.v1;

import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.calcred.api.service.SampleService;
import br.com.calcred.api.dto.SampleDTO;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class SampleResource {

    private final SampleService sampleService;

    @GetMapping("/{id}")
    @Timed
    public SampleDTO findById(@PathVariable @Positive @NotNull Long id) {
        return sampleService.findById(id);
    }
}
