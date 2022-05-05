package com.catalin.project.notes.console_ui;

import com.catalin.project.notes.dto.NoteDto;
import com.catalin.project.notes.dto.Priority;
import com.catalin.project.notes.dto.State;

import java.sql.Date;

import static com.catalin.project.notes.console_ui.MenuUtil.*;

public class EditNoteController {
//Used to read details for the new note
    public static NoteDto readNewNote(){
        System.out.println("Enter details of new note: ");

        String description = readString(" Description: ",false);
        Priority priotiry = readPriority();
        Date dueDate = readDueDate();

        return new NoteDto(description,dueDate,priotiry, State.ACTIVE);
    }
    //Used to read the details which have to be updated for a note, base on Id of the note
    public static NoteDto readNoteForUpdate(long id){
        System.out.println("Enter details of note to edit: ");
        String description = readString("Description: ", false);
        Priority priority = readPriority();
        Date dueDate = readDueDate();
        State state = readState();
        return new NoteDto(id, description,  dueDate, priority, state);
    }
//Private methods to create/update fields of a note
    private static Priority readPriority() {
        char choice = readChoice(" - Priority - Low/Medium/High (L/M/H): ",'L','M','H');
        return choice=='L'? Priority.LOW : choice=='M' ? Priority.MEDIUM : Priority.HIGH;

    }
    private static State readState(){
        char choice = readChoice("State - Active/ Completed (A/C)",'A','C');
        return choice =='A'?State.ACTIVE:State.COMPLETED;
    }

    private static Date readDueDate() {
        Date dueDate = null;
        boolean isValid = false;
        do{
            String dateFromString = readString(" - Due date (empty or date like YYYY-MM-DD): ", true);
            if(dateFromString.isEmpty()){
                isValid = true;
            }else{
                try{
                    dueDate = Date.valueOf(dateFromString);
                    isValid = true;
                }catch(IllegalArgumentException e){
                    System.err.println("Invalid value of date: " + e.getMessage());
                }
            }
        }while(!isValid);
        return dueDate;
    }
}
