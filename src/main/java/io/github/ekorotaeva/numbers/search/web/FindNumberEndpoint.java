package io.github.ekorotaeva.numbers.search.web;


import io.github.ekorotaeva.numbers.search.domain.FindNumberRequest;
import io.github.ekorotaeva.numbers.search.domain.FindNumberResponse;
import io.github.ekorotaeva.numbers.search.domain.Result;
import io.github.ekorotaeva.numbers.search.service.NumberSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@Endpoint
public class FindNumberEndpoint {

    private NumberSearchService numberSearchService;

    public FindNumberEndpoint(NumberSearchService numberSearchService) {
        this.numberSearchService = numberSearchService;
    }

    @PayloadRoot(namespace = WebServiceConfiguration.NAMESPACE_URI, localPart = "FindNumberRequest")
    @ResponsePayload
    public FindNumberResponse findNumber(@RequestPayload FindNumberRequest request) {
        log.debug("FindNumberRequest: " + request.toString());

        String resultCode = "02.Result.Error";
        Result result= new Result();
        try {
            int number = Integer.valueOf(request.getNumber());
            result.setNumber(number);

            resultCode = numberSearchService.search(number)
                    ? "00.Result.OK"
                    : "01.Result.NotFound";

            log.info(String.format("Search Result Code: %s", resultCode));
        }
        catch (Exception ex) {
            String message = String.format("Exception %s has been thrown at %s", ex, Arrays.stream(ex.getStackTrace()).map(Object::toString).collect(Collectors.joining(" at \n")));
            log.error(message);
            result.setError(message);
        }

        result.setCode(resultCode);
        FindNumberResponse response = new FindNumberResponse();
        response.setResult(result);
        return response;
    }
}
