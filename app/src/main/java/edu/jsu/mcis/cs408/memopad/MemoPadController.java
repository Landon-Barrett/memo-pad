package edu.jsu.mcis.cs408.memopad;

public class MemoPadController extends AbstractController
{

    /*
     * These static property names are used as the identifiers for the elements
     * of the Models and Views which may need to be updated.  These updates can
     * be a result of changes to the Model which must be reflected in the View,
     * or a result of changes to the View (in response to user input) which must
     * be reflected in the Model.
     */

    public static final String ELEMENT_TEXT1_PROPERTY = "Text1";
    public static final String ELEMENT_TEXT2_PROPERTY = "Text2";
    public static final String ELEMENT_RECYCLER_PROPERTY = "Recycler";
    public MemoPadModel model = new MemoPadModel();

    /*
     * This is the change method which corresponds to ELEMENT_TEXT1_PROPERTY.
     * It receives the new data for the Model, and invokes "setModelProperty()"
     * (inherited from AbstractController) so that the proper Model can be found
     * and updated properly.
     */

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

    /*
     * This is the change method which corresponds to ELEMENT_TEXT2_PROPERTY.
     */

    public void changeElementText2(String newText) {
        setModelProperty(ELEMENT_TEXT2_PROPERTY, newText);
    }

}