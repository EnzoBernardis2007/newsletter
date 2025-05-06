package com.newsletter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmailManager {
    static ObjectMapper mapper;
    static Scanner scanner;

    static {
        mapper = new ObjectMapper();
        scanner = new Scanner(System.in);
    }
    
    public static void createEmail() {
        System.out.println("Name of the file:");
        String filename = scanner.nextLine();

        System.out.println("Subject of the email:");
        String subject = scanner.nextLine();

        System.out.println("HTML body (say the name of the file):");
        String htmlfilename = scanner.nextLine();

        Email email = new Email();
        email.setSubject(subject);
        email.setHtml(getHtmlBody(htmlfilename));
        
        try {
            mapper.writeValue(new File(filename + ".json"), email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getHtmlBody(String filename) {
        StringBuilder result = new StringBuilder();

        try (Scanner scannerTxt = new Scanner(new File(filename))) {
            while (scannerTxt.hasNextLine()) {
                result.append(scannerTxt.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }

        return result.toString();
    }
}
