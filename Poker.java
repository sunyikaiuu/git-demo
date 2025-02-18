package com.itcast.domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Poker extends JLabel implements MouseListener {

    //牌的名字
    private String name;

    //是否正面
    private boolean up;

    //是否可以被点击
    private boolean canClick=false;

    //当前的牌是否已经被点击，如果没有处于点击的状态，点击后弹起，否则落下
    private boolean clicked=false;

    public Poker(String name, boolean up) {
        this.name = name;
        this.up = up;
        if(this.up){
            //显示正面
            turnFront();
        }else{
            //显示反面
            turnRear();
        }
        //设置牌的高度
        this.setSize(71,96);
        //把牌显示出来
        this.setVisible(true);
        //给每一张牌添加鼠标监听
        this.addMouseListener(this);
    }

    //显示正面
public void turnFront(){
        this.setIcon(new ImageIcon("DouDiZhu\\image\\poker\\"+name+".jpg"));
        this.up=true;
}
    //显示反面
public void turnRear(){
        this.setIcon(new ImageIcon("DouDiZhu\\image\\poker\\rear.png"));
        this.up=false;
}

    @Override
    public void mouseClicked(MouseEvent e) {
        if(canClick){
            int step=0;
            if(clicked){
               step=20;
            }else{
                step=-20;
            }
            clicked=!clicked;
            Point from=this.getLocation();
            Point to=new Point(from.x, from.y+step);
            this.setLocation(to);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
