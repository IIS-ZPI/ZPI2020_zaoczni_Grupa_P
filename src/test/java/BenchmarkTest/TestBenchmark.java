package BenchmarkTest;

import functions.Session;
import functions.Statistics;
import functions.Volatility;
import json.ApiConnect;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


@State(Scope.Benchmark)
@Threads(1)
public class TestBenchmark {

    Session session = new Session("test","test","2020-07-06","EUR",5);
    Statistics statistics = new Statistics("2020-07-06","EUR",5);
    Volatility volatility = new Volatility("EUR", "USD");
    ApiConnect apiConnect = new ApiConnect();

    //Session Class methods
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void testPrintSession() {
        session.printSession();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void testCreateDataset() {
        session.createDataset();
    }

    //Statistics Class methods
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void testCalculate() {
        statistics.calculate();
    }

    //Volatility Class methods
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void testGetChangesMonthFloat() {
        volatility.getChangesMonthFloat();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void testGetChangesQuarterFloat() {
        volatility.getChangesQuarterFloat();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void testPrintMonthResults() {
        volatility.printMonthResults();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void testPrintQuarterResult() {
        volatility.printQuarterResult();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void testDeviation() {
        ArrayList<Float> list = volatility.getChangesMonthFloat();
        volatility.deviation(list);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void testToPercentage() {
        volatility.toPercentage(2.44f, 5);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void testGetQuarter() {
        volatility.getQuarter();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void testGetMonth() {
        volatility.getMonth();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void testGetCurrentDate() {
        volatility.getCurrentDate();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void testGetChangesbyInterval() {
        ArrayList<Float> list = volatility.getChangesMonthFloat();
        ArrayList<Float> list2 = null;
        list2.add(1f);
        volatility.getChangesbyIntervals(list2,list);
    }

    //ApiConnect Class methods
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public void testConnection() {
        apiConnect.connection("http://api.nbp.pl/api/exchangerates/tables/A/?format=json");
    }



    @Test
    public void benchmark() throws Exception {
        String[] argv = {};
        org.openjdk.jmh.Main.main(argv);
    }
}
