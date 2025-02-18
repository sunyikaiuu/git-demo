package com.itcast.game;
import com.itcast.domain.Poker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;




public class GameJFrame extends JFrame implements ActionListener {

    //获取界面中的隐藏容器，现在同意获取了，后面直接用就可以了
    public static Container container=null;

    //管理抢地主还是不抢两个按钮
    JButton []landlord=new JButton[2];

    //管理出牌还是不要两个按钮
    JButton []publishCard=new JButton[2];

    //地主图标
    JLabel diZhu;

    ArrayList<ArrayList<Poker>> currentList = new ArrayList<>();

    ArrayList<ArrayList<Poker>> playerList = new ArrayList<>();

    //底牌
    ArrayList<Poker> lordList = new ArrayList<>();
    //牌盒
    ArrayList<Poker> pokerList = new ArrayList<>();

    //倒计时提示
    JTextField []time = new JTextField[3];

    public GameJFrame() {
        //设置任务栏图标
        setIconImage(Toolkit.getDefaultToolkit().getImage("DouDiZhu\\image\\poker\\img.png"));
        //设置界面
        initJFrame();
        //添加组件
        initView();
        //展示界面，先展示界面再发牌，因为发牌里面有动画，界面展示不出来，动画无法展示
        this.setVisible(true);

        initCard();
        initGame();
    }
    //准备牌
    public void initCard() {
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 13; j++) {
                if ((i == 5) && (j > 2)) {
                    break;
                } else {
                    Poker poker = new Poker(i + "-" + j, false);
                    poker.setLocation(350, 150);
                    pokerList.add(poker);
                    container.add(poker);
                }
            }
        }
        Collections.shuffle(pokerList);


        //洗牌
        ArrayList<Poker> player0 = new ArrayList<>();
        ArrayList<Poker> player1 = new ArrayList<>();
        ArrayList<Poker> player2 = new ArrayList<>();

        //发牌
        for (int i = 0; i < pokerList.size(); i++) {
            Poker poker = pokerList.get(i);
            if (i <= 2) {
                lordList.add(poker);
                com.itcast.game.Common.move(poker, poker.getLocation(), new Point(270 + (75 * i), 10));
                continue;
            }
            if (i % 3 == 0) {
                com.itcast.game.Common.move(poker, poker.getLocation(), new Point(50, 60 + i * 5));
                player0.add(poker);
            } else if (i % 3 == 1) {
                com.itcast.game.Common.move(poker, poker.getLocation(), new Point(180 + i * 7, 450));
                player1.add(poker);
            } else {
                com.itcast.game.Common.move(poker, poker.getLocation(), new Point(700, 60 + i * 5));
                player2.add(poker);
            }
            playerList.add(player0);
            playerList.add(player1);
            playerList.add(player2);

            container.setComponentZOrder(poker, 0);
        }
        for (int i = 0; i < 3; i++) {
            order(playerList.get(i));
            com.itcast.game.Common.rePosition(this, playerList.get(i), i);
        }
    }
    public void order(ArrayList<Poker> list){
      Collections.sort(list,new Comparator<Poker>() {
        @Override
        public int compare(Poker o1, Poker o2) {
            int color1 = Integer.parseInt(o1.getName().substring(0, 1));
            int value1 = getValue(o1);

            int color2 = Integer.parseInt(o2.getName().substring(0, 1));
            int value2 = getValue(o2);

            int flag = value2 - value1;

            if (flag == 0) {
                return color2 - color1;
            } else {
                return flag;
            }
        }
    });
}
public int getValue(Poker poker) {
    String name = poker.getName();
    int color = Integer.parseInt(poker.getName().substring(0, 1));
    int value = Integer.parseInt(name.substring(2));

    if (color == 5) {
        return value += 100;
    }
    if (value == 1) {
        return value += 20;
    }
    if (value == 2) {
        return value += 30;
    }
    return value;
}
private void initGame(){
    for(int i=0;i<3;i++){
        ArrayList<Poker> list=new ArrayList<>();
        currentList.add(list);
    }

    landlord[0].setVisible(true);
    landlord[1].setVisible(true);

    for(JTextField field : time){
        field.setText("倒计时30秒");
        field.setVisible(true);
    }
}
@Override
public void actionPerformed(ActionEvent e){

}
public void initView(){
    //创建抢地主的按钮
    JButton robBut = new JButton("抢地主");
    //设置按钮位置
    robBut.setBounds(320,400,75,20);
    //点击添加事件
    robBut.addActionListener(this);
    //设置隐藏（抢地主按钮隐藏）
    robBut.setVisible(false);
    //添加到数组中统一管理
    landlord[0]=robBut;
    //添加到界面中
    container.add(robBut);

    JButton noBut = new JButton("不 抢");
    noBut.setBounds(420,400,75,20);
    noBut.addActionListener(this);
    noBut.setVisible(false);
    landlord[1]=noBut;
    container.add(noBut);

    JButton outCardBut=new JButton("出牌");
    outCardBut.setBounds(320,400,60,20);
    outCardBut.addActionListener(this);
    outCardBut.setVisible(false);
    publishCard[0]=outCardBut;
    container.add(outCardBut);

    JButton noCardBut=new JButton("不要");
    noCardBut.setBounds(420,400,60,20);
    noCardBut.addActionListener(this);
    noCardBut.setVisible(false);
    publishCard[1]=noCardBut;
    container.add(noCardBut);

//创建玩家前方的倒计时提示文字
    for(int i=0;i<3;i++){
        time[i]=new JTextField("倒计时");
        time[i].setEditable(false);
        time[i].setVisible(false);
        container.add(time[i]);
    }
    time[0].setBounds(140,230,60,20);
    time[1].setBounds(374,360,60,20);
    time[2].setBounds(620,230,60,20);
//创建地主图标
    diZhu = new JLabel (new ImageIcon("DouDiZhu\\image\\poker\\img.png"));
    diZhu.setVisible(false);
    //设置大小，而不能设置位置
    diZhu.setSize(40,40);
    container.add(diZhu);
}
public void initJFrame(){
    //设置标题
    this.setTitle("斗地主");
    //设置大小
    this.setSize(830,620);
    //界面居中
    this.setLocationRelativeTo(null);
    //设置关闭模式
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //获取界面中的隐藏容器
    container=this.getContentPane();
    //取消内部默认的居中放置
    container.setLayout(null);
    //设置背景颜色
    container.setBackground(Color.LIGHT_GRAY);
}
}

