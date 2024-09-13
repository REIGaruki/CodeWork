package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;

import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("stream")
public class StreamController {

    @GetMapping()
    public void getMath(@RequestParam(name="quant", required = false, defaultValue = "1000000") int quant) {
        long start = System.currentTimeMillis();
        Stream
                .iterate(1, a -> a++)
                .limit(quant)
                .reduce(0, Integer::sum);
        long finish = System.currentTimeMillis();
        System.out.println("Stream of " + quant + " elemesnts: " + (finish - start) + " ms");
        long startInt = System.currentTimeMillis();
        IntStream
                .range(1, quant + 1)
                .reduce(0, Integer::sum);
        long finishInt = System.currentTimeMillis();
        System.out.println("Integer stream of " + quant + " elemesnts: " + (finishInt - startInt) + " ms");
        long startPara = System.currentTimeMillis();
        Stream
                .iterate(1, a -> a++)
                .limit(quant)
                .parallel()
                .reduce(0, Integer::sum);
        long finishPara = System.currentTimeMillis();
        System.out.println("Parallel stream of " + quant + " elemesnts: " + (finishPara - startPara) + " ms");
    }
}
