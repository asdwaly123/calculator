package com.larissa.android.quiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private CalculatorViewModel viewmodel;

    public LogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogFragment newInstance(String param1, String param2) {
        LogFragment fragment = new LogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.cal_log_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewmodel=new ViewModelProvider(requireActivity()).get(CalculatorViewModel.class);
        QuestionItemAdapter adapter=new QuestionItemAdapter(viewmodel.str_save);
        recyclerView.setAdapter(adapter);
    }

    private class QuestionItemHolder extends RecyclerView.ViewHolder{
        private TextView equa_text;
        String equation;
        public QuestionItemHolder(View view){
            super(view);
            view.setOnClickListener(itemView->{
                NavDirections action=LogFragmentDirections.actionLogFragmentToCalculatorFragment();
                viewmodel.my_str=equation.substring(0,equation.indexOf("="));
                viewmodel.ans=equation.substring(equation.indexOf("=")+1);
                Navigation.findNavController(itemView).navigate(action);
            });
            equa_text=view.findViewById(R.id.cal_log);
        }
        public void bind(String equa_str){
            this.equation=equa_str;
            equa_text.setText(equa_str);
        }
    }
    private class QuestionItemAdapter extends RecyclerView.Adapter<QuestionItemHolder> {
        private String[] log_text;

        public QuestionItemAdapter(String[] log_text) {
            this.log_text = log_text;
        }

        @NonNull
        @Override
        public QuestionItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.log_item, parent, false);
            return new QuestionItemHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionItemHolder holder, int position) {
            String str = log_text[position];
            holder.bind(str);
        }

        @Override
        public int getItemCount() {
            return log_text.length;
        }
    }
}

