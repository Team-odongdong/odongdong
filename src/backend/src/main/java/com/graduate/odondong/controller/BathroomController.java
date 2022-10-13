package com.graduate.odondong.controller;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.dto.BathroomRequestDto;
import com.graduate.odondong.dto.CoordinateInfoDto;
import com.graduate.odondong.service.BathroomService.BathroomService;
import com.graduate.odondong.util.ChangeByGeocoder;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BathroomController {

    private final BathroomService bathroomService;
    private final ChangeByGeocoder changeByGeocoder;

    @ResponseBody
    @GetMapping("/")
    public List<Bathroom> AllBathroomList () {
        return bathroomService.bathroomList();
    }

    @ResponseBody
    @GetMapping("/bathroom")
    public List<Bathroom> RegisterBathroomList () {
        return bathroomService.RegisterBathroomList();
    }

    @ResponseBody
    @PostMapping("/api/bathroom/add")
    public String RegisterBathroomRequest (@RequestBody BathroomRequestDto bathroomRequestDto) {
        return bathroomService.RegisterBathroomRequest(bathroomRequestDto);
    }

    @GetMapping("/not-register-bathroom")
    public String NotRegisterBathroomList (Model model) {
        List<Bathroom> bathrooms = bathroomService.NotRegisterBathroomList();
        model.addAttribute("bathrooms", bathrooms);
        return "register";
    }

    @PostMapping("/register-bathroom")
    public String RegisterBathroom(@RequestParam("id") Long id){
        bathroomService.UpdateBathroom(id);
        return "redirect:/not-register-bathroom";
    }

    @DeleteMapping("/register-bathroom")
    @ResponseBody
    public String DeleteBathroom(@RequestParam("id") Long id){
        bathroomService.DeleteBathroom(id);
        return "Delete";
    }
    @ResponseBody
    @GetMapping("/api/getBathroomInfo")
    public CoordinateInfoDto AllAddressInfo(@RequestParam("longitude") Double x, @RequestParam("latitude") Double y) throws JSONException {
        return changeByGeocoder.getAddressByCoordinate(x, y);
    }

    @ResponseBody
    @GetMapping("/api/bathroom/list")
    public List<Bathroom> get1kmBathroom(@RequestParam("longitude") Double x, @RequestParam("latitude") Double y) {
        return bathroomService.get1kmByLongitudeLatitude(x, y);
    }

}
