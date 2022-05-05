package com.catalin;

import com.catalin.project.notes.console_ui.MenuController;
import com.catalin.project.notes.service.DataBaseInitialService;
import com.catalin.project.notes.service.NoteDao;

public class Main {

    public static void main(String[] args) {
        DataBaseInitialService.createMissingTable();
        NoteDao dao = new NoteDao();
        MenuController.showMenu(dao);
    }
}
