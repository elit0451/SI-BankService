import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private Socket openSocket;
    private Map<Integer,Account> listAccounts;

    public ClientHandler(Socket openSocket, Map<Integer,Account> listAccounts)
    {
        this.openSocket = openSocket;
        this.listAccounts = listAccounts;
    }

    @Override
    public void run() {
        String request, response;

        Scanner in;
        PrintWriter out;
        try {
            in = new Scanner(openSocket.getInputStream());

            out = new PrintWriter(openSocket.getOutputStream(), true);

            while (in.hasNextLine()) {
                request = in.nextLine();

                String[] commands = request.split(" ");
                int accountNumber = Integer.parseInt(commands[0]);
                Account account;

                if(!listAccounts.containsKey(accountNumber))
                    listAccounts.put(accountNumber, new Account(accountNumber, 0));

                account = listAccounts.get(accountNumber);

                if (request.equals("stop")) {
                    out.println("Good bye, client!");
                    System.out.println("Log: " + request + " client!");
                    break;
                }

                int amount = 0;
                switch (commands[1]) {
                    case "+":
                        amount = Integer.parseInt(commands[2]);
                        account.deposit(amount);
                        out.println("Your current balance is " + account.getBalance());
                        break;
                    case "-":
                        try {
                            amount = Integer.parseInt(commands[2]);
                            account.withdraw(amount);
                            out.println("Your current balance is " + account.getBalance());
                        }
                        catch (Exception e)
                        {
                            out.println(e.getMessage());
                        }
                        break;
                    case "balance":
                        out.println("Your current balance is " + account.getBalance());
                        break;
                    default:
                        out.println("The current command is not implemented");
                        break;
                }
            }

            openSocket.close();
            System.out.println("Connection to client closed");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
