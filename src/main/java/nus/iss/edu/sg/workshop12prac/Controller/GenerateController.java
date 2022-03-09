package nus.iss.edu.sg.workshop12prac.Controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import nus.iss.edu.sg.workshop12prac.Exception.RandomNumberException;
import nus.iss.edu.sg.workshop12prac.Model.Generate;

@Controller
public class GenerateController {
    private Logger logger = LoggerFactory.getLogger(GenerateController.class);

    @GetMapping("/")
    public String showGenerateForm(Model model){
        Generate generate = new Generate();
        model.addAttribute("generateObj", generate);
        return "generatePage";
    }

    @PostMapping("/generate")
    public String generateNumbers(@ModelAttribute Generate generate, Model model){
        try{
            logger.info("From the form " + generate.getNumberVal());
            int numberRandomNumbers = generate.getNumberVal();
            if (numberRandomNumbers > 10){
                throw new RandomNumberException();
            }

            String[] imgNumbers = {"1.png","2.png","3.png","4.png","5.png","6.png","7.png","8.png","9.png","10.png"};

            List<String> selectedImg = new ArrayList<String>();
            Random randNum = new Random();
            Set<Integer> uniqueRandomNumber = new LinkedHashSet<Integer>();
            
            while(uniqueRandomNumber.size() < numberRandomNumbers){
                Integer randNumResult = randNum.nextInt(generate.getNumberVal() + 1);
                uniqueRandomNumber.add(randNumResult);
            }

            Iterator<Integer> it = uniqueRandomNumber.iterator();
            Integer currentElem = null;

            while(it.hasNext()){
                currentElem = it.next();
                selectedImg.add(imgNumbers[currentElem.intValue()]);
            }
            model.addAttribute("randNumResult", selectedImg.toArray());
            model.addAttribute("numInputByUser", numberRandomNumbers);
        }catch(RandomNumberException e){
            model.addAttribute("errorMessage", "Number exceeds 10!");
            return "error";
        }
        return "result";
    }    
}
