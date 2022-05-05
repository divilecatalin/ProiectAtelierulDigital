package com.catalin.project.notes.console_ui;

import com.catalin.project.notes.dto.NoteDto;
import com.catalin.project.notes.dto.State;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;

import static com.catalin.project.notes.console_ui.MenuUtil.readChoice;
import static com.catalin.project.notes.console_ui.NotesController.Order.BY_PRIORITY;

public class NotesController {
    enum Order{
        BY_PRIORITY,BY_DUE_DATE
    }

    private static Order orderBy = BY_PRIORITY;
    private static boolean hideCompleted = false;
    public static Order getOrder(){
        return orderBy;
    }
    public static boolean isHideCompleted(){
        return hideCompleted;
    }
    public static void changeOrder(){
        char choice = readChoice("You can order as follows: \nP - by priority(High to Low) \nD - by due date (ascending)",'P','D');
        orderBy= choice =='P'? BY_PRIORITY:Order.BY_DUE_DATE;
    }
    public static void hideCompleted(){
        hideCompleted = !hideCompleted;
        System.out.println("Hide completed notes: " + hideCompleted);
    }

    public static void display(List<NoteDto> notes){

        System.out.println("\n ================= NOTES =========("+orderBy +")===================================");

        notes.stream().filter(note -> note.getState()== State.ACTIVE|| !hideCompleted).sorted(getDisplayComparator()).forEach(System.out::println);

        System.out.println("=====================================================================================");
    }

    private static Comparator<NoteDto> getDisplayComparator(){
        if(orderBy == BY_PRIORITY){
            return Comparator.comparing(NoteDto::getPriority);
        }else{
            Date defaultDate = new Date(0);
            return(note1,note2)->{
                Date date1 = note1.getDueDate() != null ? note1.getDueDate() :defaultDate;
                Date date2 = note2.getDueDate() !=null ? note2.getDueDate() :defaultDate;
                return date1.compareTo(date2);
            };
        }
    }
}
