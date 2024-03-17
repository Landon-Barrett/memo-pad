package edu.jsu.mcis.cs408.memopad;

import java.util.List;

public class MemoPadModel extends AbstractModel {

    private List<Memo> recycler = null;

    public void addNewMemo(Memo memo, DatabaseHandler db) {
        if((memo.getMemo().equals("")) || (memo.getMemo().equals(" "))) {
            //Do nothing.
        }
        else {
            db.addMemo(memo);
        }

    }

    public void deleteMemo(int selectedMemo, DatabaseHandler db) {

        db.deleteMemo(selectedMemo);
    }

    public void listMemos(DatabaseHandler db) {

        List<Memo> oldRecycler = this.recycler;

        this.recycler = db.getAllMemosAsList();
        firePropertyChange(MemoPadController.ELEMENT_RECYCLER_PROPERTY, oldRecycler, recycler);
    }

}
