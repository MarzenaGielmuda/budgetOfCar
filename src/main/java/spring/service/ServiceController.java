package spring.service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.postgres.PostgreSQLService;


import java.util.ArrayList;
import java.util.List;

import spring.type.*;

import static java.lang.Long.parseLong;

@Controller
public class ServiceController {




    List<Service> listService = new ArrayList<>();

    List<Other> listOther = new ArrayList<>();
    List<Parts> listParts = new ArrayList<>();
    List<PetrolGas> listPetrolGas = new ArrayList<>();

    PostgreSQLService postgresServiceStorage = new PostgreSQLService();


    @RequestMapping("/")
    public String indexGet() {

//        return "budget/serviceIndex";
        return "budget/MainIndex";
    }

    @RequestMapping("/serviceIndex")
    public ModelAndView serviceIndex(Model model) {

        return new ModelAndView("budget/serviceIndex");
}

//    @RequestMapping("/totalBudget")
//    public ModelAndView totalBudget(Model model) {
//
//        return new ModelAndView("budget/totalBudget");
//    }

    @RequestMapping("/totalBudget")
    public ModelAndView totalBudget(Model model) {
        ModelAndView modelAndView = new ModelAndView();

        listService = postgresServiceStorage.getAllServices();
        listOther = postgresServiceStorage.getAllOther();
        listParts = postgresServiceStorage.getAllParts();
        listPetrolGas = postgresServiceStorage.getAllPetrolGas();

        modelAndView.addObject("listParts", listParts);
        modelAndView.addObject("listService", listService);
        modelAndView.addObject("listPetrolGas", listPetrolGas);
        modelAndView.addObject("listOther", listOther);
        modelAndView.setViewName("budget/totalBudget");

        return  modelAndView;
    }

    @RequestMapping("/otherIndex")
    public ModelAndView otherIndex(Model model) {

        return new ModelAndView("budget/otherIndex");
    }
    @RequestMapping("/MainIndex")
    public ModelAndView Mainreturn(Model model) {

        return new ModelAndView("budget/MainIndex");
    }

    @RequestMapping("/partsIndex")
    public ModelAndView partsIndex(Model model) {

        return new ModelAndView("budget/partsIndex");
    }

    @RequestMapping("/petrolGasIndex")
    public ModelAndView petrolGasIndex(Model model) {

        return new ModelAndView("budget/petrolGasIndex");
    }





