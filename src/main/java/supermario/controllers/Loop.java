package supermario.controllers;

public abstract class Loop implements Runnable {
    private final double fps;
    private boolean isRunning = false;
    private long frameCounter = 0;
    private Thread thread;

    public Loop(double fps) {
        this.fps = fps;
    }

    public abstract void update();

    @Override
    public void run() {
        double updateInterval = 1000. * 1000 * 1000 / fps;
        double nextUpdate = System.nanoTime() + updateInterval;

        while (isRunning) {
            frameCounter++;
            update();
            double timeToSleep = nextUpdate - System.nanoTime();
            timeToSleep = Math.max(timeToSleep, 0.);
            long millisToSleep = (long) (timeToSleep / (1000 * 1000));
            int nanosToSleep = (int) (timeToSleep % (1000 * 1000));
            try {
                //noinspection BusyWait
                Thread.sleep(millisToSleep, nanosToSleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            nextUpdate += updateInterval;
        }
    }

    public void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public long getFrameCounter() {
        return frameCounter;
    }

    public double getFPS() {
        return fps;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
