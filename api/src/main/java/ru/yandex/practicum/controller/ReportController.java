package ru.yandex.practicum.controller;

import java.security.Principal;
import java.util.UUID;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.model.Report;

@RestController
@CrossOrigin
public class ReportController {

    @GetMapping("/reports")
    Report getReposts(Principal principal) {
        Report report = new Report();
        report.setUsername(principal.getName());
        report.setData(UUID.randomUUID().toString());

        return report;
    }
}
