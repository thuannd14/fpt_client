import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class GuiRenameFile extends JFrame implements ActionListener{
	private JTextField txtOldName = new JTextField();
	private JTextField txtNewName;
	private JButton btnOk,btnHuy;
	private JLabel lbThongBao;
	private String oldPath,newPath,link;
	private JFrame f;
	public void giaoDien(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    } catch (Exception evt) {
	    	evt.printStackTrace();
	    }
		f = new JFrame("Rename");
		JLabel lbOldName = new JLabel("Old name:");
		lbOldName.setBounds(20, 20, 50, 20);
		JLabel lbNewName = new JLabel("New name:");
		lbNewName.setBounds(20, 50, 100, 20);
		
		txtOldName = new JTextField();
		txtOldName.setBounds(90, 20, 200, 20);
		txtOldName.enable(true);
		txtNewName = new JTextField();
		txtNewName.setBounds(90, 50, 200, 20);
		
		btnOk = new JButton("OK");
		btnOk.setBounds(180, 80, 50, 20);
		btnHuy = new JButton("Hủy");
		btnHuy.setBounds(230, 80, 60, 20);
		
		lbThongBao = new JLabel();
		lbThongBao.setBounds(20, 130, 200, 20);
		
		f.add(lbThongBao);
		f.add(btnHuy);
		f.add(btnOk);
		f.add(txtNewName);
		f.add(txtOldName);
		f.add(lbOldName);
		f.add(lbNewName);
		f.setLayout(null);
		f.getContentPane().setBackground(Color.white);
		f.setVisible(true);
		f.setSize(330, 170);
		
		btnHuy.addActionListener(this);
		btnOk.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnOk){
			newPath = link+"/"+txtNewName.getText();
			if(GuiMain.renameFile(oldPath, newPath)){
				lbThongBao.setText("Đổi tên thành công!");
				GuiMain.deleteAllRow();
				GuiMain.showDataWithTable(link);
			}
			else{
				JOptionPane.showMessageDialog(f,"Đổi tên thất bại!","Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(e.getSource()==btnHuy){
			f.setVisible(false);
            f.dispose();
		}
	}
	public void setPathRenameFile(String path){
		oldPath = path;
		int n = path.lastIndexOf('/');
		link = path.substring(0,n);
		path = path.substring(n+1, path.length());
		txtOldName.setText(path);
	}
}
