package src;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String stroka = scan.nextLine();
        calc(stroka);
    }

    public static String calc(String input) {
        String resultCalc = base (input);
        System.out.println(resultCalc);
        return resultCalc;
    }

    public static String base(String stroka) {
        Number arab = new Number("Arabskie chusla", 0);
        Number rim = new Number("Rimskie chisla", 0);
        String[] mStroka = stroka.split(" ");
        String[] massArSravneniay = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] massRimSravneniay = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII",
                "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX"};
        String[] znak = {"+", "-", "*", "/"};
        if(mnoghestvoZnakov(mStroka, znak) != true) {
            //Вызываем исключение throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)
        }
        int indexZnaka = raschetIndexaZnaka(znak, mStroka);     //посчитал что так удобнее будет, чем через срвнение
        int variantAr = proverayemNaArabskie(mStroka);
        int variantRim = proverayemNaRimskie(mStroka, massRimSravneniay);
        if(variantAr !=1 && variantRim != 1) {
            try {
                throw new IOException();}
            catch (IOException e) {
                System.out.println("throws Exception //т.к. используются одновременно разные системы счисления");
                System.exit(1);
            }
        }
        String onOut = " ";
        if (variantAr == 1) {
            int[] arOtvet = numberFofWork(mStroka);
            boolean provIsklPoDiapazonu = isklPoDiapazonu(arOtvet);
            arab.chislo = raschetZnacheniy(arOtvet, indexZnaka);
            onOut = Integer.toString(arab.chislo);
            } else {
            if (variantRim == 1) {
                int[] rimOtvet = rimVAr(mStroka, massRimSravneniay);
                int rim1Chislo = raschetZnacheniy(rimOtvet, indexZnaka);
                otricatelnueRim(rim1Chislo);
                String rimNameOnOut = aruVRim(rim1Chislo, massRimSravneniay);
//                System.out.println(rimNameOnOut);
            onOut = rimNameOnOut;
            }
        }
        return onOut;
    }

    static int proverayemNaArabskie(String[] rMStroka) throws NumberFormatException  {    //блок 1.1
        int a = 0;
        try {
            Integer n1 = Integer.parseInt(rMStroka[0]);
            Integer n2 = Integer.parseInt(rMStroka[2]);
            if (n1 <= 0 || n1 > 0) {
                if (n2 <= 0 || n2 > 0) {
                    a = 1;
                }
            }
        }
        catch (NumberFormatException e) {}
        return a;
    }
    static int proverayemNaRimskie(String[] zStroka, String[] massSravneniay) { //Блок 1.2
        int a = 0;
        int b = 0;
        int c = 0;
        for (int i = 0; i < massSravneniay.length; i++) {

            if(massSravneniay[i].equals(zStroka[0])){
                b = 1;}

            if(massSravneniay[i].equals(zStroka[2])){
                c = 1;
            }
        }

        if(b == 1 && c == 1) {a = 1;}
        else {a = 0;}
        return a;
    }

    static int raschetIndexaZnaka(String[] izZnaka, String[] izStroki){ //блок 2.0 потом подставим в блок арифметических
        // операций switch
        int a = 4;
        if(izStroki.length <= 2) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("throws Exception //т.к. строка не является математической операцией");
                System.exit(1);
            }
        }
        for (int i = 0; i < izZnaka.length; i++){
            if(izZnaka[i].equals(izStroki[1])){
                a = i;
            }
        }
        if(a == 4) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("throws Exception //т.к. строка не является математической операцией");
                System.exit(1);
            }
        }
        return a;
    }

    static int[] rimVAr(String[] rStroka, String[] rim) {
        //блок 3.1 Сопоставляем римским цифрам полученным из консоли - арабские
        int[] a = new int[2];
        for (int i = 0; i < 10; i++) {

            if(rStroka[0].equals(rim[i])) {
                a[0] = i + 1;
            }
            if(rStroka[2].equals(rim[i])) {
                a[1] = i + 1;
            }
        }
        if(a[0] <= 0 || a[0] > 0) {
            if (a[1] <= 0 || a[1] > 0) { } else {
                try {
                    throw new IOException();
                }
                catch (IOException e) {
                    System.out.println("throws Exception //т.к. используются одновременно разные системы счисления");
                    System.exit(1);
                }
            }
        }
        return a;
    }
    static String aruVRim(int otvet, String[] rim){     //блок 3.2 полученное значение переводим в римские цифры
        String a = new String();
        for (int i = 0; i < rim.length; i++) {
            if(i == otvet) {
                a = (String) rim[i - 1];
            }
        }
        if (otvet == 20) {
            a = (String) rim[19];
        }
        return a;
    }

    static int[] numberFofWork (String[] rMStroka) {       //Блок 4. Преобразование строки в число для арабских цифр
        Integer a = Integer.parseInt(rMStroka[0]);
        Integer b = Integer.parseInt(rMStroka[2]);
        int[] number = {a, b};
        return number;
    }

    static boolean isklPoDiapazonu(int[] rMNumber) {  //Блок 5.1 Проверка на совпадение с заданым  диапазоном цифр.
        boolean z = false;
        int a = rMNumber[0];
        int b = rMNumber[1];
        if (a < 0 || a > 10){
            try {
                throw new IOException();
            }
            catch (IOException e) {
                System.out.println("Первое введенное число вне диапазона");
                System.exit(1);
            }

        } else {
            if (b < 0 || b > 10){
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("Второе введенное число вне диапазона");
//                    b = false;
                    System.exit(1);
                }
            } else { z = true; } }
        return z;
    }

    static int raschetZnacheniy (int[] dannue, int rZnak){     //Блок 6. Расчет арифметического выражения
        int otvet = 0;
        switch (rZnak){
            case 0 :
                otvet = dannue[0] + dannue[1];
                break;
            case 1 :
                otvet = dannue[0] - dannue[1];
                break;
            case 2:
                otvet = dannue[0] * dannue[1];
                break;
            case 3 :
                otvet = dannue[0] / dannue[1];
                break;
        }
//        if(rZnak < 0 || rZnak >3 || rZnak == null)
        return otvet;
    }

    static boolean otricatelnueRim (int a) {    //Блок 7. Проверка на отрицательные значения при операциях с римскими цифрами
        boolean p = false;
        if (a <= 0) {
            try { throw new NumberFormatException(); }
            catch (NumberFormatException e) {
                System.out.println("throws Exception //т.к. в римской системе нет отрицательных чисел");
                System.exit(1);
            }
        } else { p = true;}
        return p;
    }

    static boolean mnoghestvoZnakov(String[] rMStroka, String[] rZnak) {    //Блок 8. исключаем выражение типа 1 + 1 + 1
        boolean a = false, b = false, z = false;
        if (rMStroka.length > 3) {
            for (int i = 0; i < rZnak.length; i++){
                if(rZnak[i].equals(rMStroka[1])){
                    a = true;
                }
                if(rZnak[i].equals(rMStroka[1])){
                    b = true;
                }
            }
        }
        if(a == true && b == true) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
                System.exit(1);
            }
        } else { z = true;}
        return z;
    }
}

class Number {
    String name;
    int chislo;

    public Number(String name, int chislo) {
        this.name = name;
        this.chislo = chislo;
    }
}
