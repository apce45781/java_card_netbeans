package card;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

public class Card_png {
    
    private String path;
    private Image[][] card_type = new Image[4][13];
    private Image card;
    private Image back;
    
    Card_png(){
        path = new File("paper_card").getAbsolutePath();
        setCard_type();
    }
    
    public void setCard_type(){
        String[] type ={"spades_" , "love_" , "square_" , "plum_blossom_"};
        for(int i = 0 ; i < 4 ; i ++){
            for(int j = 0 ; j < 13 ; j ++){
                card_type[i][j] = Toolkit.getDefaultToolkit().getImage(path + "\\" + type[i] + (j + 1) + ".png");
            }
        }
        card = Toolkit.getDefaultToolkit().getImage(path + "\\card.png");
        back = Toolkit.getDefaultToolkit().getImage(path + "\\back.png");
    }
}
