/*
 * Romeo.java
 *
 * Romeo class.  Implements the Romeo subsystem of the Romeo and Juliet ODE system.
 */


import java.lang.Thread;
import java.net.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;

import javafx.util.Pair;

public class Romeo extends Thread {

    private ServerSocket ownServerSocket = null; //Romeo's (server) socket
    private Socket serviceMailbox = null; //Romeo's (service) socket


    private double currentLove = 0;
    private double a = 0; //The ODE constant

    //Class construtor
    public Romeo(double initialLove) {
        currentLove = initialLove;
        a = 0.02;
        int thePort = 7778;
        try {

            //TO BE COMPLETED
            ownServerSocket = new ServerSocket(thePort);
            System.out.println("Romeo: What lady is that, which doth enrich the hand\n" +
                    "       Of yonder knight?");
        } catch(Exception e) {
            System.out.println("Romeo: Failed to create own socket " + e);
        }
    }

    //Get acquaintance with lover;
    public Pair<InetAddress,Integer> getAcquaintance() {
        System.out.println("Romeo: Did my heart love till now? forswear it, sight! For I ne'er saw true beauty till this night.");
        System.out.println("Romeo: Entering service iteration.");
        System.out.println("Romeo: Awaiting letter \nJuliet: Awaiting letter");
        Pair<InetAddress, Integer> info_romeo = null;
        int thePort = 7778;
        try{
            info_romeo = new Pair<>(InetAddress.getByName("127.0.0.1"), thePort);
        }
        catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        //TO BE COMPLETED
        return info_romeo;
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
                if (x == 'J'){
                    break;
                }
                stringBuffer.append(x);
            }
            tmp = Double.parseDouble(stringBuffer.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Romeo: O sweet Juliet... (<-" + tmp + ")");
        return tmp;
    }


    //Love (The ODE system)
    //Given the lover's love at time t, estimate the next love value for Romeo
    public double renovateLove(double partnerLove){
        System.out.println("Romeo: But soft, what light through yonder window breaks?\n" +
                "       It is the east, and Juliet is the sun.");
        currentLove = currentLove+(a*partnerLove);
        return currentLove;
    }


    //Communicate love back to playwriter
    public void declareLove(){

        //TO BE COMPLETED
        try{
            OutputStream outcomeStream = this.serviceMailbox.getOutputStream();
            OutputStreamWriter outcomeStreamWriter = new OutputStreamWriter(outcomeStream);
            String love = currentLove+"R";
            System.out.println("Romeo: I would I were thy bird. (->"+love+")  \nRomeo: Exiting service iteration.  \nRomeo: Entering service iteration. \nRomeo: Awaiting letter");
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
                double JulietLove = this.receiveLoveLetter();

                //Estimate new love value
                this.renovateLove(JulietLove);

                //Communicate love back to playwriter
                this.declareLove();
            }
        }catch (Exception e){
            System.out.println("Romeo: " + e);
        }
        if (this.isInterrupted()) {
            System.out.println("Romeo: Here's to my love. O true apothecary,\n" +
                    "Thy drugs are quick. Thus with a kiss I die." );
        }
    }

}
