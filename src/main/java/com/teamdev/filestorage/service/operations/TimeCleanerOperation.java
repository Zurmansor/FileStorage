package com.teamdev.filestorage.service.operations;


import java.util.Timer;
import java.util.TimerTask;

/**
 *periodic cleanup of temporary files
 */
public class TimeCleanerOperation {
    public void timeCleaner() {
        final TempFileCleanerOperation tempFileCleanerOperation = new TempFileCleanerOperation();
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //call the Cleaner method
                tempFileCleanerOperation.clean();
            }
        };
        timer.schedule(timerTask, 3000, 3000);
    }
}
