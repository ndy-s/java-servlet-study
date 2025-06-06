package ndys.http.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/json")
public class JsonServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = """
                {
                    "name": "Hendy",
                    "value": 100
                }
                """;

        // Header setHeader, setIntHeader, setDateHeader
        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().println(json);
    }
}
