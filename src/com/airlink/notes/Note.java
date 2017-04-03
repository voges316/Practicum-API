package com.airlink.notes;

import java.util.Objects;

public class Note {
	private int id;
    private String first;
    private String last;

    public Note() {
    }
    
    public Note(int id, String first, String last) {
        this.id = id;
        this.first = first;
        this.last = last;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.first);
        hash = 37 * hash + Objects.hashCode(this.last);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Note other = (Note) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.first, other.first)) {
            return false;
        }
        if (!Objects.equals(this.last, other.last)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Note{" + "id=" + id + ", first=" + first + ", last=" + last + '}';
    }
}
