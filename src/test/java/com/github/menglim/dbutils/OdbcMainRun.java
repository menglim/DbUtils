package com.github.menglim.dbutils;

import com.github.menglim.mutils.AppUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class OdbcMainRun {

    public static void main(String[] args) {
        DbConnectionManager.initConnection(
                0,
                DbType.Oracle,
                "" + "jdbc:oracle:thin:@host:1521:XE",
                "" + "password",
                "" + "password"
        );

        DbConnectionManager.initConnection(
                1,
                DbType.MySQL,
                "" + "jdbc:mysql://localhost:3306/database",
                "" + "password",
                "" + "password"
        );

//        DbFactory<BeneficiaryBank> factory = new DbFactory<>();
////
//        BeneficiaryBank beneficiaryBank = factory.selectOne("select * from CLNPROD.FST_PARTICIPANTS where PARTICIPANT_TYPES_ID=5 order by NAME"
//                , resultSet -> {
//                    BeneficiaryBank bank = null;
//                    try {
//                        bank = new BeneficiaryBank(
//                                resultSet.getLong("PARTICIPANT_ID"),
//                                "" + resultSet.getString("PARTICIPANT_CODE"),
//                                "" + resultSet.getString("NAME"),
//                                "" + resultSet.getString("INT_PARTICIPANT_CODE")
//                        );
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                    return bank;
//                });

//        BeneficiaryBank dd = BeneficiaryBank.builder().intParticipantBankCode("").participantCode("").participantId(111L).participantName("Name").build();
//        System.out.println(dd.toString());
//        List<BeneficiaryBank> beneficiaryBank = factory.selectMany(new BeneficiaryBank());
//        System.out.println("Bank: " + beneficiaryBank.get(0).toString());


//        List<User> users = factory.selectMany(User.builder().build(), null);
//        System.out.println("User => " + users.size());
//        users.forEach(user -> {
//            System.out.println(user.toString());
//        });


//        User user1 = User.builder().displayName("admin").build();
//
//        List<User> users = factory.selectMany(User.builder().build(), new Condition.Builder(user1).where("displayName"));
//        System.out.println("User => " + users.size());
//        users.forEach(user -> {
//            System.out.println(user.toString());
//        });


//        String condition = new Condition.Builder<User>(users.get(0)).where("userId").or("username").build();
//        System.out.println("Condition: " + condition);


//        users.get(0).setDisplayName("1111");
//        factory.save(users.get(0));
//        User user = User.builder().displayName("MENGLIM").username("menglim").build();
//        System.out.println("User => " + user.toString());
//        user = factory.save(user);
//        System.out.println("User => " + user.toString());

//        DbFactory<LinkModel> f1 = new DbFactory<>();
//        List<LinkModel> list = f1.selectMany(new LinkModel(), null);

//        DbFactory<BeneficiaryBank> f1 = new DbFactory<>();
//        List<BeneficiaryBank> list = f1.selectMany(BeneficiaryBank.builder().build(), null);
//        System.out.println("List => " + list.toString());
////        f1.selectMany("")

        String oracleDatabaseUrl = "jdbc:oracle:thin:@//host:1521/SID";
//        oracleDatabaseUrl="jdbc:oracle:thin:username/username@host:1521:SID";
//        oracleDatabaseUrl="jdbc:oracle:thin:@host:1521:SID";
//        //"jdbc:oracle:thin:@:10.10.31.10:1521:DBUAT"
//
        DbConnectionManager.initConnection(
                0,
                DbType.Oracle,
                oracleDatabaseUrl,
                "username",
                "username"
        );
        DbFactory<CustomerInformation> factory = new DbFactory<>();
        CustomerInformation response = factory.selectOne("select * from username.Customer_Information WHERE CIFKEY= " + AppUtils.getInstance().sql("3"), new CustomerInformation());
        System.out.println("=> " + response);

//        Connection conn1 = null;
//        Connection conn2 = null;
//        Connection conn3 = null;

//        try {
//            // registers Oracle JDBC driver - though this is no longer required
//            // since JDBC 4.0, but added here for backward compatibility
//            System.out.println("Trying Method 1");
//            Class.forName("oracle.jdbc.OracleDriver");
//
//            // METHOD #1
//            String dbURL1 = "jdbc:oracle:thin:username/username@host:1521:SID";
//            conn1 = DriverManager.getConnection(dbURL1);
//            if (conn1 != null) {
//                System.out.println("Connected with connection #1");
//            }
//
//
//        } catch (ClassNotFoundException ex) {
//            System.out.println("Error Method 1");
//            ex.printStackTrace();
//        } catch (SQLException ex) {
//            System.out.println("Error Method 1");
//            ex.printStackTrace();
//        } finally {
//            try {
//                if (conn1 != null && !conn1.isClosed()) {
//                    conn1.close();
//                }
//                if (conn2 != null && !conn2.isClosed()) {
//                    conn2.close();
//                }
//                if (conn3 != null && !conn3.isClosed()) {
//                    conn3.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println("Error Method 1");
//                ex.printStackTrace();
//            }
//        }


//        try {
//            // registers Oracle JDBC driver - though this is no longer required
//            // since JDBC 4.0, but added here for backward compatibility
//            System.out.println("Trying Method 2");
//            Class.forName("oracle.jdbc.OracleDriver");
//
//            // METHOD #2
//            String dbURL2 = "jdbc:oracle:thin:@host:1521:SID";
//            //jdbc:oracle:thin:@//host:1521/SID also working
//            String username = "username";
//            String password = "username";
//            conn2 = DriverManager.getConnection(dbURL2, username, password);
//            if (conn2 != null) {
//                System.out.println("Connected with connection #2");
//            }
//
//        } catch (ClassNotFoundException ex) {
//            System.out.println("Error Method 2");
//            ex.printStackTrace();
//        } catch (SQLException ex) {
//            System.out.println("Error Method 2");
//            ex.printStackTrace();
//        } finally {
//            try {
//                if (conn1 != null && !conn1.isClosed()) {
//                    conn1.close();
//                }
//                if (conn2 != null && !conn2.isClosed()) {
//                    conn2.close();
//                }
//                if (conn3 != null && !conn3.isClosed()) {
//                    conn3.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println("Error Method 2");
//                ex.printStackTrace();
//            }
//        }


//        try {
//            // registers Oracle JDBC driver - though this is no longer required
//            // since JDBC 4.0, but added here for backward compatibility
//            System.out.println("Trying Method 3");
//            Class.forName("oracle.jdbc.OracleDriver");
//
//            // METHOD #3
//            String dbURL3 = "jdbc:oracle:oci:@SID";
//            Properties properties = new Properties();
//            properties.put("user", "username");
//            properties.put("password", "username");
//            properties.put("defaultRowPrefetch", "20");
//
//            conn3 = DriverManager.getConnection(dbURL3, properties);
//            if (conn3 != null) {
//                System.out.println("Connected with connection #3");
//            }
//
//        } catch (ClassNotFoundException ex) {
//            System.out.println("Error Method 3");
//            ex.printStackTrace();
//        } catch (SQLException ex) {
//            System.out.println("Error Method 3");
//            ex.printStackTrace();
//        } finally {
//            try {
//                if (conn1 != null && !conn1.isClosed()) {
//                    conn1.close();
//                }
//                if (conn2 != null && !conn2.isClosed()) {
//                    conn2.close();
//                }
//                if (conn3 != null && !conn3.isClosed()) {
//                    conn3.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println("Error Method 3");
//                ex.printStackTrace();
//            }
//        }
    }
}
