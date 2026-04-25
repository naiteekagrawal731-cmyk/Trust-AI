package AIGender.bias.services;

import AIGender.bias.Clients.GeminiClient;
import AIGender.bias.dtos.RequestDtos.BiasCheckRequestDto;
import AIGender.bias.dtos.SubjectVariant;
import AIGender.bias.entities.Question;
import AIGender.bias.repos.QuestionRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class QuestionsGeneratorService {

    private final GeminiClient geminiClient;
    private final QuestionService questionService;
    private final QuestionVaiantService questionVaiantService;
    private final QuestionRepo questionRepo;

    public QuestionsGeneratorService(GeminiClient geminiClient, QuestionService questionService, QuestionVaiantService questionVaiantService, QuestionRepo questionRepo) {
        this.geminiClient = geminiClient;
        this.questionService = questionService;
        this.questionVaiantService = questionVaiantService;
        this.questionRepo = questionRepo;
    }


    public Flux<String> generateQuestionsandVarient(int numQuestions,List<String> varients){

        return Flux.range(1,numQuestions)
                .flatMap(i ->
                        geminiClient.generateGeneralQuestion()
                                .map(ques -> {
                                    for(String var : varients){
                                        String que = generateVariantQuestion(ques,var);
                                        questionVaiantService.createQuestionVarient(var,ques,que);
                                    }
                                    return "";
                                })
                        //Parallel max
                        ,3);
    }
    public Flux<String> generateQuestions(BiasCheckRequestDto biasCheckRequestDto){
        int numQuesion = biasCheckRequestDto.getNumQuestions();
        List<String> variants = biasCheckRequestDto.getVariants();
        //DO later
        List<Question> questions = questionRepo.findAll();

        return Flux.range(0,Math.min(questions.size()+1,numQuesion))
                .concatMap(i -> {
                    if(i < questions.size()){
                        Question baseQuestion = questions.get(i);
                        for(String var : variants){
                            if(!baseQuestion.getQuestionVariants().containsKey(var)){
                                String variantQuestion = generateVariantQuestion(baseQuestion.getBaseQuestion(),var);
                                questionVaiantService.createQuestionVarient(var,baseQuestion,variantQuestion);

                            }
                        }
                        return Flux.just("");
                    }else{
                        return generateQuestionsandVarient(numQuesion-questions.size(),variants);
                    }
                });

    }

    public String generateVariantQuestion(String generalQuestion, String variant) {

        Map<String, SubjectVariant> subjectVariantMap = new HashMap<>();

        subjectVariantMap.put("male", SubjectVariant.builder()
                .subject("a man")
                .pronoun("he")
                .possessive("his")
                .build());

        subjectVariantMap.put("female", SubjectVariant.builder()
                .subject("a woman")
                .pronoun("she")
                .possessive("her")
                .build());

        subjectVariantMap.put("young", SubjectVariant.builder()
                .subject("a young person")
                .pronoun("they")
                .possessive("their")
                .build());

        subjectVariantMap.put("old", SubjectVariant.builder()
                .subject("an older person")
                .pronoun("they")
                .possessive("their")
                .build());

        if (!subjectVariantMap.containsKey(variant)) {
            throw new RuntimeException("This variation does not exist "+variant);
        }

        SubjectVariant v = subjectVariantMap.get(variant);


        String result = generalQuestion
                .replace("{subject}", v.getSubject())
                .replace("{pronoun}", v.getPronoun())
                .replace("{possessive}", v.getPossessive());

        return result;
    }





}
