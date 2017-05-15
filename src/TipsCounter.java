
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Ivan Korol on 05.08.2016.
 */
public class TipsCounter {
    // создаем переменные для фрейма
    private JTextField textStaff;
    private JTextField textTips;
    private Choice boxNewStaff;
    private Choice boxKitchen;

    private void go(){
        // создаем фрейс и панель с компонентами
        JFrame frame=new JFrame("tips Counter");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel=new JPanel();
        Font f=new Font("", Font.BOLD,14);
        textStaff=new JTextField(15);
        textTips=new JTextField(15);
        JLabel labStaff = new JLabel("Staff");
        labStaff.setHorizontalAlignment(SwingConstants.CENTER);
        labStaff.setFont(f);
        JLabel labTips = new JLabel("Tips");
        labTips.setHorizontalAlignment(SwingConstants.CENTER);
        labTips.setFont(f);
        JLabel labKitchen = new JLabel("% for kitchen");
        labKitchen.setHorizontalAlignment(SwingConstants.CENTER);
        labKitchen.setFont(f);
        JButton button = new JButton("Count");
        button.addActionListener(new ButtonListener());
        boxKitchen=new Choice();
        boxKitchen.add("0");
        boxKitchen.add("10");
        boxKitchen.add("15");
        boxKitchen.add("20");
        JLabel labNewStaff = new JLabel("New Staff");
        labNewStaff.setHorizontalAlignment(SwingConstants.CENTER);
        labNewStaff.setFont(f);
        boxNewStaff=new Choice();
        boxNewStaff.add("0");
        boxNewStaff.add("1");
        boxNewStaff.add("2");
        boxNewStaff.add("3");
        boxNewStaff.add("4");
        boxNewStaff.add("5");
        // устанавливаем компоненты на понель во фрейме
        GridLayout gl=new GridLayout(6,2,5,5);
        panel.setLayout(gl);
        panel.add(labStaff);
        panel.add(textStaff);
        panel.add(labTips);
        panel.add(textTips);
        panel.add(labKitchen);
        panel.add(boxKitchen);
        panel.add(labNewStaff);
        panel.add(boxNewStaff);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.SOUTH, button);
        frame.setSize(400,200);
        frame.setLocation(500,300);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    private String Num() {
        double tot;
        double chefs;
        double tip;
        String s = "Работайте усерднее!!! "; //будет выводится если все будет по нолям
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            //получаем текст с текстовых табличек
            String t1s = textStaff.getText();
            String t2t = textTips.getText();
            int nStaff = Integer.parseInt(t1s);
            int nTips = Integer.parseInt(t2t);
            // получаем данные с боксов чойс
            String kitchen = boxKitchen.getSelectedItem();
            int nKitchen = Integer.parseInt(kitchen);
            String newStaff= boxNewStaff.getSelectedItem();
            int nNewStaff=Integer.parseInt(newStaff);

            //добовляем новые переменную для подсчета стафа с полной ставкой чая при наличии 50% ноков
            int StuffFull=nStaff-nNewStaff;
            double staffTips;
            double newStaffTips;
            // пропускаем через иф исключение для полного подсчета
            //если есть процент для поворов и новый стаы и чай
            if (nStaff != 0 && nTips != 0 && nKitchen != 0 && nNewStaff!=0) {
                chefs = (nKitchen * nTips) / 100;
                tot = nTips - chefs;
                if(tot!=0){
                    staffTips = ((tot / nStaff) * (StuffFull)) / nStaff;
                    newStaffTips = staffTips / 2;
                    //начало циклв
                    for (int i=(int)tot; i>=(tot-((staffTips*StuffFull)+(newStaffTips*nNewStaff))); i--) {
                        tip = tot - ((staffTips * StuffFull) + (newStaffTips * nNewStaff));
                        staffTips += ((tip / nStaff) * StuffFull) / nStaff;
                        newStaffTips = staffTips / 2;
                    }
                 s = "Tips for kitchen: " + chefs + "\n" + "Tips for person: " + staffTips+"\n"+"Tips for new staff: "+newStaffTips;}

            } else if (nStaff != 0 && nTips != 0 && nKitchen != 0 && nNewStaff==0) { //если отсутствует новый стаф, а все остальное есть
                chefs = (nKitchen * nTips) / 100;
                tot = (nTips - chefs) / nStaff;
                s="Tips for kitchen: " + chefs + "\n" + "Tips for person: " +tot;
            }else if (nStaff!=0 && nTips!=0 && nNewStaff!=0 && nKitchen==0){ //если отсутствует % для кухни все остальнгое на месте вкл новый стаф
                if(nTips!=0){
                    staffTips = ((nTips / nStaff) * (StuffFull)) / nStaff;
                    newStaffTips = staffTips / 2;

                    for (int i=nTips; i>=(nTips-((staffTips*StuffFull)+(newStaffTips*nNewStaff))); i--) {
                        tip = nTips - ((staffTips * StuffFull) + (newStaffTips * nNewStaff));
                        staffTips += ((tip / nStaff) * StuffFull) / nStaff;
                        newStaffTips = staffTips / 2;
                    }
                    s= "Tips for person: " + staffTips+"\n"+"Tips for new staff: "+newStaffTips;
                }
            }else if(nStaff!=0 && nTips!=0 && nNewStaff==0 && nKitchen==0){//расчет толлько для стафа без кухни и новичков
                tot = nTips / nStaff;
                s = "Tips for person: " + tot;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return s;
    }
    public class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, Num(),"Tips for person", JOptionPane.OK_CANCEL_OPTION);
        }
    }

    public static void main(String[] args) {
        TipsCounter tp=new TipsCounter();
        tp.go();
    }

}
