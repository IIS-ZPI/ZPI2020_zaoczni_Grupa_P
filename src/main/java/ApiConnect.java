import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ApiConnect {

    public void connection()  {
        URL url=null;
        try{
            url = new URL("http://api.nbp.pl/api/cenyzlota/2013-01-01/2013-01-31/?format=json");
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

        System.out.println(responseCode);

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

            JSONParser parse = new JSONParser();
            try{
                JSONArray array = (JSONArray) parse.parse(inline);
                System.out.println(array);
            }catch (ParseException e ){
                e.printStackTrace();
            }

        }

    }


}


