 package dao;
 
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

 
public class CategoriasDaoTest {
    
    public CategoriasDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
 
     Categoria  categoria = new Categoria(null, "Chuches", "Prueba chuches");
     CategoriasDao instance = new CategoriasDao(); 
    //comporbacion del READ
     @Test
     public void testRead() 
     {
        System.out.println("read");
        Integer idCategoria = 1;
        
        Categoria result = instance.read(idCategoria);
        assertEquals(result.getId(),idCategoria);
    }
     
    //comporbacion del Insert
     
    @Test
    public void testInsert() {
        System.out.println("insert");
        
       
        CategoriasDao instance = new CategoriasDao();
        Integer result = instance.insert(categoria);
        assertNotNull(result);
    }
     
    //comprobacion UPDATE
    @Test
    public void testUpdate() {
         System.out.println("update");
        Categoria categoria = new  Categoria(1, "CondimentosPrueba", "CondimentosDescripcionPrueba");
        CategoriasDao result = new CategoriasDao();
        result.update(categoria);
        assertNotNull(result);
        
        
        
    }
     
     //comprobacion DELETE
    @Test
    public void testDelete() {
        System.out.println("delete");
        int result = instance.delete(categoria.getId());
        assertNotEquals(result, 0);
        //solo eleimina los que agregas
        
    }
    //comprobacion de LISTAR
    @Test
    public void testListar() 
    {
        System.out.println("listar");
        Integer posicion = 5;
         
        ArrayList<Categoria> result = instance.listar(posicion);
         assertNotNull(result);
    }
    
    
    
    
    
    
    
    
     
}
