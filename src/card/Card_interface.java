package card;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

public class Card_interface{
    
    public final int incompatible = -1;
    
    public final int spades = 0;
    public final int love = 1;
    public final int square = 2;
    public final int plum_blossom = 3;
    public final int background = 4;
    public final int card = 5;
    
    public final int row = 0;
    public final int column = 1;
    //--------------------------------------------------------------------------------------------------------------------------------------------------
    protected Paper_card pc;
    private Card_png[] png = 
    {new Card_png("spades") , new Card_png("love") , new Card_png("square") , new Card_png("plum_blossom") , new Card_png("background") , new Card_png("card")};
    
    private int[] jpanel_size = new int [2];
    private int[] papercard_size = new int[2];
    private int[] position_gap = new int[2];
    
    private int[][] p_coordinate = new int[13][2];
    private int[][] t_coordinate = new int[13][2];
    
    private Map<Integer , Float> papercard_gap = new HashMap();
    private float[] top_papercard_gap = new float[2];
    
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
            
            int initial = -1;
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

        if(position == pc.initial){
            top_papercard_gap[row] = papercard_size[column] / 120;
            top_papercard_gap[column] = papercard_size[column] / 100;
            for(int i = 0 ; i < 7 ; i ++){
                papercard_gap.put(i , (float)(papercard_size[column] / 4));
            }
        }else{
            if(t_coordinate[position][column] + papercard_size[column] >= jpanel_size[column]){
                float gap = (t_coordinate[position][column] - p_coordinate[position][column]) / (pc.get_positive_quantity(position) - 1);
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
            }else if(pc.get_positive_quantity(i) == 0){
                g.drawImage(png[background].get(background) ,
                        p_coordinate[i][row] ,
                        p_coordinate[i][column] ,
                        papercard_size[row] ,
                        papercard_size[column] ,
                        jpanel);
            }
        }
    }
    
    public void show_picture(Graphics g , Mouse_keyboard mk , JPanel jpanel) {
        
        for(int position = 0 ; position < 13 ; position ++){
            position_show(g , position , jpanel);
        }
        
        if(mk.get_mouse_status() == mk.move){
            move_card_show(g , jpanel);
        }
    }
    
    public void move_card_show(Graphics g , JPanel jpanel){
        int quantity = pc.get_move_quantity();
        
        if(quantity == 1){
            int point1 = (int)(mouse_point[row] - papercard_size[row] / 2);
            int point2 = (int)(mouse_point[column] - papercard_size[column] / 2);
            g.drawImage(png[pc.getMT(quantity)].get(pc.getMN(quantity)) ,
                    point1 ,
                    point2 ,
                    papercard_size[row] ,
                    papercard_size[column] ,
                    jpanel);
        }else{
            for(int i = 0 ; i < quantity ; i ++){
                int point1 = (int)(mouse_point[row] - papercard_size[row] / 2);
                int point2 = (int)(mouse_point[column] - papercard_gap.get(i) / 2 + (i * papercard_gap.get(i)));
                g.drawImage(png[pc.getMT(i + 1)].get(pc.getMN(i + 1)) ,
                        point1 ,
                        point2 ,
                        papercard_size[row] ,
                        papercard_size[column] ,
                        jpanel);
            }
        }
    }
    
    public void position_show(Graphics g , int position , JPanel jpanel){
        
        for(int i = 0 ; i < pc.get_positive_quantity(position) ; i ++){
            if(position == 12){
                int point1 = (int)(p_coordinate[position][row] + (i * top_papercard_gap[row]));
                int point2 = (int)(p_coordinate[position][column] + (i * top_papercard_gap[column]));
                g.drawImage(png[card].get(card) , point1 , point2 , papercard_size[row] , papercard_size[column] , jpanel);
            }else if(position > 6){
                //只實現最上層1張卡牌
                if(i > pc.get_positive_quantity(position) - 2){
                    
                    g.drawImage(png[pc.getT(position , pc.get_positive_quantity(position) - i)].get(pc.getN(position , pc.get_positive_quantity(position) - i)) ,
                            p_coordinate[position][row] ,
                            p_coordinate[position][column] ,
                            papercard_size[row] ,
                            papercard_size[column] , jpanel);
                }
            }else{
                if(i < pc.get_negative_quantity(position)){
                    g.drawImage(png[card].get(card) ,
                            p_coordinate[position][row] ,
                            (int)(p_coordinate[position][column] + i * papercard_gap.get(position)) ,
                            papercard_size[row] ,
                            papercard_size[column] ,
                            jpanel);
                }else{
                    g.drawImage(png[pc.getT(position , pc.get_positive_quantity(position) - i)].get(pc.getN(position , pc.get_positive_quantity(position) - i)) ,
                            p_coordinate[position][row] ,
                            (int)(p_coordinate[position][column] + i * papercard_gap.get(position)) ,
                            papercard_size[row] ,
                            papercard_size[column] ,
                            jpanel);
                }
            }
        }
        if(position == 12){
            t_coordinate[position][row] = (int)(p_coordinate[position][row] + (pc.get_positive_quantity(position) - 1) * top_papercard_gap[row]);
            t_coordinate[position][column] = (int)(p_coordinate[position][column] + (pc.get_positive_quantity(position) - 1) * top_papercard_gap[column]);
        }else if(position > 6){
            t_coordinate[position][row] = p_coordinate[position][row];
            t_coordinate[position][column] = p_coordinate[position][column];
        }else{
            t_coordinate[position][row] = p_coordinate[position][row];
            t_coordinate[position][column] = (int)(p_coordinate[position][column] + (pc.get_positive_quantity(position) - 1) * papercard_gap.get(position));
        }
    }
    
    public int getRow_card_size(){
        return papercard_size[row];
    }
    
    public int getColumn_card_size(){
        return papercard_size[column];
    }
    
    public int getRow_p_coordinate(int position){
        return p_coordinate[position][row];
    }
    
    public int getRow_t_coordinate(int position){
        return t_coordinate[position][row];
    }
    
    public int getColumn_t_coordinate(int position){
        return t_coordinate[position][column];
    }
    
    public float get_gap(int position){
        return papercard_gap.get(position);
    }
}
