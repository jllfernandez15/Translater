package es.diputacion.malaga.translater;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import es.diputacion.malaga.translater.extracciones.UsuAcc;
import es.diputacion.malaga.translater.extracciones.UsuRol;
import es.diputacion.malaga.translater.util.ImageUtil;
import es.diputacion.malaga.translater.util.Utilidades;

public class ScriptLauncher extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		new ScriptLauncher();

	}

	String xlsFile, scriptName, scriptPath = null;
	String space = "";
	String[] codPeticiones = null;
	JButton chooseXls, aceptarScript, cancel, limpiaDialog, limpiaForm,
			crear = null;
	JTextField tfFile, tfScript, usuario, ejercicio, organico, entidad,
			usuariocreacion, fechacreacion, fechafin, tfFullScript = null;

	JComboBox<String> comboPeticiones = null;

	JLabel usuarioCreacionDesc, usuarioDesc, ejercicioDesc, peticionesDesc,
			organicoDesc, entidadDesc, fechacreacionDesc, fechafinDesc,
			labelScript = null;

	JPanel panelCombo = null;

	/**
	 * 
	 */
	public ScriptLauncher() {
		super();

		setIconImage(ImageUtil.getImageFromJar("deploy.gif").getImage());
		this.setTitle("Generar Script");
		setLayout(null);

		showDialog();

	}

	/**
	 * 
	 */
	private void showDialog() {

		int width = 480;
		int height = 220;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;

		JPanel pane = new JPanel();

		pane.setLayout(null);

		createComponents();

		addComponents(pane);

		addListeners();

		this.setVisible(true);

		pane.setBounds(0, 0, x + width, y + height);
		this.setBounds(x, y, x + width, y + height);

		this.add(pane);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		pack();
		validate();
		setSize(x, y);

	}

	private void createComponents() {
		String space = "   ";

		chooseXls = new JButton("Seleccionar Plantilla");

		tfFile = new JTextField(30);
		tfFile.setEditable(false);

		labelScript = new JLabel();
		labelScript.setText("Nombre de Script");
		labelScript.setForeground(new java.awt.Color(102, 102, 255));

		tfScript = new JTextField(10);
		tfScript.setBackground(new Color(255, 255, 51));
		tfScript.setForeground(new java.awt.Color(102, 102, 255));

		tfFullScript = new JTextField(30);
		tfScript.setBackground(new Color(255, 255, 51));
		tfScript.setForeground(new java.awt.Color(102, 102, 255));
		tfFullScript.setEditable(false);

		aceptarScript = new JButton("Validar nombre");

		peticionesDesc = new JLabel();
		peticionesDesc.setText(space.concat("Código de Petición"));

		peticionesDesc.setForeground(new java.awt.Color(102, 102, 255));
		comboPeticiones = new JComboBox<String>();

		usuarioDesc = new JLabel();
		usuarioDesc.setText(space.concat("Usuario"));
		usuarioDesc.setForeground(new java.awt.Color(102, 102, 255));
		usuario = new JTextField(10);
		usuario.setBackground(new Color(255, 255, 51));
		usuario.setForeground(new java.awt.Color(102, 102, 255));

		ejercicioDesc = new JLabel();
		ejercicioDesc.setText(space.concat("Ejercicio"));
		ejercicioDesc.setForeground(new java.awt.Color(102, 102, 255));
		ejercicio = new JTextField(10);
		ejercicio.setBackground(new Color(255, 255, 51));
		ejercicio.setForeground(new java.awt.Color(102, 102, 255));

		organicoDesc = new JLabel();
		organicoDesc.setText(space.concat("Orgánico"));
		organicoDesc.setForeground(new java.awt.Color(102, 102, 255));
		organico = new JTextField(10);
		organico.setBackground(new Color(255, 255, 51));
		organico.setForeground(new java.awt.Color(102, 102, 255));

		entidadDesc = new JLabel();
		entidadDesc.setText(space.concat("Entidad"));
		entidadDesc.setForeground(new java.awt.Color(102, 102, 255));
		entidad = new JTextField(10);
		entidad.setBackground(new Color(255, 255, 51));
		entidad.setForeground(new java.awt.Color(102, 102, 255));

		usuarioCreacionDesc = new JLabel();
		usuarioCreacionDesc.setText(space.concat("Usu Crea."));
		usuarioCreacionDesc.setForeground(new java.awt.Color(102, 102, 255));
		usuariocreacion = new JTextField(10);
		usuariocreacion.setBackground(new Color(255, 255, 51));
		usuariocreacion.setForeground(new java.awt.Color(102, 102, 255));

		fechacreacionDesc = new JLabel();
		fechacreacionDesc.setText(space.concat("Fec Crea."));
		fechacreacionDesc.setForeground(new java.awt.Color(102, 102, 255));
		fechacreacion = new JTextField(10);
		fechacreacion.setBackground(new Color(255, 255, 51));
		fechacreacion.setForeground(new java.awt.Color(102, 102, 255));

		fechafinDesc = new JLabel();
		fechafinDesc.setText(space.concat("Fecha Fin"));
		fechafinDesc.setForeground(new java.awt.Color(102, 102, 255));
		fechafin = new JTextField(10);
		fechafin.setBackground(new Color(255, 255, 51));
		fechafin.setForeground(new java.awt.Color(102, 102, 255));

		// Buttons
		crear = new JButton("Crear");
		crear.setEnabled(false);

		cancel = new JButton("Salir");
		limpiaDialog = new JButton("Limpia Todo");
		limpiaForm = new JButton("Limpia Formulario");

	}

	/**
	 * @param pane
	 */
	private void addComponents(JPanel pane) {
		defineComponentAt(pane, chooseXls, 25, 5, 20, 10);
		defineComponentAt(pane, tfFile, 25, 42, 220, 10);

		defineComponentAt(pane, labelScript, 25, 75, 20, 10);
		defineComponentAt(pane, tfScript, 145, 75, 20, 10);
		defineComponentAt(pane, aceptarScript, 437, 75, 20, 10);

		defineComponentAt(pane, tfFullScript, 25, 112, 220, 10);

		defineComponentAt(pane, peticionesDesc, 25, 155, 20, 10);

		panelCombo = addComboboxAt(145, 155, 200, 30, comboPeticiones);
		pane.add(panelCombo);

		defineComponentAt(pane, usuarioDesc, 25, 195, 20, 10);
		defineComponentAt(pane, usuario, 95, 195, 20, 10);

		defineComponentAt(pane, organicoDesc, 250, 195, 20, 10);
		defineComponentAt(pane, organico, 320, 195, 20, 10);

		defineComponentAt(pane, entidadDesc, 475, 195, 20, 10);
		defineComponentAt(pane, entidad, 545, 195, 20, 10);

		defineComponentAt(pane, usuarioCreacionDesc, 25, 225, 20, 10);
		defineComponentAt(pane, usuariocreacion, 95, 225, 20, 10);

		defineComponentAt(pane, fechacreacionDesc, 250, 225, 20, 10);
		defineComponentAt(pane, fechacreacion, 320, 225, 20, 10);

		defineComponentAt(pane, fechafinDesc, 475, 225, 20, 10);
		defineComponentAt(pane, fechafin, 545, 225, 20, 10);

		// Buttons
		defineComponentAt(pane, crear, 25, 325, 20, 10);
		defineComponentAt(pane, limpiaForm, 200, 325, 20, 10);
		defineComponentAt(pane, limpiaDialog, 400, 325, 20, 10);
		defineComponentAt(pane, cancel, 600, 325, 20, 10);

	}

	/**
	 * @param pane
	 * @param comp
	 * @param x
	 * @param y
	 * @param ancho
	 * @param alto
	 */
	private void defineComponentAt(JPanel pane, JComponent comp, int x, int y,
			int ancho, int alto) {
		pane.add(comp);
		comp.setLayout(null);

		Insets insets = pane.getInsets();

		Dimension size = comp.getPreferredSize();
		comp.setBounds(x + insets.left, y + insets.top, size.width + ancho,
				size.height + alto);

	}

	/**
	 * 
	 */
	private void addListeners() {
		// Add Listeners
		chooseXls.addActionListener(this);
		aceptarScript.addActionListener(this);
		comboPeticiones.addActionListener(this);
		usuario.addActionListener(this);
		crear.addActionListener(this);
		cancel.addActionListener(this);
		limpiaDialog.addActionListener(this);
		limpiaForm.addActionListener(this);

	}

	/**
	 * @param width
	 * @param height
	 * @param combo
	 * @return
	 */
	private JPanel addComboboxAt(int x, int y, int ancho, int alto,
			JComboBox<String> combo) {

		JPanel pane = new JPanel();
		pane.setLayout(new FlowLayout());

		combo.setPreferredSize(new Dimension(ancho - 5, alto - 5));
		pane.setBounds(x, y, ancho, alto);
		pane.add(combo);

		return pane;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == cancel) {
			// Exit
			System.exit(0);
		} else if (ae.getSource() == limpiaDialog) {
			// Clean the form
			usuario.setText("");
			ejercicio.setText("");
			organico.setText("");
			entidad.setText("");
			usuariocreacion.setText("");
			fechacreacion.setText("");
			fechafin.setText("");
			xlsFile = "";
			scriptName = "";
			scriptPath = "";
			tfFile.setText(xlsFile);
			tfScript.setText(scriptName);
			tfFullScript.setText("");
			comboPeticiones.removeAllItems();
			crear.setEnabled(false);

		} else if (ae.getSource() == limpiaForm) {
			// Clean the form
			usuario.setText("");
			ejercicio.setText("");
			organico.setText("");
			entidad.setText("");
			usuariocreacion.setText("");
			fechacreacion.setText("");
			fechafin.setText("");

		} else if (ae.getSource() == crear) {
			// Launch the process
			if (isXlsFile(xlsFile) && isNotEmpty(scriptName)) {

				String codPet = codPeticiones[comboPeticiones
						.getSelectedIndex()].replace(" ", "");
				String[] mask = new String[] { codPet, usuario.getText(),
						ejercicio.getText(), organico.getText(), "", "", "",
						entidad.getText(), "", "", fechafin.getText(),
						usuariocreacion.getText(), fechacreacion.getText(), "",
						"" };

				Utilidades.MASK = mask;

				new UsuRol(scriptPath, scriptName.concat(".sql"), xlsFile);

				// If Codigo Operacion == 1
				if ("1".equals(codPet)) {
					new UsuAcc(scriptPath, scriptName.concat(".sql"), xlsFile);
				}

				Utilidades.log(" Generacion correcta con Codigo de Peticion "
						.concat(codPet));
			}

		} else if (ae.getSource() == chooseXls) {
			JFileChooser jfc = new JFileChooser(FileSystemView
					.getFileSystemView().getHomeDirectory());

			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setMultiSelectionEnabled(false);
			// aqui filtro lo que quiero que se cargue
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"XLS files", "xls");
			jfc.setFileFilter(filter);

			File workingDirectory = new File(System.getProperty("user.dir"));
			jfc.setCurrentDirectory(workingDirectory);

			// int returnValue =
			jfc.showOpenDialog(null);

			File f = jfc.getSelectedFile();
			xlsFile = f.getAbsolutePath();

			tfFile.setText(xlsFile);
			if (isXlsFile(xlsFile)) {
				codPeticiones = Utilidades.getCodePeticiones(xlsFile);
				for (String cod : codPeticiones) {
					comboPeticiones.addItem(cod);
				}
			}

			if (isXlsFile(xlsFile) && isNotEmpty(scriptName)) {
				crear.setEnabled(true);
			}
		} else if (ae.getSource() == aceptarScript) {
			scriptName = tfScript.getText();
			if (isNotEmpty(scriptName)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				File workingDirectory = new File(System.getProperty("user.dir"));
				scriptPath = workingDirectory.getAbsolutePath()
						.concat(File.separator).concat("Scripts")
						.concat(File.separator)
						.concat(dateFormat.format(new Date()));

				tfFullScript.setText(scriptPath.concat(File.separator)
						.concat(scriptName).concat(".sql"));
				if (isXlsFile(xlsFile) && isNotEmpty(scriptName)) {
					crear.setEnabled(true);
				}
			}
		}

	}

	/**
	 * @param file
	 * @return
	 */
	private boolean isXlsFile(String file) {
		return isNotEmpty(file) && file.endsWith(".xls");
	}

	/**
	 * @param value
	 * @return
	 */
	private boolean isNotEmpty(String value) {
		return null != value && !("".equals(value));
	}

}
