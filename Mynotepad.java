import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

class Mynotepad implements ActionListener
{
static JTextArea ta=new JTextArea();
JFrame jf=new JFrame("MyNotepad");
JPopupMenu p=new JPopupMenu("Pop");
JMenuBar mb;
JMenu menu;
JMenuItem newoption,open,save,exit;
JMenuItem undo,cut,copy,paste,del,font,compile,run;
static JTextArea ta1;
static String str="";
String result="";
String result1="";
Runtime r;

Mynotepad()
{
r=Runtime.getRuntime();
WindowUtilities.setNativeLookAndFeel();
jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
Font displayfont=new Font("Serif",Font.BOLD,15);
ta.setFont(displayfont);
jf.add(ta);
JScrollPane jp=new JScrollPane(ta);
jf.add(jp);

mb=new JMenuBar();     

menu=new JMenu("File");
menu.setMnemonic(KeyEvent.VK_F);

mb.add(menu);

newoption=new JMenuItem("New");
open=new JMenuItem("Open");
save=new JMenuItem("Save");
exit=new JMenuItem("Exit");

menu.add(newoption);
menu.add(open);
menu.add(save);
menu.addSeparator();
menu.add(exit);


newoption.addActionListener(this);
open.addActionListener(this);
save.addActionListener(this);
exit.addActionListener(this);


menu=new JMenu("Edit");
menu.setMnemonic(KeyEvent.VK_E);
mb.add(menu);

undo=new JMenuItem("Undo");
cut=new JMenuItem("Cut");
copy=new JMenuItem("Copy");
paste=new JMenuItem("Paste");
del=new JMenuItem("Delete");

menu.add(undo);
menu.add(cut);
menu.add(copy);
menu.add(paste);
menu.add(del);

undo.addActionListener(this);
cut.addActionListener(this);
copy.addActionListener(this);
paste.addActionListener(this);
del.addActionListener(this);


menu=new JMenu("Format");
menu.setMnemonic(KeyEvent.VK_T);
mb.add(menu);
font=new JMenuItem("Font");
menu.add(font);

font.addActionListener(this);

JMenuItem i1=new JMenuItem("Undo");
JMenuItem i2=new JMenuItem("Cut");
JMenuItem i3=new JMenuItem("Copy");
JMenuItem i4=new JMenuItem("Paste");
JMenuItem i5=new JMenuItem("Select All");

p.add(i1);
p.add(i2);
p.add(i3);
p.add(i4);
p.add(i5);

i1.addActionListener(this);
i2.addActionListener(this);
i3.addActionListener(this);
i4.addActionListener(this);
i5.addActionListener(this);

POPUP pp=new POPUP(this);
ta.addMouseListener(pp);


menu=new JMenu("Options");
menu.setMnemonic(KeyEvent.VK_O);
mb.add(menu);
compile=new JMenuItem("Compile");
run=new JMenuItem("Run");
menu.add(compile);
menu.add(run);

compile.addActionListener(this);
run.addActionListener(this);


//Setting shortcuts
KeyStroke opn=KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK);
open.setAccelerator(opn);

KeyStroke sv=KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK);
save.setAccelerator(sv);

KeyStroke ct=KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK);
cut.setAccelerator(ct);

KeyStroke cpy=KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK);
copy.setAccelerator(cpy);

KeyStroke  pst=KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK);
paste.setAccelerator(pst);

KeyStroke  cmp=KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK);
compile.setAccelerator(cmp);

KeyStroke  rn=KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK);
run.setAccelerator(rn);



jf.add(mb,BorderLayout.NORTH);
jf.setSize(600,600);
jf.setVisible(true);

}


