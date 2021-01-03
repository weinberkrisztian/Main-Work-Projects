
package github;

import java.util.ArrayList;
import java.util.Scanner;


public class TicTacToe {

    static int hossz = 3;
    static int counter = 1;
    static boolean helyes = false;
    static int s = 0;
    static int f = 0;
    static char[][] finalArr = new char[hossz][hossz];
    static String X = "X wins";
    static String O = "O wins";
    static String ered = "";
    static String imp = "";
    static boolean XW = false;
    static boolean OW = false;
    static boolean IP = false;
    static boolean DR = false;
    static boolean GNF = false;
    //sor,oszlop és átlok vizsgalata által megkapott String viszgálata
    public static void bools(String im, char[][] tt) {
        boolean vane = false;
        //van-e ures mező
        for (int i = 0; i < hossz; i++) {
            for (int j = 0; j < hossz; j++) {
                if (tt[i][j] == ' ') {
                    vane = true;
                }
            }
        }
        // döntetlen
        if (vane == false && imp.equals("") && counter == 10) {
            DR = true;
        // X nyer
        } else if (imp.contains(X)) {
            XW = true;
        // O nyer    
        } else if (imp.contains(O)) {
            OW = true;
        }
    }

    //ellenőrzi a mezők adatait oszloponként, visszeküld egy stringet ami vagy X vagy O t tartalmaz
    public static String oszlop(char[][] ch, ArrayList<Character> arrChar) {
        String ered = "";
        for (int i = 0; i < ch.length; i++) {
            for (int j = 0; j < ch.length; j++) {
                arrChar.add(ch[j][i]);
            }

            String string = strb(arrChar);
            ered = whowin(string);
            imp = imp.concat(ered);
            arrChar.clear();
        }
        return imp;
    }
 //ellenőrzi a mezők adatait soronként, visszeküld egy stringet ami vagy X vagy O t tartalmaz
    public static String sor(char[][] ch, ArrayList<Character> arrChar) {
        String ered = "";
        for (int i = 0; i < ch.length; i++) {
            for (int j = 0; j < ch.length; j++) {
                arrChar.add(ch[i][j]);
            }

            String string = strb(arrChar);
            ered = whowin(string);
            imp = imp.concat(ered);
            arrChar.clear();
        }
        return imp;
    }
//ellenőrzi a mezők adatait átlóra, visszeküld egy stringet ami vagy X vagy O t tartalmaz
    public static String atl1(char[][] ch, ArrayList<Character> arrChar) {
        String ered = "";
        for (int i = 0; i < ch.length; i++) {
            arrChar.add(ch[i][i]);

        }
        String string = strb(arrChar);
        ered = whowin(string);
        imp = imp.concat(ered);
        return imp;
    }
//ellenőrzi a mezők adatait átlóra, visszeküld egy stringet ami vagy X vagy O t tartalmaz, k=0 bemenetnél
    public static String atl2(char[][] ch, ArrayList<Character> arrChar, int k) {
        String ered = "";
        for (int i = 2; i >= 0; i--) {
            arrChar.add(ch[k][i]);
            k++;
        }
        String string = strb(arrChar);
        ered = whowin(string);
        imp = imp.concat(ered);
        return imp;
    }
    //egyszerű StringBuilder,ami átalakítja az eddigi mezők adatait
    public static String strb(ArrayList<Character> arrChar) {
        StringBuilder sb = new StringBuilder();
        for (Character cha : arrChar) {
            sb.append(cha);
        }
        String string = sb.toString();
        return string;
    }
    
