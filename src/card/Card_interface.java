package card;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

public class Card_interface {
    private final int spades = 0;
    private final int love = 1;
    private final int square = 2;
    private final int plum_blossom = 3;
    private final int background = 4;
    private final int card = 5;
    
    private final int row = 0;
    private final int column = 1;
    private final int positive = 0;
    private final int negative = 1;
    //--------------------------------------------------------------------------------------------------------------------------------------------------
    private Paper_card paper_card;
    private Card_png[] png = 
    {new Card_png("spades") , new Card_png("love") , new Card_png("square") , new Card_png("plum_blossom") , new Card_png("background") , new Card_png("card")};
    
    private int[] jpanel_size = new int [2];
    public int[] papercard_size = new int[2];
    private int[] position_gap = new int[2];
    
    private int[][] p_coordinate = new int[13][2];
    private int[][] t_coordinate = new int[13][2];
    
    private Map<Integer , Float> papercard_gap = new HashMap();
    private float[] top_papercard_gap = new float[2];
    
    private int[][] position_quantity = new int[2][13];
    
    public Card_interface(){
        paper_card = new Paper_card();
        restart();
    }
    
    public void position_setup(int jpanel_size_row , int jpanel_size_column){
        if(jpanel_size[row] != jpanel_size_row || jpanel_size[column] != jpanel_size_column){
            
            jpanel_size[row] = jpanel_size_row;
            jpanel_size[column] = jpanel_size_column;
            
            papercard_size[row] = jpanel_size[row] / 10;
            papercard_size[column] = jpanel_size[column] / 5;
            
            position_gap[row] = papercard_size[row] / 7;
            position_gap[column] = papercard_size[column] / 12;
            
            papercard_location_settings();
            
            //設定單一座標卡片放置的間距
            int initial = 100;
            card_spacing(initial);
        }
    }
    
    public void papercard_location_settings(){
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
        
        int initial = 100;
        if(position == initial){
            top_papercard_gap[row] = papercard_size[column] / 120;
            top_papercard_gap[column] = papercard_size[column] / 100;
            for(int i = 0 ; i < 7 ; i ++){
                papercard_gap.put(i , (float)(papercard_size[column] / 4));
            }
        }/*else{
            if(Getmove_paper_position() < 7){
                if(card_position[Getmove_paper_position()][1] + (card_col / 4) * (positive[Getmove_paper_position()] - 1) + card_col > getheight){
                    card_spacing[1][Getmove_paper_position()] = (getheight - card_position[Getmove_paper_position()][1] - card_col) / (positive[Getmove_paper_position()] - 1);
                }else if(card_spacing[1][Getmove_paper_position()] != card_col / 4){
                    card_spacing[1][Getmove_paper_position()] = card_col / 4;
                }
            }
            if(position < 7){
                if(card_position[position][1] + (card_col / 4) * (positive[position] - 1) + card_col > getheight){
                    card_spacing[1][position] = (getheight - card_position[position][1] - card_col) / (positive[position] - 1);
                }else if(card_spacing[1][position] != card_col / 4){
                    card_spacing[1][position] = card_col / 4;
                }
            }
        }*/
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
    
    public void show_picture(Graphics g , JPanel jpanel){
        
        int papercard_count = 0;
        
        for(int position = 0 ; position < 13 ; position ++){
            papercard_count = show(g , position , papercard_count , jpanel);
        }
        
//        if(move_paper_switch == 2){
//            move_card(g , jp);
//        }
    }
    
    public int show(Graphics g , int position , int count , JPanel jpanel){
        int card_count = count;
        
        for(int i = 0 ; i < position_quantity[positive][position] ; i ++){
            if(position == 12){
                g.drawImage(png[card].get(card) ,(int)(p_coordinate[position][row] + (i * top_papercard_gap[row])) , (int)(p_coordinate[position][column] + (i * top_papercard_gap[column])) , papercard_size[row] , papercard_size[column] , jpanel);
            }else if(position > 6){
                //只實現最上層2張卡牌
                if(i > position_quantity[positive][position] - 2){
                    g.drawImage(png[paper_card.getT(card_count)].get(paper_card.getN(card_count)) , p_coordinate[position][row] , p_coordinate[position][column] , papercard_size[row] , papercard_size[column] , jpanel);
                }
            }else{
                if(i < position_quantity[negative][position]){
                    g.drawImage(png[card].get(card) , p_coordinate[position][row] , (int)(p_coordinate[position][column] + i * papercard_gap.get(position)) , papercard_size[row] , papercard_size[column] , jpanel);
                }else{
                    g.drawImage(png[paper_card.getT(card_count)].get(paper_card.getN(card_count)) , p_coordinate[position][row] , (int)(p_coordinate[position][column] + i * papercard_gap.get(position)) , papercard_size[row] , papercard_size[column] , jpanel);
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
    
    public void restart(){
        
        paper_card.shuffle();
        
        position_quantity[positive][0] = 7;
        position_quantity[positive][1] = 6;
        position_quantity[positive][2] = 5;
        position_quantity[positive][3] = 4;
        position_quantity[positive][4] = 3;
        position_quantity[positive][5] = 2;
        position_quantity[positive][6] = 1;
        position_quantity[positive][7] = 0;
        position_quantity[positive][8] = 0;
        position_quantity[positive][9] = 0;
        position_quantity[positive][10] = 0;
        position_quantity[positive][11] = 0;
        position_quantity[positive][12] = 24;
        
        position_quantity[negative][0] = 6;
        position_quantity[negative][1] = 5;
        position_quantity[negative][2] = 4;
        position_quantity[negative][3] = 3;
        position_quantity[negative][4] = 2;
        position_quantity[negative][5] = 1;
        position_quantity[negative][6] = 0;
        position_quantity[negative][7] = 0;
        position_quantity[negative][8] = 0;
        position_quantity[negative][9] = 0;
        position_quantity[negative][10] = 0;
        position_quantity[negative][11] = 0;
        position_quantity[negative][12] = 24;
        
        int initial = 100;
        card_spacing(initial);
        
//        move_card.clear();
//        move_type.clear();
//        fraction = 0;
    }
}
