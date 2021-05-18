package client;

import javafx.application.Application;

import java.rmi.RemoteException;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        Application.launch(MyApplication.class);



    /*RmiCaseClient client = new RmiCaseClient();
    Scanner input = new Scanner(System.in);
    System.out.print("Enter a string to convert to uppercase: ");
    String line = input.nextLine();
    String convertedLine = client.convert(line, true);
    System.out.println("Uppercase version: " + convertedLine);
    System.out.print("Enter a string to capitalize first letter: ");
    line = input.nextLine();
    convertedLine = client.convert(line, false);
    System.out.println("Capitalized version: " + convertedLine);*/
    }
}