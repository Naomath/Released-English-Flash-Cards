package com.naoto.dennnoukishidann.word_book.mainFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogCheckSolveFragment;
import com.naoto.dennnoukishidann.word_book.extendSugar.Word;
import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.processings.GettingPieData;
import com.naoto.dennnoukishidann.word_book.processings.MakeString;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    View view;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        settingListener();
        settingPieChart();
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.check_button:
                    CustomDialogCheckSolveFragment dialogCheckFragment = new CustomDialogCheckSolveFragment();
                    dialogCheckFragment.show(getFragmentManager(), "check");
                    break;

            }
        }

    }

    public void settingListener() {
        //listenerの設定
        view.findViewById(R.id.check_button).setOnClickListener(this);
    }

    public void settingPieChart() {
        //pagerの設定
        TextView text_explain = (TextView) view.findViewById(R.id.text_explain);
        text_explain.setText("単語の記憶度");
        //piechartの設定
        PieChart pieChart = (PieChart) view.findViewById(R.id.pie_chart);
        List<Word> all_words = Word.listAll(Word.class);
        List<String> messages = Arrays.asList(String.valueOf(all_words.size()),"単語");
        pieChart.setCenterText(MakeString.makeString(messages));
        pieChart.setCenterTextSize(20f);
        PieData pieData = GettingPieData.gettingPieData1();
        pieChart.setData(pieData);
    }

}
