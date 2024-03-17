package edu.jsu.mcis.cs408.memopad;

import android.util.Log;

import java.util.List;

public class MemoPadModel extends AbstractModel {

    public static final String TAG = "DefaultModel";

    /*
     * This is a simple implementation of an AbstractModel which encapsulates
     * two text fields, text1 and text2, which (in this example) are each
     * reflected in the View as an EditText field and a TextView label.
     */

    private String text1;

    private String text2;
    private List<Memo> recycler = null;

    /*
     * Initialize the model elements to known default values.  We use the setter
     * methods instead of changing the values directly so that these changes are
     * properly announced to the Controller, and so that the Views can be updated
     * accordingly.
     */

    /*
     * Simple getter methods for text1 and text2
     */

    public void addNewMemo(Memo memo, DatabaseHandler db) {

        db.addMemo(memo);
    }

    public void deleteMemo(int selectedMemo, DatabaseHandler db) {

        db.deleteMemo(selectedMemo);
    }

    public void listMemos(DatabaseHandler db) {

        List<Memo> oldRecycler = this.recycler;

        this.recycler = db.getAllMemosAsList();
        firePropertyChange(MemoPadController.ELEMENT_RECYCLER_PROPERTY, oldRecycler, recycler);
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }

    /*
     * Setters for text1 and text2.  Notice that, in addition to changing the
     * values, these methods announce the change to the controller by firing a
     * PropertyChange event.  Any registered AbstractController subclasses will
     * receive this event, and will propagate it to all registered Views so that
     * they can update themselves accordingly.
     */

    public void setText1(String newText) {

        String oldText = this.text1;
        this.text1 = newText;

        Log.i(TAG, "Text1 Change: From " + oldText + " to " + newText);

        firePropertyChange(MemoPadController.ELEMENT_TEXT1_PROPERTY, oldText, newText);

    }

    public void setText2(String newText) {

        String oldText = this.text2;
        this.text2 = newText;

        Log.i(TAG, "Text2 Change: From " + oldText + " to " + newText);

        firePropertyChange(MemoPadController.ELEMENT_TEXT2_PROPERTY, oldText, newText);

    }

}