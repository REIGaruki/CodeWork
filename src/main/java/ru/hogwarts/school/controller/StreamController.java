package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("stream")
public class StreamController {

    @GetMapping()
    public Long getMath(@RequestParam(name="quant", required = false, defaultValue = "1000000") long quant) {
        long start = System.currentTimeMillis();
        IntStream
                .iterate(1, a -> a++)
                .limit(quant)
                .reduce(0, Integer::sum);
        long finish = System.currentTimeMillis();
        return finish - start;
    }
}
