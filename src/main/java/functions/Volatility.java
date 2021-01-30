package functions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import json.ApiConnect;
import json.currency.CurrencyInfo;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
                //changesList.add(pairMid);
            }else {
                float change = pairMid - prevPairMid;
                changesList.add(change);
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
                //changesList.add(pairMid);
            }else {
                float change = pairMid - prevPairMid;
                changesList.add(change);
            }
            prevPairMid = pairMid;
        }
        return changesList;

    }

    public void printMonthResults(){

        System.out.println("Zmiennosc miesieczna");
        System.out.println("Odchylenie Standardowe:" +toPercentage(deviation(getChangesMonthFloat()),5));
        BigDecimal bd1;

        Float max = Collections.max(getChangesMonthFloat())*100;
        Float min = Collections.min(getChangesMonthFloat())*100;

        bd1 = new BigDecimal(max).setScale(5, RoundingMode.UP);//tu
        max = bd1.floatValue();

        bd1 = new BigDecimal(min).setScale(5, RoundingMode.UP);//tu
        min = bd1.floatValue();

        Float diff = Math.abs(max+min);
        Float temp = min;

        ArrayList<Float> intervals = new ArrayList<>();
        intervals.add(min);
        BigDecimal bd;
        while(temp<max){

            temp +=diff;
            bd = new BigDecimal(temp).setScale(5, RoundingMode.HALF_DOWN);//tu
            intervals.add(bd.floatValue());
        }

        ArrayList<Float> changesMonthValues = getChangesMonthFloat();
        getChangesbyIntervals(intervals,changesMonthValues);

    }

    public void printQuarterResult(){

        System.out.println("Zmiennosc kwartalna");
        System.out.println("Odchylenie Standardowe: "+toPercentage(deviation(getChangesQuarterFloat()),3));
        BigDecimal bd1;
        Float max = Collections.max(getChangesQuarterFloat())*100;
        Float min = Collections.min(getChangesQuarterFloat())*100;

        bd1 = new BigDecimal(max).setScale(5, RoundingMode.UP);//tu
        max = bd1.floatValue();

        bd1 = new BigDecimal(min).setScale(5, RoundingMode.UP);//tu
        min = bd1.floatValue();

        Float diff = Math.abs(max+min);
        Float temp = min;

        ArrayList<Float> intervals = new ArrayList<>();
        intervals.add(min);
        BigDecimal bd;

        while(temp<max){

            temp +=diff;
            bd = new BigDecimal(temp).setScale(5, RoundingMode.HALF_DOWN);//tu
            intervals.add(bd.floatValue());
        }

        ArrayList<Float> changesMonthValues = getChangesQuarterFloat();
        getChangesbyIntervals(intervals,changesMonthValues);

    }

     public static String toPercentage(float n, int digits){
        return String.format("%."+digits+"f",n*100)+"%";
    }

    public float deviation(ArrayList<Float> odchylenieList){
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


    public String getQuarter(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-3);
        String quarterDate = formatter.format(calendar.getTime());
        return  quarterDate;
    }

    public String getMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        String quarterDate = formatter.format(calendar.getTime());
        return  quarterDate;
    }

    public String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        String currentDate = formatter.format(calendar.getTime());
        return currentDate;
    }


    public void getChangesbyIntervals(ArrayList<Float> intervals, ArrayList<Float> changesMonthValues){

        int counterVolatility=0;
        for(int i=0;i<intervals.size();i++){
            if(i!=intervals.size()-1){
                System.out.print(intervals.get(i)+"% (-) "+intervals.get(i+1)+"%");
                for(int j=0;j<changesMonthValues.size();j++){
                    if(changesMonthValues.get(j)*100>intervals.get(i)
                            &&  changesMonthValues.get(j)*100<intervals.get(i+1)){
                        counterVolatility++;
                    }
                }
                System.out.print(" "+counterVolatility);
                counterVolatility=0;
            }

            System.out.println("");
        }

    }

}
