package io.datajek.springmvc.tennis_player_web;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TennisPlayerController {

    @Autowired
    PlayerService playerService;
    @Autowired
    private Player player;

    @RequestMapping(value="/")
//    @ResponseBody
    public String welcome(){
//        return "welcome to my team";
        return "main-menu";
    }

    @RequestMapping(value="/showPlayerForm")
    public String showPlayerForm(){
        return "player-form";
    }

    @RequestMapping(value="processPlayerForm")
    public String processPlayer(){
        return "player-detail";
    }

    @RequestMapping(value="searchPlayerForm")
    public String searchPlayerForm(){
        return "search-player-form";
    }

//    @RequestMapping(value="/processSearchForm")
//    public String processForm(HttpServletRequest request, Model model){
//        String pName = request.getParameter("name");
//        Player player = playerService.getPlayerByName(pName);
//
//        model.addAttribute("name", player.getName());
//        model.addAttribute("nationality", player.getNationality());
//        model.addAttribute("birthDate", player.getBirthDate());
//        model.addAttribute("titles", player.getTitles());
//
//        return "player-detail";
//
//    }

    @RequestMapping(value="/processSearchForm")
    public String processForm(@RequestParam(value="name", defaultValue = "Djokovic") String pName, Model model){
        Player player = playerService.getPlayerByName(pName);
        model.addAttribute("name", player.getName());
        model.addAttribute("nationality", player.getNationality());
        model.addAttribute("birthDate", player.getBirthDate());
        model.addAttribute("titles", player.getTitles());
        return "player-detail";

    }
}
