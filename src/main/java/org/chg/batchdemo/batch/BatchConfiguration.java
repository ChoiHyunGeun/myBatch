package org.chg.batchdemo.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration {
    private final MyItemReader myItemReader;
    private final MyItemProcess myItemProcess;
    private final MyItemWriter myItemWriter;

    public BatchConfiguration(MyItemReader myItemReader, MyItemProcess myItemProcess, MyItemWriter myItemWriter) {
        this.myItemReader = myItemReader;
        this.myItemProcess = myItemProcess;
        this.myItemWriter = myItemWriter;
    }

    @Bean
    public Job jobOne(JobRepository jobRepository, Step step) {
        return new JobBuilder("jobOne", jobRepository)
                .start(step)
                //.next(step) -> step이 여러 개 실행되어야 할 때
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, Tasklet testTasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("step", jobRepository)
                .tasklet(testTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet testTasklet(){
        return ((contribution, chunkContext) -> {
            System.out.println("SUCCESS11");
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public Tasklet testTasklet2(){
        return ((contribution, chunkContext) -> {
            System.out.println("SUCCESS22");
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public Job chunkJob(JobRepository jobRepository, Step chunkStep) {
        return new JobBuilder("chunkJob", jobRepository)
                .start(chunkStep)
                .build();
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository,  PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("chunkStep", jobRepository)
                .<Integer, Integer>chunk(10, platformTransactionManager)
                .reader(myItemReader)
                .processor(myItemProcess)
                .writer(myItemWriter)
                .build();
    }

    //국고 배치 설정과 비교하여 알아두기
}
