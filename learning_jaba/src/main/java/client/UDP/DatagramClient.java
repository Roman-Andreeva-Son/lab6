package client.UDP;

import client.commands.ExecuteScript;
import client.utility.Ask;
import global.object.Request;
import global.object.Response;
import global.object.MusicBand;
import global.utility.StandardConsole;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class DatagramClient {
    static StandardConsole console = new StandardConsole();
    private String host;
    private int port;
    private static DatagramChannel channel;
    private static InetSocketAddress serverAddress;

    public DatagramClient(String host, int port) throws IOException {
        this.port = port;
        this.host = host;
        this.serverAddress = new InetSocketAddress(host, port);
        this.channel = DatagramChannel.open();
        this.channel.configureBlocking(false);
    }

    public void start() throws Exception {
        console.println("Подключение к серверу через UDP...");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            String[] tokens = (command.trim() + " ").split(" ", 2);
            String command1 = tokens[0];

            if (command1.equals("exit")) {
                console.println("Завершение сеанса");
                try {
                    channel.close();
                } catch (IOException e) {
                    console.println("Ошибка при закрытии канала: " + e.getMessage());
                }
                System.exit(0);
            }

            if (command1.equals("save")) {
                console.println("Вам недоступна данная команда");
                continue;
            }

            if (command1.equals("reconnect")) {
                console.println("Попытка переподключиться....");
                try {
                    if (channel != null && channel.isOpen()) {
                        channel.close();  // Закрываем предыдущий канал, если он открыт
                    }
                    this.channel = DatagramChannel.open();
                    this.channel.configureBlocking(false);
                    console.println("Давай попробуем отправить еще раз:");
                } catch (IOException e) {
                    console.println("Не удалось подключиться к серверу. Сервер еще не работает.");
                }
                continue;
            }

            try {
                if (command1.equals("add") || command1.equals("add_if_min") || command1.equals("update_id") || command1.equals("add_if_max") || command1.equals("insert_at")) {
                    MusicBand musicBand = Ask.askMusicBand(console);
                    Request request = new Request(command, musicBand);
                    sendRequest(request);
                } else if (command1.equals("execute_script")) {
                    ExecuteScript.execute(command);
                } else if (!command1.equals("save")) {
                    Request request = new Request(command, null);
                    sendRequest(request);
                }
            }catch(Ask.AskBreak e){
                console.println("ОтмЭна");
            }
        }
    }

    public static void sendRequest(Request request) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(request);
            objectOutputStream.close();
            ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

            // Отправка запроса на сервер
            channel.send(buffer, serverAddress);

            // Получение ответа от сервера
            Object response = getAnswer();
            if (response != null) {
                console.println("Ответ от сервера: \n" + response);
            } else {
                console.println("Нет ответа от сервера (данные не отправлены или  сервер в ауте).");
            }
        } catch (IOException e) {
            console.println("Не удалось отправить запрос: " + e.getMessage());
            console.println("Не удалось отправить запрос, так как или сервер временно недоступен, или данные были введены не корректно.");
//            console.println("Сервер временно недоступен. Попробуйте переподключиться, используя reconnect.");
        }
    }

    public static Object getAnswer() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        DatagramPacket packet = new DatagramPacket(buffer.array(), buffer.array().length);
        long startTime = System.currentTimeMillis();

        // Установка тайм-аута для получения ответа
        while (System.currentTimeMillis() - startTime < 4000) {  // Тайм-аут в 4 секунды
            buffer.clear();
            SocketAddress responseAddress = channel.receive(buffer);

            if (responseAddress != null) {
                byte[] responseBytes = buffer.array();
                try (ObjectInputStream oi = new ObjectInputStream(new ByteArrayInputStream(responseBytes))) {
                    Response answer = (Response) oi.readObject();
                    return answer.getMessage();
                } catch (ClassNotFoundException | EOFException | StreamCorruptedException e) {
                    console.println("Ошибка при десериализации ответа: " + e.getMessage());
                }
            }
        }
        return null;  // Вернуть null, если ответ не получен в течение заданного тайм-аута
    }
}

