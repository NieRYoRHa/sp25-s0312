package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.Collection;

import static utils.Utils.TOP_14337_WORDS_FILE;
import static utils.Utils.TOTAL_COUNTS_FILE;

public class HistoryHandler extends NgordnetQueryHandler {
    public String handle(NgordnetQuery q){
        System.out.println("Got query that looks like:");
        System.out.println("Words: " + q.words());
        System.out.println("Start Year: " + q.startYear());
        System.out.println("End Year: " + q.endYear());

        NGramMap ngm = new NGramMap(TOP_14337_WORDS_FILE, TOTAL_COUNTS_FILE);
        String str= q.words().toString();
        String[] splitLine =str.substring(1,str.length()-1).replaceAll("\\s+","").split(",");
        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for(String s:splitLine){
            labels.add(s);
            lts.add(ngm.weightHistory(s, q.startYear(), q.endYear()));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
