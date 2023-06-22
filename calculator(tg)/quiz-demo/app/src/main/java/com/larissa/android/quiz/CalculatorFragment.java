package com.larissa.android.quiz;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.larissa.android.quiz.databinding.ActivityMainBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatorFragment extends Fragment {

    private String answer= "";
    private String equation = "";
    private Calculator calculator;
    private TextView euation_text;
    private TextView answer_text;

    private CalculatorViewModel calculator_viewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalculatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalculatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculatorFragment newInstance(String param1, String param2) {
        CalculatorFragment fragment = new CalculatorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
        calculator_viewModel = new ViewModelProvider(requireActivity()).get(CalculatorViewModel.class);
        calculator = new Calculator();
        view.findViewById(R.id.btn_0).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.btn_1).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.btn_2).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.btn_3).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.btn_4).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.btn_5).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.btn_6).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.btn_7).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.btn_8).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.btn_9).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.add).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.sub).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.mul).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.div).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.point).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.delete).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.C).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.equal).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.l_bracket).setOnClickListener(v -> btnClick(v));
        view.findViewById(R.id.r_bracket).setOnClickListener(v -> btnClick(v));


        euation_text = view.findViewById(R.id.equation);
        answer_text = view.findViewById(R.id.answer);   //对文本进行绑定
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {  //如果是竖直就只对竖直的按键设置监听器，水平时需要对额外的按键设置监听器
            view.findViewById(R.id.sin).setOnClickListener(v -> btnClick(v));
            view.findViewById(R.id.cos).setOnClickListener(v -> btnClick(v));
            view.findViewById(R.id.tan).setOnClickListener(v -> btnClick(v));
            view.findViewById(R.id.x_2).setOnClickListener(v -> btnClick(v));
            view.findViewById(R.id.x_3).setOnClickListener(v -> btnClick(v));
            view.findViewById(R.id.x_y).setOnClickListener(v -> btnClick(v));
            view.findViewById(R.id.jiecheng).setOnClickListener(v -> btnClick(v));
            view.findViewById(R.id.sqrt).setOnClickListener(v -> btnClick(v));
            view.findViewById(R.id.ln).setOnClickListener(v -> btnClick(v));
            view.findViewById(R.id.one_x).setOnClickListener(v -> btnClick(v));
            view.findViewById(R.id.pi).setOnClickListener(v -> btnClick(v));
            view.findViewById(R.id.e).setOnClickListener(v -> btnClick(v));
        }

        equation = calculator_viewModel.my_str;
        answer = calculator_viewModel.ans;
        euation_text.setText(equation);
        answer_text.setText(answer);
    }
    private void btnClick(View v) {
        switch(v.getId()) {
            case R.id.btn_0:equation = equation + "0";
                Log.d("ail","anxia0");break;
            case R.id.btn_1:equation = equation + "1";break;
            case R.id.btn_2:equation = equation + "2";break;
            case R.id.btn_3:equation = equation + "3";break;
            case R.id.btn_4:equation = equation + "4";break;
            case R.id.btn_5:equation = equation + "5";break;
            case R.id.btn_6:equation = equation + "6";break;
            case R.id.btn_7:equation = equation + "7";break;
            case R.id.btn_8:equation = equation + "8";break;
            case R.id.btn_9:equation = equation + "9";break;
            case R.id.add:equation = equation + "+";break;
            case R.id.sub:equation = equation + "-";break;
            case R.id.mul:equation = equation + "*";break;
            case R.id.div:equation = equation + "/";break;
            case R.id.point: if(equation.charAt(equation.length()-1)>='0'&&equation.charAt(equation.length()-1)<='9')equation = equation + ".";break;
            //对于小数点的输出，我们给个判断，如果前面输入的内容不是数字，则小数点输不出来
            case R.id.l_bracket:equation = equation + "(";break;
            case R.id.r_bracket:equation = equation + ")";break;
            case R.id.C:equation =""; answer = "";break;
            case R.id.equal: answer = calculator.compute(equation);
                           calculator_viewModel.my_str = equation;
                           calculator_viewModel.ans = answer;
                           calculator_viewModel.save_log();
            break;   //如果按下等于好键则计算出结果
            case R.id.delete:if(equation.length() != 0) {
                if(equation.length()>=3 && equation.charAt(equation.length()-1) == '(') {
                    if(equation.charAt(equation.length()-2) == '√' )     //先判断是不是根号运算，再判断其他特殊运算
                        equation = equation.substring(0,equation.length()-2);
                    else {
                        switch (equation.charAt(equation.length() - 3)) {
                            case 'i':
                            case 'o':
                            case 'a':equation = equation.substring(0,equation.length()-4);break;  //三角函数的撤销
                            case 'l':equation = equation.substring(0,equation.length()-3);break;  //ln的撤销
                            default:equation = equation.substring(0,equation.length()-1);  //其余情况就把左括号删除
                        }
                    }
                }
                else {
                    equation = equation.substring(0,equation.length()-1);
                }
            }
                break;

            case R.id.sin:equation = equation + "sin(";break;
            case R.id.cos:equation = equation + "cos(";break;
            case R.id.tan:equation = equation + "tan(";break;
            case R.id.x_2:equation = equation + "^(2)";break;
            case R.id.x_3:equation = equation + "^(3)";break;
            case R.id.x_y:equation = equation + "^(";break;
            case R.id.jiecheng:equation = equation + "!";break;
            case R.id.sqrt:equation = equation + "√(";break;
            case R.id.ln:equation = equation + "ln(";break;
            case R.id.one_x:equation = equation + "^(-1)";break;
            case R.id.pi:equation = equation + "π";break;
            case R.id.e:equation = equation + "e";break;
        }
        calculator_viewModel.my_str = equation;

        euation_text.setText(equation);
        answer_text.setText(answer);   //对按键按下后的文本进行更新
    }

/*
    @Override
    public void onSaveInstanceState(Bundle outState) {  //实现onSaveInstanceState()函数，在翻转屏幕时对数据进行保存
        super.onSaveInstanceState(outState);
        outState.putString("equation",equation);    //保存currentid数据
        outState.putString("answer",answer);            //保存count数据
    }
*/
}