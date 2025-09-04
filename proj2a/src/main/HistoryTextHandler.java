package main;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.ArrayList;
import java.util.Iterator;

public class HistoryTextHandler extends NgordnetQueryHandler {

    private NGramMap ngm;
    public HistoryTextHandler(NGramMap n){
        ngm=n;
    }

    @Override
    public String handle(NgordnetQuery q) {

        String str= q.words().toString();
        String[] splitLine =str.substring(1,str.length()-1).replaceAll("\\s+","").split(",");
        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        String response="";


        for(String s:splitLine){
            TimeSeries ts=ngm.weightHistory(s, q.startYear(), q.endYear());
            response+=s+": {";

            int count = 0;
            for(int i:ts.years()){
                if(count!=0){
                    response+=", ";
                }
                response += i + "=" + ts.get(i);
                count+=1;
            }
            response+="}"+"\n";
        }

        return response;
    }
}