    //a bemenő Stringet ellenőrzi,ha 3 X van benne akkor X, ha 3 O akkor O valászt küld vissza
    public static String whowin(String st) {
        String eredm = "";
        if (st.equals("XXX")) {
            eredm = X;
        } else if (st.equals("OOO")) {
            eredm = O;
        }
        return eredm;
    }  
    //alap üres táblát rajzol le
    public static void rajz(int h, char[][] ch, char[] c) {
        System.out.println("");
        System.out.println("---------");
        for (int i = 0; i < h; i++) {
            System.out.print("| ");
            for (int j = 0; j < h; j++) {
                if (c[j + (i * h)] == '_') {
                    c[j + (i * h)] = ' ';
                }
                ch[i][j] = c[j + (i * h)];

                System.out.print(ch[i][j] + " ");
            }
            System.out.print("|");
            System.out.println("");

        }
        System.out.println("---------");
        for (int i = 0; i < hossz; i++) {
            for (int j = 0; j < hossz; j++) {
                finalArr[i][j] = ch[i][j];

            }

        }
    }
        //ellenőrzi hogy a bemenő koordináták-1 egyenlő e az adott i vagy j vel, a counter figyeli hogy X vagy O jön e
        //végül kirajzolás
    public static void rajzbyuser(int h, char[][] tt, char[] c, int m, int e) {

        System.out.println("");
        System.out.println("---------");
        for (int i = 0; i < h; i++) {
            System.out.print("| ");
    
            for (int j = 0; j < h; j++) {
                if (i == m - 1 && j == e - 1 && tt[i][j] == ' ' && counter % 2 == 1) {
                    tt[i][j] = 'X';

                }
                if (i == m - 1 && j == e - 1 && tt[i][j] == ' ' && counter % 2 == 0) {
                    tt[i][j] = 'O';

                }
                System.out.print(tt[i][j] + " ");
            }
            System.out.print("|");
            System.out.println("");

        }
        System.out.println("---------");
        for (int i = 0; i < hossz; i++) {
            for (int j = 0; j < hossz; j++) {
                finalArr[i][j] = tt[i][j];

            }

        }
    }
//ellenőrzi, hogy az adott hely üres-e
    public static boolean feltolt(int h, char[][] ch, char[] c, int m, int e) {
        boolean joe = false;
        if (ch[m - 1][e - 1] == ' ') {
            joe = true;
        }
        return helyes = joe;
    }

    public static void main(String[] args) throws InterruptedException {
        // üres mezők létrehozása
        String str = "";
        for (int i = 0; i < 9; i++) {
            str = str.concat("_");

        }
        char[] c = str.toCharArray();
        //átló vizsgálatahoz szükséges paraméter
        int k = 0;
        rajz(hossz, finalArr, c);
        ArrayList<Character> arrChar = new ArrayList<>();
        do {
            System.out.print("Enter the coordinates:");
            Scanner sc = new Scanner(System.in);
            String user = sc.nextLine();
            user = user.replace(" ", "");
            char[] uc = user.toCharArray();
            //ellenőrzi, hogy 2 paramétert adtak e bemenetnek
            if(uc.length!=2){
                System.out.println("You should enter 2 parameters!");
                helyes=false;
            }
            else{
            //számokká alakitás
            int f = Character.getNumericValue(uc[0]);
            int s = Character.getNumericValue(uc[1]);
            
            
            
            if (!(Character.getType(uc[0]) == 2 || Character.getType(uc[1]) == 2)) {
                if (!((0 >= f) || (f >= 4) || (0 >= s) || (s >= 4))) {
                    feltolt(hossz, finalArr, c, f, s);
                }
            }
            //ellenőrzi, hogy számokat adtunk e meg
            if (Character.getType(uc[0]) == 2 || Character.getType(uc[1]) == 2) {
                helyes = false;
                System.out.println("You should enter numbers!");
            }
            //a koordinátáknak csak 1-3ig szabad terjednie
            else if ((0 >= f) || (f >= 4) || (0 >= s) || (s >= 4)) {
                helyes = false;
                System.out.println("Coordinates should be from 1 to 3!");
             //foglalt mező esetén
            } else if (helyes == false) {
                System.out.println("This cell is occupied! Choose another one!");
            //ha minden rendben újra rajzolás    
            } else if (helyes == true) {
                rajzbyuser(hossz, finalArr, c, f, s);
                counter++;
            }
            //logikai ellenőrzések
            sor(finalArr, arrChar);
            arrChar.clear();
            oszlop(finalArr, arrChar);
            arrChar.clear();
            atl1(finalArr, arrChar);
            arrChar.clear();
            atl2(finalArr, arrChar, k);
            arrChar.clear();
            bools(imp, finalArr);
            if (DR) {
                System.out.println("Draw");
                counter = 10;
            } else if (XW) {
                System.out.println("X wins");
                counter = 10;
            } else if (OW) {
                System.out.println("O wins");
                counter = 10;
            }
            }
        } while (helyes == false || counter != 10);

    }
}
