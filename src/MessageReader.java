import java.io.BufferedReader;
import java.io.IOException;

public class MessageReader extends Thread implements Runnable {
    private boolean isRunning;
    private final MessageSender messageSender;
    private final BufferedReader reader;

    public MessageReader(BufferedReader reader, MessageSender messageSender) {
        this.messageSender = messageSender;
        this.reader = reader;
    }

    @Override
    public synchronized void start() {
        isRunning = true;
        super.start();
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                messageSender.send(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void interrupt() {
        isRunning = false;
        super.interrupt();
    }
}
