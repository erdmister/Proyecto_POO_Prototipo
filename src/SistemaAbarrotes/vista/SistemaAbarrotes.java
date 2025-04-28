package src.SistemaAbarrotes.vista;

import javax.swing.*; // Para poder usar GUI
import java.awt.*; // Para colores, fuentes


public class SistemaAbarrotes extends JFrame{

	/** Atributos **/
	private String usuarioActual;
	private String rolActual;
	private JMenuBar menuBar; 
	private JToolBar toolBar; 
	private JPanel panelPrincipal;
	private JPanel loginPanel;

	/** Constructores **/
	public SistemaAbarrotes() {
        setTitle("Super Abarrotes la Esquina - Sistema de Gestión");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mostrarLogin();
    }

    /** Metodos **/
    /* Mostrar ventana de login */
    private void mostrarLogin() {
        loginPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        
        JTextField txtUsuario = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JButton btnLogin = new JButton("Iniciar Sesión");
        
        loginPanel.add(new JLabel("Usuario:"));
        loginPanel.add(txtUsuario);
        loginPanel.add(new JLabel("Contraseña:"));
        loginPanel.add(txtPassword);
        loginPanel.add(new JLabel(""));
        loginPanel.add(btnLogin);
        
        btnLogin.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String password = new String(txtPassword.getPassword());
            
            // Validar credenciales (simulado)
            if (validarCredenciales(usuario, password)) {
                getContentPane().removeAll();
                inicializarInterfaz();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        setContentPane(loginPanel);
        revalidate();
        repaint();
    }


    /* Metodo para similar credenciales, sustituir con BD */
	private boolean validarCredenciales(String usuario, String password) {
		
	    class Credencial {

	    	//Atirbutos
	        String usuario;
	        String password;
	        
	        //Constructor
	        Credencial(String u, String p) {
	            usuario = u;
	            password = p;
	        }
	    }
	    
	    Credencial[] credenciales = {
	        new Credencial("dueno", "dueno123"),
	        new Credencial("gerente", "gerente123"),
	        new Credencial("contador", "contador123"),
	        new Credencial("cajero", "cajero123"),
	        new Credencial("vendedor", "vendedor123")
	    };
	    
	    // En esta parte se implementaria la Base de datos 
	    for (Credencial cred : credenciales) {
	        if (cred.usuario.equals(usuario) && cred.password.equals(password)) {
	            usuarioActual = usuario;
	            rolActual = usuario;
	            return true;
	        }
	    }
	    return false;
	}

	/* Interfaz principal una vez que el usuario inicio sesion */
    private void inicializarInterfaz() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());
        
        menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Opciones");

        JMenuItem itemSalir = new JMenuItem("Cerrar sesión");
        itemSalir.addActionListener(e -> cerrarSesion()); 
        menuArchivo.add(itemSalir); 
        menuBar.add(menuArchivo); 
        setJMenuBar(menuBar); 
        
        toolBar = new JToolBar();
        toolBar.setFloatable(false); 
        toolBar.setPreferredSize(new Dimension(getWidth(), 50)); 
        configurarToolbarPorRol();
             
        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(new JLabel("Bienvenido " + usuarioActual, JLabel.CENTER), BorderLayout.CENTER);
        
        add(toolBar, BorderLayout.PAGE_START);
        add(panelPrincipal, BorderLayout.CENTER);
        
        revalidate();
        repaint();
    }

    /* Metodo para cambiar la barra de herramientas segun el rol */
    private void configurarToolbarPorRol() {
        toolBar.removeAll(); 
        
        switch(rolActual) {
            case "dueno":
                configurarToolbarDueño();
                break;
            case "gerente":
                break;
            // ... otros roles
        }
        
        toolBar.revalidate();
        toolBar.repaint();
        System.out.println("ToolBar components: " + toolBar.getComponentCount());
        if (toolBar.getComponentCount() > 0) {
            Component c = toolBar.getComponent(0);
            System.out.println("First component: " + c);
            if (c instanceof JButton) {
                System.out.println("Button text: " + ((JButton)c).getText());
                System.out.println("Button icon: " + ((JButton)c).getIcon());
            }
        }
    }

	/* Metodo para cerrar sesion */
	private void cerrarSesion() {
	    System.out.println("Cerrando sesión...");
	    mostrarLogin(); 
	    if (panelPrincipal.isVisible()) { 
	        panelPrincipal.setVisible(false);
	    }
	    setContentPane(loginPanel); 
	    revalidate();
	    repaint(); 
	}

    /* Metodos y configuraciones para la ventana de dueño */
    private void configurarToolbarDueño() {
        JButton btnFinanciero = new JButton("Financiero");
        
        btnFinanciero.setToolTipText("Reporte Financiero");
        btnFinanciero.addActionListener(e -> mostrarFormularioReporteFinanciero());
        
        btnFinanciero.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnFinanciero.setHorizontalTextPosition(SwingConstants.CENTER);
        btnFinanciero.setFocusPainted(false);
        
        toolBar.add(btnFinanciero);
        toolBar.addSeparator();
    }

    /* Métodos para mostrar formularios (Dueño) */
    private void mostrarFormularioReporteFinanciero() {
        System.out.println("Mostrando reporte financiero...");

        JPanel panel = new JPanel(new BorderLayout());
        
        panel.add(new JLabel("Reporte Financiero - " + rolActual, JLabel.CENTER), BorderLayout.NORTH);
        
        // Igual ocuparia BD
        String[] columnas = {"Concepto", "Monto"};
        Object[][] datos = {
            {"Ventas Totales", "$50,000"},
            {"Costos", "$30,000"},
            {"Ganancias", "$20,000"}
        };
        
        JTable tabla = new JTable(datos, columnas);
        
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        
        panelPrincipal.removeAll();
        panelPrincipal.add(panel, BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    /** Metodo para probar clase **/
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SistemaAbarrotes().setVisible(true);
        });
    }
}

