package com.frwsoftware.exmsdemo.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

@Controller
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Page controller name", description = "Page controller description")
public class HikingPageController {


    @Autowired
    public TemplateEngine templateEngine;


    /*
    public void fm() throws IOException, TemplateException {

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDirectoryForTemplateLoading(new File("/where/you/store/templates"));
        //cfg.setClassForTemplateLoading();
        // Recommended settings for new projects:
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());

        Map root = new HashMap();
        root.put("user", "Big Joe");
        Product latest = new Product();
        latest.setUrl("products/greenmouse.html");
        latest.setName("green mouse");
        root.put("latestProduct", latest);
        Template temp = cfg.getTemplate("test.ftlh");

        Map<String, Object> templateMap = new HashMap<>();
        StringWriter out = new StringWriter();
        temp.process(templateMap, out);
        out.flush();
        out.getBuffer().toString();

        //https://www.baeldung.com/freemarker-operations
        //https://www.baeldung.com/freemarker-in-spring-mvc-tutorial

    }


    @RequestMapping(method = RequestMethod.GET, value = "/links")
    public ModelAndView links() {
        log.debug("templateEngine = " + templateEngine);

        //https://www.thymeleaf.org/apidocs/thymeleaf/3.0.0.RELEASE/org/thymeleaf/TemplateEngine.html
        //A WebContext would also need HttpServletRequest, HttpServletResponse and ServletContext objects as constructor arguments:
        //final IContext ctx = new WebContext(request, response, servletContext);
        Context ctx = new Context(new Locale("gl", "ES"));
        ctx.setVariable("allItems", "items");

        //https://www.baeldung.com/thymeleaf-in-spring-mvc
        //https://www.thymeleaf.org/doc/articles/springmvcaccessdata.html


        String s = templateEngine.process("links.htm", ctx);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("links.htm");
        return modelAndView;
    }
        */

    @RequestMapping(method = RequestMethod.GET, value = "/maps")
    public String maps() {
        return "maps.htm";
    }

    //  fails with 404 resource not found by default
    //  NO fail, if spring mvc view suffix is set in properties e.g.: spring.mvc.view.suffix=.html
    //  NO fail, if thymeleaf is added, and there is a file login.html in a resources/templates folder
    @RequestMapping(method = RequestMethod.GET, value = "/terms")
    public String terms() {
        return "terms.htm";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "terms.htm";
    }

    @GetMapping(value = "/welcome", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String welcomeAsHTML() {
        return "<html>\n" + "<header><title>Welcome</title></header>\n" +
                "<body>\n" + "Hello world\n" + "</body>\n" + "</html>";
    }

    //https://www.baeldung.com/spring-template-engines
}
/*
public class ReportTemplateEngine {

    private static TemplateEngine instance;

    private ReportTemplateEngine() {}

    public static TemplateEngine getInstance() {
        if(instance == null){
            synchronized (ReportTemplateEngine.class) {
                if(instance == null) {
                    instance = new TemplateEngine();
                    StringTemplateResolver templateResolver = new StringTemplateResolver();
                    templateResolver.setTemplateMode(TemplateMode.HTML);
                    instance.setTemplateResolver(templateResolver);
                }
            }
        }
        return instance;
    }
}
Ugh, Singleton... He uses Spring so defining a StringTemplateResolver Bean, which is Singleton by default, would be better.
 */