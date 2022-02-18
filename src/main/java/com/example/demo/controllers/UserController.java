package com.example.demo.controllers;

import com.example.demo.dto.RequestDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.service.UserService;
import com.example.demo.util.LoggingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final LoggingUtil loggingUtil;

    @PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto getUserById(@RequestBody RequestDto requestDto) {

        //Remove condition when ready to work with DB.
        if (!(requestDto.getId() == 1)) {
            return null;
        }

        String fio = userService.getUserById(requestDto.getId());
        ResponseDto responseDto = convertUserFioToResponseDto(fio);

        loggingUtil.logControllerActivity(requestDto, responseDto);

        return responseDto;
    }

    private ResponseDto convertUserFioToResponseDto(String fio) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setFio(fio);

        return responseDto;
    }

}
