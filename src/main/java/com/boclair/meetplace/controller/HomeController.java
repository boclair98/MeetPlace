package com.boclair.meetplace.controller;

import com.boclair.meetplace.domain.MeetingPurpose;
import com.boclair.meetplace.dto.RecommendationRequest;
import com.boclair.meetplace.dto.RecommendationResult;
import com.boclair.meetplace.service.MeetingPlaceRecommendationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final MeetingPlaceRecommendationService recommendationService;

    public HomeController(MeetingPlaceRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("request", new RecommendationRequest());
        model.addAttribute("purposes", MeetingPurpose.values());
        return "index";
    }

    @PostMapping("/recommend")
    public String recommend(
            @Valid @ModelAttribute("request") RecommendationRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        model.addAttribute("purposes", MeetingPurpose.values());

        if (bindingResult.hasErrors()) {
            return "index";
        }

        RecommendationResult result = recommendationService.recommend(request);
        model.addAttribute("result", result);
        return "index";
    }
}
