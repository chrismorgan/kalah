package com.morgan.kalah.controllers;

import com.morgan.kalah.api.GamesApiController;
import com.morgan.kalah.model.GameDescriptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

@Controller
@RequestMapping("${openapi.Kalah.base-path:/v1}")
public class KalahController extends GamesApiController {

    public KalahController(NativeWebRequest request) {
        super(request);
    }

    @Override
    public ResponseEntity<GameDescriptor> createGame(){
        GameDescriptor gameDescriptor = new GameDescriptor().id("123").uri("https://localhost:8080/v1/123");
        return new ResponseEntity<>(gameDescriptor, HttpStatus.CREATED);
    }
}
