package gov.peru.escuela.conducir.persistencia;

import gov.peru.escuela.conducir.bean.Escuela;
import gov.peru.escuela.conducir.exception.EscuelaException;
import gov.peru.escuela.conducir.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EscuelaDBImpl implements EscuelaDB {
    private static final String DB_INSERT = "CALL insert_centro(?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DB_SELECT_ALL = "SELECT * FROM get_data_by_table_type(?)";

    @Override
    public List<Escuela> findAll() {
        DbConnection db = new DbConnection();
        try {
            Connection cn = db.getConnection();

            PreparedStatement ps = cn.prepareStatement(DB_SELECT_ALL);
            ps.setString(1, "escuelas");

            ResultSet rs = ps.executeQuery();

            List<Escuela> escuelas = new ArrayList<>();
            while (rs.next()) {
                Escuela escuela = new Escuela();
                escuela.setId_establecimiento(rs.getInt("id_establecimiento"));
                escuela.setNo_ruc(rs.getString("no_ruc"));
                escuela.setNombre_centro(rs.getString("nombre_centro"));
                escuela.setDireccion_centro(rs.getString("direccion_centro"));
                escuela.setDepartamento(rs.getString("departamento"));
                escuela.setProvincia(rs.getString("provincia"));
                escuela.setDistrito(rs.getString("distrito"));
                escuela.setEmail(rs.getString("email"));
                escuela.setPhone(rs.getString("phone"));
                escuela.setEstado_autorizacion(rs.getBoolean("estado_autorizacion"));
                escuelas.add(escuela);
            }

            rs.close();
            ps.close();
            cn.close();
            return escuelas;

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    @Override
    public Boolean insertEscuela(Escuela escuela) {
        DbConnection db = new DbConnection();
        try {
            Connection cn = db.getConnection();


            CallableStatement cs = cn.prepareCall(DB_INSERT);
            cs.setString(1, escuela.getNo_ruc());
            cs.setString(2, escuela.getNombre_centro());
            cs.setString(3, escuela.getDireccion_centro());
            cs.setString(4, escuela.getEmail());
            cs.setString(5, escuela.getPhone());
            cs.setString(6, escuela.getId_distrito());
            cs.setString(7, "escuelas");
            cs.registerOutParameter(8, Types.INTEGER);

            cs.execute();

            // Get the returned ID
            Integer newId = cs.getInt(8);
            System.out.println("Successfully inserted escuela with ID: " + newId);

            cs.close();
            cn.close();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert escuela: " + e.getMessage(), e);
        }
    }
}
