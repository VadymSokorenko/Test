package com.example.demo.util;

import java.time.LocalDateTime;
import javax.crypto.SecretKey;

import com.example.demo.dto.RequestDto;
import com.example.demo.dto.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class LoggingUtil {

    private static final String NEW_REQUEST_HEADER = "processing request:";

    private static final String ENCRYPTION_HEADER = "=== encryption: ";

    private static final String DECRYPTION_HEADER = "=== decryption: ";

    private final AesUtil aesUtil;

    public void logControllerActivity(RequestDto requestDto, ResponseDto responseDto) {
        String requestData = convertObjectToJson(requestDto);
        String responseData = convertObjectToJson(responseDto);

        //Might be better to use StringBuilder class with dedicated method to cut the number of log.info calls
        log.info("\n");
        log.info(NEW_REQUEST_HEADER);
        log.info(LocalDateTime.now());
        log.info(requestData);
        log.info(responseData);

        SecretKey secretKey = aesUtil.generateKey();

        String encryptedRequestData = aesUtil.encrypt(requestData, secretKey);
        String decryptedRequestData = aesUtil.decrypt(encryptedRequestData, secretKey);

        log.info(ENCRYPTION_HEADER + encryptedRequestData);
        log.info(DECRYPTION_HEADER + decryptedRequestData);

        String encryptedResponseData = aesUtil.encrypt(responseData, secretKey);
        String decryptedResponseData = aesUtil.decrypt(encryptedResponseData, secretKey);

        log.info(ENCRYPTION_HEADER + encryptedResponseData);
        log.info(DECRYPTION_HEADER + decryptedResponseData);
    }

    private String convertObjectToJson(Object source) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
