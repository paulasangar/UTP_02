import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final int WARMUP = 5000;
        final int N = 10000;

        for (int i = 0; i < WARMUP; i++) {
            measureExt();
            measureRun();
        }

        long[] extTimes = new long[N];
        long[] runTimes = new long[N];

        for (int i = 0; i < N; i++) {
            extTimes[i] = measureExt();
            runTimes[i] = measureRun();
        }

        long sumExt = 0;
        for (long t : extTimes) {
            sumExt += t;
        }
        double avgExt = (double) sumExt / N;

        Arrays.sort(extTimes);
        long p50Ext = extTimes[(int) (N * 0.50)];
        long p90Ext = extTimes[(int) (N * 0.90)];
        long p99Ext = extTimes[(int) (N * 0.99)];

        long sumRun = 0;
        for (long t : runTimes) {
            sumRun += t;
        }
        double avgRun = (double) sumRun / N;

        Arrays.sort(runTimes);
        long p50Run = runTimes[(int) (N * 0.50)];
        long p90Run = runTimes[(int) (N * 0.90)];
        long p99Run = runTimes[(int) (N * 0.99)];

        System.out.println("RESULTS FOR N = " + N + " ns");
        System.out.printf("ExtThread -> Median: %.2f ns | P50: %d ns | P90: %d ns | P99: %d ns\n",
                avgExt, p50Ext, p90Ext, p99Ext);
        System.out.printf("RunTask   -> Median: %.2f ns | P50: %d ns | P90: %d ns | P99: %d ns\n",
                avgRun, p50Run, p90Run, p99Run);
    }

    public static long measureExt() {
        ExtThread thread = new ExtThread();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return thread.getTime();
    }

    public static long measureRun() {
        RunTask task = new RunTask();
        Thread thread = new Thread(task);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return task.getTime();
    }

}