/*package es.ufv.dis.back.final2025.AASM.PdfUtil;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import es.ufv.dis.back.final2025.AASM.model.Usuario;
import es.ufv.dis.back.final2025.AASM.model.Direccion;
import es.ufv.dis.back.final2025.AASM.model.MetodoPago;

import java.io.FileOutputStream;
import java.util.List;

public class PdfUtil {

    /**
     * Genera un PDF con la lista de usuarios y lo guarda como 'info.pdf'.
     *
    public static void crearPdf(List<Usuario> usuarios) throws Exception {
        // 1) Documento A4
        Document document = new Document(PageSize.A4);  // Document en iText 4.x :contentReference[oaicite:8]{index=8}

        // 2) Asociar PdfWriter a file output
        PdfWriter.getInstance(document, new FileOutputStream("info.pdf"));  // PdfWriter de iText :contentReference[oaicite:9]{index=9}

        document.open();  // open() abre el flujo para añadir contenido :contentReference[oaicite:10]{index=10}

        // 3) Añadir cada usuario
        for (Usuario u : usuarios) {
            // 3a) Datos básicos
            String linea =
                    String.format("ID: %s, Nombre: %s %s, NIF: %s",
                            u.getId(), u.getNombre(), u.getApellidos(), u.getNif());
            Paragraph p = new Paragraph(linea);
            p.setAlignment(Element.ALIGN_LEFT);
            document.add(p);  // add() añade el párrafo al documento :contentReference[oaicite:11]{index=11}

            // 3b) Dirección si existe
            Direccion dir = u.getDireccion();
            if (dir != null) {
                Paragraph pd = new Paragraph(
                        "Dirección: " +
                                dir.getCalle() + " nº" + dir.getNumero() +
                                ", CP " + dir.getCodigoPostal() +
                                ", Piso " + dir.getPisoLetra() +
                                ", " + dir.getCiudad()
                );
                document.add(pd);
            }

            // 3c) Método de pago si existe
            MetodoPago mp = u.getMetodoPago();
            if (mp != null) {
                Paragraph pm = new Paragraph(
                        "Método de pago: " +
                                mp.getNombreAsociado() +
                                " (" + mp.getNumeroTarjeta() + ")"
                );
                document.add(pm);
            }

            // 3d) Separador
            document.add(new Paragraph(" "));
        }

        // 4) Cerrar documento
        document.close();  // close() cierra el flujo y finaliza el PDF :contentReference[oaicite:12]{index=12}
    }
}
*/