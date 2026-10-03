package app;

import java.awt.EventQueue;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Categoria;
import model.Producto;
import model.Proveedor;

public class FrmManteProd extends JFrame {

    private JPanel contentPane;

    private JTextArea txtSalida;
    
    private JTable tblProductos;
    
    private JTextField txtCodigo;
    private JComboBox<Categoria> cboCategorias;
    private JComboBox<Proveedor> cboProveedores;
    private JTextField txtDescripcion;
    private JTextField txtStock;
    private JTextField txtPrecio;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrmManteProd frame = new FrmManteProd();
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
    public FrmManteProd() {

        setTitle("Mantenimiento de Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 390);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrar());
        btnRegistrar.setBounds(324, 29, 89, 23);
        contentPane.add(btnRegistrar);

        /*JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 171, 414, 143);
        contentPane.add(scrollPane);

        txtSalida = new JTextArea();
        scrollPane.setViewportView(txtSalida);*/
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 171, 414, 143);
        contentPane.add(scrollPane);

        tblProductos = new JTable();
        scrollPane.setViewportView(tblProductos);

        JButton btnListado = new JButton("Listado");
        btnListado.addActionListener(e -> listado());
        btnListado.setBounds(177, 322, 89, 23);
        contentPane.add(btnListado);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(122, 11, 86, 20);
        contentPane.add(txtCodigo);
        txtCodigo.setColumns(10);

        JLabel lblCodigo = new JLabel("Id. Producto :");
        lblCodigo.setBounds(10, 14, 102, 14);
        contentPane.add(lblCodigo);

        cboCategorias = new JComboBox<Categoria>();
        cboCategorias.setBounds(122, 70, 86, 22);
        contentPane.add(cboCategorias);

        JLabel lblCategoria = new JLabel("Categoría");
        lblCategoria.setBounds(10, 74, 102, 14);
        contentPane.add(lblCategoria);

        JLabel lblNomProducto = new JLabel("Nom. Producto :");
        lblNomProducto.setBounds(10, 45, 102, 14);
        contentPane.add(lblNomProducto);

        txtDescripcion = new JTextField();
        txtDescripcion.setColumns(10);
        txtDescripcion.setBounds(122, 42, 144, 20);
        contentPane.add(txtDescripcion);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(10, 106, 102, 14);
        contentPane.add(lblStock);

        txtStock = new JTextField();
        txtStock.setColumns(10);
        txtStock.setBounds(122, 103, 77, 20);
        contentPane.add(txtStock);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(10, 134, 102, 14);
        contentPane.add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setColumns(10);
        txtPrecio.setBounds(122, 131, 77, 20);
        contentPane.add(txtPrecio);

        JLabel lblProveedores = new JLabel("Proveedor:");
        lblProveedores.setBounds(230, 106, 102, 14);
        contentPane.add(lblProveedores);

        cboProveedores = new JComboBox<Proveedor>();
        cboProveedores.setBounds(300, 104, 120, 22);
        contentPane.add(cboProveedores);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscar());
        btnBuscar.setBounds(324, 63, 89, 23);
        contentPane.add(btnBuscar);

        llenaCombo();
    }


    void llenaCombo() {

        EntityManagerFactory fabrica =
                Persistence.createEntityManagerFactory("mysqlconex");

        EntityManager manager =
                fabrica.createEntityManager();
        try {

            String jpqlCategoria =
                    "SELECT c FROM Categoria c";

            List<Categoria> listaCategorias =
                    manager.createQuery(
                            jpqlCategoria,
                            Categoria.class)
                           .getResultList();

            for (Categoria c : listaCategorias) {
                cboCategorias.addItem(c);
            }
            String jpqlProveedor =
                    "SELECT p FROM Proveedor p";

            List<Proveedor> listaProveedores =
                    manager.createQuery(
                            jpqlProveedor,
                            Proveedor.class)
                           .getResultList();
            for (Proveedor p : listaProveedores) {
                cboProveedores.addItem(p);
            }

        } finally {

            manager.close();
            fabrica.close();
        }
    }

    void registrar() {

        EntityManagerFactory fabrica =
                Persistence.createEntityManagerFactory("mysqlconex");

        EntityManager manager =
                fabrica.createEntityManager();

        try {

            Producto p = new Producto();

            p.setId_prod(txtCodigo.getText());
            p.setDes_prod(txtDescripcion.getText());
            p.setStk_prod(Integer.parseInt(txtStock.getText()));
            p.setPre_prod(Double.parseDouble(txtPrecio.getText()));
            p.setEst_prod(true);

            Categoria categoria =
                    (Categoria) cboCategorias.getSelectedItem();

            Proveedor proveedor =
                    (Proveedor) cboProveedores.getSelectedItem();

            p.setObjCategoria(categoria);
            p.setObjProveedor(proveedor);

            manager.getTransaction().begin();

            manager.persist(p);

            manager.getTransaction().commit();

            txtSalida.setText(
                    "Producto registrado correctamente."
            );

        } catch (Exception e) {

            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }

            txtSalida.setText(
                    "Error al registrar el producto."
            );

            e.printStackTrace();

        } finally {

            manager.close();
            fabrica.close();
        }
    }

    void listado() {

    	 EntityManagerFactory fabrica =
    	            Persistence.createEntityManagerFactory("mysqlconex");

    	    EntityManager manager =
    	            fabrica.createEntityManager();

    	    try {

    	        String jpql =
    	                "SELECT p FROM Producto p";

    	        List<Producto> lista =
    	                manager.createQuery(
    	                        jpql,
    	                        Producto.class)
    	                       .getResultList();

    	        DefaultTableModel modelo =
    	                new DefaultTableModel();

    	        modelo.addColumn("Código");
    	        modelo.addColumn("Producto");
    	        modelo.addColumn("Stock");
    	        modelo.addColumn("Precio");
    	        modelo.addColumn("Categoría");
    	        modelo.addColumn("Proveedor");
    	        modelo.addColumn("Estado");

    	        for (Producto p : lista) {

    	            modelo.addRow(new Object[] {
    	                p.getId_prod(),
    	                p.getDes_prod(),
    	                p.getStk_prod(),
    	                p.getPre_prod(),
    	                p.getObjCategoria().getDescripcion(),
    	                p.getObjProveedor().getNombre_rs(),
    	                p.isEst_prod() ? "Activo" : "Inactivo"
    	            });
    	        }

    	        tblProductos.setModel(modelo);

    	    } finally {

    	        manager.close();
    	        fabrica.close();
    	    }
    }

    void buscar() {

        EntityManagerFactory fabrica =
                Persistence.createEntityManagerFactory("mysqlconex");

        EntityManager manager =
                fabrica.createEntityManager();

        try {

            String codigo = txtCodigo.getText();

            Producto p =
                    manager.find(
                            Producto.class,
                            codigo);

            if (p != null) {

                txtDescripcion.setText(
                        p.getDes_prod()
                );

                txtStock.setText(
                        String.valueOf(
                                p.getStk_prod()
                        )
                );

                txtPrecio.setText(
                        String.valueOf(
                                p.getPre_prod()
                        )
                );

                cboCategorias.setSelectedItem(
                        p.getObjCategoria()
                );

                cboProveedores.setSelectedItem(
                        p.getObjProveedor()
                );

                txtSalida.setText(
                        "Producto encontrado."
                );

            } else {

                txtSalida.setText(
                        "Producto no existe."
                );
            }

        } finally {

            manager.close();
            fabrica.close();
        }
    }
}