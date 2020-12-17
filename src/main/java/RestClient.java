import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RestClient {


    public void connection() throws IOException, ParseException {
        URL url = new URL("http://api.nbp.pl/api/cenyzlota/2013-01-01/2013-01-31/?format=json");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responsecode = connection.getResponseCode();
        System.out.println(responsecode);


        if (responsecode != 200)
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        else {
            String inline = "";
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            scanner.close();

            //Using the JSON simple library parse the string into a json object
            JSONParser parse = new JSONParser();
            //JSONObject data_obj = (JSONObject) parse.parse(inline);
            JSONArray array = (JSONArray) parse.parse(inline);
            System.out.println(array);

        }



    }




}


