package card;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

public class Card_interface{
    private final int spades = 0;
    private final int love = 1;
    private final int square = 2;
    private final int plum_blossom = 3;
    private final int background = 4;
    private final int card = 5;
    
    protected final int row = 0;
    protected final int column = 1;
    protected final int positive = 0;
    protected final int negative = 1;
    //--------------------------------------------------------------------------------------------------------------------------------------------------
    protected Paper_card pc;
    private Card_png[] png = 
    {new Card_png("spades") , new Card_png("love") , new Card_png("square") , new Card_png("plum_blossom") , new Card_png("background") , new Card_png("card")};
    
    private int[] jpanel_size = new int [2];
    public int[] papercard_size = new int[2];
    private int[] position_gap = new int[2];
    
    protected int[][] p_coordinate = new int[13][2];
    protected int[][] t_coordinate = new int[13][2];
    
    protected Map<Integer , Float> papercard_gap = new HashMap();
    private float[] top_papercard_gap = new float[2];
    
    protected int[][] position_quantity = {{7 , 6 , 5 , 4 , 3 , 2 , 1 , 0 , 0 , 0 , 0 , 0 , 24} , {6 , 5 , 4 , 3 , 2 , 1 , 0 , 0 , 0 , 0 , 0 , 0 , 23}};
    
    public float[] mouse_point = new float[2];
    
    public Card_interface(){
        pc = new Paper_card();
    }
    
    public void position_setup(int jpanel_size_row , int jpanel_size_column){
//        各種尺寸設定
        if(jpanel_size[row] != jpanel_size_row || jpanel_size[column] != jpanel_size_column){
            
            jpanel_size[row] = jpanel_size_row;
            jpanel_size[column] = jpanel_size_column;
            
            papercard_size[row] = jpanel_size[row] / 10;
            papercard_size[column] = jpanel_size[column] / 5;
            
            position_gap[row] = papercard_size[row] / 7;
            position_gap[column] = papercard_size[column] / 12;
            
            papercard_location_settings();
            
            int initial = 100;
            card_spacing(initial);
        }
    }
    
    public void papercard_location_settings(){
//        位置1~13座標設定
        for(int i = 0 ; i < 7 ; i ++){
            p_coordinate[i][row] =
                    jpanel_size[row] - ((i + 1) * papercard_size[row]) - (i + 1) * ((jpanel_size[row] - (7 * papercard_size[row])) / 8);
            p_coordinate[i][column] = 5 * position_gap[column] + papercard_size[column];
        }
        int count = 3;
        for(int i = 7 ; i < 11 ; i ++ , count += 3){
            p_coordinate[i][row] = jpanel_size[row] - (count * position_gap[row]) - (i - 6) * papercard_size[row];
            p_coordinate[i][column] = position_gap[column];
        }
        p_coordinate[11][row] = (8 * position_gap[row]) + papercard_size[row];
        p_coordinate[11][column] = position_gap[column];
        p_coordinate[12][row] = 3 * position_gap[row];
        p_coordinate[12][column] = position_gap[column];
    }
    
    public void card_spacing(int position){ 
//        每個位置，卡片間的間距設定
        int initial = 100;
        if(position == initial){
            top_papercard_gap[row] = papercard_size[column] / 120;
            top_papercard_gap[column] = papercard_size[column] / 100;
            for(int i = 0 ; i < 7 ; i ++){
                papercard_gap.put(i , (float)(papercard_size[column] / 4));
            }
        }else{
            if(t_coordinate[position][column] + papercard_size[column] >= jpanel_size[column]){
                float gap = (t_coordinate[position][column] - p_coordinate[position][column]) / (position_quantity[positive][position] - 1);
                papercard_gap.put(position , gap);
            }
        }
    }
    
    public void show_background(JPanel jpanel , Graphics g){
        g.setColor(Color.darkGray);
        g.fillRect(0 , 0 , jpanel_size[row] , jpanel_size[column]);
        for(int i = 0 ; i < 13 ; i ++){
            if(i == 11){
                continue;
            }else if(position_quantity[positive][i] == 0){
                g.drawImage(png[background].get(background) , p_coordinate[i][row] , p_coordinate[i][column] , papercard_size[row] , papercard_size[column] , jpanel);
            }
        }
    }
    
    public void show_picture(Graphics g , Mouse_keyboard mk , JPanel jpanel) {
        
        int papercard_count = 0;
        
        for(int position = 0 ; position < 13 ; position ++){
            papercard_count = position_show(g , position , papercard_count , jpanel);
        }
        
        if(mk.mouse_status() == mk.move){
            move_card_show(g , jpanel);
        }
    }
    
