package DAO;
import DTO.IMeiProductDTO;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class IMeiProductDAO {
    private Connection conn;
    public IMeiProductDAO(Connection conn) {
        if(conn == null){
            JOptionPane.showMessageDialog(null,"Khong the ket noi co so du lieu","loi",JOptionPane.ERROR_MESSAGE);
        }else{
            this.conn = conn;
        }
        
    }
    public boolean insertIMeiProduct(IMeiProductDTO ImeiProduct) throws SQLException{
        String sql = "INSERT INTO IMei_Product (IMei_No, Product_ID,State) VALUES (?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,ImeiProduct.getImeiNo());
            stmt.setString(2,ImeiProduct.getProductId());
            stmt.setString(3,ImeiProduct.getState());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteIMeiProduct(String imeiNo) throws SQLException{
        String sql = "DELETE FROM IMei_Product WHERE IMei_No=?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,imeiNo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateIMeiProduct(IMeiProductDTO ImeiProduct) throws SQLException {
        String sql = "UPDATE IMei_Product SET IMei_No = ?, Product_ID = ? ,State = ? WHERE IMei_No = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ImeiProduct.getImeiNo());
            stmt.setString(2, ImeiProduct.getProductId());
            stmt.setString(3, ImeiProduct.getState());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<IMeiProductDTO> getAllImeiproduct() throws SQLException {
        ArrayList<IMeiProductDTO> imeiProductList = new ArrayList<>();
        String sql = "SELECT * FROM IMei_Product";

        try(PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            while (rs.next()) {
                IMeiProductDTO imeiProduct = new IMeiProductDTO(
                        rs.getString("IMei_No"),
                        rs.getString("Product_ID"),
                        rs.getString("State")
                );
            }
            return imeiProductList;
        }
    }
    public IMeiProductDTO getIMeiProductByImeiNo(String ImeiNo){
        IMeiProductDTO imeiProduct =null;
        String query = "SELECT * FROM IMei_Product WHERE IMei_No = ?";

        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,ImeiNo);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                imeiProduct = new IMeiProductDTO(
                    rs.getString("IMei_No"),
                    rs.getString("Product_ID"),
                    rs.getString("State")
                );
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return imeiProduct;
    }


}



