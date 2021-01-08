package json;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class ApiConnect {

    public String connection(String address)  {
        URL url=null;
        try{
            url = new URL(address);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        HttpURLConnection connection =null;
        int responseCode=0;
        try{
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            responseCode = connection.getResponseCode();
        }catch (IOException e){
            e.printStackTrace();
        }

        if (responseCode != 200)
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        else {
            String inline = "";
            Scanner scanner = null;
            try {
                scanner = new Scanner(url.openStream());
            }catch (IOException e){
                e.printStackTrace();
            }

            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            scanner.close();
            return inline;
        }

    }


}