package com.daim.assignment.v1.controllers;

import com.daim.assignment.domain.Diff;
import com.daim.assignment.services.DiffService;
import com.daim.assignment.services.exceptions.DiffNotFoundException;
import com.daim.assignment.v1.controllers.models.DiffRequest;
import com.daim.assignment.v1.controllers.models.DiffResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/diff")
public class DiffController {

    private final DiffService diffService;

    public DiffController(DiffService diffService) {
        this.diffService = diffService;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/left")
    public ResponseEntity<?> putLeft(@PathVariable("id") Long id, @RequestBody DiffRequest diffRequest) {

        Diff diff = new Diff(id, diffRequest.getData(), null);

        boolean created = diffService.saveDiff(diff);

        HttpStatus responseStatus = HttpStatus.NO_CONTENT;

        if (created) {
            responseStatus = HttpStatus.CREATED;
        }

        return new ResponseEntity<>(responseStatus);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}/right")
    public ResponseEntity<?> putRight(@PathVariable("id") Long id, @RequestBody DiffRequest diffRequest) {

        Diff diff = new Diff(id, null, diffRequest.getData());

        boolean created = diffService.saveDiff(diff);

        HttpStatus responseStatus = HttpStatus.NO_CONTENT;

        if (created) {
            responseStatus = HttpStatus.CREATED;
        }

        return new ResponseEntity<>(responseStatus);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<DiffResponse> getDiff(@PathVariable("id") Long diffId) {

        try {
            Diff diff = diffService.getDiff(diffId);
            DiffResponse diffResponse = new DiffResponse(diff);

            return new ResponseEntity<>(diffResponse, HttpStatus.OK);
        } catch (DiffNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
