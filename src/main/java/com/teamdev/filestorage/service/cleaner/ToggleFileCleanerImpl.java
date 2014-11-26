package com.teamdev.filestorage.service.cleaner;


import java.util.Timer;
import java.util.TimerTask;

public class ToggleFileCleanerImpl implements ToggleFileCleaner{

    final TempFileCleanerOperation tempFileCleanerOperation = new TempFileCleanerOperation();
    Timer timer = new Timer();

    /**
     * Start periodic cleanup of temporary files
     */
    @Override
    public void startFileCleaner() {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //call the Cleaner method
                tempFileCleanerOperation.clean();
            }
        };
        timer.schedule(timerTask, 3000, 3000);
    }

    @Override
    public void stopFileCleaner() {
        timer.cancel();
    }
}
