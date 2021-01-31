import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import functions.Volatility;
import json.ApiConnect;
import json.currency.CurrencyInfo;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class VolatilityTest {

    ArrayList listMonth = getChanges("EUR", "USD", "2020-12-15", "2021-01-15");
    ArrayList listQuarter = getChanges("EUR", "USD", "2020-07-06", "2021-01-15");

    Float[] arrayMonth = new Float[]{1.215763f, 0.004774332f, 0.0027208328f, 0.0017018318f, -0.007415056f, 0.003142476f, -0.0012880564f, -2.8681755E-4f, 0.0057843924f, 4.7326088E-5f, 0.0019060373f, 0.0010126829f, 0.0015277863f, -0.0021486282f, -3.4880638E-4f, -0.0028893948f, -0.007910967f, 1.0585785E-4f, 0.0026909113f, -0.0022940636f, -0.0026950836f};
    private Object value1 = arrayMonth;
    List<Float> listM = new ArrayList<>(Arrays.asList((Float[])value1));
    Float[] arrayQuarter = new Float[]{1.1290542f, -0.002545476f, 0.0013841391f, 0.0051163435f, -0.0043959618f, 0.002339244f, 0.004149914f, 0.008594751f, -0.0044094324f, 0.0024232864f, 0.0040950775f, -0.0013920069f, 0.00784111f, 0.006651759f, 0.0011813641f, 0.009398699f, 0.0024995804f, 0.0030733347f, -8.609295E-4f, 0.011615992f, -0.007970214f, 0.0013651848f, 0.0048356056f, 1.9180775E-4f, -9.496212E-4f, -0.005981922f, -9.274483E-5f, -0.00241673f, 0.009058833f, -0.0022397041f, 0.002223134f, 0.007460952f, 0.0028636456f, -0.010463953f, -0.0023946762f, 8.916855E-4f, 9.429455E-5f, -8.2600117E-4f, 0.0019285679f, 0.007505536f, -1.01327896E-4f, 0.006412983f, -0.0115202665f, -0.0023866892f, 0.0025330782f, -0.0025411844f, -0.002454877f, -0.004478574f, 0.006940484f, 0.0023959875f, 6.0796738E-6f, 0.0024917126f, -0.0016055107f, -0.0062549114f, 0.005616188f, -0.005855441f, -0.0055351257f, -0.0040620565f, -0.004686594f, -2.1386147E-4f, -0.0014865398f, 0.004287839f, 0.0025866032f, 0.0014226437f, -0.0010596514f, 0.0035954714f, 0.0026608706f, -0.0017154217f, 2.7179718E-4f, 0.0040397644f, 9.6178055E-4f, -0.0017639399f, -0.005461931f, -0.0032356977f, -2.1278858E-4f, 0.0033191442f, 0.0044527054f, 0.006800413f, -6.5016747E-4f, -6.020069E-4f, -0.002819419f, -8.428097E-5f, -0.0060019493f, -0.002141118f, -0.0053083897f, -0.0033620596f, 0.0056073666f, -0.0020929575f, 0.0117794275f, 0.006061673f, 0.0019974709f, -0.0067967176f, 2.4354458E-4f, 0.001798749f, 0.0018148422f, 0.0023093224f, 0.0021219254f, -0.006031513f, 0.0029371977f, 0.0019496679f, 6.1941147E-4f, 0.0020771027f, -9.894371E-5f, 0.0017991066f, 0.0062639713f, -3.6382675E-4f, 0.0072134733f, 0.005832672f, 0.0055651665f, -0.0074088573f, 0.003445983f, -1.7249584E-4f, -0.0019783974f, 0.0011248589f, 0.004004717f, 4.6610832E-5f, 0.004774332f, 0.0027208328f, 0.0017018318f, -0.007415056f, 0.003142476f, -0.0012880564f, -2.8681755E-4f, 0.0057843924f, 4.7326088E-5f, 0.0019060373f, 0.0010126829f, 0.0015277863f, -0.0021486282f, -3.4880638E-4f, -0.0028893948f, -0.007910967f, 1.0585785E-4f, 0.0026909113f, -0.0022940636f, -0.0026950836f};
    private Object value2 = arrayQuarter;
    List<Float> listQ = new ArrayList<>(Arrays.asList((Float[])value2));

    Volatility volatility = new Volatility("EUR", "USD");
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    void getChangesMonthTest() {
        assertEquals(listMonth,listM);
    }

    @Test
    void getChangesQuarterTest(){
        assertEquals(listQuarter,listQ);
    }

    @Test
    void getChangesMonthTestNotEqual() {
        assertNotEquals(listMonth,1);
    }

    @Test
    void getChangesQuarterTestNotEqual(){
        assertNotEquals(listQuarter,1);
    }

    @Test
    void deviationTestMonth(){
        assertEquals(0.2589489817619324,volatility.deviation(listMonth));
    }

    @Test
    void deviationTestQuarter(){
        assertEquals(0.09650065749883652,volatility.deviation(listQuarter));
    }

    @Test
    void toPercentageTest(){
        assertEquals("25,895%",volatility.toPercentage((float) 0.2589489817619324,3));
    }

    @Test
    void getQuarterTest(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-3);
        String quarterDate = formatter.format(calendar.getTime());

        assertEquals(quarterDate,volatility.getQuarter());
    }

    @Test
    void getMonthTest(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        String monthDate = formatter.format(calendar.getTime());

        assertEquals(monthDate,volatility.getMonth());
    }

    @Test
    void getCurrentDateTest(){
        Calendar calendar = Calendar.getInstance();
        String currentDate = formatter.format(calendar.getTime());

        assertEquals(currentDate,volatility.getCurrentDate());
    }

    @Test
    void getChangesbyIntervalsMonthTest(){
        HashMap<String,Integer> hashMap = new HashMap<String,Integer>();
        hashMap.put("-0.7911% (-) 119.9941%", 20);
        hashMap.put("119.9941% (-) 240.7793%", 1);
        assertEquals(hashMap, getChangesbyIntervals("EUR", "USD", "2020-12-15", "2021-01-15"));
    }

    @Test
    void getChangesbyIntervalsQuarterTest(){
        HashMap<String,Integer> hashMap = new HashMap<String,Integer>();
        hashMap.put("-1.15203% (-) 110.60136%", 135);
        hashMap.put("110.60136% (-) 222.35474%", 1);
        assertEquals(hashMap, getChangesbyIntervals("EUR", "USD", "2020-07-06", "2021-01-15"));
    }

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
