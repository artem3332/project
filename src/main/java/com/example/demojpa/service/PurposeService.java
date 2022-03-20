package com.example.demojpa.service;

import com.example.demojpa.entity.Comment;
import com.example.demojpa.entity.Purpose;
import com.example.demojpa.entity.Status;
import com.example.demojpa.exception.BusinessException;
import com.example.demojpa.exception.ErrorCode;
import com.example.demojpa.repository.CommentRepository;
import com.example.demojpa.repository.PersonRepository;
import com.example.demojpa.repository.PurposeRepository;
import com.example.demojpa.request.ChangePurposeRequest;
import com.example.demojpa.request.CommentRequest;
import com.example.demojpa.request.DeletePurposeRequest;
import com.example.demojpa.request.PurposeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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



    public void creatBotPurpose(PurposeRequest requestPurpose,Integer vkid) throws BusinessException {
        if(purposeRepository.findPurpose(requestPurpose.getPurpose(),personRepository.findPersonByVkid(vkid).get().getId()).isPresent())
        {
            throw new BusinessException(ErrorCode.PURPOSE_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        else if (!personRepository.findPersonByVkid(vkid).isPresent())
        {
            throw new BusinessException(ErrorCode.PERSON_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        else
        {
            Purpose purpose = new Purpose(requestPurpose.getPurpose(),requestPurpose.getStatus(),requestPurpose.getTime());
            purpose.setUserId(personRepository.findPersonByVkid(vkid).get().getId());
            purpose.setStatus(Status.PROCESS);


            purposeRepository.save(purpose);


        }
    }


    public void creatPurpose(PurposeRequest tas, Long userid) throws BusinessException
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
            Purpose purpose = new Purpose(tas.getPurpose(),tas.getStatus(),tas.getTime());
            purpose.setUserId(userid);
            purpose.setStatus(Status.PROCESS);
            purpose.setTime(LocalDateTime.now());
            purpose.setEmail(personRepository.getById(userid).getEmail());
            purposeRepository.save(purpose);
        }
    }

    public Purpose getPurpose(Long id) throws BusinessException
    {
        return  purposeRepository.findById(id).orElseThrow(()-> new BusinessException(ErrorCode.PURPOSE_NOT_FOUND,HttpStatus.NOT_FOUND));
    }

    public List<Purpose> allPurpose(Long id)
    {

        return personRepository.getById(id).getPurposes();
    }


    public void deleteByUserId(Long id) {
        purposeRepository.deleteByUserId(id);
    }

    @Transactional
    public void deletePurposeByName(DeletePurposeRequest deletePurposeRequest) {
        personRepository.findPersonByVkid(deletePurposeRequest.getUserId().intValue())
                .ifPresent(p -> purposeRepository.deletePurposeByName(deletePurposeRequest.getPurpose(), p.getId()));
    }

    @Transactional
    public void toChangeStatusPurpose(ChangePurposeRequest changePurposeRequest)
    {
        Optional<Purpose> purpose = purposeRepository.findPurposeByPurpose(changePurposeRequest.getPurpose());
        purpose.get().setStatus(Status.COMPLETED);
        purposeRepository.save(purpose.get());

    }

    @Transactional
    public void toChangeTimePurpose(ChangePurposeRequest changePurposeRequest)
    {
        Optional<Purpose> purpose = purposeRepository.findPurposeByPurpose(changePurposeRequest.getPurpose());
        purpose.get().setTime(purpose.get().getTime().plusMinutes(10));
        purposeRepository.save(purpose.get());

    }







    public void addComment(CommentRequest n, Long id) throws BusinessException {

        purposeRepository.findPurposeByUserId(id).ifPresentOrElse(p ->
        {
            Comment comment = new Comment(n.getComment(), LocalDateTime.now());
            Purpose purpose = p;
            purpose.getComments().add(comment);
            purposeRepository.save(purpose);
        }, () -> {
            throw new BusinessException(ErrorCode.PURPOSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        });



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

    public void toChangeComment(CommentRequest n, Long id) throws BusinessException
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

    public void addSubGoal(PurposeRequest n, Long id) throws BusinessException
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

            Purpose subPurpose = new Purpose(n.getPurpose(),n.getStatus(),n.getTime());
            subPurpose.setTime(LocalDateTime.now());
            subPurpose.setEmail(mainPurpose.getEmail());
            subPurpose.setStatus(Status.PROCESS);
            mainPurpose.getSubGoals().add(subPurpose);
            purposeRepository.save(mainPurpose);
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
            purpose.setStatus(Status.COMPLETED);
            purposeRepository.save(purpose);
        }
        else
        {
            throw new BusinessException(ErrorCode.PURPOSE_NOT_FOUND,HttpStatus.NOT_FOUND);
        }




    }


    }