public void actionPerformed(ActionEvent e)
{
if (e.getActionCommand().equals("New"))
{
ta.setText("");
ta1.setVisible(false);
}

if (e.getActionCommand().equals("Exit"))
System.exit(0);

if (e.getActionCommand().equals("Cut"))
ta.cut();

if (e.getActionCommand().equals("Copy"))
ta.copy();

if (e.getActionCommand().equals("Paste"))
ta.paste();

if (e.getActionCommand().equals("Open"))
{
new filechooser();
}

if (e.getActionCommand().equals("Save"))
{
new filesave();
}

if (e.getActionCommand().equals("Font"))
{
new fontstyle1();
}
//if (e.getActionCommand().equals("Undo"))
//ta.setText();

if (e.getActionCommand().equals("Compile"))
{
try{
ta1=new JTextArea();
ta1.setBackground(Color.red);
JScrollPane jp=new JScrollPane(ta1);
jf.add(ta1,BorderLayout.SOUTH);
ta1.setEditable(false);
Process error=r.exec("C:\\Program Files\\Java\\jdk1.8.0_92\\bin\\javac.exe -d . " +str);
//System.out.println(str);
BufferedReader err=new BufferedReader(new InputStreamReader(error.getErrorStream()));

while(true)
{
String temp=err.readLine();
if(temp!=null)
{
result+=temp;
result+="\n";
}
else break;
}

if(result.equals(""))
{
ta1.setText("Compilation successful!"+str);
err.close();
}
else
ta1.setText(result);
}
catch (Exception E){}
}

if (e.getActionCommand().equals("Run"))
{
try{
int count=0;
char ch[]=str.toCharArray();
for (int i=0;ch[i]!='.';i++)
count++;
String s1=str.substring(0,count);
Process p=r.exec( "C:\\Program Files\\Java\\jdk1.8.0_92\\bin\\java     " +s1);
//System.out.println(s1);
BufferedReader output=new BufferedReader(new InputStreamReader(p.getInputStream()));
BufferedReader error=new BufferedReader(new InputStreamReader(p.getErrorStream()));

while(true)
{
String temp=output.readLine();
if(temp!=null)
{
result+=temp;
result+="\n";
}
else break;
}

while(true)
{
String temp=error.readLine();
if (temp!=null)
{
result1+=temp;
result1+="\n";
}
else break;
}

output.close();
error.close();

ta1.setText(result+ "\n" + result1);
}
catch(Exception e2)
{
System.out.println(e2);
}
}


}




public static void main(String... s)
{
new Mynotepad();
}
}







class POPUP extends MouseAdapter
{
Mynotepad mn;

POPUP(Mynotepad mn)
{
this.mn=mn;
}
public void mouseClicked(MouseEvent e)
{
int x=e.getButton(); 

if (x==MouseEvent.BUTTON3)
mn.p.show(e.getComponent(),e.getX(),e.getY());
}
}


