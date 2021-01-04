import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        ApiConnect apiConnect = new ApiConnect();
        String response = apiConnect.connection();

        TypeToken<List<CurrencyInfo>> myToken = new TypeToken<List<CurrencyInfo>>(){};
        List<CurrencyInfo> myInfo = gson.fromJson(response, myToken.getType());
        CurrencyInfo myFinalInfo = myInfo.get(0);
        for (Rates r: myFinalInfo.rates) {
            System.out.println(r.code);
        }

    }
}
