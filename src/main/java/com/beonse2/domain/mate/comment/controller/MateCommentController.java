package com.beonse2.domain.mate.comment.controller;

import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.mate.comment.dto.MateCommentRequestDTO;
import com.beonse2.domain.mate.comment.dto.MateCommentResponseDTO;
import com.beonse2.domain.mate.comment.service.MateCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<MateCommentResponseDTO>> getMateCommentList(@PathVariable("mateBoard-id") Long mateBoardId) {
        return mateCommentService.findMateCommentList(mateBoardId);
    }

    @PatchMapping("/{mateComment-id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<SuccessMessageDTO> deleteMateComment(@PathVariable("mateBoard-id") Long mateBoardId,
                                                               @PathVariable("mateComment-id") Long mateCommentId,
                                                               @RequestHeader(value = "Authorization") String accessToken) {
        return mateCommentService.removeMateComment(mateBoardId, mateCommentId, accessToken);
    }

}
