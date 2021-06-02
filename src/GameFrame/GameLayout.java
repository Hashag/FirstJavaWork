package GameFrame;

import Utils.TankOfUtils;

import javax.swing.*;
import java.awt.*;
import  static Utils.AllConstNature.*;

public class GameLayout {
//菜单布局器
public static class GLayout implements LayoutManager{
    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }

    @Override
    public void layoutContainer(Container parent) {


        int w = parent.getWidth(); // 父窗口的宽度 width

        int h = parent.getHeight(); // 父窗口的高度 height

        //将菜单按钮添加到布局器上
        JButton[] buttons = J_BUTTONS;
        final int GAP_DIS = 100; // 列间距
        final int BUTTON_LEN = 100; //button宽度
        int x = w - BUTTON_LEN >> 1;
        int y = h / 4;
        for (int i = 0; i < buttons.length; i++) {
            JButton button = buttons[i];
            if(button.isVisible()) {
                button.setPreferredSize(new Dimension(BUTTON_LEN,20));
                button.setText(MAIN_BORD[i]);
                //TODO if set the color ?
                button.setFont(FONT_BUTTON);
                button.setForeground(TankOfUtils.randomColor());
                button.setBounds(x,y + i*GAP_DIS,100,30);
            }
        }

    }
}

//游戏帮助布局
public static class HelpLayout implements LayoutManager{

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }

    @Override
    public void layoutContainer(Container parent) {
        //游戏介绍
        final int GAP_DIS = 100; // 列间距
        for (int i = 1; i < HELP_LABELS.length; i++) {
            HELP_LABELS[i].setFont(FONT_BUTTON);
            HELP_LABELS[i].setForeground(TankOfUtils.randomColor());
            HELP_LABELS[i].setBounds(100,GAP_DIS*i,800,200);
        }
        HELP_LABELS[0].setFont(FONT_BUTTON);
        HELP_LABELS[0].setForeground(TankOfUtils.randomColor());
        HELP_LABELS[0].setBounds(100,0,800,200);
        HELP_LABELS[0].setText("游戏规则: 消灭敌人以获得指定的分数(" + TOTAL_SCORES + "分)");

        HELP_LABELS[1].setText("坦克方向键： W/Up(向上)  S/Down(向下)  A/Left(向左)  D/Right(向右)  F/Space(开火)");
        HELP_LABELS[2].setText("查看坦克的得分： R");
        HELP_LABELS[3].setText("保存游戏： T");

        //返回按钮
        BUTTON_BACK.setFont(FONT_BUTTON);
        BUTTON_BACK.setForeground(TankOfUtils.randomColor());
        BUTTON_BACK.setBounds(400,700,100,30);
    }
}

//游戏关于布局
public static class AboutLayout implements LayoutManager{

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }

    @Override
    public void layoutContainer(Container parent) {
        //游戏介绍
        final int GAP_DIS = 100; // 列间距
        for (int i = 0; i < ABOUT_LABELS.length; i++) {
            ABOUT_LABELS[i].setFont(FONT_BUTTON);
            ABOUT_LABELS[i].setForeground(TankOfUtils.randomColor());
            ABOUT_LABELS[i].setBounds(200,GAP_DIS*i,400,200);
        }
        ABOUT_LABELS[0].setText("Author: 杨洁");
        ABOUT_LABELS[1].setText("MailBox: abcdekl35@gmail.com");


        //返回按钮
        BUTTON_BACK.setFont(FONT_BUTTON);
        BUTTON_BACK.setForeground(TankOfUtils.randomColor());
        BUTTON_BACK.setBounds(400,700,100,30);
    }
}

public static class Score implements LayoutManager{

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }

    @Override
    public void layoutContainer(Container parent) {
        final int GAP_DIS = 100;
        for (int i = 0; i < SCORE_PANEL.length; i++) {
            SCORE_PANEL[i].setFont(FONT_BUTTON);
            SCORE_PANEL[i].setForeground(TankOfUtils.randomColor());
            SCORE_PANEL[i].setBounds(350,GAP_DIS*i,300,200);
        }
    //返回按钮
        BUTTON_BACK.setFont(FONT_BUTTON);
        BUTTON_BACK.setForeground(TankOfUtils.randomColor());
        BUTTON_BACK.setBounds(400,700,100,30);
    }
}
}