class fontstyle1 implements ListSelectionListener,ActionListener
{
JFrame jf1;
JLabel jl1,jl2,jl3;
JList list1,list2,list3;
JButton jb1,jb2;
JTextField jt1,jt2,jt3;
JPanel jp1,jp2,jp3;
Font font1; 
String fonts[] =GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
String entry[]={"PLAIN","ITALIC","BOLD"};
String size[]={"1","2","5","8","10","12","23","45","46","47","48","49"};
fontstyle1()
{
jf1=new JFrame("Font Chooser");
jl1=new JLabel("Font:");
jl2=new JLabel("Font Style:");
jl3=new JLabel("FontSize:");
jt1=new JTextField("Serif",15);
jt2=new JTextField("BOLD",15);
jt3=new JTextField("12",15);
jp1=new JPanel();
jp2=new JPanel();
jp3=new JPanel();

jb1=new JButton("OK");
jb2=new JButton("Cancel");
jb1.addActionListener(this);
jb2.addActionListener(this);

list1=new JList(fonts);
list1.setVisibleRowCount(4);
Font displayFont1 = new Font("Serif", Font.BOLD,12 );
list1.setFont(displayFont1);
JScrollPane listPane = new JScrollPane(list1);
jp1.add(jl1);
jp1.add(jt1);
jp1.add(listPane);
jp1.setLayout(new GridLayout(3,0));
jf1.add(jp1);
list1.addListSelectionListener(this);

jt1.setFont(displayFont1);
jt2.setFont(displayFont1);
jt3.setFont(displayFont1);


list2=new JList(entry);
list2.setVisibleRowCount(3);
list2.setFont(displayFont1);
JScrollPane listPane1 = new JScrollPane(list2);
jp2.add(jl2);
jp2.add(jt2);
jp2.add(listPane1);
jp2.setLayout(new GridLayout(3,0));
jf1.add(jp2);
list2.addListSelectionListener(this);


list3=new JList(size);
list3.setVisibleRowCount(4);
list3.setFont(displayFont1);
JScrollPane listPane2 = new JScrollPane(list3);
jp3.add(jl3);
jp3.add(jt3);
jp3.add(listPane2);
jp3.setLayout(new GridLayout(3,0));
jf1.add(jp3);
list3.addListSelectionListener(this);

jf1.add(jb1);
jf1.add(jb2);
jf1.setLayout(new FlowLayout(FlowLayout.LEFT));


jf1.pack();
jf1.setVisible(true);
}

public void valueChanged(ListSelectionEvent e)
{
if (e.getSource()==list1)
{
int idx = list1.getSelectedIndex();
jt1.setText(fonts[idx]);
String st = jt2.getText();
String siz = jt3.getText();
			
int style1= 0;
			
if(st.equals("BOLD")) style1 =1;
else if(st.equals("ITALIC")) style1 =2;
else if(st.equals("PLAIN")) style1 =0;

font1 =  new Font(fonts[idx] ,style1,Integer.parseInt(siz) );
//label.setFont(font1);
}

if (e.getSource()==list2)
{
int idx = list2.getSelectedIndex();
jt2.setText(entry[idx]);
String fname=jt1.getText();
String st = jt2.getText();
String siz = jt3.getText();
			
int style1= 0;
			
if(st.equals("BOLD")) style1 =1;
else if(st.equals("ITALIC")) style1 =2;
else if(st.equals("PLAIN")) style1 =0;

font1 =  new Font(fname ,style1,Integer.parseInt(siz) );
//label.setFont(font1);
}
if (e.getSource()==list3)
{
int idx = list3.getSelectedIndex();
jt3.setText(size[idx]);
String fname=jt1.getText();
String st = jt2.getText();
String size2 = jt3.getText();
			
int style1= 0;
			
if(st.equals("BOLD")) style1 =1;
else if(st.equals("ITALIC")) style1 =2;
else if(st.equals("PLAIN")) style1 =0;

font1 =  new Font(fname,style1,Integer.parseInt(size2) );
//label.setFont(font1);
}
}                               
                              


public void actionPerformed(ActionEvent ae)
{
if (ae.getSource()==jb1)
{
Mynotepad.ta.setFont(font1);
jf1.setVisible(false);
}

if (ae.getSource()==jb2)
{
jf1.setVisible(false);
}
}
}





class filechooser 
{
JFileChooser jfc;

filechooser()
{
jfc=new JFileChooser("D:");
StringBuffer sb = new StringBuffer();
int x=jfc.showOpenDialog(null);       
if(x==JFileChooser.APPROVE_OPTION)
{
File f=jfc.getSelectedFile();
try{
FileReader fr=new FileReader(f);
int ch ;
while((ch = fr.read())!=-1)
{
sb.append((char)ch);
}
Mynotepad.ta.setText(sb.toString());
}
catch (IOException e)
{
e.printStackTrace();
}
}
} 
} 




class filesave
{
JFileChooser jfc;

filesave()
{
jfc=new JFileChooser("D:");
String str=Mynotepad.ta.getText();
int x=jfc.showSaveDialog(null); 
if(x==JFileChooser.APPROVE_OPTION)
{
File f=jfc.getSelectedFile();
Mynotepad.str=f.getName();
try{
PrintWriter fw=new PrintWriter(f);
fw.println(str);
fw.close();
}
catch (IOException e)
{
e.printStackTrace();
}
}
}
} 