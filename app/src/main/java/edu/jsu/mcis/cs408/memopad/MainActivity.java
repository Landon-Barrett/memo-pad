package edu.jsu.mcis.cs408.memopad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.beans.PropertyChangeEvent;
import java.util.List;

import edu.jsu.mcis.cs408.memopad.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements AbstractView {

    private ActivityMainBinding binding;
    private int selectedMemo;
    private DatabaseHandler db;
    private MemoPadController controller;
    private final MemoPadItemClickHandler itemClick = new MemoPadItemClickHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        db = new DatabaseHandler(this, null, null, 1);
        updateRecyclerView(db.getAllMemosAsList());


        MemoPadModel model = new MemoPadModel();
        controller = new MemoPadController(model);

        controller.addView(this);

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

        String propertyName = evt.getPropertyName();
        List<Memo> propertyValue = (List) evt.getNewValue();

        if ( propertyName.equals(MemoPadController.ELEMENT_RECYCLER_PROPERTY) ) {
            updateRecyclerView(propertyValue);
        }

    }

    public MemoPadItemClickHandler getItemClick() { return itemClick; }

    private void updateRecyclerView(List<Memo> allMemos) {

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, allMemos);
        binding.output.setHasFixedSize(true);
        binding.output.setLayoutManager(new LinearLayoutManager(this));
        binding.output.setAdapter(adapter);

    }

    class DefaultClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String tag = view.getTag().toString();

            if (tag.equals("addMemoBtn")) {
                String newText = binding.memoInput.getText().toString();
                controller.changeElementRecycler(newText, db);
            }
            else if (tag.equals("deleteMemoBtn")) {
                controller.changeElementRecyclerDelete(selectedMemo, db);
            }
        }

    }

     private class MemoPadItemClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int position = binding.output.getChildLayoutPosition(v);
            RecyclerViewAdapter adapter = (RecyclerViewAdapter)binding.output.getAdapter();
            if (adapter != null) {
                Memo memo = adapter.getItem(position);
                int id = memo.getId();
                selectedMemo = id;
                Toast.makeText(v.getContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
            }
        }
    }

}