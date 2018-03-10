import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Calculator {


    //Pomyśl czy faktycznie wszystkie zmienne są ci potrzebne globalnie.
    private double grossSalary;
    private double socialContributions;
    private double baseOfIncomeTax;
    private double healthInsurance;
    private double incomeTax;
    private double baseOfHealthInsurance;
    private double healthCareContribution;
    private double incomeTaxToBeCollected;
    private double netIncome;
    private String answer;

    public void run(){
        printHeader();
        grossSalary = askAboutGrossSalary();
        answer = askAboutWorkPlace();
        socialContributions = calculateSocialContributions();
        baseOfIncomeTax = calculateBaseOfIncomeTax(socialContributions);
        healthInsurance = calculateHealthInsurance();
        incomeTax = calculateAdvanceOfIncomeTax();
        healthCareContribution = calculateHealthCareContribution();
        incomeTaxToBeCollected = calculateIncomeTaxToBeCollected();
        netIncome = calculateNetIncome();
        printNetIncome();
    }

    private void printHeader(){
        System.out.println("KALKULATOR DOCHODU NETTO");
    }

    private double askAboutGrossSalary(){
        boolean repeat = true;
        do {
            System.out.println("Podaj swoje wynagrodzenie brutto");
            Scanner input = new Scanner(System.in);
            grossSalary = input.nextDouble();
            repeat = !(grossSalary > 0);
        } while(repeat);
        return grossSalary;
    }

    //Ta metoda może być typu void
    private String askAboutWorkPlace(){
        boolean repeat = true;
        List<String> possibleAnswer = getPossibleAnswer();
        do {
            System.out.println("Czy wykonujesz prace poza miejscem zamieszkania? (Y/N)");
            Scanner input2 = new Scanner(System.in);
            //ponieważ tutaj przypisujesz już wartość zmiennej globalnej
            //pamimętaj że masz zmienne globalne i lokalne. Możesz to wykorzystać tutaj i przerobić
            //tą metodę tak aby faktynie musiała coś zwracać.
            answer = input2.nextLine();
            repeat = !possibleAnswer.contains(answer);
        }while(repeat);
        //Tutaj zwracasz zmienną answer. Do której już przypisałeś wartość.
        return answer;
    }
    private List<String> getPossibleAnswer() {
        return Arrays.asList("Y", "N", "y", "n");
    }

    private double calculateSocialContributions(){
        double tax1 = grossSalary * 0.0976; //pension
        double tax2 = grossSalary * 0.015; //rent
        double tax3 = grossSalary * 0.0245; //sickness
        return tax1+tax2+tax3;

    }

    private boolean checkThatFromAnotherCity(){
        //zwróć uwagę na to ile masz poprawnych odpowiedzi w metodzie getPossibleAnswer()
        //a ile masz tutaj.
        return answer.equals("Y");
    }

    //zwróć uwagę że socialContributions masz dostępne jako zmienną globalną.
    //Więc nie bardzo jest sens przekazywać tutaj ją jakos argument.
    //albo zrób tak żeby nie była dostępna jako globalna albo zrób metodę jako bez argumentową
    private double calculateBaseOfIncomeTax(double socialContributions){
        boolean fromAnotherCity = checkThatFromAnotherCity();
        if (fromAnotherCity) {
            return socialContributions - 111.25;
        }
        return socialContributions - 139.06;
    }

    private double calculateHealthInsurance(){
        baseOfHealthInsurance = grossSalary - socialContributions;
        healthInsurance = baseOfHealthInsurance * 0.09;
        //Tutaj robisz to samo co wcześniej. Masz zmienną dostępną globalnie, przypisujesz jej wartość a następnie zwracasz.
        //Pamiętaj że jeżeli chcesz zwracać jakąś zmienną to ma to sens tylko jeżeli jest ona lokalna. Chyba że metoda jest getterem.
        return healthInsurance;
    }

    private double calculateAdvanceOfIncomeTax(){
        double incomePerYear = grossSalary * 12;
        if (incomePerYear < 85528) {
           incomeTax = baseOfIncomeTax * 0.18;
        }
        incomeTax = baseOfIncomeTax * 0.32;
        //Znowu zasięg zmiennej. Czy ona musi być globalna czy nie?
        return incomeTax - 46.33;
    }

    private double calculateHealthCareContribution(){
        healthCareContribution = baseOfHealthInsurance * 0.0775;
        //To samo.
        return healthCareContribution;
    }

    private double calculateIncomeTaxToBeCollected(){
        incomeTaxToBeCollected = incomeTax - healthCareContribution;
        //Znowu
        return incomeTaxToBeCollected;
    }

    private double calculateNetIncome(){
        netIncome = grossSalary - (socialContributions - healthInsurance - incomeTaxToBeCollected);
        // I raz jeszcze
        return netIncome;
    }

    private void printNetIncome(){
        System.out.println("Twoje wynagrodzenie netto to: " + netIncome + " PLN.");
    }
}