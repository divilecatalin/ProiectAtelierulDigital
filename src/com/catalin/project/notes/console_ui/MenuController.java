package com.catalin.project.notes.console_ui;

import com.catalin.project.notes.dto.NoteDto;
import com.catalin.project.notes.dto.State;
import com.catalin.project.notes.service.NoteDao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;

import static com.catalin.project.notes.console_ui.MenuUtil.*;

public class MenuController {
    public static void showMenu(NoteDao dao) {
        while(true){
            NotesController.display(dao.getAllNotes());

            char c = readChoice("A - Add a new note\n"+
                                      "C - Mark a note as Completed \n"+
                                      "D - Delete a note\n"+
                                      "S - Change sort order (current: "+ NotesController.getOrder() + ")\n"+
                                      "U - Update an existing note\n"+
                                      "H - Hide/show Completed notes (current: " +(NotesController.isHideCompleted()? "hidden":"shown") +")\n"+
                                      "Q - Quit",
                                      'A','C','D','S','U','H','Q');

            switch(c){
                case 'A':
                    NoteDto newNote= EditNoteController.readNewNote();
                    dao.insert(newNote);
                    break;
                case 'C':
                    int id = MenuUtil.readInt("Insert id of note to complete: ");
                    dao.get(id).ifPresent(note->
                            dao.update(new NoteDto(note.getId(), note.getDescription(),note.getDueDate(),note.getPriority(), State.COMPLETED)));
                    break;
                case 'D':
                    int idForDelete = MenuUtil.readInt("Insert id of note to be deleted: ");
                    dao.delete(idForDelete);
                    break;
                case 'S':
                    NotesController.changeOrder();
                    break;
                case 'U':
                    int idUpdate = MenuUtil.readInt("Insert id of note to be updated: ");
                    NoteDto update = EditNoteController.readNoteForUpdate(idUpdate);
                    dao.get(idUpdate).ifPresent(note-> dao.update(update));
                case 'H':
                    NotesController.hideCompleted();
                    break;
                default:
                    System.out.println("Bye!");
                    return;
            }
        }
    }
}
