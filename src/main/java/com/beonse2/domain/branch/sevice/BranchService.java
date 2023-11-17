package com.beonse2.domain.branch.sevice;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.exception.ErrorCode;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.domain.branch.dto.BranchListDTO;
import com.beonse2.domain.branch.dto.BranchRequestDTO;
import com.beonse2.domain.branch.mapper.BranchMapper;
import com.beonse2.domain.branch.vo.Branch;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import com.beonse2.domain.member.vo.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.beonse2.config.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BranchService {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final JwtUtil jwtUtil;

    private final BranchMapper branchMapper;

    private final MemberMapper memberMapper;

    public boolean save(BranchRequestDTO branchRequestDTO) {
        // 가입된 유저인지 확인
        if (memberMapper.findByEmail(branchRequestDTO.getEmail()).isPresent()) {
            System.out.println("이미 가입된 회원입니다");
            throw new RuntimeException("이미 가입된 회원입니다");
        }

        //멤버 빌드 저장 후 메퍼에 저장
        MemberDTO member = MemberDTO.builder()
                .email(branchRequestDTO.getEmail())
                .password(passwordEncoder.encode(branchRequestDTO.getPassword()))
                .name(branchRequestDTO.getName())
                .address(branchRequestDTO.getAddress())
                .build();

        memberMapper.saveBranch(member);

        //response로 멤버 아이디  findbyEmail찾음

        MemberDTO findMember = memberMapper.findByEmail(branchRequestDTO.getEmail()).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        //가맹점 빌드 저장
        Branch branch = Branch.builder()
                .memberId(findMember.getMid())
                .name(branchRequestDTO.getBranchName())
                .address(branchRequestDTO.getAddress())
                .introduction(branchRequestDTO.getIntroduction())
                .build();

        //가맹점 매퍼 저장
        branchMapper.save(branch);

        return memberMapper.findByEmail(member.getEmail()).isPresent();
    }

    public ResponseEntity<List<String>> findBranchNames() {

        List<String> branchNames = branchMapper.findAllBranchName();

        if (branchNames.isEmpty()) {
            throw new CustomException(NOT_FOUND_BRANCH_NAMES);
        }

        return ResponseEntity.ok(branchNames);
    }

    public ResponseEntity<List<BranchListDTO>> findByAllBranch (Role role) {

        List<BranchListDTO> branches = branchMapper.findByAllBranch(role);

        if(branches.isEmpty()) {
            throw new CustomException(NOT_FOUND_BRANCH);
        }

        return ResponseEntity.ok(branches);
    }
}
