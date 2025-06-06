package ndys.http.file;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@WebServlet(urlPatterns = "/form-upload")
@MultipartConfig
public class FormUploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path path = Path.of(FormUploadServlet.class.getResource("/html/form-upload.html").getPath());
        String html = Files.readString(path);
        resp.getWriter().println(html);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Part profile = req.getPart("profile");

        Path uploadLocation = Path.of("src/main/upload/" + UUID.randomUUID() + profile.getSubmittedFileName());
        Files.copy(profile.getInputStream(), uploadLocation);

        // Simple response
        // resp.getWriter().println("Hello " + name + ", your profile saved in " + uploadLocation);

        // Response with file
        String html = """
                <!doctype html>
                <html lang="en">
                <head>
                    <title>Form Upload</title>
                </head>
                <body>
                    Name: $name
                    </br>
                    Profile: <img width="400px" height="400px" src="/download?file=$profile"/>
                </body>
                </html>
                """
                .replace("$name", name)
                .replace("$profile", uploadLocation.getFileName().toString());

        resp.getWriter().println(html);
    }
}
