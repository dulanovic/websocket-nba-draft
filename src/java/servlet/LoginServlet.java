package servlet;

import model.Korisnik;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

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
        /*String korisnickoIme = request.getParameter("korisnickoIme");
        String korisnickaSifra = request.getParameter("korisnickaSifra");

        RequestDispatcher rd = null;
        Korisnik k = DatabaseBroker.getInstance().login(korisnickoIme, korisnickaSifra);
        if (k == null) {
            String poruka = "Pogresno korisnicko ime i/ili sifra!!!";
            request.setAttribute("poruka", poruka);
            rd = getServletContext().getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
            return;
        }
        ServletContext sctx = this.getServletConfig().getServletContext();

        List<Korisnik> listaUlogovanihKorisnika = (List<Korisnik>) sctx.getAttribute("listaUlogovanihKorisnika");
        if (listaUlogovanihKorisnika == null) {
            listaUlogovanihKorisnika = new ArrayList<>();
            listaUlogovanihKorisnika.add(k);
        } else {
            listaUlogovanihKorisnika.add(k);
        }
        sctx.setAttribute("listaUlogovanihKorisnika", listaUlogovanihKorisnika);

        request.setAttribute("ulogovan_korisnik", k);
        
        HttpSession sesija = request.getSession();
        sesija.setAttribute("ulogovan_korisnik", k);
        
        rd = getServletContext().getRequestDispatcher("/WEB-INF/welcome.jsp");
        rd.forward(request, response);
        Enumeration<String> atributi = sctx.getAttributeNames();
        while (atributi.hasMoreElements()) {
            String atribut = atributi.nextElement();
            System.out.println(atribut + " ---> " + sctx.getAttribute(atribut));
        }*/

        HttpSession sesija = request.getSession();
        ServletContext sctx = request.getServletContext();
        RequestDispatcher rd = null;

        Korisnik k = (Korisnik) request.getAttribute("ulogovan_korisnik");

        if (k == null) {
            String poruka = "Pogresno korisnicko ime i/ili sifra!!!";
            request.setAttribute("poruka", poruka);
            rd = getServletContext().getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
        } else {

            sesija.setAttribute("ulogovan_korisnik", k);
            rd = request.getServletContext().getRequestDispatcher("/WEB-INF/welcome.jsp");
            rd.forward(request, response);

            List<Korisnik> listaUlogovanihKorisnika = (List<Korisnik>) sctx.getAttribute("listaUlogovanihKorisnika");
            if (listaUlogovanihKorisnika == null) {
                listaUlogovanihKorisnika = new ArrayList<>();
                listaUlogovanihKorisnika.add(k);
            } else {
                listaUlogovanihKorisnika.add(k);
            }
            sctx.setAttribute("listaUlogovanihKorisnika", listaUlogovanihKorisnika);

            /*Enumeration<String> atributi = sctx.getAttributeNames();
        while (atributi.hasMoreElements()) {
            String atribut = atributi.nextElement();
            System.out.println(atribut + " ---> " + sctx.getAttribute(atribut));
        }*/
        }

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
