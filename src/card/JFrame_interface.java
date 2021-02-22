package card;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JFrame_interface {
    
    public JFrame jframe;
    public JPanel jpanel;
    
    private int card_row;
    private int card_col;
    
    public JFrame_interface(int initial_row , int initial_column){
        jframe = new JFrame();
        jpanel = new JPanel();
        
        jframe.setBounds(100 , 50 , initial_row , initial_column);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void JFrame_start(){
        jframe.add(jpanel);
        jframe.setVisible(true);
    }
}
