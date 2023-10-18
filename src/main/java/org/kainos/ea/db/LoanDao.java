package org.kainos.ea.db;

import org.kainos.ea.cli.Loan;
import org.kainos.ea.cli.Loan;
import org.kainos.ea.client.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDao implements IDAO<Loan>{
    @Override
    public List<Loan> getAll() throws FailedToFetchException {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String SQL = "SELECT id, member_id, book_id, loan_date, return_date FROM Loans";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            List<Loan> loans = new ArrayList<>();

            while(rs.next()){
                loans.add(new Loan(
                        rs.getInt("id"),
                        rs.getInt("member_id"),
                        rs.getInt("book_id"),
                        rs.getDate("loan_date"),
                        rs.getDate("return_date")
                ));
            }

            return loans;

        } catch (SQLException e) {
            throw new FailedToFetchException("Failed to fetch all books from the database");
        }
    }

    @Override
    public Loan getById(int id) throws FailedToFetchException, NotFoundException {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String SQL = "SELECT id, member_id, book_id, loan_date, return_date FROM Loans WHERE id = " + id;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while(rs.next()){
                return new Loan(
                        rs.getInt("id"),
                        rs.getInt("member_id"),
                        rs.getInt("book_id"),
                        rs.getDate("loan_date"),
                        rs.getDate("return_date")
                );
            }

            throw new NotFoundException("No loan with id: " + id + " found");
        } catch (SQLException e) {
            throw new FailedToFetchException("Failed to fetch loan with id: " + id + " from the database");
        }
    }

    @Override
    public int create(Loan loan) throws FailedToCreateException {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String SQL = "INSERT INTO Loans(member_id,book_id,loan_date,return_date) VALUES(?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);

            st.setInt(1,loan.getMemberId());
            st.setInt(2,loan.getBookId());
            st.setDate(3,loan.getLoanDate());
            st.setDate(4,loan.getReturnDate());

            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();

            if(rs.next()){
                return rs.getInt(1);
            }

            return -1;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToCreateException("Failed to create loan with data: " + loan.toString());
        }

    }

    @Override
    public void update(int id, Loan loan) throws FailedToUpdateException {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String SQL = "UPDATE Loans SET member_id = ?, book_id = ?, loan_date = ?, return_date = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);

            st.setInt(1,loan.getMemberId());
            st.setInt(2,loan.getBookId());
            st.setDate(3,loan.getLoanDate());
            st.setDate(4,loan.getReturnDate());
            st.setInt(4,loan.getLoanId());

            st.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToUpdateException("Failed to update loan with id: " + id);
        }
    }

    @Override
    public void delete(int id) throws FailedToDeleteException {
        try {
            Connection c = DatabaseConnector.getConnection();
            String deleteStatement = "DELETE FROM Loans WHERE id = ?";

            PreparedStatement ps = c.prepareStatement(deleteStatement);

            ps.setInt(1,id);

            ps.executeUpdate();
        } catch(SQLException e){
            throw new FailedToDeleteException("Failed to delete loan with id: " + id);
        }
    }
}
