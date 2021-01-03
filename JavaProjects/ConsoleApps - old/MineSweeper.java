/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Minesweeper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author wkris
 */
public class MineSweeper {
    static  Map <Integer,Integer> map=new HashMap<>();
    static  Map <Integer,Integer> mapForStars=new HashMap<>();
    static char[][] array = new char[9][9];
    static char[][] arrayEmpty = new char[9][9];
    static int count=0;
    static boolean accept=true;
    static boolean end=false;
    static boolean once=false;

    
    
    // array feltöltése üres helyekkel
    static char [][] emptyFill(char[][] array){
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                array[i][j]='.';
                
            }
            
        }
        return array;
    }
    // adott array rajzolása
    public static void draw(char[][] array) {
        System.out.print(" |");
        for (int i = 1; i < 10; i++) {
            System.out.print(i);
            
        }
        System.out.print("|");
        System.out.println("");
        System.out.print("-|");
        for (int i = 1; i < 10; i++) {
            System.out.print("-");
            
        }
        System.out.print("|");
        System.out.println("");
        for (int i = 0; i < array.length; i++) {
            System.out.print(i+1+"|");
            for (int j = 0; j < array.length; j++) {
                System.out.print(array[i][j]);
            }
            System.out.print("|");
            System.out.println("");
        }
            
            
        System.out.print("-|");
        for (int k = 1; k < 10; k++) {
            System.out.print("-");
            
        }
        System.out.print("|");
        }
    
    
    public static char [][] firstSet(char [][] arrayFront,int x, int y, String action){
        if(action.equals("mine") && arrayFront[x][y]!='*'){
       arrayFront[x][y]='*';
        mapForStars.put((x*9)-(9-y), (x*9)-(9-y));
        } else if(action.equals("mine") && arrayFront[x][y]=='*'){
            arrayFront[x][y]='.';
                mapForStars.remove((x*9)-(9-y), (x*9)-(9-y));
        }
        
        
        return arrayFront;
    }
    
    
    // array feltöltése adattal
    public static char[][] insertData(char[][] array,int n,int x,int y,String action) {
        int count=0;
        char [] mines=new char[9*9];
        int k=0;
        //a kezdő keresés nem lehet akna
        
        for (int i = 0; i < mines.length; i++) {
            if(i<n){
            mines[i]='X';
            count++;
            }else
            mines[i]='/';               
        }
        shuffleArray(mines);
        
        do{
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
               array[i][j]=mines[k];
               k++;
        }
            }
        k=0;
        if(array[x-1][y-1]!='/'){
            shuffleArray(mines);
        }
    }while(array[x-1][y-1]!='/');
       
        
        
        once=true;
        return array;
    }

    // 'aknák' keresése
    public static char[][] checkMines(char[][] array){
        int mines=0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                // bal felső sarok ellenőrzése
                if((i==0 && j==0 && array[i][j]!='X')){
                    if(array[i][j+1]=='X'){
                        mines++;
                    }
                    if(array[i+1][j]=='X'){
                        mines++;
                    }
                    if(array[i+1][j+1]=='X'){
                        mines++;
                    }
                    if(mines!=0){
                        array[i][j]=(char)(mines+'0');
                    }
                    mines=0;
                }
                // jobb felső sarok ellenőrzése
                else if((i==0 && j==8 && array[i][j]!='X')){
                    if(array[i][j-1]=='X'){
                        mines++;
                    }
                    if(array[i+1][j]=='X'){
                        mines++;
                    }
                    if(array[i+1][j-1]=='X'){
                        mines++;
                    }
                    if(mines!=0){
                        array[i][j]=(char)(mines+'0');
                    }
                    mines=0;
                }
                // bal alsó sarok ellenőrzése
                else if((i==8 && j==0 && array[i][j]!='X')){
                    if(array[i][j+1]=='X'){
                        mines++;
                    }
                    if(array[i-1][j]=='X'){
                        mines++;
                    }
                    if(array[i-1][j+1]=='X'){
                        mines++;
                    }
                    if(mines!=0){
                        array[i][j]=(char)(mines+'0');
                    }
                    mines=0;
                }
                // jobb alsó sarok ellenőrzése
                else if((i==8 && j==8 && array[i][j]!='X')){
                    if(array[i][j-1]=='X'){
                        mines++;
                    }
                    if(array[i-1][j]=='X'){
                        mines++;
                    }
                    if(array[i-1][j-1]=='X'){
                        mines++;
                    }
                    if(mines!=0){
                        array[i][j]=(char)(mines+'0');
                    }
                    mines=0;
                }
                
                // bal oldal ellenőrzése
                else if(j==0 && (i!=0 || i!=8) && array[i][j]!='X') {
                    
                            if(array[i-1][j]=='X'){
                                mines++;
                            }
                           if(array[i-1][j+1]=='X'){
                                mines++;
                            }
                           if(array[i][j+1]=='X'){
                                mines++;
                            }
                           if(array[i+1][j]=='X'){
                                mines++;
                            }
                           if(array[i+1][j+1]=='X'){
                                mines++;
                            }
                           if(mines!=0){
                            array[i][j]=(char)(mines+'0');
                            }
                           mines=0;
                }
                // jobb oldal ellenőrzése
                 else if(j==8 && (i!=0 || i!=8) && array[i][j]!='X') {
                    
                            if(array[i-1][j]=='X'){
                                mines++;
                            }
                           if(array[i-1][j-1]=='X'){
                                mines++;
                            }
                           if(array[i][j-1]=='X'){
                                mines++;
                            }
                           if(array[i+1][j]=='X'){
                                mines++;
                            }
                           if(array[i+1][j-1]=='X'){
                                mines++;
                            }
                           if(mines!=0){
                            array[i][j]=(char)(mines+'0');
                            }
                           mines=0;
                }
                 //felső szegély ellenőrzése
                 else if(i==0 && array[i][j]!='X'){
                            if(array[i][j-1]=='X'){
                                mines++;
                            }
                           if(array[i][j+1]=='X'){
                                mines++;
                            }
                           if(array[i+1][j-1]=='X'){
                                mines++;
                            }
                           if(array[i+1][j]=='X'){
                                mines++;
                            }
                           if(array[i+1][j+1]=='X'){
                                mines++;
                            }
                           if(mines!=0){
                            array[i][j]=(char)(mines+'0');
                            }
                           mines=0;
                 }
                 //Alsó szegély ellenőrzése
                else if(i==8 && array[i][j]!='X'){
                            if(array[i][j-1]=='X'){
                                mines++;
                            }
                           if(array[i][j+1]=='X'){
                                mines++;
                            }
                           if(array[i-1][j-1]=='X'){
                                mines++;
                            }
                           if(array[i-1][j]=='X'){
                                mines++;
                            }
                           if(array[i-1][j+1]=='X'){
                                mines++;
                            }
                           if(mines!=0){
                            array[i][j]=(char)(mines+'0');
                            }
                           mines=0;
                 }
                
                //Köztes rész ellenőrzése
                else if(i!=0 && i!=8 && j!=0 && j!=8 && array[i][j]!='X'){
                            if(array[i-1][j-1]=='X'){
                                mines++;
                            }
                           if(array[i-1][j]=='X'){
                                mines++;
                            }
                           if(array[i-1][j+1]=='X'){
                                mines++;
                            }
                           if(array[i][j-1]=='X'){
                                mines++;
                            }
                           if(array[i][j+1]=='X'){
                                mines++;
                            }
                           if(array[i+1][j-1]=='X'){
                                mines++;
                            }
                           if(array[i+1][j]=='X'){
                                mines++;
                            }
                           if(array[i+1][j+1]=='X'){
                                mines++;
                            }
                           if(mines!=0){
                            array[i][j]=(char)(mines+'0');
                            }
                           mines=0;
                    
                }
                        
                
            }
            
        }
        
        
        
        return array;
    }
    
    
    // array random keverése
     static void shuffleArray(char[] ar)
  {

    Random rnd = new Random();
    for (int i = ar.length - 1; i > 0; i--)
    {
      int index = rnd.nextInt(i + 1);
      // Simple swap
      char a = ar[index];
      ar[index] = ar[i];
      ar[i] = a;
    }
  }
     // aknák elfedése
     static char [][] hideTheMines(char[][] array, Map <Integer,Integer> map){
         for (int i = 0; i < array.length; i++) {
             for (int j = 0; j < array.length; j++) {
                 if(array[i][j]=='X'){
                     map.put((i*9)-(9-j), (i*9)-(9-j));
                     array[i][j]='.';
                 }
                 
             }
             
         }
         
         return array;
     }
     
     // lehetséges aknák megjelölése * al
     static char [][] set(char [][] array,int x,int y){
         if(array[x][y]=='.'){
             array[x][y]='*';
             mapForStars.put((x*9)-(9-y), (x*9)-(9-y));
         }else if(array[x][y]=='*'){
             array[x][y]='.';
             mapForStars.remove((x*9)-(9-y), (x*9)-(9-y));
         }else
             System.out.println("There is a number here!");
         
         return array;
     }
     
     static char[][] dataTry(char [][] arrayBack, char [][] arrayFront,int x,int y,String action){
          if(arrayFront[x][y]=='*' && action.equals("mine")){
             arrayFront[x][y]='.';
             mapForStars.remove((x*9)-(9-y), (x*9)-(9-y));
         }
        else if(arrayBack[x][y]=='.' && action.equals("mine")){
             arrayFront[x][y]='*';
             mapForStars.put((x*9)-(9-y), (x*9)-(9-y));
         }
        // sarkak ellenőrzése
        else if(arrayBack[x][y]=='/' && x==0 && y==0 && action.equals("free")){
             arrayFront[x][y]=arrayBack[x][y];
             arrayFront[x+1][y]=arrayBack[x+1][y];
             arrayFront[x+1][y+1]=arrayBack[x+1][y+1];
             arrayFront[x][y+1]=arrayBack[x][y+1];
         }
         else if(arrayBack[x][y]=='/' && x==0 && y==8 && action.equals("free")){
             arrayFront[x][y]=arrayBack[x][y];
             arrayFront[x][y-1]=arrayBack[x][y-1];
             arrayFront[x+1][y]=arrayBack[x+1][y];
             arrayFront[x][y-1]=arrayBack[x][y-1];
         }
         else if(arrayBack[x][y]=='/' && x==8 && y==0 && action.equals("free") ){
             arrayFront[x][y]=arrayBack[x][y];
             arrayFront[x-1][y]=arrayBack[x-1][y];
             arrayFront[x-1][y+1]=arrayBack[x-1][y+1];
             arrayFront[x][y+1]=arrayBack[x][y+1];
         }
         else if(arrayBack[x][y]=='/' && x==8 && y==8 && action.equals("free")){
             arrayFront[x][y]=arrayBack[x][y];
             arrayFront[x-1][y]=arrayBack[x-1][y];
             arrayFront[x-1][y-1]=arrayBack[x-1][y-1];
             arrayFront[x][y-1]=arrayBack[x][y-1];
         }
         // szelső oldalak ellenőrzése sarkak nélkül
         else if(x==0 && (y!=0 || y!=8) && arrayBack[x][y]=='/' && action.equals("free")){
             arrayFront[x][y]=arrayBack[x][y];
             arrayFront[x][y+1]=arrayBack[x][y+1];
             arrayFront[x][y-1]=arrayBack[x][y-1];
             arrayFront[x+1][y+1]=arrayBack[x+1][y+1];
             arrayFront[x+1][y-1]=arrayBack[x+1][y-1];
             arrayFront[x+1][y+1]=arrayBack[x+1][y+1];
         }
         else if(x==8 && (y!=0 || y!=8) && arrayBack[x][y]=='/' && action.equals("free")){
             arrayFront[x][y]=arrayBack[x][y];
             arrayFront[x][y+1]=arrayBack[x][y+1];
             arrayFront[x][y-1]=arrayBack[x][y-1];
             arrayFront[x-1][y+1]=arrayBack[x-1][y+1];
             arrayFront[x-1][y-1]=arrayBack[x-1][y-1];
             arrayFront[x-1][y+1]=arrayBack[x-1][y+1];
         }
         
         else if(y==0 && (x!=0 || x!=8) && arrayBack[x][y]=='/' && action.equals("free")) {
             arrayFront[x][y]=arrayBack[x][y];
             arrayFront[x-1][y]=arrayBack[x-1][y];
             arrayFront[x+1][y]=arrayBack[x+1][y];
             arrayFront[x][y+1]=arrayBack[x][y+1];
             arrayFront[x-1][y+1]=arrayBack[x-1][y+1];
             arrayFront[x+1][y+1]=arrayBack[x+1][y+1];
         }
         else if(y==8 && (x!=0 || x!=8) && arrayBack[x][y]=='/' && action.equals("free")) {
             arrayFront[x][y]=arrayBack[x][y];
             arrayFront[x-1][y]=arrayBack[x-1][y];
             arrayFront[x+1][y]=arrayBack[x+1][y];
             arrayFront[x][y-1]=arrayBack[x][y-1];
             arrayFront[x-1][y-1]=arrayBack[x-1][y-1];
             arrayFront[x+1][y-1]=arrayBack[x+1][y-1];
         } 
         // köztes rész ellenőrzése
         else if(x!=0 && x!=8 && y!=0 && y!=8 && arrayBack[x][y]=='/' && action.equals("free")){
             arrayFront[x][y]=arrayBack[x][y];
             arrayFront[x][y+1]=arrayBack[x][y+1];
             arrayFront[x][y-1]=arrayBack[x][y-1];
             arrayFront[x-1][y]=arrayBack[x-1][y];
             arrayFront[x-1][y+1]=arrayBack[x-1][y+1];
             arrayFront[x-1][y-1]=arrayBack[x-1][y-1];
             arrayFront[x+1][y]=arrayBack[x+1][y];
             arrayFront[x+1][y+1]=arrayBack[x+1][y+1];
             arrayFront[x+1][y-1]=arrayBack[x+1][y-1];
         }
         
         else if(arrayBack[x][y]!='/' && arrayBack[x][y]!='.' && action.equals("free")){
             
             arrayFront[x][y]=arrayBack[x][y];
         }
          else if(arrayBack[x][y]!='/' && arrayBack[x][y]!='.' && action.equals("mine")){
             arrayFront[x][y]='*';
             mapForStars.put((x*9)-(9-y), (x*9)-(9-y));
         }
          else if(arrayBack[x][y]=='/' && action.equals("mine")){
             arrayFront[x][y]='*';
             mapForStars.put((x*9)-(9-y), (x*9)-(9-y));
         }
         
         
         return arrayFront;
     }
     
     // '/' jel felfedése esetén a környező / jeleket is felfedi ezáltal egy loopba kerül
     static void setDataLoop(char [][] array,char [][] arrayEmpty,String action){
         for (int i = 0; i < arrayEmpty.length; i++) {
                   for (int j = 0; j < arrayEmpty.length; j++) {
                       if(arrayEmpty[i][j]=='/'){
                           dataTry(array, arrayEmpty, i, j,action);
                           
                       } 
                   }
         }
         
     }
     // end valtozó átállítása X re lépés esetén
     static void endedByMines(char [][] arrayBack,char [][] arrayFront, int x,int y, String action){
         if((arrayBack[x][y]=='.' || arrayBack[x][y]=='*') && action.equals("free")  ){
            arrayFront[x][y]=arrayBack[x][y];
         end= true;
         }else 
         end= false;
     }
     
     // végső táblázat összerakása
     static char [][] finalArray(char [][] array){
         for (int i = 0; i < array.length; i++) {
             for (int j = 0; j < array.length; j++) {
                 if(array[i][j]=='.'){
                     array[i][j]='X';
                 }
                 
             }
             
         }
         
         
         
         return array;
     }

    public static void main(String[] args) {
        System.out.println("Welcome! This is a minesweeper game!");
        System.out.println("First you have to give two coordinates(X and Y)!");
        System.out.println("After that you can give a command(free or mine)!");
        System.out.println("Like : 23 free, 55 mine.");
        System.out.println("Free command checks the choosen field, the mine command marks the field with a *,which warns you about a possible mine!");
        System.out.println("If you mark an already marked field,it removes the * and it won't be considered as marked!");
        System.out.println("You have to mark all the mines or check all mine-free fields without stepping on a mine!");
        System.out.println("Good luck!");
        System.out.println("How many mines do you want on the field?");
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        
        emptyFill(arrayEmpty);
        draw(arrayEmpty);
        
        
        
        do{
        System.out.println("");
        
        System.out.println("Enter coordinates and command!");
            Scanner scanner = new Scanner(System.in);
            String user = scanner.nextLine();
            // checks if the input is empty or not
            if(!user.equals("")){
            user = user.replace(" ", "");
            String action=user.substring(2);
            String numbers=user.substring(0, 2);
            char[] uc = numbers.toCharArray();
            
            if(uc.length!=2){
                System.out.println("You should enter 2 parameters!");
              
            }
            //ellenőrzi hogy számok e az adatok
            else if(!(Character.isDigit(uc[0]) && Character.isDigit(uc[1]))){
                System.out.println("You should enter 2 numbers!");
                
            }
            else if(!(action.equals("free") || action.equals("mine"))){
                System.out.println("Enter command free or mine!");
            }
                    
            else{
            //számokká alakitás
            int x = Character.getNumericValue(uc[0]);
            int y = Character.getNumericValue(uc[1]);
            if(x>9 || x<1 || y>9 || y<1){
                System.out.println("X and Y should be between 1 and 9!");
            }else{
         // az insertData függvényben átállítódik a once truera ha egyszer már lefutott
         
        if(once==false && action.equals("free")) {
            
        insertData(array,n,x,y,action);
        checkMines(array);
        hideTheMines(array,map);
        
        }   
        if(once==false && action.equals("mine")){
                firstSet(arrayEmpty,x-1,y-1,action);
        }
            // megjelölt cella körüli értékelés
        else{ 
            
            dataTry(array, arrayEmpty, x-1, y-1,action);
            if(!action.equals("mine")){
                for (int i = 0; i < arrayEmpty.length; i++) {
                    for (int j = 0; j < arrayEmpty.length; j++) {
                        if(arrayEmpty[i][j]=='/')
                         setDataLoop(array, arrayEmpty,action);
                    }
                    
                }
            }
          
                
//        set(array, x-1, y-1);
       endedByMines(array, arrayEmpty, x-1, y-1, action);
        }
            if(end==false)
        draw(arrayEmpty);
            }
        }
            }
        }while((!(map.equals(mapForStars)) || once==false) && end==false  );
        System.out.println("");
        if(end==true){
            finalArray(array);
            draw(array);
            System.out.println("");
        System.out.println("You stepped on a mine and failed!");
}else     
        System.out.println("Congratulations! You found all mines!");
    }
}

