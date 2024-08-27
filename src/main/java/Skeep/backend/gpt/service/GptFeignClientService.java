package Skeep.backend.gpt.service;

import Skeep.backend.gpt.service.dto.ChatGptRequest;
import Skeep.backend.gpt.service.dto.ChatGptResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "gptFeignClientService", url = "https://gpt.sodong.site/api/gpt")
public interface GptFeignClientService {
    @PostMapping("/chatgpt")
    ChatGptResponse sendRequest(@RequestBody ChatGptRequest request);
}