import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class MessageSender extends Thread implements Runnable {
    private final ArrayList<Message> messageBuffer;
    private boolean isRunning;
    private final int timeout;
    private final BufferedWriter writer;

    public MessageSender(Socket socket, int timeout) {
        this.messageBuffer = new ArrayList<>();
        this.timeout = timeout;
        OutputStream out = null;
        try {
            out = socket.getOutputStream();
        } catch (IOException e) {
            System.out.println("Невозможно получить поток вывода!");
            System.exit(-1);
        }
        writer = new BufferedWriter(new OutputStreamWriter(out));
    }

    @Override
    public void interrupt() {
        isRunning = false;
        super.interrupt();
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
                sendMessages();
                sleep(timeout);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String message) {
        messageBuffer.add(new Message(message));
    }

    private void sendMessages() throws IOException {

        for (Message message : messageBuffer) {
            writer.write(message.text + " :: " + message.date.toString().split(" ")[3] + "\n");
            writer.flush();
        }
        messageBuffer.clear();
    }

    private static class Message {
        Date date;
        String text;

        private Message(String message) {
            text = message;
            date = new Date();
        }
    }

}
