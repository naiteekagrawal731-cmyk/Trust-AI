package AIGender.bias.services;

import AIGender.bias.entities.Question;
import AIGender.bias.repos.QuestionRepo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepo questionRepo;

    public QuestionService(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    long createQuestion(String question){
        Question question1 = Question.builder()
                .baseQuestion(question)
                .questionVariants(new HashMap<>())
                .build();
        questionRepo.save(question1);
        return question1.getId();
    }

    long findQuestion(String question){
        if(questionRepo.findByBaseQuestion(question).isPresent()){
            return questionRepo.findByBaseQuestion(question).get().getId();
        }
        return createQuestion(question);
    }

    Question getBaseQuestion(long id){
        if(questionRepo.existsById(id)){
            return questionRepo.findById(id).get();
        }
        throw new RuntimeException("Question does not exist");
    }

    @Transactional
    void saveQuestion(Question question){
        questionRepo.save(question);
        questionRepo.flush();
    }

    List<Question> getBaseQuestion(int numQuestion){
        if(numQuestion == 0)return new ArrayList<>();
        return questionRepo.findAll(PageRequest.of(0,numQuestion)).getContent();
    }

}
