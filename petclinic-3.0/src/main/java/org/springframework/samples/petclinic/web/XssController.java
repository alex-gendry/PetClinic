package org.springframework.samples.petclinic.web;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class XssController {
    @ResponseBody
    @RequestMapping(value="/xss", method = RequestMethod.GET)
    public void display(@RequestParam(value = "name", defaultValue = "default") String name, HttpServletRequest req, HttpServletResponse resp)  
    {  
        resp.setContentType("text/html");
        try {
            ServletOutputStream out = resp.getOutputStream();
            out.print("hello " + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}
