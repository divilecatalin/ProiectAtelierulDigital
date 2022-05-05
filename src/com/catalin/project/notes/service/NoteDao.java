package com.catalin.project.notes.service;

import com.catalin.project.notes.DbManager;
import com.catalin.project.notes.dto.NoteDto;
import com.catalin.project.notes.dto.Priority;
import com.catalin.project.notes.dto.State;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.catalin.project.notes.DbManager.getConnection;
import static com.catalin.project.notes.service.NotesTable.*;

public class NoteDao {

    public List<NoteDto> getAllNotes() {
        StringBuilder query = new StringBuilder();

        query.append(" SELECT * FROM ");
        query.append(TABLE_NAME);
        query.append(" ORDER BY ");
        query.append(FIELD_DUE_DATE);
        query.append(" DESC, ");
        query.append(FIELD_PRIORITY);

        List<NoteDto> notes = new ArrayList<>();

        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(query.toString());
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                notes.add(extractFromResult(rs));
            }
        }catch (SQLException e){
            System.err.println("Error getting all notes: "+ e.getMessage());
        }
        return notes;
    }

    public Optional<NoteDto> get(long id)  {
        StringBuilder query = new StringBuilder();
        query.append(" SELECT * from ").append( TABLE_NAME);
        query.append(" WHERE ");
        query.append(FIELD_ID);
        query.append(" = ?");

        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(query.toString())){
            ps.setLong(1, id);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    NoteDto note = extractFromResult(rs);
                    return Optional.of(note);
                }
            }
        }catch(SQLException e){
            System.err.println("Error getting note with id "+ id+ e.getMessage());
        }
        return Optional.empty();
    }

    public void insert(NoteDto note){
        StringBuilder query = new StringBuilder();

        query.append("INSERT INTO ").append(TABLE_NAME);
        query.append(" ( ").append(FIELD_DESCRIPTION).append(", ").append(FIELD_PRIORITY).append(", ")
                .append(FIELD_DUE_DATE).append(", ").append(FIELD_STATE).append(") ");
        query.append(" VALUES(?,?,?,?)");

        try(Connection conn = DbManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query.toString())){
            ps.setString(1,note.getDescription());
            ps.setString(2,note.getPriority().name());
            ps.setDate(3,note.getDueDate());
            ps.setString(4,note.getState().name());

            ps.execute();
        }catch(SQLException e) {
            System.err.println("Error inserting in data base " + note+ " : " + e.getMessage());
        }
    }

    public void update(NoteDto note){
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ").append(TABLE_NAME);
        query.append(" SET ").append(FIELD_DESCRIPTION).append(" = ?, ").append(FIELD_PRIORITY).append(" = ?, ")
                .append(FIELD_DUE_DATE).append(" = ?, ").append(FIELD_STATE).append(" = ? ");
        query.append(" WHERE ").append(FIELD_ID).append(" = ?");

        try(Connection conn = DbManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query.toString())){

            ps.setString(1, note.getDescription());
            ps.setString(2, note.getPriority().name());
            ps.setDate(3, note.getDueDate());
            ps.setString(4, note.getState().name());
            ps.setLong(5, note.getId());

            ps.executeUpdate();
        }catch(SQLException e){
            System.err.println("Error updating note "+ note + " : "+ e.getMessage());
        }
    }

    public void delete(long id){
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(TABLE_NAME);
        query.append(" WHERE ").append(FIELD_ID).append(" =?");

        try(Connection conn = DbManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query.toString())){

            ps.setLong(1, id);

            ps.execute();
        }catch(SQLException e){
            System.err.println("Error deleting note "+ id + " : "+ e.getMessage());
        }

    }

    private NoteDto extractFromResult(ResultSet rs) throws SQLException{
        long id = rs.getLong(FIELD_ID);
        String description = rs.getString(FIELD_DESCRIPTION);
        Date dueDate = rs.getDate(FIELD_DUE_DATE);
        Priority priority = Priority.valueOf(rs.getString(FIELD_PRIORITY));
        State state = State.valueOf(rs.getString(FIELD_STATE));

        return new NoteDto(id,description,dueDate,priority,state);
    }
}
