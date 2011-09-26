package com.davvo.visakarta;

import java.io.IOException;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.davvo.visakarta.server.JDOMapServiceImpl;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;


@SuppressWarnings("serial")
public class ShowMap extends HttpServlet {

    JDOMapServiceImpl mapserv = new JDOMapServiceImpl();
    Configuration cfg = new Configuration();
    
    public void init() {        
        cfg.setServletContextForTemplateLoading(getServletContext(), "");        
        cfg.setObjectWrapper(new DefaultObjectWrapper());        
    }
    
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        
        String id = req.getPathInfo().substring(1);
        
        if (!mapserv.exists(id)) {
            res.sendRedirect("/");
            return;
        }
        
        com.davvo.visakarta.shared.Map map = mapserv.load(id);
        
        Map<String, Object> root = new HashMap<String, Object>();
        
        root.put("map", map);
        
        try {
            
            Template ftl = cfg.getTemplate("showmap.ftl");
            Writer out = res.getWriter();        
            ftl.process(root, out);
            out.flush();
            
        } catch (Exception x) {
            
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, x.getMessage());
            
        }
    }        
    
}
