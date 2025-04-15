import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.PrintWriter;
@WebServlet(urlPatterns = "/player.do")
public class PlayerServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try{

//        PrintWriter writer = response.getWriter();
//        writer.println("<html>");
//        writer.println("<head>" +
//                "<title>Player DB</title>" +
//                "</head>");
//        writer.println("<body>" +
//                "<H2>Welcome to the Tennis Players database!</H2>" +
//                "</body>");
//        writer.println("</html>");
            request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request,response);
        }catch(Exception e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }
    }
}
