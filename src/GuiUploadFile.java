import javax.swing.*;
import java.io.File;
import java.awt.Color;
import java.awt.event.*;

public class GuiUploadFile  extends JFrame implements ActionListener {
	private JTextField txtPathHost =  new JTextField(); // Phải xin vung nho trước do phương thức setURL cần dùng
	private	JTextField txtBrower;
	private JFileChooser fileChooserComputer;
	private JButton btnUpload,btnBrower,btnCancel;
	private JProgressBar tienTrinh = new JProgressBar(0,100); //// Phải xin vùng nhớ trước do phương thức setValueProcess cần dùng
	private JLabel lbThongBao;
	private JFrame f;
	private String URL = ""; 
	private String pathh = null;
	private JTextField txtSize = new JTextField();
	private File file;
	public void giaoDien(){
		f = new JFrame("Tải lên");
		f.setLayout(null);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    } catch (Exception evt) {
	    	evt.printStackTrace();
	    }
		JLabel lbUrl = new JLabel("URL:");
		lbUrl.setBounds(70,10, 80, 25);
		txtPathHost.setBounds(110, 10, 350, 25);
		
		btnUpload= new JButton("Tải lên");
		btnUpload.setIcon(new ImageIcon("./image/up-arrow.png"));
		btnUpload.setBounds(250, 100, 110, 30);
		
		JLabel lbPath= new JLabel("Đường dẫn lưu:");
		lbPath.setBounds(20,45, 100, 25);
		txtBrower = new JTextField();
		txtBrower.setBounds(110, 45, 310, 25);
		txtBrower.setEditable(false);
		btnBrower = new JButton("...");
		btnBrower.setBounds(430,45, 30, 25);
		
		btnCancel = new JButton("Hủy bỏ");
		btnCancel.setBounds(370, 100, 90, 30);
		btnCancel.setIcon(new ImageIcon("./image/cancel.png"));
		
		lbThongBao = new JLabel();
		lbThongBao.setBounds(200,80,300, 20);
		lbThongBao.setForeground(new Color(6, 176, 37));
		
		tienTrinh.setBounds(20, 135, 440, 5);
		tienTrinh.setVisible(false);
		tienTrinh.setValue(0);
		tienTrinh.setMaximum(100);
		tienTrinh.setForeground(new Color(52, 168, 83));
		
		JLabel lbSize = new JLabel("Size:");
		lbSize.setBounds(70, 80, 80, 20);
		txtSize.setBounds(110, 80, 80, 20);
		txtSize.setEditable(false);
		
		f.add(txtSize);
		f.add(lbSize);
		f.add(tienTrinh);
		f.add(lbThongBao);
		f.add(btnCancel);
		f.add(lbPath);
		f.add(txtBrower);
		f.add(btnBrower);
		f.add(btnUpload);
		f.add(lbUrl);
		f.add(txtPathHost);
		
		btnBrower.addActionListener(this);
		btnCancel.addActionListener(this);
		btnUpload.addActionListener(this);
		f.getContentPane().setBackground(Color.white);
		f.setSize(500, 180);
		f.setVisible(true);
	}
	public void setURL(String url){
		txtPathHost.setText(url);
	}
	public void setSize(String size){
		txtSize.setText(size);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		lbThongBao.setText(null);
		if(e.getSource() == btnBrower){
			fileChooserComputer = new JFileChooser();
			//Thiết lập JFileChooser để cho phép người dùng: chỉ lựa chọn file or chỉ thư mục hoặc cả hai
			fileChooserComputer.setFileSelectionMode(0); 
	        if (fileChooserComputer.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	          txtBrower.setText(fileChooserComputer.getSelectedFile().getAbsolutePath());
	          file = new File(txtBrower.getText());
	          txtSize.setText(String.valueOf(file.length()));
	        }
		}
		if(e.getSource() == btnCancel){
			f.setVisible(false);
			f.dispose();
		}
		if(e.getSource()==btnUpload){
			if(txtPathHost.getText().equals("")){
				lbThongBao.setText("Đường dẫn tải file còn trống!");
			}
			else if(txtBrower.getText().equals("")){
				lbThongBao.setText("Bạn chưa chọn đường dẫn để lưu file!");
			}
			else{
				if(GuiMain.checkConnect){
					new Thread(){
				        public void run() {
				        	//Cài đặt giá trị ban đầu của JProcegarbar = 0;
			        		tienTrinh.setValue(0);
			        		// Xử lý đường dẫn khi đưa lên host
			        		pathh = txtBrower.getText();
			        		pathh = pathh.replace('\\', '/');
			        		String []s = pathh.split("/");
			        		URL = txtPathHost.getText();
			        		URL += "/"+s[s.length-1];
			        		System.out.println(URL);
			        		System.out.println(pathh);
			        		tienTrinh.setVisible(true);
			        		GuiMain.uploadFile(new FileDowUp(URL, pathh,Long.valueOf(txtSize.getText())));
							// Upload xong thì....
							if(tienTrinh.getValue()==100){
								lbThongBao.setForeground(new Color(58,95,205));
								lbThongBao.setText("Tải lên hoàn tất!");
								// Làm mới lại bảng sau khi Update thành công
								GuiMain.deleteAllRow();
								GuiMain.updatePath(txtPathHost.getText());
								GuiMain.showDataWithTable(txtPathHost.getText());
							}
				        }
				      }.start();
				}
				else{
					lbThongBao.setText("Bạn chưa đăng nhập!");		
				}
			}
		}
	}
	public void setValueProcessUp(int i){
		tienTrinh.setValue(i);
	}
}
