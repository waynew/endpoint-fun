package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EndpointFun{
    public static void main(String [] argv) throws IOException {
        ServerSocket listener = new ServerSocket(8765);
        try{
            System.out.println("Listening on port 8765");
            while (true) {
                String response = "{\"server_type\": \"Java\"}";
                Socket socket = listener.accept();
                try {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream())
                    );
                    String body = "";
                    String data = "garbage";
                    String status = "OK";
                    Integer status_code = 200;
                    data = reader.readLine();
                    String[] fnord = data.split(" ");
                    String path = new String(fnord[1]);
                    String method = fnord[0];

                    while (data.trim().length() > 0){
                        data = reader.readLine();
                    }

                    if (!(method.equals("GET") || method.equals("POST"))){
                        status_code = 405;
                        status = "METHOD NOT ALLOWED";
                    }




                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("HTTP/1.1 " + status_code + " " + status);
                    System.out.println(method + " " + path);
                    if (method.equals("GET")){
                        if (path.equals("/index.html")){
                            out.println("Content-Type: text/html");
                            Scanner in = new Scanner(new FileReader("index.html"));
                            response = "";
                            while (in.hasNext()){
                                response += in.nextLine() + "\n";
                            }
                            out.println("Content-Length: " + response.length());
                            out.println("");
                            out.print(response);
                        }
                        else{
                            out.println("Content-Type: application/json");
                            out.println("Content-Length: " + response.length());
                            out.println("");
                            out.print(response);
                        }
                    }
                    else if (method.equals("POST")){
                        response = "{\"name\":\"Jerva\", \"fnord\":\"Needs more power\", \"random_table\":[[1,2,3], [3,2,1], [1,1,1]], \"favorite_color\": \"brown\"}";
                        out.println("Content-Type: application/json");
                        out.println("Content-Length: " + response.length());
                        out.println("");
                        out.print(response);
                    }

                    out.flush();
                }
                finally {
                    socket.close();
                }
            }
        }
        finally {
            listener.close();
        }
    }
}
