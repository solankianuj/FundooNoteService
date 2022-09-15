package com.example.fundoonotemicroservice.config;

import com.example.fundoonotemicroservice.model.NotesModel;
import com.example.fundoonotemicroservice.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * purpose-creating rest template bean.
 */

@Component
@EnableScheduling
public class NoteConfig {

    @Autowired
    NoteRepository noteRepository;
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }


//    @Scheduled(fixedDelay = 1000)
//    public void scheduleFixedDelayTask() {
//        List<NotesModel> notesModelList = noteRepository.findAll();
//        for (NotesModel notesModel:notesModelList
//             ) {
//                  String  time="2022-09-15T09:13";
//            if (notesModel.getReminderTime().equals(time)){
//                System.out.println(notesModel.getReminderTime());
//            }
//        }
//
//        System.out.println(
//                "Fixed delay task - " + System.currentTimeMillis() / 1000);
//    }

}
