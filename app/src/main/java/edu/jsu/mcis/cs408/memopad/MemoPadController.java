package edu.jsu.mcis.cs408.memopad;

public class MemoPadController extends AbstractController
{

    public static final String ELEMENT_RECYCLER_PROPERTY = "Recycler";
    public MemoPadModel model = new MemoPadModel();

    public MemoPadController(MemoPadModel model) {

        super();
        this.model = model;
        addModel(model);
    }
    public void changeElementRecycler(String newText, DatabaseHandler db) {

        model.addNewMemo(new Memo(newText), db);
        model.listMemos(db);
    }

    public void changeElementRecyclerDelete(int selectedMemo, DatabaseHandler db) {

        model.deleteMemo(selectedMemo, db);
        model.listMemos(db);

    }

}
