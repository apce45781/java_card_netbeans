package card;

public class Card_interface {
    private final int row = 0;
    private final int column = 1;
    
    private int[] panel_size = new int [2];
    public int[] papercard_size = new int[2];
    private int[] interval_size = new int[2];
    private int[][] card_position = new int[13][2];
    
    
    public Card_interface(int panel_size_row , int panel_size_column){
        setSize(panel_size_row , panel_size_column);
        card_position_setup();
    }
    
    public void setSize(int panel_size_row , int panel_size_column){
        panel_size[row] = panel_size_row;
        panel_size[column] = panel_size_column;
        papercard_size[row] = panel_size_row / 10;
        papercard_size[column] = panel_size_column / 5;
        interval_size[row] = panel_size_row / 7;
        interval_size[column] = panel_size_column / 12;
    }
    
    public void card_position_setup(){
        
        if(papercard_size[row] != panel_size[row] / 10 || papercard_size[column] != panel_size[column] / 5){
            
            papercard_size[row] = panel_size[row] / 10;
            papercard_size[column] = panel_size[column] / 5;
            
            interval_size[row] = panel_size[row] / 7;
            interval_size[column] = panel_size[column] / 12;
            
            card_position();
            
            //設定單一座標卡片放置的間距
            card_spacing(100 , 0);
        }
        
        //卡片背景設置
        card_back_ground(row , column , jp);
        
        /*
        1.判斷位置，排列編號
        2.依序將紙牌號碼以及type實現至畫面上        */
        start_licensing(col , jp);
    }
    
    public void card_position(){
        /*
        底部位置 => 卡片張數
        0 => 7 , 1 => 6 , 2 => 5 , 3 => 4 , 4 => 3 , 5 => 2 , 6 => 1
        四色牌組位置 => 卡片張數
        7 => 0 , 8 => 0 , 9 => 0 , 10 => 0
        發牌位置 => 卡片張數
        11 => 0 , 12 => 24        */
        for(int i = 0 ; i < 7 ; i ++){
            card_position[i][row] = panel_size[row] - ((i + 1) * papercard_size[row]) - (i + 1) * ((panel_size[row] - (7 * papercard_size[row])) / 8);
            card_position[i][column] = 5 * interval_size[column] + papercard_size[column];
        }
        int count = 3;
        for(int i = 7 ; i < 11 ; i ++ , count += 3){
            
            card_position[i][row] = panel_size[row] - (count * interval_size[row]) - (i - 6) * papercard_size[row];
        }
        card_position[7][row] = panel_size[row] - (3 * interval_size[row]) - 1 * papercard_size[row];
        card_position[7][column] = interval_size[column];
        card_position[8][row] = panel_size[row] - (6 * interval_size[row]) - 2 * papercard_size[row];
        card_position[8][column] = interval_size[column];
        card_position[9][row] = panel_size[row] - (9 * interval_size[row]) - 3 * papercard_size[row];
        card_position[9][column] = interval_size[column];
        card_position[10][row] = panel_size[row] - (12 * interval_size[row]) - 4 * papercard_size[row];
        card_position[10][column] = interval_size[column];
        card_position[11][row] = (8 * interval_size[row]) + papercard_size[row];
        card_position[11][column] = interval_size[column];
        card_position[12][row] = 3 * interval_size[row];
        card_position[12][column] = interval_size[column];
    }
    
}
