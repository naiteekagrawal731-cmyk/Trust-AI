package AIGender.bias.services;

import AIGender.bias.dtos.ResponseDtos.VariantQuestionDto;
import AIGender.bias.entities.AIModel;
import AIGender.bias.entities.Question;
import AIGender.bias.entities.QuestionVariant;
import AIGender.bias.repos.QuestionVariantRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class QuestionVaiantService {

    private final QuestionVariantRepo questionVariantRepo;
    private final QuestionService questionService;

    public QuestionVaiantService(QuestionVariantRepo questionVariantRepo, QuestionService questionService) {
        this.questionVariantRepo = questionVariantRepo;
        this.questionService = questionService;
    }
    @Transactional
    public void createQuestionVarient(
            String variant,
            String baseQuestion,
            String variantQuestion
    ) {
        long baseQuestionId = questionService.findQuestion(baseQuestion);
        Question baseQuestionEntity = questionService.getBaseQuestion(baseQuestionId);

        QuestionVariant questionVariant = addQuestionVarient(variant,variantQuestion);


        baseQuestionEntity.getQuestionVariants().put(variant,questionVariant);

        questionService.saveQuestion(baseQuestionEntity);

        questionVariantRepo.flush();
    }
    @Transactional
    public void createQuestionVarient(
            String variant,
            Question baseQuestion,
            String variantQuestion
    ) {



        QuestionVariant questionVariant = addQuestionVarient(variant,variantQuestion);


        baseQuestion.getQuestionVariants().put(variant,questionVariant);

        questionService.saveQuestion(baseQuestion);

        questionVariantRepo.flush();
    }

    QuestionVariant addQuestionVarient(String varient,String question){
        QuestionVariant questionVariant = QuestionVariant.builder()
                .question(question)
                .variantType(varient)
                .build();
        questionVariantRepo.save(questionVariant);
        return questionVariant;
    }

    public List<VariantQuestionDto> getVariantsQuestion(AIModel aiModel){

        List<Question> baseQuestions = questionService.getBaseQuestion(aiModel.getNumQuestions());

        List<VariantQuestionDto> variantQuestionDtos = new ArrayList<>();
        for(Question question : baseQuestions){
            for(String varient : aiModel.getVariants()){
                if(question.getQuestionVariants().containsKey(varient)){
                    QuestionVariant questionVariant = question.getQuestionVariants().get(varient);
                    variantQuestionDtos.add(VariantQuestionDto.builder()
                                    .generalQuestionId(question.getId())
                                    .questionId(questionVariant.getId())
                                    .question(questionVariant.getQuestion())
                            .build());
                }
            }
        }

        return variantQuestionDtos;
    }

    QuestionVariant getQuestionVariant(long id){
        Optional<QuestionVariant> questionVariant = questionVariantRepo.findById(id);
        if(questionVariant.isPresent()){
            return questionVariant.get();
        }
        throw new RuntimeException("Invalid question variant id");
    }




}
