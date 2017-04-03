package com.airlink.notes;

import java.util.ArrayList;
import java.util.List;

public class NoteMgr {

    private static List<Note> noteList;
    
    // Implement Singleton
    private static NoteMgr instance;
    
    private NoteMgr() { 
        setupNoteList();
    }
    
    public static synchronized NoteMgr getInstance() {
        if (instance == null) {
            instance = new NoteMgr();
        }
        
        return instance;
    }
    
    public Note create(Note note) {
        noteList.add(note);
        return note;
    }
    
    public Note get(int id) {
        Note result = null;
        for (Note n : noteList) {
            if (n.getId() == id) {
                result = n;
                break;
            }
        }        
        
        return result;
    }
    
    public Note update(Note note) {
        Note result = null;
        int lookupId = note.getId();
        
        for (int i = 0; i < noteList.size(); i++) {
            if (noteList.get(i).getId() == lookupId) {
                noteList.set(i, note);
                result = note;
                break;
            }
        }
        
        return result;
    }
    
    public List<Note> getNotes() {
        return noteList;
    }
    
    private static void setupNoteList() {
        noteList = new ArrayList<>();
        Note note1 = new Note(1, "mike", "v");
        Note note2 = new Note(2, "charis", "s");
        Note note3 = new Note(3, "adiya", "g");
        
        noteList.add(note1);
        noteList.add(note2);
        noteList.add(note3);
    }
    
    public static void main(String[] args) {
        NoteMgr srch = NoteMgr.getInstance();
        
        System.out.println("Testing get notes");
        for (Note n : srch.getNotes()) {
            System.out.println(n);
        }
        
        System.out.println("Testing get a note");
        int srchId = 2;
        System.out.println(srch.get(srchId));
        
        System.out.println("Testing creating a note");
        Note newNote = new Note(4, "aniah", "f");
        srch.create(newNote);
        for (Note n : srch.getNotes()) {
            System.out.println(n);
        }
        
        System.out.println("Testing updating a note");
        Note updateNote = new Note(3, "bart", "simpson");
        srch.update(updateNote);
        for (Note n : srch.getNotes()) {
            System.out.println(n);
        }
    }   	
}
