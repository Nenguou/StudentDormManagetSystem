package Dao;

import Bean.PropertyBean;
import JDBCUtils.JDBCUtils;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class PropertyDao {
    private static Connection connection;

    public PropertyDao(){
        connection = (Connection) JDBCUtils.getConnection();
    }

    /**
     * get all property list
     * @return all property list
     */
    public List<PropertyBean> getAllPropertyList(){
        String sql = "select * from PropertyTable";
        List<PropertyBean> list = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                PropertyBean property=new PropertyBean();
                property.setId(resultSet.getInt(1));
                property.setBuildNumber(resultSet.getInt(2));
                property.setGoodName(resultSet.getString(3));
                property.setPrice(resultSet.getFloat(4));
                list.add(property);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * get this build's property
     * @param buildNumber one build's number
     * @return this build's all property information
     */
    public List<PropertyBean> getBuildProperty(int buildNumber){
        List<PropertyBean> list=new ArrayList<>();
        String sql="select * from PropertyTable where buildNumber=?";
        try{
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1, buildNumber);
            ResultSet resultSet=statement.executeQuery(sql);
            while(resultSet.next())
            {
                PropertyBean property=new PropertyBean();
                property.setId(resultSet.getInt(1));
                property.setBuildNumber(resultSet.getInt(2));
                property.setGoodName(resultSet.getString(3));
                property.setPrice(resultSet.getFloat(4));
                list.add(property);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * change a property information
     * @param property propertyBean
     * @return boolean: update success return true,else false
     */
    public boolean updatePropertyInfo(PropertyBean property){
        try{
            String sql="update PropertyTable set buildNumber=(?),goodName=(?),price=(?) where id=(?)";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setInt(1, property.getBuildNumber());
            statement.setString(2,property.getGoodName());
            statement.setFloat(3,property.getPrice());
            statement.setInt(4, property.getId());
            statement.execute();
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * add a property to PropertyTable
     * @param buildNumber property's build number
     * @param goodName property's goodName
     * @param price property's price
     * @return boolean: insert success true, failed false
     */
    public boolean addPropertyInfo(int buildNumber,String goodName,float price) {
        try{
            String sql = "insert into PropertyTable(buildNumber,goodName,price) values (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,buildNumber);
            statement.setString(2,goodName);
            statement.setFloat(3,price);
            statement.execute();
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
