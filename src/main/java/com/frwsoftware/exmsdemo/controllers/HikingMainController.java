package com.frwsoftware.exmsdemo.controllers;

import com.frwsoftware.exmsdemo.exception.WebConfigurationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Main controller name", description="Main controller description")
public class HikingMainController {


    @Value("${server.port}")
    private String myPort;

    @Autowired
    private MessageSource messageSource;

    //@Autowired
    //private I18nService i18n;
    private String getLocalizedMessage(String translationKey)
    {
        //Simple holder class that associates a LocaleContext instance with the current thread.
        // The LocaleContext will be inherited by any child threads spawned by the current thread if
        // the inheritable flag is set to true.
        //Used as a central holder for the current Locale in Spring, wherever necessary: for example,
        // in MessageSourceAccessor. DispatcherServlet automatically exposes its current Locale here.
        // Other applications can expose theirs too, to make classes like MessageSourceAccessor automatically
        // use that Locale.
        Locale locale = LocaleContextHolder.getLocale();

        String datePattern = DateTimeFormatterBuilder.getLocalizedDateTimePattern(FormatStyle.MEDIUM,
                FormatStyle.MEDIUM, Chronology.ofLocale(locale), locale);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        //dateTimeFormatter.format(new Date());

        return messageSource.getMessage(translationKey, null, locale);
    }
    @GetMapping(path = "/testrepo")
    public String testrepo( HttpServletRequest request) throws WebConfigurationException {
        log.info("Begin test repo");


        /*
        i18n.setLocaleByLang("en");
        log.info(i18n.getString("menu.about.project"));
        log.info(i18n.getString("basetest"));
        log.info(i18n.getString("sitetest"));
        i18n.setLocaleByLang("ru");
        log.info(i18n.getString("menu.about.project"));
        log.info(i18n.getString("basetest"));
        log.info(i18n.getString("sitetest"));

         */

        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        log.info("localeResolver " + localeResolver.toString());

        Locale currentLocale = request.getLocale();

        log.info("request locale");
        log.debug(currentLocale.toString());//en_US
        log.debug(currentLocale.toLanguageTag());//en-US
        log.debug(currentLocale.getDisplayLanguage()); //English
        log.debug(currentLocale.getDisplayCountry());	//United States
        log.debug(currentLocale.getLanguage());		//en
        log.debug(currentLocale.getCountry());			//US

        String lang = currentLocale.getLanguage();

        log.info("LocaleContextHolder locale");
        currentLocale = LocaleContextHolder.getLocale();
        log.debug(currentLocale.toString());



        //Use MessageSource to get translated messages. This is the default way to get translated messages in Spring Boot.
        Locale locale = currentLocale; //Locale.of("pl","PL");

        //ResourceBundle.clearCache();

        String titleTextWithArgument=messageSource.getMessage("menu.home",new Object[]{"Foo Bar"},locale);
        log.debug("menu.home = " + titleTextWithArgument);

        /*
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("lang/messages");
        messageSource.setDefaultEncoding("UTF-8");
        System.out.println(messageSource.getMessage("hello", null, Locale.ITALIAN));

         */

        log.info("End test ");
        return "OK";
    }
    @Operation(summary = "My summary for method", description="My description for method")
    @GetMapping(path = "/test")
    public String test() throws WebConfigurationException {
        log.info("Begin test controller method");
        log.info("test controller my port: " + myPort);
        if (true){
            throw new WebConfigurationException("My webConfigurationException");
        }
        log.info("End test controller method");
        return "OK";
    }
    @GetMapping(path = "/rtextest")
    public String rtextest() {
        if (true) {
            throw new RuntimeException("My rt ex");
        }
        return "OK";
    }
    @GetMapping(path = "/extest")
    public String extest() throws Exception {
        try {
            if (true) {
                throw new Exception("My ex");
            }
        }
        catch (Exception ex){
            log.error("log Test error", ex);
            throw ex;
        }
        return "OK";
    }

    @GetMapping(path = "/sttest")
    public String sttest() {
        recursiveMethod();
        return "OK";
    }

    public void recursiveMethod() {
        recursiveMethod();
    }

        /*
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale) {
        log.info("Welcome home! The client locale is {}.", locale);
        return "index";
    }

         */



    /*
    @RequestMapping("/images/*.png")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getImage(HttpServletRequest request) throws IOException {
        String context = request.getRequestURI();
        log.debug("Processing image: " + context);
        Resource resource = hikingLandSiteService.getResource(context);
        InputStream in = resource.getInputStream();
        String mimeType = Files.probeContentType(resource.getFile().toPath());//https://www.baeldung.com/java-file-mime-type
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, mimeType);
        return ResponseEntity.ok().headers(responseHeaders)
                .body(new InputStreamResource(in));
    }
     */

}
