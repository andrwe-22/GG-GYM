package com.example.demo.controller;

import com.example.demo.model.Faculty;
import com.example.demo.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculties")
public class FacultyController {

    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Faculty>> getAllFaculties() {
        List<Faculty> faculties = facultyService.getAllFaculties();
        return ResponseEntity.ok(faculties);
    }

    @PostMapping("/")
    public ResponseEntity<Faculty> createOrUpdateFaculty(@RequestBody Faculty faculty) {
        Faculty savedFaculty = facultyService.saveOrUpdate(faculty);
        return ResponseEntity.ok(savedFaculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);  // Ensure the service method correctly handles the deletion.
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestBody Faculty facultyDetails) {
        return facultyService.getFacultyById(id)
                .map(faculty -> {
                    faculty.setName(facultyDetails.getName());
                    // Update other fields as needed
                    Faculty updatedFaculty = facultyService.saveOrUpdate(faculty);
                    return ResponseEntity.ok(updatedFaculty);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
