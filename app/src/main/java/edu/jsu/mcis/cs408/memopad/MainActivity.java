package edu.jsu.mcis.cs408.memopad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.beans.PropertyChangeEvent;

import edu.jsu.mcis.cs408.memopad.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements AbstractView {

    public static final String TAG = "MainActivity";

    private ActivityMainBinding binding;
    private DatabaseHandler db;

    private DefaultController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        db = new DatabaseHandler(this, null, null, 1);

        /* Create Controller and Model */

        controller = new DefaultController();
        DefaultModel model = new DefaultModel();

        /* Register Activity View and Model with Controller */

        controller.addView(this);
        controller.addModel(model);

        /* Initialize Model to Default Values */


        /* Associate Click Handler with Input Buttons */

        DefaultClickHandler click = new DefaultClickHandler();
        ConstraintLayout layout = binding.layout;

        for (int i = 0; i < layout.getChildCount(); ++i) {
            View child = layout.getChildAt(i);
            if(child instanceof Button) {
                child.setOnClickListener(click);
            }
        }

    }

    @Override
    public void modelPropertyChange(final PropertyChangeEvent evt) {

        /*
         * This method is called by the "propertyChange()" method of AbstractController
         * when a change is made to an element of a Model.  It identifies the element that
         * was changed and updates the View accordingly.
         */

        String propertyName = evt.getPropertyName();
        String propertyValue = evt.getNewValue().toString();

        Log.i(TAG, "New " + propertyName + " Value from Model: " + propertyValue);
        /*
        if ( propertyName.equals(DefaultController.ELEMENT_TEXT1_PROPERTY) ) {

            String oldPropertyValue = binding.outputText1.getText().toString();

            if ( !oldPropertyValue.equals(propertyValue) ) {
                binding.outputText1.setText(propertyValue);
            }

        }

        else if ( propertyName.equals(DefaultController.ELEMENT_TEXT2_PROPERTY) ) {

            String oldPropertyValue = binding.outputText2.getText().toString();

            if ( !oldPropertyValue.equals(propertyValue) ) {
                binding.outputText2.setText(propertyValue);
            }

        }
        */
    }

    private void updateRecyclerView() {

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(db.getAllContactsAsList());
        binding.output.setHasFixedSize(true);
        binding.output.setLayoutManager(new LinearLayoutManager(this));
        binding.output.setAdapter(adapter);

    }

    class DefaultClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            /*
             * When the "Change" buttons are clicked, inform the controller of an input field
             * change, so that the Model(s) can be updated accordingly.
             */

            String tag = view.getTag().toString();

            if (tag.equals("addMemoBtn")) {
                String newText = binding.memoInput.getText().toString();
                controller.changeElementRecycler(newText, db);
                updateRecyclerView();
            }

        }

    }

}