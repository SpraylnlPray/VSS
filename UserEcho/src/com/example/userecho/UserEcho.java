package com.example.userecho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserEcho {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter String to echo");
        try {
            String s = br.readLine();
            while (!s.equals("exit")) {
                System.out.println(s);
                s = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("End of program");
    }
}
