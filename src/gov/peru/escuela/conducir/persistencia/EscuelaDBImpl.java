package gov.peru.escuela.conducir.persistencia;

import gov.peru.escuela.conducir.bean.Escuela;
import gov.peru.escuela.conducir.exception.EscuelaException;
import gov.peru.escuela.conducir.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EscuelaDBImpl implements EscuelaDB {
    private final String DB_INSERT = "INSERT INTO peru_driver_escuelas (no_ruc, nombre_centro, direccion_centro, email, phone, estado_autorizacion, id_distrito) VALUES (?,?,?,?,?,?,?) ";
        private final String DB_SELECT_ALL = "{ ? = call get_data_by_table_type(?) }";
    @Override
    public List<Escuela> findAll(){
        DbConnection db = new DbConnection();
        try {

            //Transaction for cursor to work
            Connection cn = db.getConnection();

            //Transaction for cursor to work
            cn.setAutoCommit(false);

            //Call
            CallableStatement cs = cn.prepareCall(DB_SELECT_ALL);
            cs.registerOutParameter(1, Types.OTHER);
            cs.setString(2, "escuelas");
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);

            // 3️⃣ Mapear los resultados
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
            cs.close();
            cn.commit(); // ✅ IMPORTANT: Commit the transaction
            cn.close();
            return escuelas;
        } catch (EscuelaException e) {
            throw new EscuelaException(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public Boolean insertEscuela(Escuela escuela) {
        DbConnection db = new DbConnection();


        try {
            Connection cn = db.getConnection();
            PreparedStatement ps = cn.prepareStatement(DB_INSERT);
            ps.setString(1, escuela.getNo_ruc());
            ps.setString(2, escuela.getNombre_centro());
            ps.setString(3, escuela.getDireccion_centro());
            ps.setString(4, escuela.getEmail());
            ps.setString(5, escuela.getPhone());
            ps.setBoolean(6, true);
            ps.setString(7, escuela.getId_distrito());
            ps.executeUpdate();
            ps.close();
            cn.close();
            return true;

        } catch (SQLException e) {
            // Use proper logging instead of System.out
            throw new RuntimeException("Failed to insert escuela: " + e.getMessage(), e);
        }
    }
}
