package card;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Card extends JPanel implements MouseListener , MouseMotionListener , KeyListener{
    
    public final int negative_ten = -10;
    public final int fifty = 50;
    public final int negative_fifty = -50;
    public final int ten = 10;
    
    private JFrame jframe;
    private Mouse_keyboard mk;
    private Card_interface ci;
    
    private int fraction = 0;
    
    Card(){
        this.setFocusable(true);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addKeyListener(this);
        
        jframe = new JFrame();
        mk = new Mouse_keyboard();
        ci = new Card_interface();
        
        jframe.setBounds(100 , 50 , 875 , 700);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("接龍    點擊F2即可重新開局  分數 : " + fraction);
        
        jframe.add(this);
        jframe.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Card();
    }
    
    @Override
    public void paint(Graphics g){
        ci.position_setup(this.getWidth() , this.getHeight());
        ci.show_background(this , g);
        
        ci.show_picture(g , mk , this);
    }
    
    public void fraction(int fraction){
        this.fraction += fraction;
        jframe.setTitle("接龍    點擊F2即可重新開局  分數 : " + this.fraction);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        滑鼠完整點擊

        float mouse_X = e.getX();
        float mouse_Y = e.getY();
        
        boolean one_click_switch = mk.one_click_switch(ci , mouse_X , mouse_Y);
        if(one_click_switch){
            fraction(negative_ten);
        }
        
        int two_click = 2;
        if(e.getClickCount() == two_click && mk.get_mouse_status() == mk.initial) {
            
            int two_click_switch = mk.two_click_switch(ci , mouse_X , mouse_Y);
            int true_and_addfraction = 0;
            
            if(two_click_switch == true_and_addfraction){
                fraction(fifty);
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        滑鼠單一放開動作
        if(mk.get_mouse_status() == mk.move){
            int true_and_addfraction = 0;
            int true_not_addfraction = 1;
            int true_and_add_negativefraction = 2;
            
            int fraction_switch = 0;
            int end_position = 1;
            
            int[] mouse_up_data = mk.mouse_up(ci);
            
            if(mouse_up_data[fraction_switch] != ci.incompatible){
                ci.card_spacing(mouse_up_data[end_position]);
            }
            
            if(mouse_up_data[end_position] < 7 && mouse_up_data[fraction_switch] == true_and_addfraction){
                fraction(ten);
            }else if(mouse_up_data[end_position] < 7 && mouse_up_data[fraction_switch] == true_and_add_negativefraction){
                fraction(negative_fifty);
            }else if(mouse_up_data[end_position] < 11 && mouse_up_data[fraction_switch] == true_and_addfraction){
                fraction(fifty);
            }else if(mouse_up_data[fraction_switch] != true_and_addfraction && mouse_up_data[fraction_switch] != true_not_addfraction){
                mk.back_home(ci);
            }
            
        }
        if(mk.get_mouse_status() != mk.initial){
            ci.pc.clear_move_card();
            mk.change_mouse_status(mk.initial);
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        滑鼠點擊並移動

        ci.mouse_point[ci.row] = e.getX();
        ci.mouse_point[ci.column] = e.getY();
        
        if(mk.get_mouse_status() == mk.initial){
            float mouse_X = e.getX();
            float mouse_Y = e.getY();
            
            mk.change_mouse_status(mk.click);
            boolean mouse_move = mk.mouse_move(ci , mouse_X , mouse_Y);
            
            if(mouse_move){
                mk.change_mouse_status(mk.move);
            }
        }
        try{
            Thread.sleep(10);
        }catch(InterruptedException ex){}
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        鍵盤敲擊
        if(e.getKeyCode() == KeyEvent.VK_F2){
            if(JOptionPane.showConfirmDialog(jframe , "遊戲還未完成\n是否重開新局", "!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                restart();
                fraction(0);
                repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void restart(){
        
        ci.pc.clear_move_card();
        
        ci.pc.clear_papercard();
        ci.pc.create_and_shuffle();
        
        for(int i = 0 ; i < 13 ; i ++){
            if(i > 6 && i < 12){
                ci.pc.addAll_positive_quantity(i , 0);
                ci.pc.addAll_negative_quantity(i , 0);
            }else{
                ci.pc.addAll_positive_quantity(i , ci.pc.get_card_quantity(i));
                ci.pc.addAll_negative_quantity(i , ci.pc.get_card_quantity(i) - 1);
            }
        }
        ci.pc.addAll_negative_quantity(0 , 0);
        ci.pc.addAll_negative_quantity(1 , 0);
        ci.pc.addAll_negative_quantity(2 , 0);
        ci.pc.addAll_negative_quantity(3 , 0);
        ci.pc.addAll_negative_quantity(4 , 0);
        ci.pc.addAll_negative_quantity(5 , 0);
        ci.pc.addAll_negative_quantity(6 , 0);
        ci.pc.addAll_negative_quantity(7 , 0);
        ci.pc.addAll_negative_quantity(8 , 0);
        ci.pc.addAll_negative_quantity(9 , 0);
        ci.pc.addAll_negative_quantity(10 , 0);
        ci.pc.addAll_negative_quantity(11 , 0);
        ci.pc.addAll_negative_quantity(12 , 0);
        
        ci.card_spacing(ci.pc.initial);
        
        fraction = 0;
    }
}
