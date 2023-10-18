package org.kainos.ea.db;

import org.kainos.ea.cli.Book;
import org.kainos.ea.cli.Member;
import org.kainos.ea.client.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDao implements IDAO<Member> {

    @Override
    public List<Member> getAll() throws FailedToFetchException {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String SQL = "SELECT id, first_name,last_name,address,phone,email,register_date FROM Members";;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            List<Member> members = new ArrayList<>();

            while(rs.next()){
                members.add(new Member(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getDate("register_date")
                ));
            }

            return members;

        } catch (SQLException e) {
            throw new FailedToFetchException("Failed to fetch all members from the database");
        }
    }

    @Override
    public Member getById(int id) throws FailedToFetchException, NotFoundException {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String SQL = "SELECT id, first_name,last_name,address,phone,email,register_date FROM Members WHERE id = " + id;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while(rs.next()){
                return new Member(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getDate("register_date")
                );
            }

            throw new NotFoundException("No member with id: " + id + " found");
        } catch (SQLException e) {
            throw new FailedToFetchException("Failed to fetch member with id: " + id + " from the database");
        }
    }

    @Override
    public int create(Member member) throws FailedToCreateException {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String SQL = "INSERT INTO Members(first_name,last_name,address,phone,email,register_date) VALUES(?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);

            st.setString(1,member.getFirst_name());
            st.setString(2,member.getLast_name());
            st.setString(3,member.getAddress());
            st.setString(4,member.getPhone());
            st.setString(5,member.getEmail());
            st.setDate(6,member.getRegisterDate());

            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();

            if(rs.next()){
                return rs.getInt(1);
            }

            return -1;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToCreateException("Failed to create member with data: " + member.toString());
        }

    }

    @Override
    public void update(int id, Member member) throws FailedToUpdateException {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String SQL = "UPDATE Members SET first_name = ?, last_name = ?, address = ?, phone = ?, email = ?, register_date = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);

            st.setString(1,member.getFirst_name());
            st.setString(2,member.getLast_name());
            st.setString(3,member.getAddress());
            st.setString(4,member.getPhone());
            st.setString(5,member.getEmail());
            st.setDate(6,member.getRegisterDate());
            st.setInt(7,member.getMemberId());

            st.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToUpdateException("Failed to update member with id: " + id);
        }
    }

    @Override
    public void delete(int id) throws FailedToDeleteException {
        try {
            Connection c = DatabaseConnector.getConnection();
            String deleteStatement = "DELETE FROM Members WHERE id = ?";

            PreparedStatement ps = c.prepareStatement(deleteStatement);

            ps.setInt(1,id);

            ps.executeUpdate();
        } catch(SQLException e){
            throw new FailedToDeleteException("Failed to delete member with id: " + id);
        }
    }
}
