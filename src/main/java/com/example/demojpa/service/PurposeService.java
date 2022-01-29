package com.example.demojpa.service;

import com.example.demojpa.entity.Comment;
import com.example.demojpa.entity.Purpose;
import com.example.demojpa.exception.*;
import com.example.demojpa.repository.CommentRepository;
import com.example.demojpa.repository.PersonRepository;
import com.example.demojpa.repository.PurposeRepository;
import com.example.demojpa.request.CreateRequestComment;
import com.example.demojpa.request.CreateRequestPurpose;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PurposeService {

    @Autowired
    private PurposeRepository purposeRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EmailService emailService;

    public void creatPurpose(CreateRequestPurpose tas, Long userid) throws BusinessException
    {
        if(purposeRepository.findPurpose(tas.getPurpose(),userid).isPresent())
        {
            throw new BusinessException(ErrorCode.PURPOSE_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        else if (!personRepository.findById(userid).isPresent())
        {
            throw new BusinessException(ErrorCode.PERSON_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        else
        {
            Purpose purpose = new Purpose(tas.getPurpose(),tas.getImportance(), tas.getDays());
            purpose.setUserId(userid);
            purpose.setStatus(Purpose.Status.PROCESS);
            purpose.setTime(LocalDateTime.now());
            purpose.setEmail(personRepository.getById(userid).getEmail());
            purposeRepository.save(purpose);
            emailService.
                    sendSimpleEmail(personRepository.getById(userid).getEmail(),
                            "Purpose", "Цель успешно "+purpose.getPurpose() +" поставлена," +
                                    "на её выполнение вам"+purpose.getDays()+"дня");
        }
    }

    public Purpose getPurpose(Long id) throws BusinessException
    {
        return  purposeRepository.findById(id).orElseThrow(()-> new BusinessException(ErrorCode.PURPOSE_NOT_FOUND,HttpStatus.NOT_FOUND));
    }

    public List<Purpose> allPurpose()
    {
       return purposeRepository.findAll();
    }

    public void deletePurpose(Long id) throws BusinessException
    {
        if(purposeRepository.existsById(id))
        {
            purposeRepository.deleteById(id);
        }
        else
        {
            throw  new BusinessException(ErrorCode.PURPOSE_NOT_FOUND,HttpStatus.NOT_FOUND);
        }
    }

    public void addComment(CreateRequestComment n, Long id) throws BusinessException
    {

        if(!purposeRepository.findById(id).isPresent())
        {
            throw new BusinessException(ErrorCode.PURPOSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        else
        {
            Comment comment=new Comment(n.getComment(),LocalDateTime.now());
            Purpose purpose= purposeRepository.findById(id).get();
            purpose.getComments().add(comment);
            purposeRepository.save(purpose);
        }
    }

    public Comment getComment(Long id) throws BusinessException
    {
        return  commentRepository.findById(id).orElseThrow(()-> new BusinessException(ErrorCode.COMMENT_NOT_FOUND,HttpStatus.NOT_FOUND));
    }

    public void deleteComment(Long id) throws BusinessException
    {
        if(commentRepository.existsById(id))
        {
            commentRepository.deleteById(id);
        }
        else
        {
            throw  new BusinessException(ErrorCode.COMMENT_NOT_FOUND,HttpStatus.NOT_FOUND);
        }

    }

    public void toChangeComment(CreateRequestComment n,Long id) throws BusinessException
    {
       if(commentRepository.existsById(id))
       {
           Comment comment =commentRepository.getById(id);
           comment.setComment(n.getComment());
           commentRepository.save(comment);
       }
       else
       {
           throw new BusinessException(ErrorCode.COMMENT_NOT_FOUND,HttpStatus.NOT_FOUND);
       }
    }

    public void addSubGoal(CreateRequestPurpose n, Long id) throws BusinessException
    {
        Optional<Purpose> optional = purposeRepository.findById(id);


        if(optional.isPresent())
        {

            Purpose mainPurpose = optional.get();
            boolean match = mainPurpose.getSubGoals().stream().anyMatch(subGoal -> subGoal.getPurpose().equals(n.getPurpose()));
            if (match)
            {
                throw  new BusinessException(ErrorCode.SUBGOAL_ALREADY_EXISTS,HttpStatus.BAD_REQUEST);
            }

            Purpose subPurpose = new Purpose(n.getPurpose(),n.getImportance(),n.getDays());
            subPurpose.setTime(LocalDateTime.now());
            subPurpose.setEmail(mainPurpose.getEmail());
            subPurpose.setStatus(Purpose.Status.PROCESS);
            mainPurpose.getSubGoals().add(subPurpose);
            purposeRepository.save(mainPurpose);
            emailService.sendSimpleEmail(subPurpose.getEmail(),
                            "Sub goal", "Подзадача успешно "+subPurpose.getPurpose() +" поставлена," +
                                    "на её выполнение вам"+subPurpose.getDays()+"дня");
        }

        else
        {
            throw new BusinessException(ErrorCode.PURPOSE_NOT_FOUND,HttpStatus.NOT_FOUND);
        }
    }

    public void toChangeStatusSubGoal(Long id) throws BusinessException
    {
        if(purposeRepository.existsById(id))
        {
            Purpose purpose = purposeRepository.getById(id);
            purpose.setStatus(Purpose.Status.COMPLETED);
            purposeRepository.save(purpose);
            emailService.sendSimpleEmail(purposeRepository.getById(id).getEmail(), "Completed", "Задача "
                    + purposeRepository.getById(id).getPurpose() + " выполнена!");
        }
        else
        {
            throw new BusinessException(ErrorCode.PURPOSE_NOT_FOUND,HttpStatus.NOT_FOUND);
        }



    }


    }




