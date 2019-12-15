package spring.employees;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.email.SendEmailEmp;
import spring.foo.Foo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class EmpController {

    public EmpController() {
        addToList(new Emp( "Janek", 120000, "Radom","m.gielmuda@wp.pl"));
        addToList(new Emp("Zosia", 9000, "Makowiec", "m.gielmuda@wp.pl"));
        addToList(new Emp("Marek", 10000, "Warszawa","m.gielmuda@wp.pl"));
        addToList(new Emp("Krysytna", 13000, "Ryzowice","m.gielmuda@wp.pl"));
    }

    List<Emp> list = new ArrayList<>();


    public void addToList(Emp emp) {

       emp.setId(list.size()+1);
        list.add(emp);

    }

    @RequestMapping("/")
    public String indexGet() {
        return "emp/index";
    }

    @RequestMapping(value = "/empform", method = RequestMethod.GET)
    public ModelAndView showform(Model model) {

        return new ModelAndView("emp/empform", "emp", new Emp());
    }

    @RequestMapping(value = "/save_emp")
    public ModelAndView save(@ModelAttribute(value = "emp") Emp emp) {

        if(emp.getId()<1){
            System.out.println("is add");
            emp.setId(list.size()+1);
            list.add(emp);

        }else{
            Emp emp1= getIdEmply(emp.getId());
            emp1.setName(emp.getName());
            emp1.setDesignation(emp.getDesignation());
            emp1.setSalary(emp.getSalary());
            emp1.setEmail(emp.getEmail());
            SendEmailEmp.sendEmail(emp.getEmail());

        }

        return new ModelAndView("redirect:/viewemp2");
    }

    @RequestMapping(value = "/edit_emp")
    public ModelAndView editMode(@RequestParam(value = "emp_id") String emp_id){

        Emp emp= getIdEmply(Integer.parseInt(emp_id));

        return new ModelAndView("emp/empform", "emp", emp );
//        return new ModelAndView("emp/edit_emp", "list", list);
    }


    @RequestMapping(value = "/delete_emp")
    public ModelAndView delete(@RequestParam(value = "emp_id") String emp_id ) {


        list.remove(getIdEmply(Integer.parseInt(emp_id)));

        return new ModelAndView("redirect:/viewemp2");
    }

    @RequestMapping("/viewemp")
    public ModelAndView viewemp(Model model) {

        return new ModelAndView("emp/viewemp", "list", list);
    }

    @RequestMapping("/viewemp2")
    public ModelAndView viewemp2(Model model) {
        System.out.println("Witaj Marzena !!");
        return new ModelAndView("emp/viewemp2", "list", list);
    }

    private Emp getIdEmply(@RequestParam int id){
        list.stream().filter(f -> f.getId()== id).forEach(System.out::println);
        return  list.stream().filter(f -> f.getId()== id).findFirst().get();
    }
}