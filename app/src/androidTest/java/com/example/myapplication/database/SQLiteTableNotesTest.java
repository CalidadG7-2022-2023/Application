package com.example.myapplication.database;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import com.example.myapplication.model.Note;
import com.example.myapplication.model.User;
import junit.framework.TestCase;
import org.junit.Test;

public class SQLiteTableNotesTest extends TestCase {

    private EligaNotesDB eligaNotesDB;

    private Context context;

    private SQLiteTableNotes tableNotes;

    private SQLiteTableUsers tableUsers;
    private Note noteChecked;


    public void createDataBase() {
        this.context = InstrumentationRegistry.getTargetContext();

        this.eligaNotesDB = EligaNotesDB.getInstance(this.context);
        this.tableUsers = new SQLiteTableUsers(this.context);
        this.tableNotes = new SQLiteTableNotes(this.context);

        this.tableUsers.insertData(new User("User1", "pwd1", "pwd1"));
        this.tableUsers.insertData(new User("User2", "pwd2", "pwd2"));
        this.tableUsers.insertData(new User("User3", "pwd3", "pwd3"));
        this.tableUsers.insertData(new User("User4", "pwd4", "pwd4"));
        this.tableUsers.insertData(new User("User5", "pwd5", "pwd5"));

        this.tableNotes.insertData(new Note("Title1", "Description1"), "User1");
        this.noteChecked = new Note("Title2", "Description2");
        this.tableNotes.insertData(this.noteChecked, "User2");
        this.tableNotes.insertData(new Note("Title3", "Description3"), "User3");
        this.tableNotes.insertData(new Note("Title4", "Description4"), "User4");
        this.tableNotes.insertData(new Note("Title5", "Description5"), "User5");
        this.noteChecked = new Note("Title2", "Description2");
        this.tableNotes.insertData(this.noteChecked, "User2");
    }

    public void deleteData() {
        this.tableNotes.clearData();
        this.tableUsers.clearData();
    }

    @Test
    public void testDeleteNote() {
        createDataBase();
        assertEquals(5, tableNotes.obtainAllNotes().size());
        Note note = tableNotes.deleteNote("Title2");
        assertEquals(4, tableNotes.obtainAllNotes().size());
        assertEquals(noteChecked.getTitle() + noteChecked.getText(), note.getTitle() + note.getText());
        deleteData();
    }
}