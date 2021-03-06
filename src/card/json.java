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
        int[][] position_quantity = getPosition_quantity(ci);
        System.out.println(position_quantity[0][0]);
        if(position_quantity[0][0] != -1){
            try(BufferedWriter writeout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Record.json")))){
                
                JSONObject jo = new JSONObject();
                jo.put("position_quantity" , position_quantity);
                jo.put("paper_card" , getPaper_card(ci));
                writeout.write(jo.toString());
                writeout.flush();
            
            }catch(FileNotFoundException ffe){

            }catch(IOException ioe){
                
            }
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
        
        boolean[] Record_switch_1 = new boolean[13];
        boolean[] Record_switch_2 = new boolean[4];
        
        int[][] initial_data = {{7 , 6 , 5 , 4 , 3 , 2 , 1 , 0 , 0 , 0 , 0 , 0 , 24} , {6 , 5 , 4 , 3 , 2 , 1 , 0 , 0 , 0 , 0 , 0 , 0 , 23}};
        int[][] data = new int[2][13];
        
        for(int i = 0 ; i < 13 ; i ++){
            data[positive][i] = ci.pc.get_positive_quantity(i);
            data[negative][i] = ci.pc.get_negative_quantity(i);
            
            if(initial_data[positive][i] != ci.pc.get_positive_quantity(i) &&
                    initial_data[negative][i] != ci.pc.get_negative_quantity(i)){
                Record_switch_1[i] = true;
            }
            if(i > 8){
                if(ci.pc.get_positive_quantity(i - 9) == 13){
                    Record_switch_2[i - 9] = true;
                }
            }
        }
        if(Record_switch_1[0] || Record_switch_1[1] || Record_switch_1[2] ||
                Record_switch_1[3] || Record_switch_1[4] || Record_switch_1[5] ||
                Record_switch_1[6] || Record_switch_1[7] || Record_switch_1[8] ||
                Record_switch_1[9] || Record_switch_1[10] || Record_switch_1[11] ||
                Record_switch_1[12]){
            return data;
        }else if(Record_switch_2[0] || Record_switch_2[1] || Record_switch_2[2] || Record_switch_2[3]){
            return data;
        }else{
            return new int[][]{{-1}};
        }
    }
}