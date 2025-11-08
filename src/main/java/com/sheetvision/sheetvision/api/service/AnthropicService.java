package com.sheetvision.sheetvision.api.service;

import com.anthropic.models.messages.MessageCreateParams;
import com.sheetvision.sheetvision.api.model.InsightsRequestDTO;
import com.sheetvision.sheetvision.api.model.InsightsResponseDTO;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.anthropic.client.AnthropicClient;
import com.anthropic.models.messages.Model;
import com.anthropic.models.messages.Message;
import java.util.List;




@Service
public class AnthropicService {

    private final AnthropicClient client;
    private final ObjectMapper objectMapper;

    public AnthropicService(AnthropicClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public InsightsResponseDTO generateInsights(InsightsRequestDTO request) {

        try {

            Resource resource = new ClassPathResource("prompts/insights-prompt.txt");
            String template = new String(resource.getInputStream().readAllBytes());

            ObjectMapper mapper = new ObjectMapper();
            String rowsJson = mapper.writeValueAsString(request.rows());
            String sampleRowsJson = mapper.writeValueAsString(request.sampleRows());
            String columnsJson = mapper.writeValueAsString(request.columns());

            String prompt = template
                    .replace("{filename}", request.fileName())
                    .replace("{sheetName}", request.sheetName())
                    .replace("{columns}", columnsJson)
                    .replace("{rowCount}", request.rowCount().toString())
                    .replace("{rows}", rowsJson)
                    .replace("{sampleRows}", sampleRowsJson);

            MessageCreateParams params = MessageCreateParams.builder().maxTokens(2048L).addUserMessage(prompt).model(Model.CLAUDE_SONNET_4_20250514).build();
            Message message = this.client.messages().create(params);
            String responseText = message.content().get(0).asText().text();
            InsightsData insights = this.objectMapper.readValue(responseText, InsightsData.class);

            return new InsightsResponseDTO(
                    true,
                    null,
                    insights.trends(),
                    insights.anomalies(),
                    insights.recommendations()
            );

        } catch (Exception e) {
            return new InsightsResponseDTO(false, e.getMessage(), null, null, null);
        }

    }

    private record InsightsData(
            String trends,
            List<String> anomalies,
            String recommendations
    ) {}


}
