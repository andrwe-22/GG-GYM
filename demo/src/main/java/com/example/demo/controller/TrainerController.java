package com.example.demo.controller;

import com.example.demo.dto.MemberDTO;
import com.example.demo.model.Member;
import com.example.demo.model.Trainer;
import com.example.demo.services.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

//    @GetMapping
//    public ResponseEntity<List<Trainer>> getAllTrainers() {
//        List<Trainer> trainers = trainerService.getAllTrainers();
//        return ResponseEntity.ok(trainers);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Trainer> getTrainerById(@PathVariable Long id) {
        Optional<Trainer> trainer = trainerService.getTrainerById(id);
        return trainer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Trainer> createTrainer(@RequestBody Trainer trainer) {
        try {
            Trainer savedTrainer = trainerService.saveTrainer(trainer);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTrainer);
        } catch (Exception e) {
            System.out.println("Failed to create trainer: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/all")
    public ResponseEntity<List<Trainer>> getAllTrainers() {
        List<Trainer> trainers = trainerService.getAllTrainers();
        return ResponseEntity.ok(trainers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trainer> updateTrainer(@PathVariable Long id, @RequestBody Trainer updatedTrainer) {
        return trainerService.getTrainerById(id)
                .map(trainer -> {
                    trainer.setName(updatedTrainer.getName());
                    Trainer savedTrainer = trainerService.saveTrainer(trainer);
                    return ResponseEntity.ok(savedTrainer);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
