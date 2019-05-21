package servlets;

import classes.controllers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/*")
public class Servlet extends HttpServlet {
    private Map<String, Command> router;
    private Command command;
    @Override
    public void init() throws ServletException {
        super.init();
        router = new HashMap<>();

        router.put(RequestTypes.POST+ ":" +  "/log_in", new PostSignInCommand());
        router.put(RequestTypes.POST+ ":" + "/log_out", new PostSignOutCommand());

        router.put(RequestTypes.GET + ":" + "/categories", new GetCategoriesCommand());
        router.put(RequestTypes.POST + ":" +  "/categories", new PostCategoryCommand());
        router.put(RequestTypes.DELETE + ":" +  "/categories/{id}", new DeleteConcreteCategoryCommand());

        router.put(RequestTypes.GET + ":" + "/products", new GetProductsCommand());
        router.put(RequestTypes.GET + ":" + "/products/{id}", new GetConcreteProductCommand());
        router.put(RequestTypes.POST + ":" + "/products", new PostProductCommand());
        router.put(RequestTypes.DELETE + ":" + "/products/{id}", new DeleteConcreteProductCommand());

        router.put(RequestTypes.GET + ":" + "/users", new GetUsersCommand());

        router.put(RequestTypes.GET + ":" + "/blocklist", new GetBlockListCommand());
        router.put(RequestTypes.POST + ":" + "/blocklist", new PostBlockListCommand());
        router.put(RequestTypes.DELETE + ":" + "/blocklist/{id}", new DeleteFromBlockListCommand());

        router.put(RequestTypes.GET + ":" + "/orders", new GetOrdersCommand());
        router.put(RequestTypes.POST + ":" + "/orders", new PostOrderCommand());
        router.put(RequestTypes.PUT + ":" + "/orders/{id}", new PutConcreteOrderCommand());

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("requestType", RequestTypes.GET);
        try {
            routing(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("requestType", RequestTypes.POST);
        try {
            routing(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("requestType", RequestTypes.PUT);
        try {
            routing(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("requestType", RequestTypes.DELETE);
        try {
            routing(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "token");
    }

    protected void routing(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {

        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "token");



        try {
            if(req.getPathInfo().substring(1).contains("/")){

                String str = req.getPathInfo().substring(1);
                String id = str.substring(str.indexOf("/")+1);
                req.setAttribute("id", id);



                for (String pattern: router.keySet()) {

//               It makes str get:/products/{id} -> products/{id}
//                and checks if it contains /
                    if(pattern.substring(pattern.indexOf(":/") + 2).contains("/")){

                        if(req.getAttribute("requestType").toString().equals(pattern.split(":")[0])){

                            command = router.get(pattern);


                            resp.getWriter().println(command.execute(req, resp));
                            break;

                        }
                        else{ continue; }
                    }

                }
                return;
            }

            if(req.getParameter("byCategoryName") != null){
                String categoryName = req.getParameter("byCategoryName");
                req.setAttribute("categoryName", categoryName);
                command = router.get(RequestTypes.GET + ":" + "/products");
                resp.getWriter().println(command.execute(req, resp));
                return;

            }
            if(req.getPathInfo().contains("/")){
                for (String pattern: router.keySet()) {
                    if(pattern.substring(pattern.indexOf(":") + 1).equals(req.getPathInfo())){
                        if(req.getAttribute("requestType").toString().equals(pattern.split(":")[0])){

                            command = router.get(pattern);
                            resp.getWriter().println(command.execute(req, resp));
                        }
                        else {continue;}
                    }
                }
                return;
            }

            else{
                resp.getWriter().println("path "+ req.getContextPath() + "doesn`t exist");
            }
        } catch (Exception ex){
            ex.printStackTrace();

            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.writeValueAsString(new RestException(ex.getMessage()));

        }


    }

}
