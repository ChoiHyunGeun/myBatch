package org.chg.batchdemo.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyJob {
    private final JobLauncher jobLauncher;

    @Qualifier("jobOne")
    private final Job jobOne;

    @Qualifier("chunkJob")
    private final Job chunkJob;

    public MyJob(JobLauncher jobLauncher, Job jobOne, Job chunkJob) {
        this.jobLauncher = jobLauncher;
        this.jobOne = jobOne;
        this.chunkJob = chunkJob;
    }

    //@Scheduled(cron = "0/5 * * * * ?")
    public void runBatchJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(jobOne, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void runBatchChunkJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            JobExecution e = jobLauncher.run(chunkJob, jobParameters);

            if( e.getStatus() == BatchStatus.FAILED) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
