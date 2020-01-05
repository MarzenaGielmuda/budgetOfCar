package spring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.postgres.PostgreSQLService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import spring.type.Service;

import static java.lang.Long.getLong;
import static java.lang.Long.parseLong;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
public class ServiceController {

//    public ServiceController() {
//        addToList(new Emp( "Janek", 120000, "Radom"));
//        addToList(new Emp("Zosia", 9000, "Makowiec"));
//        addToList(new Emp("Marek", 10000, "Warszawa"));
//        addToList(new Emp("Krysytna", 13000, "Ryzowice"));
//    }

    //    List<Emp> list = new ArrayList<>();
    List<Service> listService = new ArrayList<>();

    PostgreSQLService postgresServiceStorage = new PostgreSQLService();


//    public void addToList(Emp emp) {
//
//       emp.setId(list.size()+1);
//        list.add(emp);
//
//    }

    @RequestMapping("/")
    public String indexGet() {

        return "budget/serviceIndex";
//        return "emp/index";
    }

    //    @RequestMapping("/serviceIndex")
//    public ModelAndView viewService(Model model) {
//
//        listService = postgresBookStorage.getAllServices();
//
//
//        return new ModelAndView("budget/serviceGetAll", "list", list);
//    }
    @RequestMapping("/serviceGetAll")
    public ModelAndView serviceGetAll(Model model) {


        listService = postgresServiceStorage.getAllServices();

        return new ModelAndView("budget/serviceGetAll", "list", listService);
    }


    @RequestMapping(value = "/add_service")

    public ModelAndView save(@ModelAttribute(value = "service") Service service) {

        if (service.getId() < 1) {
            System.out.println("is add");

//            if ( listService.size()<1) {
//                service.setId(listService.size() + 2);
//            }else{
//                service.setId(listService.size() + 1);
//            }
            postgresServiceStorage.addService(service);

        } else {
            Service service1 = getIdService((int) service.getId());
            service1.setValue(service.getValue());
            service1.setDescription(service.getDescription());

        }
            return new ModelAndView("redirect:/serviceGetAll");

    }



    @RequestMapping(value = "/serviceAdd", method = RequestMethod.GET)
    public ModelAndView showEdit(Model model) {
//
        return new ModelAndView("budget/serviceAdd", "service", new Service());
    }



//    /delete_service

    @RequestMapping(value = "/delete_service")
    public ModelAndView delete(@RequestParam(value = "id") String id ) {

        long param= parseLong(id);
        postgresServiceStorage.removeService(param);

        return new ModelAndView("redirect:/serviceGetAll");
    }



//    @RequestMapping(value = "/save_emp")
//    public ModelAndView save(@ModelAttribute(value = "emp") Emp emp) {
//
//        if(emp.getId()<1){
//            System.out.println("is add");
//            emp.setId(list.size()+1);
//            list.add(emp);
//
//        }else{
//            Emp emp1= getIdEmply(emp.getId());
//            emp1.setName(emp.getName());
//            emp1.setDesignation(emp.getDesignation());
//            emp1.setSalary(emp.getSalary());
//
//          //  SendEmailEmp.sendEmail(emp.getEmail());
//
//        }
//
//        return new ModelAndView("redirect:/viewemp2");
//    }

    private Service getIdService(@RequestParam int id) {
        listService.stream().filter(f -> f.getId() == id).forEach(System.out::println);
        return listService.stream().filter(f -> f.getId() == id).findFirst().get();
    }
//        return new ModelAndView("budget/serviceAdd", "service", service );
////        return new ModelAndView("emp/edit_emp", "list", list);
//    }
//
//    private Service getIdService(@RequestParam int id){
//        listService.stream().filter(f -> f.getId()== id).forEach(System.out::println);
//        return  listService.stream().filter(f -> f.getId()== id).findFirst().get();
//    }


//    @RequestMapping(value = "/empform", method = RequestMethod.GET)
//    public ModelAndView showform(Model model) {
//
//        return new ModelAndView("emp/empform", "emp", new Emp());
//    }
//
//    @RequestMapping(value = "/save_emp")
//    public ModelAndView save(@ModelAttribute(value = "emp") Emp emp) {
//
//        if(emp.getId()<1){
//            System.out.println("is add");
//            emp.setId(list.size()+1);
//            list.add(emp);
//
//        }else{
//            Emp emp1= getIdEmply(emp.getId());
//            emp1.setName(emp.getName());
//            emp1.setDesignation(emp.getDesignation());
//            emp1.setSalary(emp.getSalary());
//
//          //  SendEmailEmp.sendEmail(emp.getEmail());
//
//        }
//
//        return new ModelAndView("redirect:/viewemp2");
//    }
//
//    @RequestMapping(value = "/edit_emp")
//    public ModelAndView editMode(@RequestParam(value = "emp_id") String emp_id){
//
//        Emp emp= getIdEmply(Integer.parseInt(emp_id));
//
//        return new ModelAndView("emp/empform", "emp", emp );
////        return new ModelAndView("emp/edit_emp", "list", list);
//    }
//
//
//    @RequestMapping(value = "/delete_emp")
//    public ModelAndView delete(@RequestParam(value = "emp_id") String emp_id ) {
//
//
//        list.remove(getIdEmply(Integer.parseInt(emp_id)));
//
//        return new ModelAndView("redirect:/viewemp2");
//    }
//
//    @RequestMapping("/viewemp")
//    public ModelAndView viewemp(Model model) {
//
//        return new ModelAndView("emp/viewemp", "list", list);
//    }
//
//    @RequestMapping("/viewemp2")
//    public ModelAndView viewemp2(Model model) {
//        System.out.println("Witaj Marzena !!");
//        return new ModelAndView("emp/viewemp2", "list", list);
//    }
//
//    private Emp getIdEmply(@RequestParam int id){
//        list.stream().filter(f -> f.getId()== id).forEach(System.out::println);
//        return  list.stream().filter(f -> f.getId()== id).findFirst().get();
//    }
}
