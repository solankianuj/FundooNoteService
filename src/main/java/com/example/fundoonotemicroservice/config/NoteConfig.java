package com.example.fundoonotemicroservice.config;

import com.example.fundoonotemicroservice.model.NotesModel;
import com.example.fundoonotemicroservice.repository.NoteRepository;
import com.example.fundoonotemicroservice.service.mailService.MailServices;
import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

/**
 * purpose-creating rest template bean.
 */

@Component
@EnableScheduling
public class NoteConfig {

    @Autowired
    NoteRepository noteRepository;
    @Autowired
    MailServices mailServices;
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }


//    @Scheduled(fixedDelay = 1000)
//    public void scheduleFixedDelayTask() throws LazyInitializationException {
//        List<NotesModel> notesModelList = noteRepository.findAll();
//
//        for (NotesModel notesModel:notesModelList
//             ) {
//            if (notesModel.getReminderTime().equals(LocalDate.now())) {
//                System.out.println("Email sent successfully ...");
//                mailServices.send(notesModel.getEmailId(), "Reminder ...","You have reminder today for the NoteId:"+notesModel.getNoteId());
//            }
//        }
//
//    }

}
