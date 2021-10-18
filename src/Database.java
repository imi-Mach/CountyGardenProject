import java.sql.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Database {
    /**
     * The connection to the database.  When there is no connection, it should
     * be null.  Otherwise, there is a valid open connection
     */
    private Connection mConnection;

    /*
    private PreparedStatement mSelectAll;
    private PreparedStatement mSelectOne;
    private PreparedStatement mDeleteOne;
    private PreparedStatement mInsertOne;
    private PreparedStatement mUpdateOne;
    private PreparedStatement mCreateTable;
    private PreparedStatement mDropTable;
    */

    private PreparedStatement mSelectAllCustomerInfo;
    private PreparedStatement mSelectCustomerInfo;
    private PreparedStatement mSelectAllCustomerPolicies;
    private PreparedStatement mSelectAllCustomerClaims;
    private PreparedStatement mSelectAllCustomerInquires;
    private PreparedStatement mSelectAllCustomerPaymentsTransactions;
    private PreparedStatement mSelectCustomerPolicy;
    private PreparedStatement mSelectCustomerPendingClaims;
    private PreparedStatement mSelectAllCustomerPayments;
    private PreparedStatement mSelectCustomerItems;
    private PreparedStatement mSelectCustomerClaim;
    private PreparedStatement mSelectNewAvaEmployee;
    private PreparedStatement mSelectOldAvaEmployee;
    private PreparedStatement mInsertCustomer;
    private PreparedStatement mInsertIntoPolicy;
    private PreparedStatement mInsertIntoTransaction;
    private PreparedStatement mInsertIntoItem;
    private PreparedStatement mInsertIntoHurt;
    private PreparedStatement mInsertAgent;
    private PreparedStatement mSelectAllAgentInfo;
    private PreparedStatement mSelectAgentInfo;
    private PreparedStatement mSelectAllAgentCustomers;
    private PreparedStatement mSelectAgentRevenu;

    

    /**
     * The Database constructor is private: we only create Database objects 
     * through the getDatabase() method.
     */
    private Database() {
    }

    /**
     * Get a fully-configured connection to the database
     * 
     * @param ip   The IP address of the database server
     * @param port The port on the database server to which connection requests
     *             should be sent
     * @param user The user ID to use when connecting
     * @param pass The password to use when connecting
     * 
     * @return A Database object, or null if we cannot connect properly
     */
    static Database getDatabase(String dbRef, String user, String pass) {
        // Create an un-configured Database object
        Database db = new Database();

        // Give the Database object a connection, fail if we cannot get one
        try {
            Connection conn = DriverManager.getConnection(dbRef, user, pass);
            if (conn == null) {
                System.err.println("Error: DriverManager.getConnection() returned a null object");
                return null;
            }
            db.mConnection = conn;
        } catch (SQLException e) {
            System.err.println("Error: DriverManager.getConnection() threw a SQLException");
            //e.printStackTrace();
            return null;
        }

        // Attempt to create all of our prepared statements.  If any of these 
        // fail, the whole getDatabase() call should fail
        try {
            // NB: we can easily get ourselves in trouble here by typing the
            //     SQL incorrectly.  We really should have things like "tblData"
            //     as constants, and then build the strings for the statements
            //     from those constants.

            // Note: no "IF NOT EXISTS" or "IF EXISTS" checks on table 
            // creation/deletion, so multiple executions will cause an exception

            /*
            db.mCreateTable = db.mConnection.prepareStatement(
                    "CREATE TABLE tblData (id SERIAL PRIMARY KEY, subject VARCHAR(50) "
                    + "NOT NULL, message VARCHAR(500) NOT NULL)");
            db.mDropTable = db.mConnection.prepareStatement("DROP TABLE tblData");

            // Standard CRUD operations
            db.mDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblData WHERE id = ?");
            db.mInsertOne = db.mConnection.prepareStatement("INSERT INTO tblData VALUES (default, ?, ?)");
            db.mSelectAll = db.mConnection.prepareStatement("SELECT id, subject FROM tblData");
            db.mSelectOne = db.mConnection.prepareStatement("SELECT * from tblData WHERE id=?");
            db.mUpdateOne = db.mConnection.prepareStatement("UPDATE tblData SET message = ? WHERE id = ?");
            */

            db.mSelectAllCustomerInfo = db.mConnection.prepareStatement("SELECT * FROM customer");
            db.mSelectCustomerInfo = db.mConnection.prepareStatement("select * FROM customer WHERE id = ?");
            db.mSelectAllCustomerPolicies = db.mConnection.prepareStatement("select * FROM policy WHERE c_id = ?");
            db.mSelectAllCustomerClaims = db.mConnection.prepareStatement("select * FROM claim WHERE c_id = ?");
            db.mSelectAllCustomerInquires = db.mConnection.prepareStatement("select * FROM transaction JOIN policy on policy.id = transaction.id WHERE policy.end_date is NULL AND policy.c_id = ?");
            db.mSelectAllCustomerPaymentsTransactions = db.mConnection.prepareStatement("select * from payment where t_id = ? AND c_id = ?");
            db.mSelectCustomerPolicy = db.mConnection.prepareStatement("select * from policy natural join item where c_id = ? AND p_id = ? AND end_date is null");
            db.mSelectCustomerPendingClaims = db.mConnection.prepareStatement("select * FROM claim where c_id = ? AND STATUS = 'PENDING'");
            db.mSelectAllCustomerPayments = db.mConnection.prepareStatement("select * FROM payment where c_id = ?");
            db.mSelectCustomerItems =   db.mConnection.prepareStatement("select * FROM policy natural join item where c_id = ? AND end_date is null");
            db.mSelectCustomerClaim =   db.mConnection.prepareStatement("select * FROM claim where c_id = ? AND id = ?");
            db.mSelectNewAvaEmployee =  db.mConnection.prepareStatement("SELECT min(employee.id) FROM employee LEFT OUTER JOIN customer on employee.id = customer.e_id where e_id is null");
            db.mSelectOldAvaEmployee =  db.mConnection.prepareStatement("SELECT min(employee.id) FROM employee LEFT OUTER JOIN customer on employee.id = customer.e_id where e_id is not null");
            db.mInsertCustomer =        db.mConnection.prepareStatement("INSERT INTO customer (first_name, last_name, street, city, st, pay_method, e_id) VALUES (?, ?, ?, ?, ?, ?, ?)");
            db.mInsertIntoPolicy =      db.mConnection.prepareStatement("INSERT INTO policy (INSUR_TYPE, START_DATE, END_DATE, C_ID) VALUES (?, SYSDATE, NULL, ?)", new String[]{"ID"});
            db.mInsertIntoTransaction = db.mConnection.prepareStatement("INSERT INTO transaction VALUES(DEFAULT, ?, '31-JAN-22', ?)");
            db.mInsertIntoItem =        db.mConnection.prepareStatement("INSERT INTO item VALUES(DEFAULT, ?, ?, ?)");
            db.mInsertIntoHurt =        db.mConnection.prepareStatement("INSERT INTO hurt VALUES(?, ?)");
            db.mInsertAgent =           db.mConnection.prepareStatement("INSERT INTO employee (first_name, last_name, street, city, st, salary) VALUES (?, ?, ?, ?, ?, ?)");
            db.mSelectAllAgentInfo =    db.mConnection.prepareStatement("select distinct e_id, employee.first_name, employee.last_name FROM employee join customer on employee.id = customer.e_id");
            db.mSelectAgentInfo =       db.mConnection.prepareStatement("select distinct e_id, employee.first_name, employee.last_name FROM employee join customer on employee.id = customer.e_id where e_id = ?");
            db.mSelectAllAgentCustomers = db.mConnection.prepareStatement("select distinct customer.id, customer.first_name, customer.last_name FROM employee join customer on employee.id = customer.e_id where e_id = ?");
            db.mSelectAgentRevenu =     db.mConnection.prepareStatement("select sum(payment.amount_paid) FROM (employee join customer on employee.id = customer.e_id) natural join payment where employee.id = ?");
            //db. = db.mConnection.prepareStatement("");



        } catch (SQLException e) {
            //System.err.println("Error creating prepared statement");
            //e.printStackTrace();
            db.disconnect();
            return null;
        }
        return db;
    }

    /**
     * Close the current connection to the database, if one exists.
     * 
     * NB: The connection will always be null after this call, even if an 
     *     error occurred during the closing operation.
     * 
     * @return True if the connection was cleanly closed, false otherwise
     */
    boolean disconnect() {
        if (mConnection == null) {
            System.err.println("Unable to close connection: Connection was null");
            return false;
        }
        try {
            mConnection.close();
        } catch (SQLException e) {
            //System.err.println("Error: Connection.close() threw a SQLException");
            //e.printStackTrace();
            mConnection = null;
            return false;
        }
        mConnection = null;
        return true;
    }

    int getAvaEmployee() {
        int e_id = -1;
        try {
            ResultSet rs1 = mSelectNewAvaEmployee.executeQuery();
            rs1.next();
            e_id = rs1.getInt("min(employee.id)");
            rs1.close();

            if(e_id != -1){
                return e_id;
            }

            ResultSet rs2 = mSelectOldAvaEmployee.executeQuery();
            rs2.next();
            e_id = rs2.getInt("min(employee.id)");
            rs2.close();

            return e_id;
        } catch (SQLException e) {
            //e.printStackTrace();
            return -1;
        }
    }

    int insertCustomer(String fname, String lname, String street, String city, String state, String payment) {
        List<String> headers = Arrays.asList(new String[]{"ID", "First Name", "Last Name"});
        List<List<String>> res = new ArrayList<>();
        int e_id = -1;
        res.add(headers);

        e_id = getAvaEmployee();

        try {
            mInsertCustomer.setString(1, fname);
            mInsertCustomer.setString(2, lname);
            mInsertCustomer.setString(3, street);
            mInsertCustomer.setString(4, city);
            mInsertCustomer.setString(5, state);
            mInsertCustomer.setString(6, payment);
            mInsertCustomer.setInt(7, e_id);

            ResultSet rs = mInsertCustomer.executeQuery();
            rs.close();
            return 1;
        } catch (SQLException e) {
            //e.printStackTrace();
            return -1;
        }
    }

    int insertAgent(String fname, String lname, String street, String city, String state, float salary) {
        List<String> headers = Arrays.asList(new String[]{"ID", "First Name", "Last Name"});
        List<List<String>> res = new ArrayList<>();
        res.add(headers);

        try {
            mInsertAgent.setString(1, fname);
            mInsertAgent.setString(2, lname);
            mInsertAgent.setString(3, street);
            mInsertAgent.setString(4, city);
            mInsertAgent.setString(5, state);
            mInsertAgent.setFloat(6, salary);

            ResultSet rs = mInsertAgent.executeQuery();
            rs.close();
            return 1;
        } catch (SQLException e) {
            //e.printStackTrace();
            return -1;
        }
    }

    List<List<String>> getAllCustomerInfo() { /* /TODO: Returns all customers in database */
        List<String> headers = Arrays.asList(new String[]{"Customer ID", "First Name", "Last Name"});
        List<List<String>> res = new ArrayList<>();
        res.add(headers);
        try {
            ResultSet rs = mSelectAllCustomerInfo.executeQuery();
            while (rs.next()) {
                List<String> newRow = Arrays.asList(new String[]{""+rs.getInt("ID"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME")});
                res.add(newRow);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    List<List<String>> getAllAgentInfo() { 
        List<String> headers = Arrays.asList(new String[]{"Agent ID", "First Name", "Last Name"});
        List<List<String>> res = new ArrayList<>();
        res.add(headers);
        try {
            ResultSet rs = mSelectAllAgentInfo.executeQuery();
            while (rs.next()) {
                List<String> newRow = Arrays.asList(new String[]{""+rs.getInt(1), rs.getString(2), rs.getString(3)});
                res.add(newRow);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    List<List<String>> getCustomerInfo(int idx) {    /* TODO: Return customer with given id */
        List<String> headers = Arrays.asList(new String[]{"Customer ID", "First Name", "Last Name"});
        List<List<String>> res = new ArrayList<>();
        res.add(headers);
        try {
            mSelectCustomerInfo.setInt(1, idx);
            ResultSet rs = mSelectCustomerInfo.executeQuery();
            while (rs.next()) {
                List<String> newRow = Arrays.asList(new String[]{""+rs.getInt("ID"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME")});
                res.add(newRow);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    List<List<String>> getAgentInfo(int idx) {
        List<String> headers = Arrays.asList(new String[]{"Agent ID", "First Name", "Last Name"});
        List<List<String>> res = new ArrayList<>();
        res.add(headers);
        try {
            mSelectAgentInfo.setInt(1, idx);
            ResultSet rs = mSelectAgentInfo.executeQuery();
            while (rs.next()) {
                List<String> newRow = Arrays.asList(new String[]{""+rs.getInt(1), rs.getString(2), rs.getString(3)});
                res.add(newRow);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    List<List<String>> getAgentCustomers(int idx) {
        List<String> headers = Arrays.asList(new String[]{"Customer", "First Name", "Last Name"});
        List<List<String>> res = new ArrayList<>();
        res.add(headers);
        try {
            mSelectAllAgentCustomers.setInt(1, idx);
            ResultSet rs = mSelectAllAgentCustomers.executeQuery();
            while (rs.next()) {
                List<String> newRow = Arrays.asList(new String[]{""+rs.getInt(1), rs.getString(2), rs.getString(3)});
                res.add(newRow);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    List<List<String>> getAgentRevenu(int idx) {
        List<String> headers = Arrays.asList(new String[]{"Revenu"});
        List<List<String>> res = new ArrayList<>();
        res.add(headers);
        try {
            mSelectAgentRevenu.setInt(1, idx);
            ResultSet rs = mSelectAgentRevenu.executeQuery();
            while (rs.next()) {
                List<String> newRow = Arrays.asList(new String[]{""+rs.getInt(1)});
                res.add(newRow);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    List<List<String>> getAllCustomerPolicies(int idx) {
        List<String> headers = Arrays.asList(new String[]{"Policy ID", "Insurance Type", "Start Date"});
        List<List<String>> res = new ArrayList<>();
        res.add(headers);
        try {
            mSelectAllCustomerPolicies.setInt(1, idx);
            ResultSet rs = mSelectAllCustomerPolicies.executeQuery();
            while (rs.next()) {
                List<String> newRow = Arrays.asList(new String[]{""+rs.getInt("ID"), rs.getString("INSUR_TYPE"), rs.getString("START_DATE")});
                res.add(newRow);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    List<List<String>> getAllCustomerClaims(int idx) {
        List<String> headers = Arrays.asList(new String[]{"Claim ID", "Review Status", "Filing Date", "Adjuster ID"});
        List<List<String>> res = new ArrayList<>();
        res.add(headers);
        try {
            mSelectAllCustomerClaims.setInt(1, idx);
            ResultSet rs = mSelectAllCustomerClaims.executeQuery();
            while (rs.next()) {
                List<String> newRow = Arrays.asList(new String[]{""+rs.getInt("ID"), rs.getString("STATUS"), rs.getString("FILLING_DATE"), rs.getString("E_ID")});
                res.add(newRow);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    List<List<String>> getAllCustomerInquires(int idx) {
        List<String> headers = Arrays.asList(new String[]{"Transaction ID", "Policy ID", "Quote", "Due Date"});
        List<List<String>> res = new ArrayList<>();
        res.add(headers);
        try {
            mSelectAllCustomerInquires.setInt(1, idx);
            ResultSet rs = mSelectAllCustomerInquires.executeQuery();
            while (rs.next()) {
                List<String> newRow = Arrays.asList(new String[]{""+rs.getInt("ID"), ""+rs.getInt("P_ID"), ""+rs.getFloat("QUOTE"), rs.getString("DUE_DATE")});
                res.add(newRow);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    List<List<String>> getCustomerPaymentsTransactions(int idx, int t_id) {
        List<String> headers = Arrays.asList(new String[]{"Transaction ID", "Quote", "Due Date"});
        List<List<String>> res = new ArrayList<>();
        res.add(headers);
        try {
            mSelectAllCustomerPaymentsTransactions.setInt(1, t_id);
            mSelectAllCustomerPaymentsTransactions.setInt(2, idx);
            ResultSet rs = mSelectAllCustomerPaymentsTransactions.executeQuery();
            while (rs.next()) {
                List<String> newRow = Arrays.asList(new String[]{""+rs.getInt("ID"), ""+rs.getFloat("AMOUNT_PAID"), rs.getString("PAID_DATE")});
                res.add(newRow);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    List<List<String>> getCustomerPolicy(int idx, int p_id) {
        List<String> headers = Arrays.asList(new String[]{"Policy ID", "Item ID", "Item Covered", "Estimated Value"});
        List<List<String>> res = new ArrayList<>();
        res.add(headers);
        try {
            mSelectCustomerPolicy.setInt(1, idx);
            mSelectCustomerPolicy.setInt(2, p_id);
            ResultSet rs = mSelectCustomerPolicy.executeQuery();
            while (rs.next()) {
                List<String> newRow = Arrays.asList(new String[]{""+rs.getInt("P_ID"), ""+rs.getInt("ID"), rs.getString("ARTICLE"),""+rs.getFloat("PRICE")});
                res.add(newRow);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    List<List<String>> getCustomerPendingClaims(int idx) {
        List<String> headers = Arrays.asList(new String[]{"Claim ID", "Review Status", "Filing Date"});
        List<List<String>> res = new ArrayList<>();
        res.add(headers);
        try {
            mSelectCustomerPendingClaims.setInt(1, idx);
            ResultSet rs = mSelectCustomerPendingClaims.executeQuery();
            while (rs.next()) {
                List<String> newRow = Arrays.asList(new String[]{""+rs.getInt("ID"), rs.getString("STATUS"), rs.getString("FILLING_DATE")});
                res.add(newRow);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    List<List<String>> insertCustomerPolicy(int idx, String insur_type, float baseRate, String article, String esti_value) {
        List<String> headers = Arrays.asList(new String[]{"Success!"});
        List<List<String>> res = new ArrayList<>();

        res.add(headers);

        try {

            mInsertIntoPolicy.setString(1, insur_type);
            mInsertIntoPolicy.setInt(2, idx);

            mInsertIntoPolicy.executeUpdate();
            ResultSet rs1 = mInsertIntoPolicy.getGeneratedKeys();

            mInsertIntoTransaction.setString(1, rs1.getString("ID"));
            mInsertIntoTransaction.setString(2, ""+baseRate);

            mInsertIntoItem.setString(1, article);
            mInsertIntoItem.setString(2, ""+esti_value);
            mInsertIntoItem.setString(3, rs1.getString("ID"));

            rs1.close();

            ResultSet rs2 = mInsertIntoTransaction.executeQuery();
            rs2.close();

            ResultSet rs3 = mInsertIntoItem.executeQuery();
            rs3.close();
                        
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    List<List<String>> getAllCustomerPayments(int idx) {
        List<String> headers = Arrays.asList(new String[]{"Transaction ID", "Amount Paid", "Paid Date"});
        List<List<String>> res = new ArrayList<>();
        res.add(headers);
        try {
            mSelectAllCustomerPayments.setInt(1, idx);
            ResultSet rs = mSelectAllCustomerPayments.executeQuery();
            while (rs.next()) {
                List<String> newRow = Arrays.asList(new String[]{""+rs.getInt("T_ID"), ""+rs.getFloat("AMOUNT_PAID"), rs.getString("PAID_DATE")});
                res.add(newRow);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    List<List<String>> insertCustomerItem(int p_id, int i_id, String article, float value) {
        List<String> headers = Arrays.asList(new String[]{"Customer ID", "Last Name"});
        return null;
    }

    List<List<String>> insertCustomerClaim(int idx, int i_id,String event) {
        List<String> headers = Arrays.asList(new String[]{"Customer ID", "Last Name"});
        return null;
    }

    List<List<String>> insertCustomerPayment(int idx, int t_id, float value) {
        List<String> headers = Arrays.asList(new String[]{"Customer ID", "Last Name"});
        return null;
    }

    int deleteCustomerPolicy(int idx, int pidx) { /* TODO check to see if delete returns 0 on fail */
        return -1;
    }

    int deleteCustomerItem(int p_id, int i_id) { /* TODO check to see if delete returns 0 on fail */
        return -1;
    }

    List<List<String>> getCustomerItems(int idx) {
        List<String> headers = Arrays.asList(new String[]{"Item ID", "Policy ID"});
        List<List<String>> res = new ArrayList<>();
        res.add(headers);
        try {
            mSelectCustomerItems.setInt(1, idx);
            ResultSet rs = mSelectCustomerItems.executeQuery();
            while (rs.next()) {
                List<String> newRow = Arrays.asList(new String[]{""+rs.getInt("ID"), ""+rs.getInt("P_ID")});
                res.add(newRow);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    List<List<String>> getCustomerClaim(int idx, int cl_id) {
        List<String> headers = Arrays.asList(new String[]{"Claim Description"});
        List<List<String>> res = new ArrayList<>();
        res.add(headers);
        try {
            mSelectCustomerClaim.setInt(1, idx);
            mSelectCustomerClaim.setInt(2, cl_id);
            ResultSet rs = mSelectCustomerClaim.executeQuery();
            while (rs.next()) {
                List<String> newRow = Arrays.asList(new String[]{rs.getString("DESC_INFO")});
                res.add(newRow);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }   
    }

    List<List<String>> updateCustomerClaim(int idx, int cl_id, String status) {
        List<String> headers = Arrays.asList(new String[]{"Customer ID", "Last Name"});
        return null;
    }

}