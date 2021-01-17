import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import functions.Statistics;
import json.ApiConnect;
import json.currency.CurrencyInfo;
import json.currency.Rates;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatisticsTest {

    //Statistics statistics = new Statistics("2020-07-06","EUR",5);
    float tab[] = calculate("2020-07-06", "2021-01-15","EUR");

    @Test
    void calculateTestMediana() {
        assertEquals(4.4745001792907715,tab[0]);
    }

    @Test
    void calculateTestOdchylenie() {
        assertEquals(0.058426350355148315,tab[1]);
    }

    @Test
    void calculateTestZmiennosc() {
        assertEquals(1.3047595024108887,tab[2]);
    }

    @Test
    void calculateTestDominanta() {
        assertEquals(4.4745001792907715,tab[3]);
    }




    public float[] calculate(String fromData, String toData, String currency) {
        Gson gson = new Gson();
        ApiConnect apiConnect = new ApiConnect();
        String connection = apiConnect.connection("http://api.nbp.pl/api/exchangerates/rates/A/"+currency+"/"+fromData+"/"+toData+"/?format=json");
        TypeToken<CurrencyInfo> myToken = new TypeToken<>(){};
        CurrencyInfo currencyInfo= gson.fromJson(connection,myToken.getType());

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
