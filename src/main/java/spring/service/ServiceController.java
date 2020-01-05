package spring.service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.postgres.PostgreSQLService;


import java.util.ArrayList;
import java.util.List;

import spring.type.Service;

import static java.lang.Long.parseLong;

@Controller
public class ServiceController {




    List<Service> listService = new ArrayList<>();

    PostgreSQLService postgresServiceStorage = new PostgreSQLService();

    @RequestMapping("/")
    public String indexGet() {

        return "budget/serviceIndex";
    }

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
            postgresServiceStorage.saveEditService(service);

        }
            return new ModelAndView("redirect:/serviceGetAll");

    }
//

//
        @RequestMapping(value = "/edit_service")

//        public ModelAndView showEdit1(Model model) {
////
//            return new ModelAndView("budget/serviceAdd", "service", new Service());
//        }


    public ModelAndView editMode(@RequestParam(value = "id") String id){

       Service service = postgresServiceStorage.getService(id);


        return new ModelAndView("budget/serviceAdd", "service", service );
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


    private Service getIdService(@RequestParam int id) {
        listService.stream().filter(f -> f.getId() == id).forEach(System.out::println);
        return listService.stream().filter(f -> f.getId() == id).findFirst().get();
    }
}
