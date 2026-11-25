

package com.example.registration.controller;

import com.example.registration.model.Student;
import com.example.registration.service.RegistrationService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    private final RegistrationService service;

    public MainController(RegistrationService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        Student student = service.login(email, password);

        if (student == null) {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }

        session.setAttribute("student", student);

        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        Student student = (Student) session.getAttribute("student");

        if (student == null) {
            return "redirect:/";
        }

        model.addAttribute("student", student);

        return "dashboard";
    }

    @GetMapping("/courses")
    public String courses(HttpSession session, Model model) {

        Student student = (Student) session.getAttribute("student");

        if (student == null) {
            return "redirect:/";
        }

        model.addAttribute("courses", service.getAllCourses());

        return "courses";
    }

    @PostMapping("/register/{id}")
    public String register(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        Student student = (Student) session.getAttribute("student");

        if (student == null) {
            return "redirect:/";
        }

        boolean success = service.register(student, id);

        if (success) {
            return "redirect:/my-courses?success";
        }

        return "redirect:/courses?error";
    }

    @GetMapping("/my-courses")
    public String myCourses(HttpSession session, Model model) {

        Student student = (Student) session.getAttribute("student");

        if (student == null) {
            return "redirect:/";
        }

        model.addAttribute(
                "courses",
                service.getStudentCourses(student)
        );

        return "my-courses";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
