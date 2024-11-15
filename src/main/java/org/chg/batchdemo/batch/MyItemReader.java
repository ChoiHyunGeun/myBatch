package org.chg.batchdemo.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@StepScope
@Component
public class MyItemReader implements ItemReader {
    private final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    private int index = 0;

    @Override
    public Object read() throws Exception {
        return index < numbers.size() ? numbers.get(index++) : null; // 데이터를 하나씩 읽어오기
    }
}
