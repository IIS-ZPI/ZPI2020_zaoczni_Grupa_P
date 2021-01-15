import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Validator {
    static int periodTimeNumber;
    public static int getNumber() {
        int number = 0;
        boolean isCorrect = false;

        while (!isCorrect) {

            try {
                System.out.println("Podaj numer waluty(1-35):");
                Scanner sc = new Scanner(System.in);
                number = sc.nextInt();
                if(number>35||number<1)
                    isCorrect=false;
                else
                    isCorrect = true;
            } catch (InputMismatchException e) {
                System.out.println("Mozesz podac tylko liczbe");
            }
        }
        return number;
    }


    public static int getFunction() {
        int number = 0;
        boolean isCorrect = false;

        System.out.println("Jaka chcesz wybrac funkcje ?");
        System.out.println("1.Sesja wzrostowa, spadkowa, bez zmian");
        System.out.println("2. mediana, dominanta, odchylenie standardowe, współczynnik zmienności");
        System.out.println("3. Rozkład zmian miesięcznych i kwartalnych");
        while (!isCorrect) {

            try {
                System.out.println("Podaj numer funkcji(1-3):");
                Scanner sc = new Scanner(System.in);
                number = sc.nextInt();
                //uproszczone sprawdzanie poprawności numeru (P.K)
                isCorrect= number == 1 || number == 2 || number ==3;

            } catch (InputMismatchException e) {
                System.out.println("Mozesz podac tylko liczbe");
            }
        }
        return number;
    }

    public static String getPeriodTime(){
        int number = 0;
        boolean isCorrect = false;

        System.out.println("Wybierz okres czasu, dla ktorego maja byc wyliczone dane");
        System.out.println("1. Jednego tygodnia");
        System.out.println("2. Dwoch tygodni");
        System.out.println("3. Jednego miesiaca");
        System.out.println("4. Jednego kwartalu");
        System.out.println("5. Pol roku");
        System.out.println("6. Jednego roku");

        while (!isCorrect) {

            try {
                System.out.println("Podaj numer (1-6):");
                Scanner sc = new Scanner(System.in);
                number = sc.nextInt();
                if(number>6||number<1)
                    isCorrect=false;
                else
                    isCorrect = true;
            } catch (InputMismatchException e) {
                System.out.println("Mozesz podac tylko liczbe");
            }
            periodTimeNumber=number;
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        switch (number){

            case 1:
                calendar.add(Calendar.WEEK_OF_MONTH,-1);
                break;
            case 2:
                calendar.add(Calendar.WEEK_OF_MONTH,-2);
                break;
            case 3:
                calendar.add(Calendar.MONTH,-1);
                break;
            case 4:
                calendar.add(Calendar.MONTH,-3);
                break;
            case 5:
                calendar.add(Calendar.MONTH,-6);
                break;
            case 6:
                calendar.add(Calendar.YEAR,-1);
                break;
        }
        String periodTime = formatter.format(calendar.getTime());
        return periodTime;
    }





    }

