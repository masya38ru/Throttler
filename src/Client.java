import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 7000;
        int timeout = 2000;
        //Создаем сокет
        Socket socket = null;
        try {
            socket = new Socket(host, port);
        } catch (UnknownHostException e) {
            System.out.println("Неизвестный хост: " + host);
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода при создании сокета " + host
                    + ":" + port);
            System.exit(-1);
        }
        MessageSender messageSender = new MessageSender(socket, timeout);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        MessageReader messageReader = new MessageReader(reader, messageSender);

        messageSender.start();
        messageReader.start();

    }
}
