package com.example.demo.api;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class XssControllerMVC {
    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @ResponseBody
    @RequestMapping(value="/xss", method = RequestMethod.GET)
    public void display(@RequestParam(value = "name", defaultValue = "default") String name, HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setContentType("text/html");
        try {
            ServletOutputStream out = resp.getOutputStream();
            out.print("hello Request Get" + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value="/xssReqPut", method = RequestMethod.PUT)
    public void displayReqPut(@RequestParam(value = "name", defaultValue = "default") String name, HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setContentType("text/html");
        try {
            ServletOutputStream out = resp.getOutputStream();
            out.print("hello Request Put" + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(path = "/xssGet/{name}")
    public void displayGet(@PathVariable("name") String name, HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setContentType("text/html");
        try {
            ServletOutputStream out = resp.getOutputStream();
            out.print("hello xss Get" + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(path = "/xssPost")
    public String xssPostForm(Model model)
    {
        model.addAttribute("xssPayload", new XssPayload());
        return "xssPostSubmit";
    }

    @PostMapping(path = "/xssPost")
    public String xssPostSubmit(@ModelAttribute XssPayload payload, Model model)
    {
        try {
            // This is to generate an SCA XSS finding but does not actually do anything
            ServletOutputStream out = new ServletOutputStream() {
                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {

                }

                @Override
                public void write(int b) throws IOException {

                }
            };
            out.print("XSS test: " + payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("xssPayload", payload);
        return "xssResult";
    }

    /**
     *
     * @param name
     * @param resp
     *  Test to see if you can exploit this in a browser
     *  Payload: http://localhost:8086/xssPut/<script>alert(1)%3B<%252Fscript>
     *      Spring will filter out '</script>' and encoded versions of it
     *      to get around this we use double encoding. '%25' is decoded to % so '%252F' gets decoded and becomes '%2f'
     *      we decode this again in displayPut() and '%sf' becomes '/' giving us an XSS payload
     */

    @PutMapping(path = "/xssPut/{name}")
    public void displayPut(@PathVariable("name") String name, HttpServletResponse resp)
    {
        resp.setContentType("text/html");
        try {
            ServletOutputStream out = resp.getOutputStream();
            String unEncodedName = URLDecoder.decode(name, "UTF-8");
            out.print("<html>xss Put result: " + unEncodedName + "</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
