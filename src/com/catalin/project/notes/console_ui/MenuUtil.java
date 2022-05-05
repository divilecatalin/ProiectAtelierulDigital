package com.catalin.project.notes.console_ui;

import java.util.*;

public class MenuUtil {
    static char readChoice(String menu, Character...options){
        Set<Character> optionsPermitted = new HashSet<>(Arrays.asList(options));
        char choice = '?';

        Scanner scanner = new Scanner(System.in);
        while(choice =='?'){
            System.out.println("\n" + menu + "\nChoice? : ");
            String input = scanner.next().trim().toUpperCase();
            char first = input.length() >= 1 ? input.charAt(0) : '?';
            if(optionsPermitted.contains(first)){
                choice = first;
            }else{
                System.err.println("'" + input + "' is not valid! Please try again!");
            }
        }
        System.out.println();
        return choice;
    }

    static String readString(String label,boolean allowEmpty){
        System.out.println(label);
        Scanner scanner = new Scanner(System.in);
        String input;
        do{
            input = scanner.nextLine().trim();
        }while(!allowEmpty && input.isEmpty());
        return input;
    }

    static int readInt(String label){
        while(true){
            String numberInput = readString(label,false);
            try{
                return Integer.parseInt(numberInput);
            }catch(NumberFormatException e){
                System.err.println("You must insert a number!!!Try again! (error: " + e.getMessage()+")");
            }
        }
    }
}
