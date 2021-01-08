package functions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import json.ApiConnect;
import json.currency.CurrencyInfo;
import json.currency.Rates;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.lang.Math;

public class Statistics {
    CurrencyInfo currencyInfo;
    int periodTimeNumber;

    public Statistics(String data, String currency, int periodTimeNumber) {
        this.periodTimeNumber=periodTimeNumber;
        Gson gson = new Gson();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = formatter.format(calendar.getTime());

        ApiConnect apiConnect = new ApiConnect();
        String connection = apiConnect.connection("http://api.nbp.pl/api/exchangerates/rates/A/"+currency+"/"+data+"/"+currentDate+"/?format=json");

        TypeToken<CurrencyInfo> myToken = new TypeToken<>(){};
        currencyInfo = gson.fromJson(connection,myToken.getType());


    }

    public float[] calculate() {
        // mediana
        Float mediana;
        ArrayList<Float> sortedList = new ArrayList<>();
        for (Rates r: currencyInfo.getRates()) {
            sortedList.add(r.getMid());
        }

        Collections.sort(sortedList);

        if(sortedList.size()%2 == 1) {
            mediana = sortedList.get(sortedList.size()/2);
        } else {
            mediana = (sortedList.get((sortedList.size() - 1 ) / 2) + sortedList.get(sortedList.size() / 2)) / 2.0f;
        }

        //odchylenie
        float odchylenie;
        ArrayList<Float> odchylenieList = new ArrayList<>();
        for (Rates r: currencyInfo.getRates()) {
            odchylenieList.add(r.getMid());
        }

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

        //wspolczynnik zmiennosci

        float zmiennosc;
        zmiennosc = odchylenie / avg * 100; //wynik w procentach, dodać % przy wyswietlaniu

        //dominanta


        ArrayList<Float> dominantaList = new ArrayList<>();
        for (Rates r: currencyInfo.getRates()) {
            dominantaList.add(r.getMid());
        }

        // https://stackoverflow.com/questions/19031213/java-get-most-common-element-in-a-list
        Map.Entry dominanta = dominantaList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).
                entrySet().stream().max(Map.Entry.comparingByValue()).get();

        //wyniki
        System.out.println("Mediana wynosi " + mediana);
        System.out.println("Odchylenie standardowe wynosi " + odchylenie);
        System.out.println("Wspolczynnik zmiennosci wynosi " + zmiennosc + "%");
        System.out.println("Dominanta wynosi " + dominanta.getKey() + " wartosc ta wystapila " + dominanta.getValue() + " krotnie");

        float [] results = {mediana,odchylenie,zmiennosc,(Float)dominanta.getKey()};
        return results;
    }
}
