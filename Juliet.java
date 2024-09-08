/*
 * Juliet.java
 *
 * Juliet class.  Implements the Juliet subsystem of the Romeo and Juliet ODE system.
 */


import javafx.util.Pair;

import java.lang.Thread;
import java.net.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class Juliet extends Thread {

    private ServerSocket ownServerSocket = null; //Juliet's (server) socket
    private Socket serviceMailbox = null; //Juliet's (service) socket

    private double currentLove = 0;
    private double b = 0;

    //Class construtor
    public Juliet(double initialLove) {
        currentLove = initialLove;
        b = 0.01;
        int thePort = 7779;
        try {
            //TO BE COMPLETED
            ownServerSocket = new ServerSocket(thePort);
            System.out.println("Juliet: Good pilgrim, you do wrong your hand too much, ...");
        } catch(Exception e) {
            System.out.println("Juliet: Failed to create own socket " + e);
        }
    }

    //Get acquaintance with lover;
    // Receives lover's socket information and share's own socket
    public Pair<InetAddress,Integer> getAcquaintance() {
        System.out.println("Juliet: My bounty is as boundless as the sea,\n" +
                "       My love as deep; the more I give to thee,\n" +
                "       The more I have, for both are infinite.");
        Pair<InetAddress, Integer> info_juliet = null;
        int thePort = 7779;
        try{

            info_juliet = new Pair<>(InetAddress.getByName("127.0.0.1"), thePort);
        }
        catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        //TO BE COMPLETED
        return info_juliet;
    }



    //Retrieves the lover's love
    public double receiveLoveLetter()
    {

        //TO BE COMPLETED
        double tmp = 0.0;
        try{
            serviceMailbox = ownServerSocket.accept();
            InputStream outcomeStream = serviceMailbox.getInputStream();
            InputStreamReader outcomeStreamReader = new InputStreamReader(outcomeStream);
            StringBuffer stringBuffer = new StringBuffer();
            char x;
            while (true) //Read until terminator character is found
            {
                x = (char) outcomeStreamReader.read();
                if (x == 'R'){
                    break;
                }
                stringBuffer.append(x);
            }
            tmp = Double.parseDouble(stringBuffer.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Juliet: Romeo, Romeo! Wherefore art thou Romeo? (<-" + tmp + ")");
        return tmp;
    }


    //Love (The ODE system)
    //Given the lover's love at time t, estimate the next love value for Romeo
    public double renovateLove(double partnerLove){
        System.out.println("Juliet: Come, gentle night, come, loving black-browed night,\n" +
                "       Give me my Romeo, and when I shall die,\n" +
                "       Take him and cut him out in little stars.");
        currentLove = currentLove+(-b*partnerLove);
        return currentLove;
    }


    //Communicate love back to playwriter
    public void declareLove(){
        //TO BE COMPLETED
        try{
            OutputStream outcomeStream = this.serviceMailbox.getOutputStream();
            OutputStreamWriter outcomeStreamWriter = new OutputStreamWriter(outcomeStream);
            String love = currentLove+"J";
            System.out.println("Juliet: Good night, good night! Parting is such sweet sorrow,          \n That I shall say good night till it be morrow.(->"+love+") \nJuliet: Awaiting letter");
            outcomeStreamWriter.write(love);
            outcomeStreamWriter.flush();
            serviceMailbox.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    //Execution
    public void run () {
        try {
            while (!this.isInterrupted()) {
                //Retrieve lover's current love
                double RomeoLove = this.receiveLoveLetter();

                //Estimate new love value
                this.renovateLove(RomeoLove);

                //Communicate back to lover, Romeo's love
                this.declareLove();
            }
        }catch (Exception e){
            System.out.println("Juliet: " + e);
        }
        if (this.isInterrupted()) {
            System.out.println("Juliet: I will kiss thy lips.\n" +
                    "Haply some poison yet doth hang on them\n" +
                    "To make me die with a restorative.");
        }

    }

}