    public void move_card_show(Graphics g , JPanel jpanel) throws java.lang.IndexOutOfBoundsException {
        int quantity = pc.move_card_number.size();
        
        if(quantity == 1){
            g.drawImage(png[pc.move_card_type.get(0)].get(pc.move_card_number.get(0)) , (int)(mouse_point[row] - papercard_size[row] / 2) , (int)(mouse_point[column] - papercard_size[column] / 2) , papercard_size[row] , papercard_size[column] , jpanel);
        }else{
            g.drawImage(png[pc.move_card_type.get(quantity - 1)].get(pc.move_card_number.get(quantity - 1)) , (int)(mouse_point[row] - papercard_size[row] / 2) , (int)(mouse_point[column] - papercard_gap.get(pc.move_card_position) / 2) , papercard_size[row] , papercard_size[column] , jpanel);
            for(int i = 1 ; i < quantity ; i ++){
                g.drawImage(png[pc.move_card_type.get(quantity - 1 - i)].get(pc.move_card_number.get(quantity - 1 - i)) , (int)(mouse_point[row] - papercard_size[row] / 2) , (int)(mouse_point[column] - papercard_gap.get(pc.move_card_position) / 2 + i * papercard_gap.get(pc.move_card_position)) , papercard_size[row] , papercard_size[column] , jpanel);
            }
        }
    }
    
    public int position_show(Graphics g , int position , int count , JPanel jpanel){
        int card_count = count;
        
        for(int i = 0 ; i < position_quantity[positive][position] ; i ++){
            if(position == 12){
                g.drawImage(png[card].get(card) ,(int)(p_coordinate[position][row] + (i * top_papercard_gap[row])) , (int)(p_coordinate[position][column] + (i * top_papercard_gap[column])) , papercard_size[row] , papercard_size[column] , jpanel);
            }else if(position > 6){
                //只實現最上層2張卡牌
                if(i > position_quantity[positive][position] - 2){
                    g.drawImage(png[pc.getT(card_count)].get(pc.getN(card_count)) , p_coordinate[position][row] , p_coordinate[position][column] , papercard_size[row] , papercard_size[column] , jpanel);
                }
            }else{
                if(i < position_quantity[negative][position]){
                    g.drawImage(png[card].get(card) , p_coordinate[position][row] , (int)(p_coordinate[position][column] + i * papercard_gap.get(position)) , papercard_size[row] , papercard_size[column] , jpanel);
                }else{
                    g.drawImage(png[pc.getT(card_count)].get(pc.getN(card_count)) , p_coordinate[position][row] , (int)(p_coordinate[position][column] + i * papercard_gap.get(position)) , papercard_size[row] , papercard_size[column] , jpanel);
                }
            }
            card_count ++;
        }
        if(position == 12){
            t_coordinate[position][row] = (int)(p_coordinate[position][row] + (position_quantity[positive][position] - 1) * top_papercard_gap[row]);
            t_coordinate[position][column] = (int)(p_coordinate[position][column] + (position_quantity[positive][position] - 1) * top_papercard_gap[column]);
        }else if(position > 6){
            t_coordinate[position][row] = p_coordinate[position][row];
            t_coordinate[position][column] = p_coordinate[position][column];
        }else{
            t_coordinate[position][row] = p_coordinate[position][row];
            t_coordinate[position][column] = (int)(p_coordinate[position][column] + (position_quantity[positive][position] - 1) * papercard_gap.get(position));
        }
        return card_count;
    }
    
    public int catch_coordinate(int position){
        int count = -1;
        for(int i = 0 ; i <= position ; i ++){
            count += position_quantity[positive][i];
        }
        return count;
    }
    
    public void position_quantity_change(int start_position , int end_position){
        position_quantity_remove(start_position);
        judgment_positive_and_negative(start_position);
        position_quantity_add(end_position);
    }
    
    public void position_quantity_add(int end_position){
        position_quantity[positive][end_position] += pc.move_card_number.size();
    }
    
    public void position_quantity_remove(int start_position){
        position_quantity[positive][start_position] -= pc.move_card_number.size();
    }
    
    public void judgment_positive_and_negative(int start_position){
        if(position_quantity[positive][start_position] == position_quantity[negative][start_position]){
            if(position_quantity[negative][start_position] != 0){
                position_quantity[negative][start_position] --;
            }
        }
    }
}
