package ro.vavedem.restapi.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.vavedem.exceptions.VaVedemApiException;
import ro.vavedem.exceptions.VaVedemEmailException;
import ro.vavedem.interfaces.MailService;
import ro.vavedem.models.EmailModel;

import javax.validation.Valid;

/**
 * @author CoruptiaUcide
 */
@Controller
public class MailAPI {

    private static final Logger logger = Logger.getLogger(EmailModel.class);

    @Autowired
    private MailService<EmailModel> mailService;

    @ApiOperation(value = "Trimite email.", tags = {"email"})
    @RequestMapping(value = {"/mail"}, method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> sendEmail(@RequestBody @Valid EmailModel model) {
        logger.info("start: trimitere email");

        try {
            mailService.send(model);
        } catch (VaVedemApiException ex) {
            if (ex instanceof VaVedemEmailException) {
                logger.info(ex.getMessage());
                return new ResponseEntity("error send email", HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }


}
