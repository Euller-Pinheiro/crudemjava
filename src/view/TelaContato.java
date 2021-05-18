package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;

public class TelaContato extends JFrame {

	private JPanel contentPane;
	private JLabel lblStatus;
	private JTextField txtId;
	private JTextField txtNome;
	private JTextField txtFone;
	private JTextField txtEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaContato frame = new TelaContato();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaContato() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// ativação do formulário (formulário carregado)
				// status da conexão
				status();
			}
		});
		setTitle("Agenda de Contatos");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaContato.class.getResource("/icones/favicon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(TelaContato.class.getResource("/icones/dberror.png")));
		lblStatus.setBounds(387, 218, 32, 32);
		contentPane.add(lblStatus);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(23, 26, 45, 13);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setBounds(23, 59, 45, 13);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Fone");
		lblNewLabel_2.setBounds(23, 97, 45, 13);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("E-mail");
		lblNewLabel_3.setBounds(23, 136, 45, 13);
		contentPane.add(lblNewLabel_3);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(71, 26, 96, 19);
		contentPane.add(txtId);
		txtId.setColumns(10);

		txtNome = new JTextField();
		txtNome.setBounds(71, 59, 260, 19);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		txtFone = new JTextField();
		txtFone.setBounds(71, 97, 151, 19);
		contentPane.add(txtFone);
		txtFone.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setBounds(71, 136, 260, 19);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		btnRead = new JButton("");
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarContato();
			}
		});
		btnRead.setEnabled(false);
		btnRead.setToolTipText("Pesquisar contato");
		btnRead.setIcon(new ImageIcon(TelaContato.class.getResource("/icones/read.png")));
		btnRead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRead.setBorder(null);
		btnRead.setBackground(SystemColor.control);
		btnRead.setBounds(341, 45, 48, 48);
		contentPane.add(btnRead);

		btnCreate = new JButton("");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirContato();
			}
		});
		btnCreate.setEnabled(false);
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setBorder(null);
		btnCreate.setBackground(SystemColor.control);
		btnCreate.setToolTipText("Adicionar contato");
		btnCreate.setIcon(new ImageIcon(TelaContato.class.getResource("/icones/create.png")));
		btnCreate.setBounds(110, 180, 64, 64);
		contentPane.add(btnCreate);

		btnUpdate = new JButton("");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarContato();
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setToolTipText("Editar contato");
		btnUpdate.setIcon(new ImageIcon(TelaContato.class.getResource("/icones/update.png")));
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setBorder(null);
		btnUpdate.setBackground(SystemColor.control);
		btnUpdate.setBounds(175, 180, 64, 64);
		contentPane.add(btnUpdate);

		btnDelete = new JButton("");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarContato();
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setBorder(null);
		btnDelete.setToolTipText("Excluir contato");
		btnDelete.setIcon(new ImageIcon(TelaContato.class.getResource("/icones/delete.png")));
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setBackground(SystemColor.control);
		btnDelete.setBounds(241, 180, 64, 64);
		contentPane.add(btnDelete);
	} // fim do construtor

	// Criação de um objeto para acessar o método da classe DAO
	DAO dao = new DAO();
	private JButton btnRead;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;

	/**
	 * Status da conexão
	 */
	private void status() {
		try {
			// estabelecer uma conexão
			Connection con = dao.conectar();
			// status
			//System.out.println(con);
			// trocando o ícone do banco(status da conexão)
			if (con != null) {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dbok.png")));
				btnRead.setEnabled(true);
			} else {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dberror.png")));
			}
			// encerrar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Selecionar contato CRUD Read
	 */
	private void selecionarContato() {
		// instrução sql para pesquisar um contato pelo nome
		String read = "select * from contatos where nome = ?";
		try {
			// estabelecer uma conexão
			Connection con = dao.conectar();
			// preparar a instrução sql
			PreparedStatement pst = con.prepareStatement(read);
			// substituir o parâmetro (?) pelo nome do contato
			pst.setString(1, txtNome.getText());
			// resultado (obter os dados do contato pesquisado)
			ResultSet rs = pst.executeQuery();
			// se existir um contato correspondente
			if (rs.next()) {
				txtId.setText(rs.getString(1)); // 1 -idcon
				txtFone.setText(rs.getString(3)); // 3 - fone
				txtEmail.setText(rs.getString(4)); // 4 - email
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
				btnRead.setEnabled(false);
			} else {
				// Criar uma caixa de mensagem no Java
				// JOptionPane.showMessageDialog(null, "Contato inexistente");
				btnCreate.setEnabled(true);
				btnRead.setEnabled(false);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Inserir um novo contato CRUD Create
	 */
	private void inserirContato() {
		// validação dos campos
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do contato");
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fone do contato");
		} else if (txtNome.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nome não pode ter mais que 50 caracteres");
		} else if (txtFone.getText().length() > 15) {
			JOptionPane.showMessageDialog(null, "O campo fone não pode ter mais que 15 caracteres");
		} else if (txtEmail.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo E-mail não pode ter mais que 50 caracteres");
		} else {
			String create = "insert into contatos (nome,fone,email) values (?,?,?)";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(create);
				// substituir os parâmetros(?,?,?) pelo conteúdo das caixas de texto
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtEmail.getText());
				// executar a query (insert no banco de dados)
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Contato adicionado");
				con.close();
				limpar();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Editar contato CRUD Update
	 */
	private void alterarContato() {
		// validação dos campos
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do contato");
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fone do contato");
		} else if (txtNome.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nome não pode ter mais que 50 caracteres");
		} else if (txtFone.getText().length() > 15) {
			JOptionPane.showMessageDialog(null, "O campo fone não pode ter mais que 15 caracteres");
		} else if (txtEmail.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo E-mail não pode ter mais que 50 caracteres");
		} else {
			String update = "update contatos set nome=?,fone=?,email=? where idcon=?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(update);
				// substituir os parâmetros(?,?,?,?) pelo conteúdo das caixas de texto
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtEmail.getText());
				pst.setString(4, txtId.getText());
				// executar a query (insert no banco de dados)
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Contato editado com sucesso");
				con.close();
				limpar();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Excluir contato CRUD Delete
	 */
	private void deletarContato() {
		String delete = "delete from contatos where idcon=?";
		//Confirmação de exclusão do contato
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste contato?","Atenção!",JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtId.getText());
				pst.executeUpdate();				
				limpar();
				JOptionPane.showMessageDialog(null, "Contato excluido");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			limpar();
		}
	}

	/**
	 * Limpar campos e configurar botões
	 */
	private void limpar() {
		// limpar os campos
		txtId.setText(null);
		txtNome.setText(null);
		txtFone.setText(null);
		txtEmail.setText(null);
		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnRead.setEnabled(true);
	}
}
