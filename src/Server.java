import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 7000;

        //Проверим доступность порта
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Порт: " + port + " - ошибка подключения!");
            System.exit(-1);
        }

        //Создание клиента
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Порт: " + port + " - ошибка подключения!");
            System.exit(-1);
        }

        //Поток ввода от клиента, получаем из него сообщения
        InputStream inClientStream = null;
        try {
            inClientStream = clientSocket.getInputStream();
        } catch (IOException e) {
            System.out.println("Невозможно получить поток ввода!");
            System.exit(-1);
        }

        //Читаем поток
        BufferedReader reader = new BufferedReader(new InputStreamReader(inClientStream));
        String readClientStream;
        try {
            while ((readClientStream = reader.readLine()) != null) {
                System.out.println("Сообщение от клиента: " + readClientStream);
                System.out.flush();
            }
        } catch (IOException e) {
            System.out.println("Невозможно прочесть сообщение!");
            System.exit(-1);
        }
    }
}
