package org.chg.batchdemo.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyItemProcess implements ItemProcessor<Integer, Integer> {
    @Override
    public Integer process(Integer item) throws Exception {
        return (item % 2 == 0) ? item : null; // 짝수만 반환하고, 나머지는 필터링
    }
}