    @RequestMapping("/serviceGetAll")
    public ModelAndView serviceGetAll(Model model) {


        listService = postgresServiceStorage.getAllServices();
        double sum =0;
        for ( Service service:listService
             ) {
            sum = sum+ service.getValue();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list", listService);
        modelAndView.addObject("sum", sum);
        modelAndView.setViewName("budget/serviceGetAll");
        return modelAndView;
    }

    @RequestMapping(value = "/add_service")

    public ModelAndView save(@ModelAttribute(value = "service") Service service) {

        if (service.getId() < 1) {
            System.out.println("is add");


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


//   @@@@@@@@@@@@@@@@@@@@@@@@@@@@other@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


    @RequestMapping("/otherGetAll")
    public ModelAndView otherGetAll(Model model) {


        listOther = postgresServiceStorage.getAllOther();

        double sum =0;
        for ( Other other:listOther
        ) {
            sum = sum + other.getValue();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list", listOther);
        modelAndView.addObject("sum", sum);
        modelAndView.setViewName("budget/otherGetAll");
        return modelAndView;
}

    @RequestMapping(value = "/add_other")

    public ModelAndView saveOther(@ModelAttribute(value = "other") Other other) {

        if (other.getId() < 1) {
            System.out.println("is add");

            postgresServiceStorage.addOther(other);

        } else {
            postgresServiceStorage.saveEditOther(other);

        }
        return new ModelAndView("redirect:/otherGetAll");

    }
//

    //
    @RequestMapping(value = "/edit_other")

    public ModelAndView editOtherMode(@RequestParam(value = "id") String id){

        Other other = postgresServiceStorage.getOther(id);


        return new ModelAndView("budget/otherAdd", "other", other );
    }



    @RequestMapping(value = "/otherAdd", method = RequestMethod.GET)
    public ModelAndView showOtherEdit(Model model) {
//
        return new ModelAndView("budget/otherAdd", "other", new Other());
    }



    @RequestMapping(value = "/delete_other")
    public ModelAndView deleteOther(@RequestParam(value = "id") String id ) {

        long param= parseLong(id);
        postgresServiceStorage.removeOther(param);

        return new ModelAndView("redirect:/otherGetAll");
    }


    private Other getIdOther(@RequestParam int id) {
        listOther.stream().filter(f -> f.getId() == id).forEach(System.out::println);
        return listOther.stream().filter(f -> f.getId() == id).findFirst().get();
    }

    //   @@@@@@@@@@@@@@@@@@@@@@@@@@@@ parts @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


    @RequestMapping("/partsGetAll")
    public ModelAndView partsGetAll(Model model) {


        listParts = postgresServiceStorage.getAllParts();

        double sum =0;
        for ( Parts parts:listParts
        ) {
            sum = sum + parts.getValue();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list", listParts);
        modelAndView.addObject("sum", sum);
        modelAndView.setViewName("budget/partsGetAll");
        return modelAndView;

    }




    @RequestMapping(value = "/add_parts")

    public ModelAndView saveParts(@ModelAttribute(value = "parts") Parts parts) {

        if (parts.getId() < 1) {
            System.out.println("is add");

            postgresServiceStorage.addParts(parts);

        } else {
            postgresServiceStorage.saveEditParts(parts);

        }
        return new ModelAndView("redirect:/partsGetAll");

    }
//

    //
    @RequestMapping(value = "/edit_parts")

    public ModelAndView editPartsMode(@RequestParam(value = "id") String id){

        Parts parts = postgresServiceStorage.getParts(id);


        return new ModelAndView("budget/partsAdd", "parts", parts );
    }



    @RequestMapping(value = "/partsAdd", method = RequestMethod.GET)
    public ModelAndView showPartsEdit(Model model) {
//
        return new ModelAndView("budget/partsAdd", "parts", new Parts());
    }



    @RequestMapping(value = "/delete_parts")
    public ModelAndView deleteParts(@RequestParam(value = "id") String id ) {

        long param= parseLong(id);
        postgresServiceStorage.removeParts(param);

        return new ModelAndView("redirect:/partsGetAll");
    }


    private Parts getIdParts(@RequestParam int id) {
        listParts.stream().filter(f -> f.getId() == id).forEach(System.out::println);
        return listParts.stream().filter(f -> f.getId() == id).findFirst().get();
    }
    //   @@@@@@@@@@@@@@@@@@@@@@@@@@@@ petrol/gas @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


    @RequestMapping("/petrolGasGetAll")
    public ModelAndView petrolGetAll(Model model) {


        listPetrolGas = postgresServiceStorage.getAllPetrolGas();

        double sum =0;
        for ( PetrolGas petrolGas:listPetrolGas
        ) {
            sum = sum + petrolGas.getValue();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list", listPetrolGas);
        modelAndView.addObject("sum", sum);
        modelAndView.setViewName("budget/petrolGasGetAll");
        return modelAndView;

    }

    @RequestMapping(value = "/add_petrolGas")

    public ModelAndView savePetrol(@ModelAttribute(value = "petrolGas") PetrolGas petrolGas) {

        if (petrolGas.getId() < 1) {
            System.out.println("is add");

            postgresServiceStorage.addPetrolGas(petrolGas);

        } else {
            postgresServiceStorage.saveEditPetrolGas(petrolGas);

        }
        return new ModelAndView("redirect:/petrolGasGetAll");

    }
//

    //
    @RequestMapping(value = "/edit_petrolGas")

    public ModelAndView editPetrolMode(@RequestParam(value = "id") String id){

       PetrolGas petrolGas = postgresServiceStorage.getPetrolGas(id);


        return new ModelAndView("budget/petrolGasAdd", "petrolGas", petrolGas );
    }



    @RequestMapping(value = "/petrolGasAdd", method = RequestMethod.GET)
    public ModelAndView showPetrolEdit(Model model) {
//
        return new ModelAndView("budget/petrolGasAdd", "petrolGas", new PetrolGas());
    }



    @RequestMapping(value = "/delete_petrolGas")
    public ModelAndView deletePetrol(@RequestParam(value = "id") String id ) {

        long param= parseLong(id);
        postgresServiceStorage.removePetrolGas(param);

        return new ModelAndView("redirect:/petrolGasGetAll");
    }


    private PetrolGas getIdPetrol(@RequestParam int id) {
        listPetrolGas.stream().filter(f -> f.getId() == id).forEach(System.out::println);
        return listPetrolGas.stream().filter(f -> f.getId() == id).findFirst().get();
    }

}
