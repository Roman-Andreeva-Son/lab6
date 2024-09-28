
package client.commands;

import client.UDP.DatagramClient;
import global.object.*;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Stack;

public class ExecuteScript {
    private static Stack<File> st = new Stack<>();

    public static void execute(String command) throws Exception {
        String[] splitCommand = command.split(" ");
        if (splitCommand.length < 2) {
            System.out.println("Ошибка: Введите имя файла скрипта");
            return;
        }

        File file = new File(splitCommand[1]);
        if (!file.canRead()) {
            System.out.println("У вас недостаточно прав для чтения этого файла");
            return;
        }
        if (st.isEmpty() || !st.contains(file)) {
            st.add(file);
        }

        String path = splitCommand[1];
        var br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        String line;
        String[] org = new String[10]; // Массив для параметров команд add, update и т.д.

        while ((line = br.readLine()) != null) {
            String mainCommand = line.split(" ")[0];

            // Обработка сложных команд с аргументами (add, update)
            if (mainCommand.equals("add") || mainCommand.equals("add_if_min") || mainCommand.equals("update_id") || mainCommand.equals("add_if_max") || mainCommand.equals("insert_at_index")) {
                  for (int n = 0; n < org.length; n++) {
                    if ((line = br.readLine()) != null) {
                        org[n] = line;
                    }
                }


                Request request = new Request(mainCommand, new MusicBand(
                        org[0], // name
                        new Coordinates(Float.parseFloat(org[1]), Integer.parseInt(org[2])), // coordinates
                        Long.parseLong(org[3]), // NOP
                        Long.parseLong(org[4]), // albumsCount
                        MusicGenre.valueOf(org[5]), // genre
                        new Person(org[6], org[7], Color.valueOf(org[8]), Color.valueOf(org[9])) // Person
                ));

                // Отправляем запрос через DatagramClient
                DatagramClient.sendRequest(request);

            } else {
                // Обработка команд без аргументов (например, help)
                if (line.contains("execute_script")) {
                    String[] newCommandSplit = line.split(" ");
                    if (newCommandSplit.length < 2) {
                        System.out.println("Ошибка: укажите файл для execute_script.");
                    } else {
                        File file_new = new File(newCommandSplit[1]);
                        if (!file_new.canRead()) {
                            System.out.println("Недостаточно прав для чтения файла.");
                        }
                        if (st.contains(file_new)) {
                            System.out.println("Рекурсия файла " + file.getName() + " была пропущена");
                        } else {
                            execute(line);
                        }
                    }
                } else {
                    // Отправляем простую команду (например, help)
                    Request request = new Request(line, null);
                    DatagramClient.sendRequest(request);
                }
            }
        }
        st.pop();
    }

    public String getName() {
        return "execute_script file_name";
    }
}
//package client.commands;
//
//import client.UDP.DatagramClient;
//import global.object.*;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//import java.util.Stack;
//
//public class ExecuteScript {
//    private static Stack<File> st = new Stack<>();
//
//    public static void execute(String command) throws Exception {
//        String[] splitCommand = command.split(" ");
//        if (splitCommand.length < 2) {
//            System.out.println("Ошибка: Введите имя файла скрипта");
//            return;
//        }
//
//        File file = new File(splitCommand[1]);
//        if (!file.canRead()) {
//            System.out.println("У вас недостаточно прав для чтения этого файла");
//            return;
//        }
//        if (st.isEmpty() || !st.contains(file)) {
//            st.add(file);
//        }
//
//        String path = splitCommand[1];
//        var br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
//        String line;
//        String[] org = new String[10]; // Массив для параметров команд add, update и т.д.
//
//        while ((line = br.readLine()) != null) {
//            String mainCommand = line.split(" ")[0];
//
//            // Обработка сложных команд с аргументами (add, update)
//            if (mainCommand.equals("add") || mainCommand.equals("add_if_min") || mainCommand.equals("update_id") || mainCommand.equals("add_if_max") || mainCommand.equals("insert_at_index")) {
//                for (int n = 0; n < org.length; n++) {
//                    if ((line = br.readLine()) != null) {
//                        org[n] = line;
//                    }
//                }
//
//                Request request = new Request(mainCommand, new MusicBand(
//                        org[0], // name
//                        new Coordinates(Float.parseFloat(org[1]), Integer.parseInt(org[2])), // coordinates
//                        Long.parseLong(org[3]), // NOP
//                        Long.parseLong(org[4]), // albumsCount
//                        MusicGenre.valueOf(org[5]), // genre
//                        new Person(org[6], org[7], Color.valueOf(org[8]), Color.valueOf(org[9])) // Person
//                ));
//
//                // Отправляем запрос через DatagramClient
//                DatagramClient.sendRequest(request);
//
//            } else {
//                // Обработка команд без аргументов (например, help)
//                if (line.contains("execute_script")) {
//                    String[] newCommandSplit = line.split(" ");
//                    if (newCommandSplit.length < 2) {
//                        System.out.println("Ошибка: укажите файл для execute_script.");
//                    } else {
//                        File file_new = new File(newCommandSplit[1]);
//                        if (!file_new.canRead()) {
//                            System.out.println("Недостаточно прав для чтения файла.");
//                        }
//                        if (st.contains(file_new)) {
//                            System.out.println("Рекурсия файла " + file.getName() + " была пропущена");
//                        } else {
//                            execute(line);
//                        }
//                    }
//                } else {
//                    // Отправляем простую команду (например, help)
//                    Request request = new Request(line, null);
//                    DatagramClient.sendRequest(request);
//                }
//            }
//        }
//        st.pop();
//    }
//
//    public String getName() {
//        return "execute_script file_name";
//    }
//}