//package client.UDP;
//
//import client.commands.ExecuteScript;
//import client.utility.Ask;
//import global.object.Request;
//import global.object.Response;
//import global.object.MusicBand;
//import global.utility.StandardConsole;
//
//import java.io.*;
//import java.net.*;
//import java.util.Scanner;
//
//public class DatagramClient {
//    static StandardConsole console = new StandardConsole();
//    private String host;
//    private int port;
//    private static DatagramSocket socket;
//    private static InetSocketAddress serverAddress;
//
//    public DatagramClient(String host, int port) throws SocketException {
//        this.port = port;
//        this.host = host;
//        this.serverAddress = new InetSocketAddress(host, port);
//        this.socket = new DatagramSocket();
//        socket.setSoTimeout(10000);  // Устанавливаем тайм-аут на 10 секунд
//    }
//
//    public void start() throws Exception {
//        console.println("Подключение к серверу через UDP...");
//
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNextLine()) {
//            String command = scanner.nextLine().trim();
//            String[] tokens = (command.trim() + " ").split(" ", 2);
//            String command1 = tokens[0];
//
//            if (command1.equals("exit")) {
//                console.println("Завершение сеанса");
//                try {
//                    socket.close();
//                } catch (Exception e) {
//                    console.println("Ошибка при закрытии сокета: " + e.getMessage());
//                }
//                System.exit(0);
//            }
//
//            if (command1.equals("save")) {
//                console.println("Вам недоступна данная команда");
//                continue;
//            }
//
//            try {
//                if (command1.equals("add") || command1.equals("add_if_min") || command1.equals("update_id") || command1.equals("add_if_max") || command1.equals("insert_at")) {
//                    MusicBand musicBand = Ask.askMusicBand(console);
//                    Request request = new Request(command, musicBand);
//                    sendRequest(request);
//                } else if (command1.equals("execute_script")) {
//                    ExecuteScript.execute(command);
//                } else if (!command1.equals("save")) {
//                    Request request = new Request(command, null);
//                    sendRequest(request);
//                }
//            } catch (Ask.AskBreak e) {
//                console.println("Отмена");
//            }
//        }
//    }
//
//    public static void sendRequest(Request request) throws IOException {
//        try {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//            objectOutputStream.writeObject(request);
//            objectOutputStream.close();
//            byte[] buffer = byteArrayOutputStream.toByteArray();
//
//            // Отправка запроса на сервер
//            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress);
//            socket.send(packet);
//
//            // Получение ответа от сервера
//            Object response = getAnswer();
//            if (response != null) {
//                console.println("Ответ от сервера: \n" + response);
//            } else {
//                console.println("Нет ответа от сервера или произошел тайм-аут.");
//            }
//        } catch (IOException e) {
//            console.println("Не удалось отправить запрос: " + e.getMessage());
//            console.println("Не удалось отправить запрос, так как или сервер временно недоступен, или данные были введены некорректно.");
//        }
//    }
//
//    public static Object getAnswer() throws IOException {
//        byte[] buffer = new byte[4096];
//        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
//        long startTime = System.currentTimeMillis();
//
//        // Установка тайм-аута для получения ответа
//        while (System.currentTimeMillis() - startTime < 10000) {  // Тайм-аут в 10 секунд
//            try {
//                socket.receive(packet);  // Получение пакета
//                byte[] responseBytes = packet.getData();
//
//                try (ObjectInputStream oi = new ObjectInputStream(new ByteArrayInputStream(responseBytes))) {
//                    Response answer = (Response) oi.readObject();
//                    return answer.getMessage();
//                } catch (ClassNotFoundException | EOFException | StreamCorruptedException e) {
//                    console.println("Ошибка при десериализации ответа: " + e.getMessage());
//                }
//            } catch (SocketTimeoutException e) {
//                console.println("Тайм-аут при ожидании ответа от сервера.");
//                return null;
//            }
//        }
//        return null;  // Вернуть null, если ответ не получен в течение заданного тайм-аута
//    }
//}
