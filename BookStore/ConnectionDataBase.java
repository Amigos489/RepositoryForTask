import java.sql.DriverManager; 
import java.sql.Connection; 
import java.sql.SQLException; 

public final class ConnectionDataBase { 
    private static ConnectionDataBase instance; 
    private Connection connection; 

    private ConnectionDataBase() {}
    
    public static ConnectionDataBase getInstance() { 
        if (instance == null) { 
            instance = new ConnectionDataBase(); 
        } 
        return instance; 
    } 
    
    public Connection openConnection(String url, String user, String password) { 
        try { 
            connection = DriverManager.getConnection(url, user, password); 
            System.out.println("Успешное подключение к бд."); 
            return connection; 
        } catch (SQLException e) { 
            System.out.println("Не удалось подключиться к бд."); 
            e.printStackTrace(); 
            return null;     
        } 
    } 
    
    public void closeConnection() {
        try { 
            if (connection == null || connection.isClosed()) { 
                return; 
            } 
            connection.close();
            System.out.println("Подключение с бд закрыто."); 
        } catch (SQLException e) { 
            e.printStackTrace(); 
            System.out.println("Не удалось закрыть подключение с бд."); 
        } 
    } 

    public Connection getConnection() { 
        return connection; 
    } 
}