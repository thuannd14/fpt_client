import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class GuiNewFolder extends JFrame implements ActionListener{
	private JTextField txtPath = new JTextField();
	private JTextField txtFolderName;
	private JButton btnOk,btnHuy;
	private JLabel lbThongBao;
	JFrame f;
	private String link;
	public void giaoDien(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    } catch (Exception evt) {
	    	evt.printStackTrace();
	    }
		f = new JFrame("Tạo folder");
		JLabel lbPath = new JLabel("Path:");
		lbPath.setBounds(20, 20, 50, 20);
		JLabel lbFolderName = new JLabel("Folder name:");
		lbFolderName.setBounds(20, 50, 100, 20);
		
		txtPath = new JTextField();
		txtPath.setBounds(90, 20, 200, 20);
		txtFolderName = new JTextField();
		txtFolderName.setBounds(90, 50, 200, 20);
		
		btnOk = new JButton("OK");
		btnOk.setBounds(180, 80, 50, 20);
		btnHuy = new JButton("Hủy");
		btnHuy.setBounds(230, 80, 60, 20);
		
		lbThongBao = new JLabel();
		lbThongBao.setBounds(20, 130, 200, 20);
		
		f.add(lbThongBao);
		f.add(btnHuy);
		f.add(btnOk);
		f.add(txtFolderName);
		f.add(txtPath);
		f.add(lbPath);
		f.add(lbFolderName);
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
			link = txtPath.getText();
			if(txtFolderName.getText().indexOf(' ') == -1){
				if(link.length()==1){
					link = "/"+txtFolderName.getText();
				}
				else{
					link = txtPath.getText() +"/"+txtFolderName.getText();
				}
				if(GuiMain.makeFolder(txtPath.getText() +"/"+txtFolderName.getText())){
					lbThongBao.setText("Tạo mới thành công!");
					GuiMain.deleteAllRow();
					GuiMain.showDataWithTable(txtPath.getText());
				}
				else{
					JOptionPane.showMessageDialog(f,"Không thể tạo mới thư mục! \n Đường dẫn chưa chính xác","Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else{
				JOptionPane.showMessageDialog(f,"Không thể tạo mới thư mục! \nTên thuc mục không được chưa khoảng trắng","Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(e.getSource()==btnHuy){
			f.setVisible(false);
			f.dispose();
		}
	}
	public void setPathNewFolder(String path){
		txtPath.setText(path);
	}
}
