package com.daim.assignment.v1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class DiffController {

    public ResponseEntity<?> putLeft() {

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> putRight() {

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> getDiff(Long diffId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
