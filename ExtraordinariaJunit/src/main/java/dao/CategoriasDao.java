package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
 
public class CategoriasDao {

    private Connection conexion = null;

    //establecemos la conexion
    public CategoriasDao() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/neptuno", "root", "");
                                
        } catch (SQLException e) {
            System.out.println("Error al conectar..." + e.getMessage());
        }
    }

    //comprobamos la conexion 
    public Connection getConexion() {
        return conexion;
        
    }

    //Este es el read
    public Categoria read(Integer idCategoria) {
        Categoria categoria = null;
        PreparedStatement stmt = null;

        if (this.conexion == null) {
            return null;
        }

        try {
            String select = "SELECT * FROM categorias WHERE id = ?";
            stmt = conexion.prepareStatement(select);
            stmt.setInt(1, idCategoria);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                categoria = new Categoria(
                        rs.getInt("id"),
                        rs.getString("categoria"),
                        rs.getString("descripcion")
                );
            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        

        return categoria;
    }

    //CREAR una categoria. Este método devuelve el id del registro insertado
    public Integer insert(Categoria categoria) {

        Integer resultado = null;
        PreparedStatement stmt = null;
        Integer ultimoID = null;

        if (this.conexion == null || categoria == null) {
            return null;
        }

        try {
            String sql = "INSERT INTO categorias "
                    + "(categoria, descripcion) "
                    + "VALUES (?, ?)";

            stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            stmt.setInt(1, empleado.getCodigoEmpleado());

            stmt.setString(1, categoria.getCategoria());
            stmt.setString(2, categoria.getDescripcion());

            if (stmt.executeUpdate() > 0) {
                // Ahora leo el valor de la última clave insertada
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    resultado = rs.getInt(1);
                }

            };

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
        System.out.println(resultado);
        return resultado;
        
    }

    //UPDATE
    public Integer update(Categoria categoria) {
        Integer resultado = 0;
        PreparedStatement stmt = null;

        if (this.conexion == null || categoria == null) {
            return resultado;
        }

        try {
            String sql = "UPDATE categorias SET categoria = ?, descripcion = ? WHERE id = ?";

            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, categoria.getCategoria());
            stmt.setString(2, categoria.getDescripcion());

            stmt.setInt(3, categoria.getId());

            resultado = stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
        return resultado;
    }

    //DELETE
    public Integer delete(Integer idCategoria) {
        Integer resultado = 0;
        PreparedStatement stmt = null;

        try {
            String sql = "DELETE FROM categorias WHERE id = ?";

            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idCategoria);

            resultado = stmt.executeUpdate();

            stmt.close();

            System.out.println();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultado;
    }

    //LISTAR
    public ArrayList<Categoria> listar(Integer posicion) {
        Categoria categoria = null;
        ArrayList<Categoria> lista = new ArrayList<>();
        PreparedStatement stmt = null;

        if (this.getConexion() == null) {
            return null;
        }

        try {
            String select = "SELECT * FROM categorias  order by categoria LIMIT 10 OFFSET ?";
            stmt = conexion.prepareStatement(select);
            stmt.setInt(1, posicion);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                categoria = new Categoria(
                        rs.getInt("id"),
                        rs.getString("categoria"),
                        rs.getString("descripcion")
                );
                lista.add(categoria);
            }
        } catch (SQLException e) {
            System.out.println("Sentencia incorrecta: " + e.getMessage());
        }

        return lista;

    }
}
