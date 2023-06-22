package com.larissa.android.quiz;

import androidx.lifecycle.ViewModel;


import java.util.Stack;

public class CalculatorViewModel extends ViewModel {
    public String my_str;
    public String ans;

    public String [] str_save;
    private int max_len=20;
    public int cur_len;
    public CalculatorViewModel(){
        my_str="";
        ans="";
        cur_len=0;
        str_save=new String[max_len];
    }
    public void save_log(){
        if(ans != "出错") {
            if (cur_len >= max_len) {
                max_len += max_len;
                str_save = new String[max_len];
            }
            str_save[cur_len++] = my_str + "=" + ans;
        }
    }
}
