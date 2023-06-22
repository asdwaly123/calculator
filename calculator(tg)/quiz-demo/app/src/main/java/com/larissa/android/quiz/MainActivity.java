package com.larissa.android.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.larissa.android.quiz.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private MenuProvider menu=new MenuProvider() {
        @Override
        public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
            menu.clear();
            menuInflater.inflate(R.menu.menu_log,menu);
        }

        @Override
        public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
            NavHostFragment hostFragment=(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            NavController controller=hostFragment.getNavController();
            NavGraph graph=controller.getNavInflater().inflate(R.navigation.nav_graph);
            graph.setStartDestination(R.id.logFragment);
            controller.setGraph(graph);
            return true;
        }
    };
    //Calculator calculator;
    private CalculatorViewModel calculator_viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculator_viewModel =new ViewModelProvider(this).get(CalculatorViewModel.class);
        NavHostFragment hostFragment=(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        NavController controller=hostFragment.getNavController();
        NavGraph graph=controller.getNavInflater().inflate(R.navigation.nav_graph);
        graph.setStartDestination(R.id.calculatorFragment);
        controller.setGraph(graph);
        addMenuProvider(menu,this);
        getSupportActionBar().setTitle("Calculator");  //设置标题
    }

}