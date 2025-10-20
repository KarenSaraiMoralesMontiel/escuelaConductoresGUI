package gov.peru.escuela.conducir.persistencia;

import gov.peru.escuela.conducir.bean.Escuela;

import java.sql.SQLException;
import java.util.List;

public interface EscuelaDB {
    List<Escuela> findAll();
    Boolean insertEscuela(Escuela escuela) throws SQLException;
}
