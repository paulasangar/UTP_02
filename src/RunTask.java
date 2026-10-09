public class RunTask implements Runnable {

    public long objCreationTime;
    public long firstInstruction;

    public RunTask() {
        this.objCreationTime = System.nanoTime();
    }

    @Override
    public void run() {
        this.firstInstruction = System.nanoTime();
    }

    public long getTime() {
        return this.firstInstruction - this.objCreationTime;
    }
}
