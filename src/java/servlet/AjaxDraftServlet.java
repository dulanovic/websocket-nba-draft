package servlet;

import db.DatabaseBroker;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Igrac;

public class AjaxDraftServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        List<Igrac> lista = new ArrayList<>();
        String liga = request.getParameter("liga");
        String kriterijum = request.getParameter("kriterijum");
        if (kriterijum.equals("svi")) {
            lista = DatabaseBroker.getInstance().vratiDostupneIgrace(liga);
        } else {
            lista = DatabaseBroker.getInstance().vratiDostupneIgracePoPoziciji(liga, kriterijum);
            System.out.println(lista.size());
        }

        String tabela = generisiTabelu(lista);
        out.write(tabela);

    }

    private String generisiTabelu(List<Igrac> lista) {
        String tabela = "<table id='tabelaIgraci'>";
        int brojIgracaURedu = 0;
        for (Igrac i : lista) {
            if (brojIgracaURedu % 4 == 0) {
                tabela += "<tr>";
            }
            tabela += "<td class='zaIzbor' id='igrac_id-" + i.getIgracId() + "'><img src='" + i.getIgracSlika() + "' class='slikaIgrac' /><br />#" + i.getBrojDres() + " " + i.getImePrezime() + "<br /><img src='" + i.getTim().getTimLogo() + "' class='timLogo' />" + i.getTim().getTimNaziv() + "</td>";
            brojIgracaURedu++;
            if (brojIgracaURedu % 4 == 0) {
                tabela += "</tr>";
            }
        }
        tabela += "</table>";

        return tabela;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
