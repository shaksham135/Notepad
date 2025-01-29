import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URI;

public class Notepad extends Component implements ActionListener {
    JFrame f;
    JMenuBar mb;
    JMenu file,edit,help,zoom;
    JMenuItem cut,copy,paste,selectAll,newWindow,saveAS,exit,find,zoomIn,zoomOut,open,about,onlineHelp;
    JTextArea area;
    int fontSize = 16;

    Notepad(){
        f = new JFrame("NotePad");
        mb = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        selectAll = new JMenuItem("Select All");
        newWindow = new JMenuItem("New Window");
        saveAS = new JMenuItem("Save As");
        exit = new JMenuItem("Exit");
        find = new JMenuItem("Find");
        zoom = new JMenu("Zoom");
        zoomIn = new JMenuItem("Zoom In");
        zoomOut = new JMenuItem("Zoom Out");
        open = new JMenuItem("Open");
        area = new JTextArea();
        area.setBounds(0,0,1400,1400);
        area.setFont(new Font("Arial" ,Font.PLAIN,fontSize));
        about = new JMenuItem("About");
        onlineHelp = new JMenuItem("Online Help");
        Image icon = Toolkit.getDefaultToolkit().getImage("D://notepad.png");
        f.setIconImage(icon);
        mb.add(file);mb.add(edit);mb.add(help);
        edit.add(copy);edit.add(paste);edit.add(cut);edit.add(find);edit.add(zoom);
        file.add(newWindow);file.add(open);file.add(saveAS);file.add(exit);
        zoom.add(zoomIn);zoom.add(zoomOut);
        help.add(about);help.add(onlineHelp);
        f.add(area);
        f.setJMenuBar(mb);
        f.setSize(800,600);
        f.setLayout(null);
        f.setVisible(true);
        exit.addActionListener(this);
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        newWindow.addActionListener(this);
        zoomIn.addActionListener(this);
        zoomOut.addActionListener(this);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        saveAS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(f,
                        "Notepad v1.0 \nDeveloped by Shaksham Agarwal\n2024","About",JOptionPane.INFORMATION_MESSAGE
                        );
            }
        });

        onlineHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://chat.openai.com/"));
                }catch (Exception ex){
                    System.out.println(ex);
                }
            }
        });
    }

    private void saveFile(){
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(f);

        if(option == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
                writer.write(area.getText());
                JOptionPane.showMessageDialog(f,"File saved Successfully");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(f,"Error saving file" + e.getMessage());
            }
        }
    }

    private void openFile(){
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(f);

        if(option== JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
                area.setText("");
                String line;
                while ((line = reader.readLine()) != null){
                    area.append(line + "\n");
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this,"error Opening File : " + e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
            }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==copy){
            area.copy();
        }
        if(e.getSource()==cut){
            area.cut();
        }
        if(e.getSource()==paste){
            area.paste();
        }
        if(e.getSource()==selectAll){
            area.selectAll();
        }
        if(e.getSource() == newWindow){
            new Notepad();
        }
        if(e.getSource()==exit){
            f.dispose();
        }
        if(e.getSource()==zoomIn){
            zoomFontSize(true);
        }
        if(e.getSource()==zoomOut){
            zoomFontSize(false);
        }
    }

    private void zoomFontSize(Boolean increase){
        if(increase){
            fontSize += 2;
        }
        else {
            fontSize = Math.max(8,fontSize-2);
        }
        area.setFont(new Font("Arial" , Font.PLAIN , fontSize));
    }

    public static void main(String[] args) {
        new Notepad();
    }
}
