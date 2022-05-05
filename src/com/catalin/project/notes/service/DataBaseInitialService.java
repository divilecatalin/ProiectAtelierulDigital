package com.catalin.project.notes.service;

import com.catalin.project.notes.DbManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.catalin.project.notes.dto.Priority.*;
import static com.catalin.project.notes.dto.State.*;
import static com.catalin.project.notes.service.NotesTable.*;

public class DataBaseInitialService {
    public static void createMissingTable(){
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append(" ( ");
        sql.append(FIELD_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sql.append(FIELD_DESCRIPTION).append(" TEXT NOT NULL, ");
        sql.append(FIELD_PRIORITY).append(" TEXT CHECK( ").
                append(FIELD_PRIORITY).append(" IN ('").
                append(LOW).append("', '").
                append(MEDIUM).append("', '").
                append(HIGH).append("')) NOT NULL, ");
        sql.append(FIELD_DUE_DATE).append(" DATETIME, ");
        sql.append(FIELD_STATE).append(" TEXT CHECK( ").
                append(FIELD_STATE).append(" IN ('").
                append(ACTIVE).append("', '").
                append(COMPLETED).append("')) NOT NULL, ");
        sql.append(" CONSTRAINT DESC_DATE_UNQ UNIQUE ( ").
                append(FIELD_DESCRIPTION).append(", ").
                append(FIELD_DUE_DATE).append( "));");

        try (Connection conInt = DbManager.getConnection();
        Statement st = conInt.createStatement()){

            st.execute(sql.toString());
        } catch(SQLException e){
            System.err.println("Error in creating missing table: "+ e.getMessage());
        }
    }
}
