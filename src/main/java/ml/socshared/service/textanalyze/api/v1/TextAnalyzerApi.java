package ml.socshared.service.textanalyze.api.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ml.socshared.service.textanalyze.domain.object.KeyWord;
import ml.socshared.service.textanalyze.domain.object.TargetPhrase;
import ml.socshared.service.textanalyze.domain.request.TextRequest;

import java.util.List;
import java.util.UUID;

public interface TextAnalyzerApi {
    @ApiOperation(value = "Extracting key words from text by RAKE")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success extract key words"),
            @ApiResponse(code = 500, message = "Error retrieving keywords")
    })
    List<KeyWord> extractKeyWords(TextRequest text, Integer minLength, Integer maxLength);

    @ApiOperation(value = "Extracting target phrases (Phrases which user defined)")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Success extracted target phrases"),
            @ApiResponse(code = 428, message = "you must first set the target phrases")
    })
    List<TargetPhrase> extractTargetPhrase(UUID systemUserId, String text);

    @ApiOperation(value = "Set target phrases for search in texts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful set target phrases")
    })
    void setTargetPhrase(UUID systemUserId, List<String> phrases);

    @ApiOperation(value = "Get installed key phrases")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "return list of target phrases for defined user"),
            @ApiResponse(code = 404, message = "for defined user don't installed target phrases")
    })
    List<String> getTargetPhrases(UUID systemUserId);

}
