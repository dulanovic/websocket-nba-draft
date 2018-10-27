package servlet;

import db.DatabaseBroker;
import model.Igrac;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AutosuggestServlet extends HttpServlet {

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
        String parametar = request.getParameter("kriterijum");
        List<Igrac> lista = DatabaseBroker.getInstance().nadjiIgrace(parametar);
        if (lista.isEmpty()) {
            out.write("<strong>Nema igraca po zadatom kriterijumu!</strong>");
            return;
        }
        out.write("<table border='0'><thead style='background-color: #D30000; color: #FFFFFF;'><tr><th></th><th>Broj dresa</th><th>Ime prezime</th><th>Visina</th><th>Pozicija</th><th>Tim</th></tr></thead><tbody>");
        for (Igrac i : lista) {
            out.write("<tr><td><img src='" + i.getIgracSlika() + "' style='width: 200px;' /></td><td>" + i.getBrojDres() + "</td><td>" + i.getImePrezime() + "</td><td>" + i.getVisina() + "</td><td>" + i.getPozicija() + "</td><td><img src='" + i.getTim().getTimLogo() + "' style='width: 100px;' /></td></tr>");
            //out.write("<tr><td><img src='" + i.getIme()+ "' /></td><td>" + i.getIme() + " " + i.getPrezime() + "</td></tr>");
        }
        out.write("</tbody></table>");
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
