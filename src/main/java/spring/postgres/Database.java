package spring.postgres;



import java.sql.*;

public class Database {


    private static String JDBC_URL = "jdbc:postgresql://localhost:5432/bookshelf_db"; //notesdb- to jest nasza baza - możemy nazwę zmieniać wg uznania
    private static String DATABASE_USER ="postgres";
    private static String DATABASE_PASS ="Astra123!";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");

        Connection connection = DriverManager.getConnection(JDBC_URL, DATABASE_USER, DATABASE_PASS);
        Statement statement = connection.createStatement();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "   INSERT INTO books (book_id, title, author,pages_sum,year_of_published,publishing_house)" +
                "VALUES" +
                "   (?,?, ?, ?, ?,?);");

        preparedStatement.setInt(1,8);
        preparedStatement.setString(2,"Kawa na ławę");
        preparedStatement.setString(3,"Kokosza");
        preparedStatement.setInt(4,56);
        preparedStatement.setInt(5,1992);
        preparedStatement.setString(6,"k&M");

        preparedStatement.executeUpdate();



//

//
//        while (resultSet.next()){
//            i++;
//            System.out.println("Kursor aktualnie jest na "+ i + " wierszu.");
//        }

//        @@@@@@@@@@@@@@@@@@@@@@@@@@@ SELECT


//        ResultSet resultSet = statement.executeQuery("SELECT * FROM books LIMIT 5");
//
//        while (resultSet.next()){
//
//           int bookId = resultSet.getInt(1);
//           String title = resultSet.getString(2);//w drugiej kolumnie jest string więc getString - analogicznie w innych wg rodzaju
//           String author = resultSet.getString("author");
//
//
//            System.out.println(bookId + " " + title + " " + author);
//        }

//        /        @@@@@@@@@@@@@@@@@@@@@@@@@@@ SELECT






        statement.close();
        connection.close();



    }

//    boolean resultFlag = statement.execute("INSERT INTO books (book_id, title, author,pages_sum,year_of_published,publishing_house) VALUES (6,'Ogniem i mieczem w wodzie', 'Jan Nowak', 352, 2009, 'W&S');");
//        if (!resultFlag){
//        System.out.println("Ilość dodanych rekordów : " + statement.getUpdateCount());
//
////    }

}
