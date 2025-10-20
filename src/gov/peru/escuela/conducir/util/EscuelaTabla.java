package gov.peru.escuela.conducir.util;

import gov.peru.escuela.conducir.bean.Escuela;

import java.util.List;
import java.util.StringJoiner;

public class EscuelaTabla {

    public String escuelaHeader() {
        String separator = getSeparator();
        String header = String.format("| %-4s | %-12s | %-12s | %-12s | %-11s | %-25s | %-25s | %-20s | %-15s |",
                "ID", "DEPARTAMENTO", "PROVINCIA", "DISTRITO", "RUC",
                "NOMBRE CENTRO", "DIRECCION", "CONTACTO", "ESTADO");
        return separator + "\n" + header + "\n" + separator;
    }
    public String getSeparator() {
        return "+------+--------------+--------------+--------------+-----------+---------------------------+---------------------------+----------------------+-----------------+";

    }

    public String escuelaRow(Escuela escuela) {
        return String.format("| %-4d | %-12s | %-12s | %-12s | %-11s | %-25s | %-25s | %-20s | %-15s |",
                escuela.getId_establecimiento(),
                truncate(escuela.getDepartamento(), 12),
                truncate(escuela.getProvincia(), 12),
                truncate(escuela.getDistrito(), 12),
                truncate(escuela.getNo_ruc(), 11),
                truncate(escuela.getNombre_centro(), 25),
                truncate(escuela.getDireccion_centro(), 25),
                truncate(escuela.getEmail() + " " + escuela.getPhone(), 20),
                escuela.isEstado_autorizacion() ? "CON AUTORIZACION" : "SIN AUTORIZACION");
    }

    // Helper method to handle long text
    private String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }

    public String escuelaTabla(List<Escuela> escuelaList) {
        StringJoiner tablaData = new StringJoiner("\n");

        tablaData.add(escuelaHeader());


        escuelaList.forEach(escuela -> {
            tablaData.add(escuelaRow(escuela));
        });

        tablaData.add(getSeparator());

        return tablaData.toString();
    }
}
