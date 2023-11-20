package com.beonse2.domain.branch.sevice;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.branch.dto.BranchDTO;
import com.beonse2.domain.branch.dto.BranchListDTO;
import com.beonse2.domain.branch.dto.BranchRequestDTO;
import com.beonse2.domain.branch.dto.ImageDTO;
import com.beonse2.domain.branch.mapper.BranchMapper;
import com.beonse2.domain.branch.vo.Branch;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public SuccessMessageDTO createBranch(BranchRequestDTO branchRequestDTO) {
        // 가입된 유저인지 확인
        if (memberMapper.findByEmail(branchRequestDTO.getEmail()).isPresent()) {
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
                .lat(branchRequestDTO.getLat())
                .lng(branchRequestDTO.getLng())
                .introduction(branchRequestDTO.getIntroduction())
                .build();

        //가맹점 매퍼 저장
        branchMapper.save(branch);

        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.CREATED.value())
                .successMessage("가맹점 회원 가입 완료")
                .build();
    }

    public List<String> findBranchNames() {

        List<String> branchNames = branchMapper.findAllBranchName();

        if (branchNames.isEmpty()) {
            throw new CustomException(NOT_FOUND_BRANCH_NAMES);
        }

        return branchNames;
    }

    public List<BranchListDTO> findByAllBranch() {

        List<BranchListDTO> branches = branchMapper.findByAllBranch();

        if (branches.isEmpty()) {
            throw new CustomException(NOT_FOUND_BRANCH);
        }

        return branches;
    }

    public List<BranchListDTO> findBranchSearch(String name) {

        List<BranchListDTO> branches = branchMapper.searchBranches(name);

        if (branches.isEmpty()) {
            throw new CustomException(NOT_FOUND_BRANCH);
        }
        return branches;
    }

    @Transactional
    public SuccessMessageDTO updateBranch(String accessToken,
                                          List<MultipartFile> images,
                                          BranchRequestDTO branchRequestDTO) throws IOException {
        /*비밀번호, 지점사 소개, 소개이미지, 주소, 지점사명*/
        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        BranchRequestDTO findBranch = branchMapper.findByMemberId(findMember.getMid()).orElseThrow(
                () -> new CustomException(NOT_FOUND_BRANCH)
        );

        Long mid = findMember.getMid();

        if (!images.isEmpty()) {
            for (MultipartFile image : images) {
                ImageDTO imageDTO = ImageDTO.builder()
                        .branchBid(findBranch.getBid())
                        .image(image)
                        .imageData(image.getBytes())
                        .originalFileName(image.getOriginalFilename())
                        .imageType(image.getContentType())
                        .build();

                branchMapper.saveImage(imageDTO);
            }
        }

        String email = findMember.getEmail();
        String password;
        if (branchRequestDTO.getPassword().isEmpty()) {
            password = findMember.getPassword();
        } else {
            password = passwordEncoder.encode(branchRequestDTO.getPassword());
        }

        Branch branch;

        if (email.equals(findBranch.getEmail())) {

            branch = Branch.builder()
                    .bid(findBranch.getBid())
                    .memberId(mid)
                    .branchName(branchRequestDTO.getBranchName())
                    .address(branchRequestDTO.getAddress())
                    .password(password)
                    .introduction(branchRequestDTO.getIntroduction())
                    .build();
        } else {
            throw new CustomException(NOT_MATCH_USER);
        }

        memberMapper.updateBranchInfo(branch);
        branchMapper.updateBranchInfo(branch);

        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("정보 수정 완료.")
                .build();
    }

    public BranchDTO findBranch(Long branchId) {

        BranchDTO branchDTO = branchMapper.findById(branchId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BRANCH)
        );

        List<ImageDTO> imageDTO = branchMapper.findImage(branchId);

        if (imageDTO.isEmpty()) {
            throw new CustomException(NOT_FOUND_IMAGE);
        }

        return BranchDTO.builder()
                .bId(branchDTO.getBId())
                .memberMid(branchDTO.getMemberMid())
                .email(branchDTO.getEmail())
                .imageDTOS(imageDTO)
                .branchName(branchDTO.getBranchName())
                .isApproval(branchDTO.getIsApproval())
                .introduction(branchDTO.getIntroduction())
                .build();
    }
}
