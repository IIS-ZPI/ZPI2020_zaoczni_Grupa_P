import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import functions.Session;
import json.ApiConnect;
import json.currency.CurrencyInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionTest {

    /*
    Session session = new Session("test","test","2020-07-06","EUR",5);
    int tab[] = session.printSession();

    @Test
    void printSessionTestGrowthSession() {
        assertEquals(37,tab[0]);
    }
    @Test
    void printSessionTestRelegationSession() {
        assertEquals(37,tab[1]);
    }
    @Test
    void printSessionTestNoChangeSession() {
        assertEquals(2,tab[2]);
    }

     */

    public int[] printSession(String fromData, String toData, String currency){

        Gson gson = new Gson();
        ApiConnect apiConnect = new ApiConnect();
        String connection = apiConnect.connection("http://api.nbp.pl/api/exchangerates/rates/A/"+currency+"/"+fromData+"/"+toData+"/?format=json");
        TypeToken<CurrencyInfo> myToken = new TypeToken<>(){};
        CurrencyInfo currencyInfo= gson.fromJson(connection,myToken.getType());

        int growthSession=0;
        int relegationSession=0;
        int noChangeSession=0;
        boolean isGrowing = false;
        boolean isfalling = false;
        boolean isStable = false;


        for(int i=0;i<currencyInfo.getRates().size()-1;i++) {
            if(currencyInfo.getRates().size()!=i){
                if(currencyInfo.getRates().get(i).getMid() > currencyInfo.getRates().get(i+1).getMid()
                        &&isfalling==false ) {
                    relegationSession++;
                    isfalling=true;
                    isGrowing=false;
                    isStable=false;
                }
                else if((currencyInfo.getRates().get(i).getMid() < currencyInfo.getRates().get(i+1).getMid())
                        &&isGrowing==false) {
                    growthSession++;
                    isGrowing=true;
                    isfalling=false;
                    isStable=false;
                }
                else if((currencyInfo.getRates().get(i).getMid().equals(currencyInfo.getRates().get(i+1).getMid()))
                        &&isStable==false) {
                    noChangeSession++;
                    isStable=true;
                    isfalling=false;
                    isGrowing=false;
                }
            }

        }

        System.out.println("Ilosc sesji wzrostowych:"+growthSession);
        System.out.println("Ilosc sesji spadkowych:"+relegationSession);
        System.out.println("Ilosc sesji bez zmian:"+noChangeSession);

        int [] results = {growthSession,relegationSession,noChangeSession};

        return results;

    }


}
