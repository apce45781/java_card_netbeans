package card;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class jframe_file extends JFrame {
    
    private int fraction = 0;
    private Card_interface ci;
    
    jframe_file(JPanel jpanel , Card_interface ci){
        this.ci = ci;
        this.setBounds(100 , 50 , 875 , 700);
        this.setTitle("接龍    點擊F2即可重新開局  分數 : " + fraction);
        this.add(jpanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    public void fraction(int fraction){
        this.fraction += fraction;
        this.setTitle("接龍    點擊F2即可重新開局  分數 : " + this.fraction);
    }
    
    public void fraction_initial(){
        fraction = 0;
        this.setTitle("接龍    點擊F2即可重新開局  分數 : " + this.fraction);
    }
    
    @Override
    protected void processWindowEvent(final WindowEvent e){
        if(e.getID() == 201){
            new json().output(ci , fraction);
        }
        super.processWindowEvent(e);
    }
}

public class Card extends JPanel implements MouseListener , MouseMotionListener , KeyListener{
    
    public final int negative_ten = -10;
    public final int fifty = 50;
    public final int negative_fifty = -50;
    public final int ten = 10;
    
    private jframe_file jframe;
    private Mouse_keyboard mk;
    private Card_interface ci;
    
    
    Card(){
        this.setFocusable(true);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addKeyListener(this);
        
        mk = new Mouse_keyboard();
        ci = new Card_interface();
        jframe = new jframe_file(this , ci);
        
        paper_card_open_start();
        
        repaint();
    }
    
    
    public static void main(String[] args) {
        new Card();
    }
    
    public void paper_card_open_start(){
        int[][][] file_data = new json().input();
        if(file_data[0][0][0] != -1){
            String path = "Record.json";
            File file = new File(path);
            if(JOptionPane.showConfirmDialog(jframe , "是否繼續上次未完成的遊戲?", "!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                int paper_card = 0;
                int position_quantity = 1;
                jframe.fraction(file_data[2][0][0]);
                ci.pc.paper_card_input(file_data[position_quantity] , file_data[paper_card]);
                for(int i = 0 ; i < 12 ; i ++){
                    ci.card_spacing(i);
                    ci.t_coordinate_settings(i);
                }
            }else{
                ci.pc.create_and_shuffle();
                for(int i = 0 ; i < 12 ; i ++){
                    ci.card_spacing(i);
                    ci.t_coordinate_settings(i);
                }
            }
            file.delete();
        }else{
            ci.pc.create_and_shuffle();
        }
    }
    
    @Override
    public void paint(Graphics g){
        ci.position_setup(this.getWidth() , this.getHeight());
        ci.show_background(this , g);
        
        ci.show_picture(g , mk , this);
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
        
        ci.card_spacing(ci.pc.initial);
        for(int i = 0 ; i < 7 ; i ++){
            ci.t_coordinate_settings(i);
        }
        jframe.fraction_initial();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        滑鼠完整點擊

        float mouse_X = e.getX();
        float mouse_Y = e.getY();
        
        boolean one_click_switch = mk.one_click_switch(ci , mouse_X , mouse_Y);
        if(one_click_switch){
            jframe.fraction(negative_ten);
        }
        
        int two_click = 2;
        if(e.getClickCount() == two_click && mk.get_mouse_status() == mk.initial) {
            
            int[] two_click_switch = mk.two_click_switch(ci , mouse_X , mouse_Y);
            int true_and_addfraction = 0;
            
            if(two_click_switch[0] == true_and_addfraction){
                jframe.fraction(fifty);
            }
            ci.card_spacing(two_click_switch[1]);
            ci.card_spacing(two_click_switch[2]);
            ci.t_coordinate_settings(two_click_switch[1]);
            ci.t_coordinate_settings(two_click_switch[2]);
            
            judgment_end();
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
            
            if(     mouse_up_data[end_position] < 7 &&
                    mouse_up_data[fraction_switch] == true_and_addfraction){
                jframe.fraction(ten);
            }else if(mouse_up_data[end_position] < 7 &&
                     mouse_up_data[fraction_switch] == true_and_add_negativefraction){
                jframe.fraction(negative_fifty);
            }else if(mouse_up_data[end_position] < 11 &&
                     mouse_up_data[fraction_switch] == true_and_addfraction){
                jframe.fraction(fifty);
            }else if(mouse_up_data[fraction_switch] != true_and_addfraction &&
                     mouse_up_data[fraction_switch] != true_not_addfraction){
                mk.back_home(ci);
            }
            
            if(mouse_up_data[fraction_switch] != ci.incompatible){
                ci.card_spacing(mouse_up_data[end_position]);
                ci.card_spacing(ci.pc.get_move_position());
                ci.t_coordinate_settings(mouse_up_data[end_position]);
                ci.t_coordinate_settings(ci.pc.get_move_position());
            }
            judgment_end();
        }
        if(mk.get_mouse_status() != mk.initial){
            ci.pc.clear_move_card();
            ci.add_Mgap(ci.pc.initial);
            mk.change_mouse_status(mk.initial);
            repaint();
        }
    }
    
    public void judgment_end(){
        if(ci.pc.get_positive_quantity(7) == 13 &&
                ci.pc.get_positive_quantity(8) == 13 &&
                ci.pc.get_positive_quantity(9) == 13 &&
                ci.pc.get_positive_quantity(10) == 13){
            repaint();
            try{
                Thread.sleep(20);
            }catch(InterruptedException e){}
            if(JOptionPane.showConfirmDialog(jframe , "恭喜完成遊戲!!\n請問是否開啟新局", "!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                restart();
                jframe.fraction(0);
                repaint();
            }else{
                System.exit(0);
            }
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
                jframe.fraction(0);
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
}