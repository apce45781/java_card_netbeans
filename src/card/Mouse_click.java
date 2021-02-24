package card;

public class Mouse_click{
    
    public boolean one_click(Card_interface ci , float mouse_X , float mouse_Y) {
//        只有位置12需要起作用
        if((mouse_X >= ci.t_coordinate[12][ci.row] && mouse_X <= ci.t_coordinate[12][ci.column] + ci.papercard_size[ci.row] ) && (mouse_Y >= ci.t_coordinate[12][1] && mouse_Y <= ci.t_coordinate[12][1] + ci.papercard_size[ci.column])){
            if(ci.position_quantity[ci.positive][12] == 0){
                ci.position_quantity[ci.positive][12] = ci.position_quantity[ci.positive][11];
                ci.position_quantity[ci.positive][11] = 0;
                return true;
            }else{
                ci.position_quantity[ci.positive][12] --;
                ci.position_quantity[ci.positive][11] ++;
                return false;
            }
        }else{
            return false;
        }
    }
    
    public int judgment_click_position(Card_interface ci , float mouse_X , float mouse_Y){
//        只有位置1、2、3、4、5、6、7、12需要起作用
        for(int i = 0 ; i < 12 ; i ++){
            if(i > 7 && i < 11){
                continue;
            }else if(ci.position_quantity[ci.positive][i] != 0 && (mouse_X >= ci.t_coordinate[i][ci.row] && mouse_X <= ci.t_coordinate[i][ci.row] + ci.papercard_size[ci.row]) && (mouse_Y >= ci.t_coordinate[i][ci.column] && mouse_Y <= ci.t_coordinate[i][ci.column] + ci.papercard_size[ci.column])){
                return i;
            }
        }
        int incompatible = 100;
        return incompatible;
    }
    
    public boolean two_click(int position){
//        
//        for(int i = 1 ; i < 13 ; i ++){
//            if(i > 7 && i < 12){
//                continue;
//            }else if(){
//                
//            }
//        }
//        //抓取"座標7~10"之紙牌個別號碼(套用至array[]需要-1)
//        int[] card_number = new int[4];
//        for(int number_1 = 7 ; number_1 < 11 ; number_1 ++) {
//            for(int number_2 = number_1 ; number_2 >= 0 ; number_2 --){
//                card_number[number_1 - 7] = card_number[number_1 - 7] + positive[number_2];
//            }
//        }
//        
//        //抓取變動紙牌之號碼(套用至array[]需要-1)
//        int move_number = 0;
//        for(int number = 0 ; number <= position ; number ++) {
//            move_number = move_number + positive[number];
//        }
//        
//        //( "座標7~10"上沒有卡片 "and" 移動的卡片必須為1 ) "or" ( 座標7~10有卡片"and"移動卡片的type與"座標7~10"的type必須相同 "and" 移動卡片的數字必須比"座標7~10"大1 )
//        if((positive[10] == 0 && paper_card[1][move_number - 1] == 1) || (positive[10] != 0 && paper_card[0][move_number - 1] == paper_card[0][card_number[3] - 1] && paper_card[1][card_number[3] - 1] + 1 == paper_card[1][move_number - 1])){
//            change(move_number - 1 , card_number[3] - 1 , position , 10);
//            return true;
//        }else if((positive[9] == 0 && paper_card[1][move_number - 1] == 1) || (positive[9] != 0 && paper_card[0][move_number - 1] == paper_card[0][card_number[2] - 1] && paper_card[1][card_number[2] - 1] + 1 == paper_card[1][move_number - 1])){
//            change(move_number - 1 , card_number[2] - 1 , position , 9);
//            return true;
//        }else if((positive[8] == 0 && paper_card[1][move_number - 1] == 1) || (positive[8] != 0 && paper_card[0][move_number - 1] == paper_card[0][card_number[1] - 1] && paper_card[1][card_number[1] - 1] + 1 == paper_card[1][move_number - 1])){
//            change(move_number - 1 , card_number[1] - 1 , position , 8);
//            return true;
//        }else if((positive[7] == 0 && paper_card[1][move_number - 1] == 1) || (positive[7] != 0 && paper_card[0][move_number - 1] == paper_card[0][card_number[0] - 1] && paper_card[1][card_number[0] - 1] + 1 == paper_card[1][move_number - 1])){
//            change(move_number - 1 , card_number[0] - 1 , position , 7);
//            return true;
//        }else{
            return false;
//        }
    }
    
    
}
