package com.example.demojpa.controller;


import com.example.demojpa.entity.Comment;
import com.example.demojpa.entity.Purpose;
import com.example.demojpa.exception.BusinessException;
import com.example.demojpa.request.CreateRequestComment;
import com.example.demojpa.request.CreateRequestPurpose;
import com.example.demojpa.service.DefaultEmailService;
import com.example.demojpa.service.PurposeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/purpose")
public class PurposeController {

    @Autowired
    private PurposeService purposeService;

    @PostMapping("/create")
    public ResponseEntity<?> createPurpose(@RequestBody CreateRequestPurpose Purpose, @RequestParam Long userid) throws BusinessException
    {
        log.info("Create purpose");
        purposeService.creatPurpose(Purpose, userid);
        return ResponseEntity.ok("Цель успешно создана!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purpose> getPurpose(@PathVariable Long id) throws BusinessException
    {
        log.info("Get purpose id");
        return ResponseEntity.ok(purposeService.getPurpose(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<Purpose>> allPurpose()
    {
        log.info("All purposes");
        return ResponseEntity.ok(purposeService.allPurpose());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePurpose(@PathVariable Long id) throws BusinessException
    {
        log.info("Delete purpose");
        purposeService.deletePurpose(id);
        return ResponseEntity.ok("Цель успешно удалена!");
    }

    @PostMapping("/comment/create")
    public ResponseEntity<?> addComment(@RequestBody CreateRequestComment comment,@RequestParam Long purposeid) throws BusinessException
    {
        log.info("Add comment");
        purposeService.addComment(comment,purposeid);
        return ResponseEntity.ok("Комментарий успешно добавлен!");
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<Comment> getcomment(@PathVariable Long id) throws BusinessException
    {
        log.info("Get Comment id");
        return ResponseEntity.ok(purposeService.getComment(id));
    }

    @DeleteMapping("/comment/deletecomment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) throws BusinessException
    {
        log.info("Delete comment");
        purposeService.deleteComment(id);
        return ResponseEntity.ok("Комментарий успешно удалён!");
    }

    @PostMapping("/comment/change/{id}")
    public ResponseEntity<?> toChangeComment(@RequestBody CreateRequestComment comment,@PathVariable Long id) throws BusinessException
    {
        log.info("To change comment");
        purposeService.toChangeComment(comment,id);
        return ResponseEntity.ok("Комментарий успешно изменён!");
    }

    @PostMapping("/subgoals/create")
    public ResponseEntity<?> addSubGoal(@RequestBody CreateRequestPurpose subgoal,@RequestParam Long id) throws BusinessException
    {
        log.info("Add sub-goal");
        purposeService.addSubGoal(subgoal,id);
        return ResponseEntity.ok("Подзадача успешно добавлена!");
    }

    @PostMapping("/subgoal/change/{id}")
    public ResponseEntity<?> toChangeSubGoal(@PathVariable Long id) throws BusinessException
    {
        log.info("To change status sub_goal");
        purposeService.toChangeStatusSubGoal(id);
        return ResponseEntity.ok("Задача успешно выполнена");
    }







// валидация , List запросов  , планировщик по времени .



}
