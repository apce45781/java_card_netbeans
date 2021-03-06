package card;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class json {
    
    public List[][] get() {
        String path = "Record.json";
        try(BufferedReader Input = new BufferedReader(new InputStreamReader(new FileInputStream(path)))){
            String datas = "";
            String catch_data = "";
            while((catch_data = Input.readLine()) != null){
                datas += catch_data;
            }
            JSONObject jo = new JSONObject(datas);
            JSONArray position_quantity = jo.getJSONArray("position_quantity");
            JSONArray paper_card = jo.getJSONArray("paper_card");
            JSONArray paper_card_type = paper_card.getJSONArray(0);
            JSONArray paper_card_number = paper_card.getJSONArray(1);
            JSONArray position_quantity_positive = position_quantity.getJSONArray(0);
            JSONArray position_quantity_negative = position_quantity.getJSONArray(1);
            
            List<Integer>[][] papercard = new ArrayList[2][2];
            for(int i = 0 ; i < 2 ; i ++){
                for(int k = 0 ; k < 2 ; k ++){
                    papercard[i][k] = new ArrayList<Integer>();
                }
            }
            
            int position_quantity_codename = 0;
            int paper_card_codename = 1;
            for(int i = 0 ; i < position_quantity_positive.length() ; i ++){
                papercard[position_quantity_codename][0].add(position_quantity_positive.getInt(i));
                papercard[position_quantity_codename][1].add(position_quantity_negative.getInt(i));
            }
            for(int i = 0 ; i < paper_card_type.length() ; i ++){
                papercard[paper_card_codename][0].add(paper_card_type.getInt(i));
                papercard[paper_card_codename][1].add(paper_card_number.getInt(i));
            }
            
            return papercard;
            
        }catch(FileNotFoundException e){
            
        }catch(IOException ioe){
            
        }
        List<Integer>[][] return_data = new ArrayList[1][1];
        return_data[0][0] = new ArrayList();
        return_data[0][0].add(-1);
        return return_data;
    }
    
    public void set(Card_interface ci) {
        try(BufferedWriter writeout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Record.json")))){
            
            JSONObject jo = new JSONObject();
            int[][] position_quantity = getPosition_quantity(ci);
            if(position_quantity[0][0] != -1){
                jo.put("position_quantity" , position_quantity);
                jo.put("paper_card" , getPaper_card(ci));
                writeout.write(jo.toString());
                writeout.flush();
            }
            
        }catch(FileNotFoundException ffe){
            
        }catch(IOException ioe){
            
        }
    }
    
    public int[][] getPaper_card(Card_interface ci){
        int type = 0;
        int number = 1;
        int count = 0;
        int[][] paper_card = new int[2][52];
        for(int i = 0 ; i < 13 ; i ++){
            for(int k = 0 ; k < ci.pc.get_card_quantity(i) ; k ++ , count ++){
                paper_card[type][count] = ci.pc.getT(i , k + 1);
                paper_card[number][count] = ci.pc.getN(i , k + 1);
            }
        }
        return paper_card;
    }
    
    public int[][] getPosition_quantity(Card_interface ci){
        int positive = 0;
        int negative = 1;
        int position_quantity[][] = new int[2][13];
        for(int i = 0 ; i < 13 ; i ++){
            position_quantity[positive][i] = ci.pc.get_positive_quantity(i);
            position_quantity[negative][i] = ci.pc.get_negative_quantity(i);
        }
        if(Record_switch(ci , position_quantity)){
            return position_quantity;
        }else{
            return new int[][]{{-1}};
        }
    }
    
    public boolean Record_switch(Card_interface ci , int[][] position_quantity){
        int positive = 0;
        int negative = 1;
        boolean Record_switch[] = new boolean[13];
        for(int i = 0 ; i < 13 ; i ++){
            if(position_quantity[positive][i] != ci.pc.get_positive_quantity(i) &&
                    position_quantity[negative][i] != ci.pc.get_negative_quantity(i)){
                Record_switch[i] = true;
            }
        }
        if(Record_switch[0] && Record_switch[1] && Record_switch[2] && Record_switch[3] && Record_switch[4] &&
                Record_switch[5] && Record_switch[6] && Record_switch[7] && Record_switch[8] && Record_switch[9] &&
                Record_switch[10] && Record_switch[11] && Record_switch[12]){
            return true;
        }else{
            return false;
        }
    }
}