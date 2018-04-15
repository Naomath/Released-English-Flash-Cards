package com.naoto.dennnoukishidann.word_book.processings;

import android.graphics.Color;

import com.naoto.dennnoukishidann.word_book.extendSugar.Word;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gotounaoto on 2018/01/30.
 */

public class GettingPieData {

    public static PieData gettingPieData1() {
        //piedata1を返す
        //ここからはセットするデータを引き出してセットする
        List<Word> word_resource = Word.listAll(Word.class);
        float number_all = 0f;
        float number_zero_twenty = 0f;
        float number_twenty_forty = 0f;
        float number_forty_sixty = 0f;
        float number_sixty_eighty = 0f;
        float number_eighty_hundred = 0f;
        //全て最初の以上を後の未満にする
        //最後は100も含めるので特例
        for (Word item : word_resource) {
            float proportion = 100f - item.getProportion();
            if (proportion >= 0.0 & proportion < 20.0) {
                if (item.getNumber_question() == 0) {
                    continue;
                }
                number_zero_twenty = number_zero_twenty + 1f;
            } else if (proportion >= 20.0 & proportion < 40.0) {
                number_twenty_forty = number_twenty_forty + 1f;
            } else if (proportion >= 40.0 & proportion < 60.0) {
                number_forty_sixty = number_sixty_eighty + 1f;
            } else if (proportion >= 60.0 & proportion < 80.0) {
                number_sixty_eighty = number_sixty_eighty + 1f;
            } else if (proportion >= 80.0 & proportion <= 100.0) {
                number_eighty_hundred = number_eighty_hundred + 1f;
            }
            number_all++;
        }
        if (number_all == 0) {
            return null;
        }
        float percentage_zero_twenty = number_zero_twenty / number_all * 100;
        float percentage_twenty_forty = number_twenty_forty / number_all * 100;
        float percentage_forty_sixty = number_forty_sixty / number_all * 100;
        float percentage_sixty_eighty = number_sixty_eighty / number_all * 100;
        float percentage_eighty_hundred = number_eighty_hundred / number_all * 100;
        List<Float> values = Arrays.asList(percentage_zero_twenty, percentage_twenty_forty, percentage_forty_sixty,
                percentage_sixty_eighty, percentage_eighty_hundred);
        List<PieEntry> entries = new ArrayList<>();
        List<String> labels = Arrays.asList("全然", "少し", "まぁまぁ", "ほぼ", "完璧！");
        for (int i = 0; i < 5; i++) {
            if (values.get(i) == 0f) {
                continue;
            }
            entries.add(new PieEntry(values.get(i), labels.get(i)));
        }
        PieDataSet pieDataSet = new PieDataSet(entries, "記憶度");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setDrawValues(true);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.WHITE);
        return pieData;
    }
}
