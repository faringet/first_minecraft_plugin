package me.fari.Server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Server {

    private static List<String> phrases;

    public static void main(String[] args) throws IOException {
        phrases = Files.readAllLines(Paths.get("/Users/mihaildavydov/IdeaProjects/FirsPlugin/src/main/resources/list of phrases.txt"));

        try (ServerSocket server = new ServerSocket(8100)) {
            System.out.println("server started");
            while (true)
                try {
                    Socket socket = server.accept();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try (
                                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                            ) {
                                while (true) {
                                    String response = rndPhrases();
                                    System.out.println("Response: " + response);
                                    writer.write(response);
                                    writer.newLine();
                                    writer.flush();
                                    Thread.sleep(5000);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            } finally {
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String rndPhrases() {
        int lineNumber = new Random().nextInt(phrases.size());
        return phrases.get(lineNumber);
    }
}

