package com.beonse2.domain.mate.comment.controller;

import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.mate.comment.dto.MateCommentRequestDTO;
import com.beonse2.domain.mate.comment.service.MateCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mates/{mateBoard-id}/comments")
public class MateCommentController {

    private final MateCommentService mateCommentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<SuccessMessageDTO> postMateComment(@PathVariable("mateBoard-id") Long mateBoardId,
                                                             @RequestHeader(value = "Authorization") String accessToken,
                                                             @RequestBody MateCommentRequestDTO mateCommentRequestDTO) {
        return mateCommentService.createMateComment(mateBoardId, accessToken, mateCommentRequestDTO);
    }
}
