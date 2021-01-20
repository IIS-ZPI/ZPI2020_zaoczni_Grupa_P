import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import json.ApiConnect;
import json.currency.CurrencyInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

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

    private LinkedHashMap<String,Integer> getChangesbyIntervals(String currencyFirst, String currencySecond, String dateFrom, String dateTo){

        ArrayList<Float> changesList = getChanges(currencyFirst,currencySecond,dateFrom,dateTo);
        ArrayList<Float> intervals = new ArrayList<>();
        LinkedHashMap<String,Integer> changesMap = new LinkedHashMap<>();

        BigDecimal bd1;

        Float max = Collections.max(changesList) *100;
        Float min = Collections.min(changesList) *100;

        bd1 = new BigDecimal(max).setScale(5, RoundingMode.UP);
        max = bd1.floatValue();

        bd1 = new BigDecimal(min).setScale(5, RoundingMode.UP);
        min = bd1.floatValue();

        Float diff = Math.abs(max+min);
        Float temp = min;

        intervals.add(min);
        BigDecimal bd;

        while(temp<max){

            temp +=diff;
            bd = new BigDecimal(temp).setScale(5, RoundingMode.HALF_DOWN);
            intervals.add(bd.floatValue());
        }

        int counterVolatility=0;
        for(int i=0;i<intervals.size();i++){
            if(i!=intervals.size()-1){
                //System.out.print(intervals.get(i)+"% (-) "+intervals.get(i+1)+"%");
                for(int j=0;j<changesList.size();j++){
                    if(changesList.get(j)*100>intervals.get(i)
                            &&  changesList.get(j)*100<intervals.get(i+1)){
                        counterVolatility++;
                    }
                }
                //System.out.print(" "+counterVolatility);
                changesMap.put(intervals.get(i)+"% (-) "+intervals.get(i+1)+"%",counterVolatility);
                counterVolatility=0;
            }

            //System.out.println("");
        }

        return changesMap;

    }







}
