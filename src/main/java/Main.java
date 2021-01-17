import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import functions.Session;
import functions.Statistics;
import functions.Volatility;
import json.ApiConnect;
import json.table.CurrencyInfo;
import org.jfree.ui.RefineryUtilities;

import java.util.List;

public class Main {
    private static final boolean DRY_RUN = true;

    public static void main(String[] args){
        Gson gson = new Gson();

        ApiConnect apiConnect = new ApiConnect();

        String currencies = apiConnect.connection("http://api.nbp.pl/api/exchangerates/tables/A/?format=json");

        TypeToken<List<CurrencyInfo>> myToken = new TypeToken<>(){};
        List<CurrencyInfo> myInfo = gson.fromJson(currencies, myToken.getType());
        CurrencyInfo myFinalInfo = myInfo.get(0);

        for(int i=0;i<myFinalInfo.getRates().size();i++){
            System.out.println(i+1+" "+myFinalInfo.getRates().get(i).getCode()+" "+ myFinalInfo.getRates().get(i).getCurrency());
        }
        int number = Validator.getNumber();

        System.out.println("Wybrano walute: "+ myFinalInfo.getRates().get(number-1).getCurrency());

        int functionNumber = Validator.getFunction();
        String periodTime="";
        if(functionNumber!=3)
             periodTime = Validator.getPeriodTime();


        int periodTimeNumber = Validator.periodTimeNumber;
        //System.out.println(periodTimeNumber);

        switch (functionNumber){
            case 1:
                Session session = new Session(myFinalInfo.getRates().get(number-1).getCode()
                        ,myFinalInfo.getRates().get(number-1).getCode()
                        ,periodTime,
                        myFinalInfo.getRates().get(number-1).getCode(),
                        periodTimeNumber);
                session.pack();
                RefineryUtilities.centerFrameOnScreen(session);
                session.setVisible( true );
                session.printSession();
                break;

            case 2:
                Statistics statistics = new Statistics(periodTime, myFinalInfo.getRates().get(number-1).getCode(), periodTimeNumber);
                statistics.calculate();
                break;

            case 3:
               int secondNumber = Validator.getNumber();
                Volatility volatility = new Volatility(myFinalInfo.getRates().get(number-1).getCode(),
                        myFinalInfo.getRates().get(secondNumber-1).getCode());
                volatility.printMonthResults();



        }

    }
}