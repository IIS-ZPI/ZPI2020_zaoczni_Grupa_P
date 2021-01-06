package functions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import json.ApiConnect;
import json.currency.CurrencyInfo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Session extends ApplicationFrame{

    CurrencyInfo currencyInfo;
    int periodTimeNumber;


    public Session(String applicationTitle , String chartTitle, String data, String currency, int periodTimeNumber) {
        super(applicationTitle);
        this.periodTimeNumber=periodTimeNumber;
        Gson gson = new Gson();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = formatter.format(calendar.getTime());

        String testowy = "http://api.nbp.pl/api/exchangerates/rates/A/"+currency+"/"+data+"/"+currentDate+"/?format=json";
        System.out.println(testowy);

        ApiConnect apiConnect = new ApiConnect();
        String connection = apiConnect.connection("http://api.nbp.pl/api/exchangerates/rates/A/"+currency+"/"+data+"/"+currentDate+"/?format=json");

        TypeToken<CurrencyInfo> myToken = new TypeToken<>(){};
        currencyInfo = gson.fromJson(connection,myToken.getType());

        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Day of Months","Currency Value",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);
        CategoryPlot plot = (CategoryPlot) lineChart.getPlot();
        NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
        numberAxis.setAutoRangeIncludesZero(false);
        CategoryAxis axis = lineChart.getCategoryPlot().getDomainAxis();
        axis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 800 , 367 ) );
        chartPanel.setMaximumDrawHeight(1280);
        chartPanel.setMaximumDrawWidth(1920);
        setContentPane( chartPanel );
    }

    private DefaultCategoryDataset createDataset( ) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if(periodTimeNumber==6){
            for(int i=0;i<currencyInfo.getRates().size();i++){
                if(i%2==0)
                    dataset.addValue(currencyInfo.getRates().get(i).getMid(),"Values",currencyInfo.getRates().get(i).getEffectiveDate().substring(2,10));
            }
        }else {
            for(int i=0;i<currencyInfo.getRates().size();i++){
                dataset.addValue(currencyInfo.getRates().get(i).getMid(),"Values",currencyInfo.getRates().get(i).getEffectiveDate().substring(2,10));
            }
        }


        return dataset;
    }

    public int[] printSession(){
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
