/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Negocio.EntidadBancaria;
import Negocio.TipoEntidadBancaria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author iTo
 */
public class EntidadBancariaDAOImplJDBC implements EntidadBancariaDAO{

    ConnectionFactory connectionFactory= new ConnectionFactoryImplJDBC();
    
    @Override
    public EntidadBancaria read(Integer idEntidad) {
            
        EntidadBancaria entidadBancaria=null;
        String selectSQL="SELECT idEntidad,codigoEntidad,nombre,cif,tipoEntidadBancaria FROM entidadbancaria WHERE idEntidad = ?";
        
            try{
                Connection conexion=connectionFactory.getConnection();
                PreparedStatement ps=conexion.prepareStatement(selectSQL);
                ps.setInt(1,idEntidad);
                ResultSet rs=ps.executeQuery();
                if(rs.next()){
                    entidadBancaria=new EntidadBancaria();
                    entidadBancaria.setIdEntidad(idEntidad);
                    entidadBancaria.setCodigoEntidad(rs.getString("codigoEntidad"));
                    entidadBancaria.setNombre(rs.getString("nombre"));
                    entidadBancaria.setCif(rs.getString("cif"));
                    entidadBancaria.setTipoEntidad((TipoEntidadBancaria.valueOf(rs.getString("tipoEntidadBancaria"))));
                    if(rs.next()) throw new RuntimeException();
                    
                    conexion.close();
                }
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
           
            return entidadBancaria;
            
    }

    @Override
    public void insert(EntidadBancaria entidadBancaria) {
        
        String insertSQL="INSERT INTO entidadBancaria(idEntidad, codigoEntidad, nombre, cif, tipoEntidadBancaria) VALUES (?,?,?,?,?)";
        
        try{
            
            Connection conexion=connectionFactory.getConnection();
            PreparedStatement ps=conexion.prepareStatement(insertSQL);
            ps.setInt(1,entidadBancaria.getIdEntidad());
            ps.setString(2,entidadBancaria.getCodigoEntidad());
            ps.setString(3,entidadBancaria.getNombre());
            ps.setString(4,entidadBancaria.getCif());
            ps.setString(5,entidadBancaria.getTipoEntidad().toString());
            ps.executeUpdate();
            conexion.close();
            
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        
    }

    @Override
    public void delete(Integer idEntidad) {
        
        String deleteSQL="DELETE FROM entidadbancaria WHERE idEntidad=?";
        
        try{
            Connection conexion=connectionFactory.getConnection();
            PreparedStatement ps=conexion.prepareStatement(deleteSQL);
            ps.setInt(1, idEntidad);
            ps.executeUpdate();
            conexion.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(EntidadBancaria entidadBancaria) {
      
        String updateSQL="UPDATE entidadbancaria SET  codigoEntidad = ?, nombre = ?, cif = ?, tipoEntidadBancaria = ? WHERE idEntidad = ?";
        
        try{
            Connection conexion=connectionFactory.getConnection();
            PreparedStatement ps=conexion.prepareStatement(updateSQL);
            ps.setString(1,entidadBancaria.getCodigoEntidad());
            ps.setString(2,entidadBancaria.getNombre());
            ps.setString(3,entidadBancaria.getCif());
            ps.setString(4,entidadBancaria.getTipoEntidad().toString());
            ps.setInt(5,entidadBancaria.getIdEntidad());
            ps.executeUpdate();
            conexion.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EntidadBancaria> findAll() {
        
        List<EntidadBancaria> entidadesBancarias=new ArrayList<>();
        String selectSQL="SELECT idEntidad,codigoEntidad,nombre,cif,tipoEntidadBancaria FROM entidadbancaria";
        
        try{
            
            EntidadBancaria entidadBancaria=new EntidadBancaria();
            Connection conexion=connectionFactory.getConnection();
            PreparedStatement ps=conexion.prepareStatement(selectSQL);
            ResultSet rs=ps.executeQuery();
            
            while(rs.next()){
                entidadBancaria.setIdEntidad(rs.getInt("idEntidad"));
                entidadBancaria.setCodigoEntidad(rs.getString("codigoEntidad"));
                entidadBancaria.setNombre(rs.getString("nombre"));
                entidadBancaria.setCif(rs.getString("cif"));
                entidadBancaria.setTipoEntidad(TipoEntidadBancaria.valueOf(rs.getString("tipoEntidadBancaria")));
                //añade la entidad al final de la lista maxacando la anterior, si hay 3 entidades, muestra 3 veces la ultima entidad
                entidadesBancarias.add(entidadBancaria);
                             
            }
            
            conexion.close();
            
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
       return entidadesBancarias;
        
    }
    
}
