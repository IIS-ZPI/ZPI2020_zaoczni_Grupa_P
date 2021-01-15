package functions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import json.ApiConnect;
import json.currency.CurrencyInfo;

import java.text.SimpleDateFormat;
import java.util.*;

public class Volatility {

    CurrencyInfo currencyInfoMonthFirst;
    CurrencyInfo currencyInfoMonthSecond;

    CurrencyInfo currencyInfoQuarterFirst;
    CurrencyInfo currencyInfoQuarterSecond;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");



    public Volatility(String currencyFirst, String currencySecond){

        Gson gson = new Gson();

        ApiConnect apiConnect = new ApiConnect();

        String connectionMonthFirst = apiConnect.connection("http://api.nbp.pl/api/exchangerates/rates/A/"+currencyFirst+"/"+getMonth()+"/"+getCurrentDate()+"/?format=json");
        TypeToken<CurrencyInfo> myTokenMonthFirst = new TypeToken<>(){};
        currencyInfoMonthFirst = gson.fromJson(connectionMonthFirst,myTokenMonthFirst.getType());

        String connectionMonthSecond = apiConnect.connection("http://api.nbp.pl/api/exchangerates/rates/A/"+currencySecond+"/"+getMonth()+"/"+getCurrentDate()+"/?format=json");
        TypeToken<CurrencyInfo> myTokenMonthSecond = new TypeToken<>(){};
        currencyInfoMonthSecond = gson.fromJson(connectionMonthSecond,myTokenMonthSecond.getType());

        String connectionQuarterFirst = apiConnect.connection("http://api.nbp.pl/api/exchangerates/rates/A/"+currencyFirst+"/"+getQuarter()+"/"+getCurrentDate()+"/?format=json");
        TypeToken<CurrencyInfo> myTokenQuarterFirst = new TypeToken<>(){};
        currencyInfoQuarterFirst = gson.fromJson(connectionQuarterFirst,myTokenQuarterFirst.getType());

        String connectionQuarterSecond = apiConnect.connection("http://api.nbp.pl/api/exchangerates/rates/A/"+currencySecond+"/"+getQuarter()+"/"+getCurrentDate()+"/?format=json");
        TypeToken<CurrencyInfo> myTokenQuarterSecond = new TypeToken<>(){};
        currencyInfoQuarterSecond = gson.fromJson(connectionQuarterSecond,myTokenQuarterSecond.getType());


    }




    public ArrayList<Float> getChangesMonthFloat(){
        ArrayList<Float> changesList = new ArrayList<>();
        float prevPairMid = 0;
        for(int i=0;i<currencyInfoMonthFirst.getRates().size();i++){

            float firstCurrencyMid  = currencyInfoMonthFirst.getRates().get(i).getMid();
            float secondCurrencyMid = currencyInfoMonthSecond.getRates().get(i).getMid();
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



    private LinkedHashMap<String,Float> getChangesMonth(){
        LinkedHashMap<String, Float> changesList = new LinkedHashMap<>();
        float prevPairMid = 0;
        for(int i=0;i<currencyInfoMonthFirst.getRates().size();i++){

            float firstCurrencyMid  = currencyInfoMonthFirst.getRates().get(i).getMid();
            float secondCurrencyMid = currencyInfoMonthSecond.getRates().get(i).getMid();
            float pairMid = firstCurrencyMid/secondCurrencyMid;
            if(i==0){
                changesList.put(currencyInfoMonthFirst.getRates().get(i).getEffectiveDate(),pairMid);
            }else {
                float change = pairMid - prevPairMid;
                changesList.put(currencyInfoMonthFirst.getRates().get(i).getEffectiveDate(),change);
            }
            prevPairMid = pairMid;
        }
        return changesList;

    }

    private LinkedHashMap<String,Float> getChangesQuarter(){
        LinkedHashMap<String, Float> changesList = new LinkedHashMap<>();
        float prevPairMid = 0;
        for(int i=0;i<currencyInfoQuarterFirst.getRates().size();i++){

            float firstCurrencyMid  = currencyInfoQuarterFirst.getRates().get(i).getMid();
            float secondCurrencyMid = currencyInfoQuarterSecond.getRates().get(i).getMid();
            float pairMid = firstCurrencyMid/secondCurrencyMid;
            if(i==0){
                changesList.put(currencyInfoQuarterFirst.getRates().get(i).getEffectiveDate(),pairMid);
            }else {
                float change = pairMid - prevPairMid;
                changesList.put(currencyInfoQuarterFirst.getRates().get(i).getEffectiveDate(),change);
            }
            prevPairMid = pairMid;
        }
        return changesList;
    }

    public ArrayList<Float> getChangesQuarterFloat(){
        ArrayList<Float> changesList = new ArrayList<>();
        float prevPairMid = 0;
        for(int i=0;i<currencyInfoQuarterFirst.getRates().size();i++){

            float firstCurrencyMid  = currencyInfoQuarterFirst.getRates().get(i).getMid();
            float secondCurrencyMid = currencyInfoQuarterSecond.getRates().get(i).getMid();
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

    public void printMonthResults(){

        LinkedHashMap<String, Float> changesListMonth= getChangesMonth();
        ArrayList<String> keysMonth = new ArrayList<>(changesListMonth.keySet());

        LinkedHashMap<String, Float> changesListQuarter=getChangesQuarter();
        ArrayList<String> keysQuarter = new ArrayList<>(changesListQuarter.keySet());

        System.out.println("Zmiana Miesieczna");
       for(String s:keysMonth){
           System.out.println("Data: "+s+" Zmiana: "+toPercentage(changesListMonth.get(s),6));
       }

        System.out.println(toPercentage(deviation(getChangesMonthFloat()),3));

        System.out.println("Zmiana kwartalna");
        for(String s:keysQuarter){
            System.out.println("Data: "+s+" Zmiana: "+toPercentage(changesListQuarter.get(s),6));
        }

        System.out.println(toPercentage(deviation(getChangesQuarterFloat()),3));

    }

     public static String toPercentage(float n, int digits){
        return String.format("%."+digits+"f",n*100)+"%";
    }

    private float deviation(ArrayList<Float> odchylenieList){
        float odchylenie;
        float sum = 0;

        for (float f: odchylenieList) {
            sum+=f;
        }

        float avg = sum/odchylenieList.size();
        odchylenie = 0;
        for (float f : odchylenieList) {
            odchylenie+= Math.pow((f - avg), 2);
        }

        odchylenie = odchylenie / odchylenieList.size();
        odchylenie = (float) Math.sqrt(odchylenie);

        return odchylenie;
    }


    private String getQuarter(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-3);
        String quarterDate = formatter.format(calendar.getTime());
        return  quarterDate;
    }

    private String getMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        String quarterDate = formatter.format(calendar.getTime());
        return  quarterDate;
    }

    private String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        String currentDate = formatter.format(calendar.getTime());
        return currentDate;
    }










}
