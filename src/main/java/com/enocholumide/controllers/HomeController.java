package com.enocholumide.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;
    /**

    @GetMapping(value = "/", name = "Show APIS")
    private ResponseEntity<List<RequestMappingInfo>> showApis() {

        Map<RequestMappingInfo, HandlerMethod> routes = handlerMapping.getHandlerMethods();
        List<RequestMappingInfo> apis = new ArrayList<>();

        routes.forEach((mappingInfo, handlerMethod) -> apis.add(mappingInfo));

        return ResponseEntity.ok().body(apis);
    }
    */

    @RequestMapping(value = "/", name = "Show APIS", method = RequestMethod.GET)
    private String showApis(Model model) {

        Map<RequestMappingInfo, HandlerMethod> routes = handlerMapping.getHandlerMethods();
        List<RequestMappingInfo> apis = new ArrayList<>();

        routes.forEach((mappingInfo, handlerMethod) -> apis.add(mappingInfo));

        model.addAttribute("apis", apis);

        return "home/home";
    }
}
