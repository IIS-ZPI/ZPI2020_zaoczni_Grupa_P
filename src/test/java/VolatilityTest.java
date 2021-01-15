import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import json.ApiConnect;
import json.currency.CurrencyInfo;

import java.util.ArrayList;

public class VolatilityTest {

    // metoda do testowania funkcji Volatility
    // wyświetlanie w procentach wyniku tak jak w mainie za pomoca metody Volatility.toPercentage, metoda statyczna
    // więc nie trzeba robić obiektu klasy, pierwszy parametr liczba, drugi ile liczb po przecinku
    private ArrayList<Float> getChanges(String currencyFirst, String currencySecond, String dateFrom, String dateTo){
        CurrencyInfo currencyInfoFirst;
        CurrencyInfo currencyInfoSecond;
        Gson gson = new Gson();
        ApiConnect apiConnect = new ApiConnect();

        String connectionFirst = apiConnect.connection("http://api.nbp.pl/api/exchangerates/rates/A/"+currencyFirst+"/"+dateFrom+"/"+dateTo+"/?format=json");
        TypeToken<CurrencyInfo> TokenFirst = new TypeToken<>(){};
        currencyInfoFirst =  gson.fromJson(connectionFirst,TokenFirst.getType());

        String connectionSecond = apiConnect.connection("http://api.nbp.pl/api/exchangerates/rates/A/"+currencySecond+"/"+dateFrom+"/"+dateTo+"/?format=json");
        TypeToken<CurrencyInfo> TokenSecond = new TypeToken<>(){};
        currencyInfoSecond = gson.fromJson(connectionSecond,TokenSecond.getType());

        ArrayList<Float> changesList = new ArrayList<>();
        float prevPairMid = 0;

        for(int i=0;i<currencyInfoFirst.getRates().size();i++){
            float firstCurrencyMid  = currencyInfoFirst.getRates().get(i).getMid();
            float secondCurrencyMid = currencyInfoSecond.getRates().get(i).getMid();
            float pairMid = firstCurrencyMid/secondCurrencyMid;
            if(i==0){
                changesList.add(pairMid);
            }else {
                float change = pairMid - prevPairMid;
                changesList.add(change);
            }
            prevPairMid = pairMid;
        }
        return changesList;


    }
}
