package com.example.sportjournal.ui.sections;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportjournal.R;
import com.example.sportjournal.SectionActivity;
import com.example.sportjournal.db.Section;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnTouchListener {
    private HomeViewModel homeViewModel;
    View root;
    private EditText serch;
    private DatabaseReference section;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listSection;
    private List<Section> listTemp;
    float fromPosition, toPosition;
    ViewFlipper flipper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_sections, container, false);
        initialization();
        getDataBase();
        setOnClickItem();
        return root;
    }

    //Инициализация компонентов
    private void initialization() {
        // Устанавливаем listener касаний, для последующего перехвата жестов
        LinearLayout mainLayout = (LinearLayout) root.findViewById(R.id.main_layout);
        mainLayout.setOnTouchListener(this);

        // Получаем объект ViewFlipper
        flipper = (ViewFlipper) root.findViewById(R.id.flipper);

        // Создаем View и добавляем их в уже готовый flipper
        LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int layouts[] = new int[]{ R.layout.all_section, R.layout.like_section};
        for (int layout : layouts)
            flipper.addView(inflater.inflate(layout, null));


        //Поиск элементов по id
        listView = (ListView) flipper.findViewById(R.id.sectionList);
        listSection = new ArrayList<>();
        listTemp = new ArrayList<>();
        adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, listSection);
        listView.setAdapter(adapter);
        section = FirebaseDatabase.getInstance().getReference(Section.KEY);
        serch = (EditText) flipper.findViewById(R.id.Section_serch);
        serch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    public boolean onTouch(View view, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent. ACTION_DOWN: // Пользователь нажал на экран, т.е. начало движения
                // fromPosition - координата по оси X начала выполнения операции
                fromPosition = event.getX();
                break;
            case MotionEvent.ACTION_UP: // Пользователь отпустил экран, т.е. окончание движения
                toPosition = event.getX();
                if (fromPosition > toPosition)
                    flipper.showNext();
                else if (fromPosition < toPosition)
                    flipper.showPrevious();
            default:
                break;
        }
        return true;
    }

    //Получение данных из БД
    private void getDataBase() {
        section.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listSection.size() > 0)
                    listSection.clear();
                if (listTemp.size() > 0)
                    listTemp.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Section s = ds.getValue(Section.class);
                    assert s != null;
                    listSection.add(s.name);
                    listTemp.add(s);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setOnClickItem(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Section section = listTemp.get(i);
                openSectionActivity(section);
            }
        });
    }

    private  void openSectionActivity(Section s){
        Intent intent = new Intent(this.getActivity(), SectionActivity.class);
        intent.putExtra("id", s.id);
        intent.putExtra("name", s.name);
        intent.putExtra("day", s.day);
        intent.putExtra("time", s.time);
        intent.putExtra("trainer", s.trainer);
        startActivity(intent);
    }
}