package server.UDP;

import global.object.Request;
import global.object.Response;
import global.object.MusicBand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.managers.CommandManager;
import global.utility.StandardConsole;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.util.Set;

public class DatagramServer {
//    private static final Logger log = LoggerFactory.getLogger(DatagramServer.class);
    private final CommandManager commandRuler;
    private DatagramChannel channel;
    private Selector selector;
    private InetSocketAddress address;
    private StandardConsole console = new StandardConsole();

    public DatagramServer(String host, int port, CommandManager commandRuler) throws IOException {
        this.address = new InetSocketAddress(host, port);
        this.commandRuler = commandRuler;
        this.channel = DatagramChannel.open();
        this.channel.configureBlocking(false); // Установка неблокирующего режима
        this.channel.bind(address);
        this.selector = Selector.open();
        this.channel.register(selector, SelectionKey.OP_READ);
    }

    public void start() throws IOException {
//        log.info("UDP Server started...");
//        console.println("UDP Server started...");
        new Thread(this::handleConsoleCommands).start();

        while (true) {
            // Ждем события
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isReadable()) {
                    handleRequest();
                }
                iterator.remove();
            }
        }
    }

    private void handleRequest() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            SocketAddress clientAddress = channel.receive(buffer); // Получение данных от клиента
            buffer.flip();

            byte[] data = new byte[buffer.limit()];
            buffer.get(data);
            try (ObjectInputStream oi = new ObjectInputStream(new ByteArrayInputStream(data))) {
                Request request = (Request) oi.readObject();
                String gotData = request.getCommandMassage();
                MusicBand gotMusicBand = request.getMusicBand();
//                log.info("Получено: " + gotData + " | MusicBand: " + gotMusicBand);
//                console.println("Получено: " + gotData + " | MusicBand: " + gotMusicBand);

                String[] tokens = (gotData.trim() + " ").split(" ", 2);
                tokens[1] = tokens[1].trim();
                String executingCommand = tokens[0];

                var command = commandRuler.getCommands().get(executingCommand);
                if (executingCommand.equals("reconnect")) {
                    return;
                }
                if (command == null && !executingCommand.equals("execute_script")) {
                    sendAnswer(new Response("Команда '" + tokens[0] + "' не найдена. Наберите 'help' для справки\n"), clientAddress);
                    return;
                }

                Response response = command.apply(tokens, gotMusicBand);
                sendAnswer(response, clientAddress);
            } catch (ClassNotFoundException | IOException e) {
//                log.error("Ошибка обработки запроса: " + e.getMessage());
//                console.println("Ошибка обработки запроса: " + e.getMessage());
            }
        } catch (IOException e) {
//            log.error("Ошибка при приеме данных: ", e);
//            console.println("Ошибка при приеме данных: " + e.getMessage());
        }
    }

    public void sendAnswer(Response response, SocketAddress clientAddress) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            objectOutputStream.flush();

            byte[] responseBytes = byteArrayOutputStream.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(responseBytes);
            channel.send(buffer, clientAddress);
        } catch (IOException e) {
//            log.error("Ошибка отправки ответа клиенту: " + e.getMessage());
//            console.println("Ошибка отправки ответа клиенту: " + e.getMessage());
        }
    }

    private void handleConsoleCommands() {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String input = consoleReader.readLine();
                String[] tokens = (input.trim() + " ").split(" ", 2);
                tokens[1] = tokens[1].trim();
                String executingCommand = tokens[0];
                var command = commandRuler.getCommands().get("save");
                var exitCommand = commandRuler.getCommands().get("exit");
                if (executingCommand.equals("save")) {
                    Response serverResponse = command.apply(tokens, null);
                } else if (executingCommand.equals("exit")) {
                    Response serverResponseSave = command.apply(tokens, null);
                    Response serverResponseExit = exitCommand.apply(tokens, null);
                } else {
//                    log.warn("Внимание! Введенная вами команда отсутствует в базе сервера. Вам доступны следующие две команды: save, exit.");
//                    console.println("Внимание! Введенная вами команда отсутствует в базе сервера. Вам доступны следующие две команды: save, exit.");
                }
            } catch (IOException e) {
//                log.error("Ошибка чтения команды из консоли: ", e.getMessage());
//                console.println("Ошибка чтения команды из консоли: " + e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

//package server.UDP;
//
//import global.object.Request;
//import global.object.Response;
//import global.object.MusicBand;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import server.managers.CommandManager;
//
//import java.io.*;
//import java.net.*;
//import java.util.HashSet;
//import java.util.Set;
//
//public class DatagramServer {
//    private static final Logger log = LoggerFactory.getLogger(DatagramServer.class);
//    private final CommandManager commandRuler;
//    private InetSocketAddress address;
//    private Set<InetSocketAddress> clients;
//    private DatagramSocket socket;
//
//    public DatagramServer(String host, int port, CommandManager commandRuler) throws SocketException {
//        this.address = new InetSocketAddress(host, port);
//        this.clients = new HashSet<>();
//        this.commandRuler = commandRuler;
//        this.socket = new DatagramSocket(address);
//    }
//
//    public void start() {
//        log.info("UDP Server started...");
//        new Thread(this::handleConsoleCommands).start();
//
//        while (true) {
//            try {
//                // Прием данных от клиента
//                byte[] buffer = new byte[4096];
//                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
//                socket.receive(packet);
//                handleRequest(packet);
//            } catch (IOException e) {
//                log.error("Ошибка при обработке данных: ", e);
//            }
//        }
//    }
//
//    private void handleRequest(DatagramPacket packet) {
//        try (ObjectInputStream oi = new ObjectInputStream(new ByteArrayInputStream(packet.getData()))) {
//            Request request = (Request) oi.readObject();
//            String gotData = request.getCommandMassage();
//            MusicBand gotMusicBand = request.getMusicBand();
//            log.info("Получено: " + gotData + " | MusicBand: " + gotMusicBand);
//
//            String[] tokens = (gotData.trim() + " ").split(" ", 2);
//            tokens[1] = tokens[1].trim();
//            String executingCommand = tokens[0];
//
//            var command = commandRuler.getCommands().get(executingCommand);
//            if (executingCommand.equals("reconnect")) {
//                return;
//            }
//            if (command == null && !executingCommand.equals("execute_script")) {
//                sendAnswer(new Response("Команда '" + tokens[0] + "' не найдена. Наберите 'help' для справки\n"), packet.getSocketAddress());
//                return;
//            }
//
//            Response response = command.apply(tokens, gotMusicBand);
//            sendAnswer(response, packet.getSocketAddress());
//        } catch (ClassNotFoundException | IOException e) {
//            log.error("Ошибка обработки запроса: " + e.getMessage());
//        }
//    }
//
//    public void sendAnswer(Response response, SocketAddress clientAddress) {
//        try {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//            objectOutputStream.writeObject(response);
//            objectOutputStream.flush();
//
//            byte[] responseBytes = byteArrayOutputStream.toByteArray();
//            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, clientAddress);
//            socket.send(responsePacket);
//        } catch (IOException e) {
//            log.error("Ошибка отправки ответа клиенту: " + e.getMessage());
//        }
//    }
//
//    private void handleConsoleCommands() {
//        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
//        while (true) {
//            try {
//                String input = consoleReader.readLine();
//                String[] tokens = (input.trim() + " ").split(" ", 2);
//                tokens[1] = tokens[1].trim();
//                String executingCommand = tokens[0];
//                var command = commandRuler.getCommands().get("save");
//                var exitCommand = commandRuler.getCommands().get("exit");
//                if (executingCommand.equals("save")) {
//                    Response serverResponse = command.apply(tokens, null);
//                } else if (executingCommand.equals("exit")) {
//                    Response serverResponseSave = command.apply(tokens, null);
//                    Response serverResponseExit = exitCommand.apply(tokens, null);
//                } else {
//                    log.warn("Внимание! Введенная вами команда отсутствует в базе сервера. Вам доступны следующие две команды: save, exit. Введите любую из них.");
//                }
//            } catch (IOException e) {
//                log.error("Ошибка чтения команды из консоли: ", e);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//}
//
