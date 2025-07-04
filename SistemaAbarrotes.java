import javax.swing.*; // Para poder usar GUI
import java.awt.*; // Para colores, fuentes 
import java.awt.event.*; // Maneja las interacciones del usuario 

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

	/* Metodo para para mandar a menus configurados dependiendo el rol que sea */
	private void configurarMenuPorRol() {
        switch(rolActual) {
            case "dueno":
                agregarMenuDueño();
                break;
            /* Proximos a implementar
            case "gerente":
                agregarMenuGerente();
                break;
            case "contador":
                agregarMenuContador();
                break;
            case "cajero":
                agregarMenuCajero();
                break;
            case "vendedor":
                agregarMenuVendedor();
                break;
            */
        }
    }

    /* Metodo para configurar menús por rol (Dueño) */
    private void agregarMenuDueño() {
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemFinanciero = new JMenuItem("Reporte Financiero");
        JMenuItem itemVentas = new JMenuItem("Reporte de Ventas");
        JMenuItem itemInventario = new JMenuItem("Reporte de Inventario");
        
        itemFinanciero.addActionListener(e -> mostrarFormularioReporteFinanciero());
        /* 
        // Proximos a implementar
        itemVentas.addActionListener(e -> mostrarFormularioReporteVentas());
        itemInventario.addActionListener(e -> mostrarFormularioReporteInventario());
        */
        
        menuReportes.add(itemFinanciero);
         /* 
        // Proximos a implementar
        menuReportes.add(itemVentas);
        menuReportes.add(itemInventario);
        */
        
        menuBar.add(menuReportes);
        
        /*
        // Proximo a implementar
        JMenu menuConfig = new JMenu("Configuración");
        JMenuItem itemUsuarios = new JMenuItem("Gestión de Usuarios");
        JMenuItem itemSucursales = new JMenuItem("Gestión de Sucursales");

        
        menuConfig.add(itemUsuarios);
        menuConfig.add(itemSucursales);
        
        menuBar.add(menuConfig);
        */
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

    private void configurarToolbarDueño() {
        JButton btnFinanciero = new JButton("Financiero");
        
        try {
            // Cargar imagen (asegúrate que la ruta es correcta)
            ImageIcon icono = new ImageIcon("img/imagen_financiera.png");
            if (icono.getImageLoadStatus() == MediaTracker.COMPLETE) {
                Image img = icono.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                btnFinanciero.setIcon(new ImageIcon(img));
                btnFinanciero.setText(""); // Eliminar texto si hay icono
            }
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        
        btnFinanciero.setToolTipText("Reporte Financiero");
        btnFinanciero.addActionListener(e -> mostrarFormularioReporteFinanciero());
        
        btnFinanciero.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnFinanciero.setHorizontalTextPosition(SwingConstants.CENTER);
        btnFinanciero.setFocusPainted(false);
        
        toolBar.add(btnFinanciero);
        toolBar.addSeparator();
    }

	/** Metodo para probar clase **/
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SistemaAbarrotes().setVisible(true);
        });
    }
}

