package org.chg.batchdemo.batch;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class MyItemWriter implements ItemWriter<Integer> {

    @Override
    public void write(Chunk chunk) throws Exception {
        chunk.getItems().forEach(System.out::println);
    }
}
